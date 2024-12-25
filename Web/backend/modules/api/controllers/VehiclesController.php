<?php

namespace backend\modules\api\controllers;

use BadMethodCallException;
use common\models\User;
use common\models\UserCar;
use Yii;
use yii\rest\ActiveController;
use yii\filters\auth\HttpBasicAuth;

class VehiclesController extends ActiveController
{
  public $modelClass = 'common\models\UserCar';

  public function behaviors()
  {
    $behaviors = parent::behaviors();
    $behaviors['authenticator'] = [
      'class' => HttpBasicAuth::className(), // ou QueryParamAuth::className(),
      'except' => ['index', 'view'],
      'auth' => [$this, 'authintercept']
    ];
    return $behaviors;
  }
  public function actionCreate()
  {

    if (!Yii::$app->request->post()) {
      throw new BadMethodCallException('Invalid request method');
    }

    $data = Yii::$app->request->post();

    // Check if received user token is valid
    $findClientByVerificationToken = User::findByVerificationToken($data['token']);

    if (!$findClientByVerificationToken) {
      throw new BadMethodCallException('Invalid token');
    }

    // retrieve data from the request
    $clientId = $findClientByVerificationToken->id;
    $carName = $data['carName'];
    $carModel = $data['carModel'];
    $carYear = $data['carYear'];
    $carDoors = $data['carDoors'];
    $createdAt = date('Y-m-d H:i:s');
    $availableFrom = date('Y-m-d H:i:s');
    $availableTo = date('Y-m-d H:i:s');
    $status = 0;

    // Create a new UserCar model with the received data
    $model = new UserCar();
    $model->clientId = $clientId;
    $model->carName = $carName;
    $model->carModel = $carModel;
    $model->carYear = $carYear;
    $model->carDoors = $carDoors;
    $model->createdAt = $createdAt;
    $model->availableFrom = $availableFrom;
    $model->availableTo = $availableTo;
    $model->status = $status;

    if ($model->save()) {
      return [
        'status' => 'success',
        'data' => $model->attributes,
      ];
    } else {
      return $model->errors;
    }
  }
}
