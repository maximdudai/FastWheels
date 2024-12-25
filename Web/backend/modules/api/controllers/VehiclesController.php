<?php

namespace backend\modules\api\controllers;

use BadMethodCallException;
use common\models\User;
use common\models\UserCar;
use Yii;
use yii\rest\ActiveController;
use yii\filters\auth\HttpBasicAuth;
use yii\web\BadRequestHttpException;
use yii\web\NotFoundHttpException;
use yii\web\UnauthorizedHttpException;

class VehiclesController extends ActiveController
{
  public $modelClass = 'common\models\UserCar';

  public function behaviors()
  {
    $behaviors = parent::behaviors();
    $behaviors['authenticator'] = [
      'class' => HttpBasicAuth::className(), // ou QueryParamAuth::className(),
      'except' => ['index', 'view', 'create'],
      'auth' => [$this, 'authintercept']
    ];
    return $behaviors;
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

    // Create a new UserCar model
    $model = new UserCar();

    // Load data into the model and validate
    if ($model->load($data, '') && $model->validate()) {
      $model->clientId = $findClientByVerificationToken->id ?? 1;
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
      // throw new UnauthorizedHttpException('Invalid token');
    
      return [
        'status' => 'error',
        'errors' => ['Invalid token' => 'Invalid token'],
      ];
    }

    // Find the existing model
    $model = UserCar::findOne($id);
    if (!$model) {
      throw new NotFoundHttpException('Invalid vehicle ID');
    }

    // Load and validate data
    if ($model->load($data, '') && $model->validate()) {
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
}
