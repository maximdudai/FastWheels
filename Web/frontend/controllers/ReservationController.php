<?php

namespace frontend\controllers;

use Bluerhinos\phpMQTT;
use common\models\Reservation;
use common\models\UserCar;
use frontend\models\ReservationSearch;
use yii\web\Controller;
use yii\web\NotFoundHttpException;
use yii\filters\VerbFilter;

/**
 * ReservationController implements the CRUD actions for Reservation model.
 */
class ReservationController extends Controller
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
     * Lists all Reservation models.
     *
     * @return string
     */
    public function actionIndex()
    {
        $searchModel = new ReservationSearch();
        $dataProvider = $searchModel->search($this->request->queryParams);

        // Filter reservations by the logged-in user's ID
        $dataProvider->query->andWhere(['clientId' => \Yii::$app->user->id]);

        return $this->render('index', [
            'searchModel' => $searchModel,
            'dataProvider' => $dataProvider,
        ]);
    }

    /**
     * Displays a single Reservation model.
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
     * Creates a new Reservation model.
     * If creation is successful, the browser will be redirected to the 'view' page.
     * @return string|\yii\web\Response
     */
    public function actionCreate()
    {
        $model = new Reservation();

        if ($this->request->isPost) {

            if ($model->load($this->request->post())) {

                $data = $this->request->post();
                $vehicle = UserCar::findOne($data['Reservation']['carId']);

                if ($vehicle->status != 0) {
                    return $this->render('create', [
                        'model' => $model,
                        'error' => 'Veículo indisponível para reserva'
                    ]);
                }

                $model->save();
                return $this->redirect(['view', 'id' => $model->id]);
            }
        } else {
            $model->loadDefaultValues();
        }

        return $this->render('create', [
            'model' => $model,
        ]);
    }

    /**
     * Updates an existing Reservation model.
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
     * Deletes an existing Reservation model.
     * If deletion is successful, the browser will be redirected to the 'index' page.
     * @param int $id ID
     * @return \yii\web\Response
     * @throws NotFoundHttpException if the model cannot be found
     */
    public function actionDelete($id)
    {
        $this->findModel($id)->delete();

        return $this->redirect(['reservation/index']);
    }

    /**
     * Finds the Reservation model based on its primary key value.
     * If the model is not found, a 404 HTTP exception will be thrown.
     * @param int $id ID
     * @return Reservation the loaded model
     * @throws NotFoundHttpException if the model cannot be found
     */
    protected function findModel($id)
    {
        if (($model = Reservation::findOne(['id' => $id])) !== null) {
            return $model;
        }

        throw new NotFoundHttpException('The requested page does not exist.');
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
            ReservationController::publishToMosquitto("RESERVATION:CREATE", json_encode($result));
        }

        // Ensure the result is returned so that the response isn't null
        return $result;
    }


    public function afterSave($insert, $changedAttributes)
    {
        parent::afterSave($insert, $changedAttributes);

        $newData = new \stdClass();
        $newData->id = $this->id;
        $newData->clientId = $this->clientId;
        $newData->carId = $this->carId;
        $newData->dateStart = $this->dateStart;
        $newData->dateEnd = $this->dateEnd;
        $newData->createAt = $this->createAt;
        $newData->filled = $this->filled;
        $newData->value = $this->value;
        $newData->feeValue = $this->feeValue;
        $newData->carValue = $this->carValue;

        $newData = json_encode($newData);

        if ($insert)
            ReservationController::publishToMosquitto("RESERVATION:CREATE", $newData);
        else
            ReservationController::publishToMosquitto("RESERVATION:UPDATE", $newData);
    }

    public function afterDelete()
    {
        parent::afterDelete();

        $newData = new \stdClass();
        $newData->id = $this->id;

        $jsonData = json_encode($newData);
        ReservationController::publishToMosquitto("RESERVATION:DELETE", $jsonData);
    }
}
