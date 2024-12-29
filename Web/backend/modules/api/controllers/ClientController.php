<?php

namespace backend\modules\api\controllers;

use common\models\Client;
use common\models\User;
use yii\rest\ActiveController;

class ClientController extends ActiveController
{
    public $modelClass = 'common\models\Client';

    public function behaviors()
    {
        $behaviors = parent::behaviors();

        $behaviors['verbs'] = [
            'class' => \yii\filters\VerbFilter::className(),
            'actions' => [
                '*' => ['POST'], // Permit only POST requests
            ],
        ];

        return $behaviors;
    }

    public function actionLogin()
    {
        if(!\Yii::$app->request->isPost) {
            throw new \yii\web\MethodNotAllowedHttpException('Invalid request method');
        }

        $receivedUser = \Yii::$app->request->post();

        if (!isset($receivedUser['username']) || !isset($receivedUser['password'])) {
            throw new \yii\web\BadRequestHttpException('Please provide username and password');
        }

        $username = $receivedUser['username'];
        $password = $receivedUser['password'];

        $user = Client::findByName($username);

        $findUser = User::findByUsername($username);
        $getUserToken = $findUser->verification_token;
 
        if ($findUser && $findUser->validatePassword($password)) {
            return [
                'status' => 'success',
                'token' => $getUserToken,
                'username' => $user->name,
                'id' => $user->id,
                'email' => $user->email,
                'phone' => $user->phone,
                'balance' => $user->balance,
                'iban' => $user->iban,
            ];
        }

        \Yii::$app->response->statusCode = 400;

        return [
            'status' => 'error',
            'message' => 'Invalid username or password',
        ];
    }
}
