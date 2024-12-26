<?php
namespace backend\modules\api\controllers;

use yii\rest\ActiveController;
use common\models\User; // Ensure this is your user model
use yii\filters\auth\HttpBasicAuth;

class ClientController extends ActiveController
{
    public $modelClass = 'common\models\Client';

    public function behaviors()
    {
        $behaviors = parent::behaviors();
        $behaviors['authenticator'] = [
            'class' => HttpBasicAuth::className(),
            'except' => ['login'], // Exclude 'login' from authentication
        ];
        return $behaviors;
    }

    public function actionLogin()
    {
        $receivedUser = \Yii::$app->request->post();

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

