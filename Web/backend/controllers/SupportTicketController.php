<?php

namespace backend\controllers;

use common\models\SupportTicket;
use backend\models\SupportTicketSearch;
use yii\web\Controller;
use yii\web\NotFoundHttpException;
use yii\filters\VerbFilter;
use app\mosquitto\phpMQTT;

/**
 * SupportTicketController implements the CRUD actions for SupportTicket model.
 */
class SupportTicketController extends Controller
{
    /**
     * @inheritDoc
     */
    public function behaviors()
    {
        return array_merge(
            parent::behaviors(),
            [
                'verbs' => [
                    'class' => VerbFilter::className(),
                    'actions' => [
                        'delete' => ['POST'],
                    ],
                ],
            ]
        );
    }

    /**
     * Lists all SupportTicket models.
     *
     * @return string
     */
    public function actionIndex()
    {
        $searchModel = new SupportTicketSearch();
        $dataProvider = $searchModel->search($this->request->queryParams);

        return $this->render('index', [
            'searchModel' => $searchModel,
            'dataProvider' => $dataProvider,
        ]);
    }

    /**
     * Displays a single SupportTicket model.
     * @param int $id ID
     * @return string
     * @throws NotFoundHttpException if the model cannot be found
     */
    public function actionView($id)
    {
        return $this->render('view', [
            'model' => $this->findModel($id),
        ]);
    }

    /**
     * Updates an existing SupportTicket model.
     * If update is successful, the browser will be redirected to the 'view' page.
     * @param int $id ID
     * @return string|\yii\web\Response
     * @throws NotFoundHttpException if the model cannot be found
     */
    public function actionUpdate($id)
    {
        $model = $this->findModel($id);

        if ($this->request->isPost && $model->load($this->request->post()) && $model->save()) {
            return $this->redirect(['view', 'id' => $model->id]);
        }

        return $this->render('update', [
            'model' => $model,
        ]);
    }

    /**
     * Deletes an existing SupportTicket model.
     * If deletion is successful, the browser will be redirected to the 'index' page.
     * @param int $id ID
     * @return \yii\web\Response
     * @throws NotFoundHttpException if the model cannot be found
     */
    public function actionDelete($id)
    {
        parent::after
        $this->findModel($id)->delete();

        $myJSON = new \stdClass();
        $myJSON->id = $id;
        $this->FazPublishNoMosquitto("DELETE",$myJSON);

        return $this->redirect(['index']);
    }

    /**
     * Finds the SupportTicket model based on its primary key value.
     * If the model is not found, a 404 HTTP exception will be thrown.
     * @param int $id ID
     * @return SupportTicket the loaded model
     * @throws NotFoundHttpException if the model cannot be found
     */
    protected function findModel($id)
    {
        if (($model = SupportTicket::findOne(['id' => $id])) !== null) {
            return $model;
        }

        throw new NotFoundHttpException('The requested page does not exist.');
    }


    public static function publishToMosquitto($topic, $message)
    {
        $server = "127.0.0.1";
        $port = 1883;
        $username = ""; // set your username
        $password = ""; // set your password
        $client_id = "phpMQTT-publisher"; // unique!
        $mqtt = new phpMQTT($server, $port, $client_id);
        if ($mqtt->connect(true, NULL, $username, $password)) {
            $mqtt->publish($topic, $message, 0);
            $mqtt->close();
        } else {
            file_put_contents("debug.output", "Time out!");
        }
    }

    public function afterSave($insert, $changedAttributes)
    {
        parent::afterSave($insert, $changedAttributes);

        $newData = new \stdClass();
        $newData->id = $this->id;
        $newData->name = $this->name;
        $newData->email = $this->email;
        $newData->phone = $this->phone;
        $newData->createdAt = $this->createdAt;
        $newData->balance = $this->balance;
        $newData->iban = $this->iban;

        $newData = json_encode($newData);
        $this->publishToMosquitto("supportticket", $newData);

        if ($insert)
            $this->FazPublishNoMosquitto("INSERT", $newData);
        else
            $this->FazPublishNoMosquitto("UPDATE", $newData);
    }
}
