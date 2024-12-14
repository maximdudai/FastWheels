<?php

namespace backend\modules\api\controllers;


use Yii;
use yii\rest\ActiveController;
use yii\filters\auth\HttpBasicAuth;
use common\models\User;

class ClientController extends ActiveController
{
    public $modelClass = 'common\models\Client';

    public function behaviors()
    {
        $behaviors = parent::behaviors();
        $behaviors['authenticator'] = [
            'class' => HttpBasicAuth::className(),
            'except' => ['login'],
        ];
        return $behaviors;
    }


    public function actionLogin()
    {
        $receivedUser = \Yii::$app->request->post();

        // Check if the username and password were sent
        if (!isset($receivedUser['username']) || !isset($receivedUser['password'])) {
            throw new \yii\web\BadRequestHttpException('Please provide username and password');
        }

        $username = $receivedUser['username'];
        $password = $receivedUser['password'];

        $user = User::findByUsername($username);

        if ($user && $user->validatePassword($password)) {
            return [
                'status' => 'success',
                'token' => $user->verification_token,
            ];
        }

        \Yii::$app->response->statusCode = 400;

        return [
            'status' => 'error',
            'message' => 'Invalid username or password',
        ];
    }
}
