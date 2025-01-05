<?php

namespace backend\modules\api\controllers;

use app\mosquitto\phpMQTT;
use common\models\Reservation;
use common\models\User;
use Yii;
use yii\rest\ActiveController;
use yii\filters\auth\HttpBasicAuth;
use yii\web\BadRequestHttpException;
use yii\web\NotFoundHttpException;
use yii\web\UnauthorizedHttpException;

class ReservationController extends ActiveController
{
  public $modelClass = 'common\models\Reservation';

  public function behaviors()
  {
    $behaviors = parent::behaviors();
    $behaviors['authenticator'] = [
      'class' => HttpBasicAuth::className(),
      'except' => [
        'index',
        'view',
        'count',
        'list', 'create'
      ],
      'auth' => [$this, 'authintercept']
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

  public function actionCount()
  {
    return ['count' => Reservation::find()->count()];
  }

  public function actionList()
  {
    return Reservation::find()->all();
  }

  public function actionIndex($id)
  {
    return Reservation::findOne($id);
  }

  public function actionCreate()
  {
    if (!Yii::$app->request->isPost) {
      throw new BadRequestHttpException('Invalid request method');
    }

    $data = Yii::$app->request->post();

    // Validate token
    $findClientByVerificationToken = User::findByVerificationToken($data['token'] ?? null);

    if (!$findClientByVerificationToken) {
      throw new UnauthorizedHttpException('Invalid token');
    }

    $model = new Reservation();

    // Load data into the model and validate
    if ($model->load($data, '') && $model->validate()) {
      $model->clientId = $findClientByVerificationToken->id;
      $model->createdAt = date('Y-m-d H:i:s');

      if ($model->save()) {
        return [
          'status' => 'success',
          'data' => $model->attributes,
        ];
      }
    }

    return [
      'status' => 'error',
      'errors' => $model->errors,
    ];
  }

  public function actionUpdate($id)
  {
    if (!Yii::$app->request->isPut) {
      throw new BadRequestHttpException('Invalid request method');
    }

    $data = Yii::$app->request->put();

    // Validate token

    $findClientByVerificationToken = User::findByVerificationToken($data['token'] ?? null);

    if (!$findClientByVerificationToken) {
      throw new UnauthorizedHttpException('Invalid token', 403);
    }

    // Find the existing model
    $model = Reservation::findOne($id);
    if (!$model) {
      throw new NotFoundHttpException('Invalid vehicle ID');
    }

    // Load and validate data
    if ($model->load($data) && $model->validate()) {
      if ($model->save()) {
        return [
          'status' => 'success',
          'data' => $model->attributes,
        ];
      }
    }

    // Return validation errors
    return [
      'status' => 'error',
      'errors' => $model->errors,
    ];
  }
  public function actionDelete($id)
  {
    if (!Yii::$app->request->isDelete) {
      throw new BadRequestHttpException('Invalid request method');
    }

    $data = Yii::$app->request->delete();

    // Validate token
    $findClientByVerificationToken = User::findByVerificationToken($data['token'] ?? null);

    if (!$findClientByVerificationToken) {
      throw new UnauthorizedHttpException('Invalid token');
    }

    // Find the existing model
    $model = Reservation::findOne($id);
    if (!$model) {
      throw new NotFoundHttpException('Invalid vehicle ID');
    }

    // Delete the model
    if ($model->delete()) {
      return [
        'status' => 'success',
      ];
    }

    return [
      'status' => 'error',
      'errors' => $model->errors,
    ];
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
            $this->FazPublishNoMosquitto("RESERVATION:CREATE", $newData);
        else
            $this->FazPublishNoMosquitto("RESERVATION:UPDATE", $newData);
    }

    public function afterDelete() {
        parent::afterDelete();

        $newData = new \stdClass();
        $newData->id = $this->id;

        $jsonData = json_encode($newData);
        $this->FazPublishNoMosquitto("RESERVATION:DELETE", $jsonData);
    }
}
