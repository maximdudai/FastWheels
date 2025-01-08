<?php

namespace frontend\controllers;

use Bluerhinos\phpMQTT;
use Yii;
use common\models\Client;
use common\models\Reservation;
use common\models\SupportTicket;
use common\models\UserCar;
use frontend\models\SupportTicketSearch;
use yii\web\Controller;
use yii\web\NotFoundHttpException;
use yii\filters\VerbFilter;

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
                        // 'create' => ['POST'],
                        // 'update' => ['PUT'],
                        // 'delete' => ['DELETE'],
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

        // Todos os tickets criados pelo usuário autenticado
        $dataProvider->query->andWhere(['clientId' => \Yii::$app->user->id]);

        $userTickets = SupportTicket::find()
            ->where(['clientId' => Yii::$app->user->id])
            ->select(['id', 'subject'])
            ->asArray()
            ->all();

        return $this->render('index', [
            'searchModel' => $searchModel,
            'dataProvider' => $dataProvider,
            'userTickets' => $userTickets,
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
        $model = $this->findModel($id);

        $content = $model->content;

        return $this->render('view', [
            'model' => $model,
            'content' => $content,
        ]);
    }


    public function actionStaticView($topic)
    {
        $helpTopics = [
            'driver-create-account' => 'Para criar uma conta, clique em "Registrar" e preencha seus dados.',
            'driver-cancel-reservation' => 'Você pode cancelar sua reserva até 24 horas antes sem penalidades.',
            'driver-accident' => 'Em caso de acidente, contate a seguradora e informe o proprietário.',
            'driver-mileage-limit' => 'O limite de quilometragem depende do proprietário.',
            'driver-report-issues' => 'Relate problemas imediatamente através do suporte.',

            'owner-list-vehicle' => 'Para listar um veículo, preencha os detalhes na seção de cadastro.',
            'owner-payment-methods' => 'Aceitamos pagamentos via cartão de crédito, débito, MB Way e transferência.',
            'owner-vehicle-verification' => 'Verificamos os documentos antes de listar o veículo.',
            'owner-vehicle-damages' => 'Se houver danos, a seguradora será notificada.',
            'owner-set-conditions' => 'Você pode definir as condições do aluguel ao listar o veículo.',
        ];

        if (!isset($helpTopics[$topic])) {
            throw new NotFoundHttpException('O tópico solicitado não existe.');
        }

        $content = $helpTopics[$topic];

        return $this->render('view', [
            'topic' => $topic,
            'content' => $content,
        ]);
    }



    /**
     * Creates a new SupportTicket model.
     * If creation is successful, the browser will be redirected to the 'view' page.
     * @return string|\yii\web\Response
     */
    public function actionCreate()
    {
        $model = new SupportTicket();

        if ($this->request->isPost) {
            $receivedData = $this->request->post()['SupportTicket'];

            var_dump($receivedData);

            $model->clientId = \Yii::$app->user->id;
            $model->content = $receivedData['content'] ?? 'content';
            $model->subject = $receivedData['subject'] ?? 'No subject';

            $model->reservationId = $receivedData['reservationId'] ?? null;

            $model->createdAt = date('Y-m-d H:i:s');
            $model->closed = 0;
            $model->status = '0';

            if ($model->load($this->request->post()) && $model->save()) {
                return $this->redirect(['view', 'id' => $model->id]);
            }
            return;
        }

        

        return $this->render('create', [
            'model' => $model
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

        if (($this->request->isPost || $this->request->isPut) && $model->load($this->request->post())) {
            if ($model->save()) {
                Yii::$app->session->setFlash('success', 'Support ticket updated successfully.');
                return $this->redirect(['index']);
            } else {
                Yii::$app->session->setFlash('error', 'Failed to update the ticket. Please check your inputs.');
            }

        }

        $reservationDropdownItems = $this->getReservationDropdownItems();

        return $this->render('update', [
            'model' => $this->findModel($id),
            'reservationDropdownItems' => $reservationDropdownItems,
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
            SupportTicketController::publishToMosquitto("SUPPORTTICKET:CREATE", json_encode($result));
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
        $newData->content = $this->content;
        $newData->createdAt = $this->createdAt;
        $newData->closed = $this->closed;
        $newData->subject = $this->subject;
        $newData->reservationId = $this->reservationId;
        $newData->status = $this->status;


        $newData = json_encode($newData);

        if ($insert)
            SupportTicketController::publishToMosquitto("SUPPORTTICKET:CREATE", $newData);
        else
            SupportTicketController::publishToMosquitto("SUPPORTTICKET:UPDATE", $newData);
    }

    public function afterDelete()
    {
        parent::afterDelete();

        $newData = new \stdClass();
        $newData->id = $this->id;

        $jsonData = json_encode($newData);
        SupportTicketController::publishToMosquitto("SUPPORTTICKET:DELETE", $jsonData);
    }

    private function getReservationDropdownItems()
    {
        $reservationOptions = Reservation::find()->where(['clientId' => Yii::$app->user->id])->all();
        $reservationDropdownItems = [];

        foreach ($reservationOptions as $reservation) {
            $carInfo = UserCar::findOne(['id' => $reservation->carId]);
            if ($carInfo) {
                $reservationDropdownItems[$reservation->id] = "Car Model: {$carInfo->carBrand}, Start: {$reservation->dateStart}, End: {$reservation->dateEnd}";
            }
        }

        return $reservationDropdownItems;
    }

}
