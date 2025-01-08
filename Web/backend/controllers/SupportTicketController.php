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
        $this->findModel($id)->delete();

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
        $server = "54.229.223.123"; // AWS IP address
        $port = 1883; 
        $username = ""; // set your username if needed
        $password = ""; // set your password if needed
        $client_id = "phpMQTT-publisher"; // unique client ID
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
        $newData->clientId = $this->clientId;
        $newData->content = $this->content;
        $newData->subject = $this->subject;
        $newData->createdAt = $this->createdAt;
        $newData->closed = $this->closed;
        $newData->status = $this->status;
        
        $newData = json_encode($newData);

        if ($insert)
            $this->FazPublishNoMosquitto("SUPPORTTICKET:CREATE", $newData);
        else
            $this->FazPublishNoMosquitto("SUPPORTTICKET:UPDATE", $newData);
    }

    public function afterDelete() {
        parent::afterDelete();

        $newData = new \stdClass();
        $newData->id = $this->id;

        $jsonData = json_encode($newData);
        $this->FazPublishNoMosquitto("SUPPORTTICKET:DELETE", $jsonData);
    }
}
