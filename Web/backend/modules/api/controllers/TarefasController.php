<?php

namespace backend\modules\api\controllers;

use BadMethodCallException;
use Bluerhinos\phpMQTT;
use common\models\Tarefa;
use common\models\User;
use Yii;
use yii\filters\auth\HttpBasicAuth;
use yii\rest\ActiveController;

class TarefasController extends ActiveController
{
    public $modelClass = 'common\models\Tarefa';

    public function behaviors()
    {
        $behaviors = parent::behaviors();

        $behaviors['authenticator'] = [
            'class' => HttpBasicAuth::className(),
            'except' => [
                'index',
            ],

            'auth' => [$this, 'authintercept']
        ];

        $behaviors['verbs'] = [
            'class' => \yii\filters\VerbFilter::class,
            'actions' => [
                'index' => ['GET'],
                'create' => ['POST'],
                'update' => ['PUT'],
                'delete' => ['DELETE'],
            ],
        ];

        return $behaviors;
    }

    public function authintercept($username, $password)
    {
        $user = User::findByUsername($username);

        if ($user && $user->validatePassword($password)) {
            $this->user = $user; //Guardar user autenticado
            return $user;
        }
        // error code 403
        throw new \yii\web\ForbiddenHttpException('Unauthorized', 403);
    }

    public function actionIndex()
    {
        return Tarefa::find()->all();
    }


    public function actionCreate()
    {
        //garantir que é um pedido POST
        if (!Yii::$app->request->isPost) {
            throw new BadMethodCallException('Method Not Allowed', 405);
        }
        // 'clientId' => 'Client ID',
        // 'descricao' => 'Descricao',
        // 'feito' => 'Feito',

        $receivedData =  Yii::$app->request->post();

        $tarefa = new Tarefa();
        $tarefa->clientId = $receivedData['clientId'];
        $tarefa->descricao = $receivedData['descricao'];
        $tarefa->feito = $receivedData['feito'];

        if ($tarefa->validate() && $tarefa->save()) {
            return [
                'status' => true,
                'data' => 'Tarefa criada com sucesso',
                'tarefa' => $tarefa
            ];
        } else {
            return [
                'status' => false,
                'data' => 'Erro ao criar tarefa',
                'errors' => $tarefa->errors
            ];
        }
    }

    public function actionUpdate($id)
    {
        //garantir que é um pedido PUT
        if (!Yii::$app->request->isPut) {
            throw new BadMethodCallException('Method Not Allowed', 405);
        }

        $receivedData =  Yii::$app->request->post();

        $tarefa = Tarefa::findOne($id);

        $tarefa->clientId = $receivedData['clientId'] ?? $tarefa->clientId;
        $tarefa->descricao = $receivedData['descricao']  ?? $tarefa->descricao;
        $tarefa->feito = $receivedData['feito'] ?? $tarefa->feito;

        if ($tarefa->validate() && $tarefa->save()) {
            return [
                'status' => true,
                'data' => 'Tarefa atualizada com sucesso',
                'tarefa' => $tarefa
            ];
        } else {
            return [
                'status' => false,
                'data' => 'Erro ao atualizar tarefa',
                'errors' => $tarefa->errors
            ];
        }
    }

    public function actionDelete($id)
    {
        //garantir que é um pedido DELETE
        if (!Yii::$app->request->isDelete) {
            throw new BadMethodCallException('Method Not Allowed', 405);
        }

        $tarefa = Tarefa::findOne($id);

        if ($tarefa->delete()) {
            return [
                'status' => true,
                'data' => 'Tarefa eliminada com sucesso',
            ];
        } else {
            return [
                'status' => false,
                'data' => 'Erro ao eliminar tarefa',
                'errors' => $tarefa->errors
            ];
        }
    }

    public static function publishToMosquitto($topic, $message)
    {
        $server = "127.0.0.1"; // AWS IP address
        $port = 1883;
        $username = ""; // set your username if needed
        $password = ""; // set your password if needed
        $client_id = "phpMQTT-publisher-" . uniqid();

        $mqtt = new phpMQTT($server, $port, $client_id);

        if ($mqtt->connect(true, NULL, $username, $password)) {
            $mqtt->publish($topic, $message, 0);
            $mqtt->close();
        } else {
            file_put_contents("debug.output", "Time out!");
        }
    }

    public function afterAction($action, $result)
    {
        $result = parent::afterAction($action, $result);

        if ($action->id == 'create' || $action->id == 'update') {
            TarefasController::publishToMosquitto("TAREFA:CREATE", json_encode($result));
        }
        // Ensure the result is returned so that the response isn't null
        return $result;
    }

    public function actionDone($id, $feito) {
        if(!Yii::$app->request->isPut) {
            throw new BadMethodCallException('Method Not Allowed', 405);
        }


        $tarefa = Tarefa::findOne($id);
        $tarefa->feito = $feito;

        if($tarefa->save()) {
            return [
                'status' => true,
                'data' => 'Tarefa marcada como feita',
            ];
        } else {
            return [
                'status' => false,
                'data' => 'Erro ao marcar tarefa como feita',
                'errors' => $tarefa->errors
            ];
        }
    }
}

// mosquitto_sub -h localhost -p 8883 -t secure/topic
// mosquitto_pub -h localhost -p 8883 -t secure/topic -m "Secure message"
