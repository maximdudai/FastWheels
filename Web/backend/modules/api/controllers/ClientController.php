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

    public function actionRegister() {
        if(!\Yii::$app->request->isPost) {
            throw new \yii\web\MethodNotAllowedHttpException('Invalid request method');
        }

        $receivedUser = \Yii::$app->request->post();

        // waiting for:

        //name
        //email
        //pass

        if (!isset($receivedUser['username']) || !isset($receivedUser['password']) || !isset($receivedUser['email'])) {
            throw new \yii\web\BadRequestHttpException('Please provide all required fields');
        }

        $username = $receivedUser['username'];
        $email = $receivedUser['email'];
        $password = $receivedUser['password'];

    
        // Create and save user in User table
        $user = new User();
        $user->username = $username;
        $user->email = $email;
        $user->status = User::STATUS_ACTIVE;
        $user->setPassword($password);
        $user->generateAuthKey();
        $user->generateEmailVerificationToken();
        $user->status = 10;

        if (!$user->save()) {
            // Log or display validation errors
            throw new \yii\web\BadRequestHttpException('Error creating user');
            return null;
        }

        $auth = \Yii::$app->authManager;
        $authorRole = $auth->getRole('client');
        $auth->assign($authorRole, $user->getId());

        $client = new Client();
        $client->userId = $user->id;
        $client->name = $username;
        $client->email = $email;
        $client->phone = 'none';
        $client->roleId = 1;
        $client->createdAt = date('Y-m-d H:i:s');
        $client->balance = 0;
        $client->iban = 'none';

        if(!$client->save()) {
            // Log or display validation errors
            throw new \yii\web\BadRequestHttpException('Error creating user');
            return null;
        }

        if($client->save()) {
            $getUserToken = $user->verification_token;

            return [
                'status' => 'success',
                'token' => $getUserToken,
                'username' => $client->name,
                'id' => $client->id,
                'email' => $client->email,
                'phone' => $client->phone,
                'balance' => $client->balance,
                'iban' => $client->iban,
            ];
        }

        \Yii::$app->response->statusCode = 400;

        return [
            'status' => 'error',
            'message' => 'Error creating user',
        ];
    }

    public function actionUpdate() {
        if(!\Yii::$app->request->isPost) {
            throw new \yii\web\MethodNotAllowedHttpException('Invalid request method');
        }

        $receivedUser = \Yii::$app->request->post();

        $id = $receivedUser['id'];

        $clientModel = Client::findModel($id);

        // if ($clientModel->load($this->request->post())) {

        //     // update User table
        //     $loggedUser = $clientModel->userId;

        //     $user->username = $clientModel->name;
        //     $user->email = $clientModel->email;

        //     $user->save();
        //     $clientModel->save();
        // }

        // $clientUser = User::findOne(['id' => $clientModel->userId]);

        // if($user->save()) {
        //     return [
        //         'status' => 'success',
        //         'token' => $clientUser->verification_token,
        //         'username' => $user->name,
        //         'id' => $user->id,
        //         'email' => $user->email,
        //         'phone' => $user->phone,
        //         'balance' => $user->balance,
        //         'iban' => $user->iban,
        //     ];     
        // } else {
        //     \Yii::$app->response->statusCode = 400;

        //     return [
        //         'status' => 'error',
        //         'message' => 'Error updating user',
        //     ];
        // }
    }
}
