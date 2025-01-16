<?php

namespace backend\modules\api\controllers;

use common\models\Client;
use common\models\User;
use yii\filters\auth\HttpBasicAuth;
use yii\rest\ActiveController;

class ClientsController extends ActiveController
{
    public $modelClass = 'common\models\Client';

    public function behaviors()
    {
        $behaviors = parent::behaviors();

        // Add authentication
        $behaviors['authenticator'] = [
            'class' => HttpBasicAuth::className(),
            'auth' => [$this, 'authintercept'],
            'except' => ['login', 'register'],
        ];

        // Access control for HTTP methods
        $behaviors['access'] = [
            'class' => \yii\filters\AccessControl::class,
            'rules' => [
                [
                    'allow' => true,
                    'actions' => ['login', 'register'],
                    'verbs' => ['POST'],
                    'roles' => ['?'],  // No authentication required for these actions
                ],
                [
                    'allow' => true,
                    'actions' => ['update'],
                    'verbs' => ['PUT'],
                    'roles' => ['@'], // Authentication required for these actions
                ],
                [
                    'allow' => true,
                    'actions' => ['delete'],
                    'verbs' => ['DELETE'],
                    'roles' => ['@'], // Authentication required for these actions
                ],
            ],
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

    public function actionLogin()
    {
        if (!\Yii::$app->request->isPost) {
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

    public function actionRegister()
    {
        if (!\Yii::$app->request->isPost) {
            throw new \yii\web\MethodNotAllowedHttpException('Invalid request method');
        }

        $receivedUser = \Yii::$app->request->post();

        if (!isset($receivedUser['username']) || !isset($receivedUser['password']) || !isset($receivedUser['email'])) {
            throw new \yii\web\BadRequestHttpException('Please provide all required fields');
        }

        if (User::findByUsername($receivedUser['username']) || User::findByEmail($receivedUser['email'])) {
            throw new \yii\web\BadRequestHttpException('User already exists');
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

        if (!$client->save()) {
            // Log or display validation errors
            throw new \yii\web\BadRequestHttpException('Error creating user');
            return null;
        }

        if ($client->save()) {
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

    // public function beforeAction($action)
    // {
    //     if ($action->id == 'update') {
    //         $receivedUser = \Yii::$app->request->post();

    //         $user = User::find()->where(['id' => $receivedUser['id']])->one();

    //         die( var_dump($user->attributes) );
    //     }
    //     return parent::beforeAction($action);
    // }

    public function actions()
    {
        $actions = parent::actions();
        unset($actions['update']); 
        return $actions;
    }

    public function actionUpdate($id)
    {
        $receivedUser = \Yii::$app->request->post();

        $modelClient = Client::findOne($id);
        $modelUser = User::findOne(['id' => $id]);
        

        $getUserToken = $modelUser->verification_token;

        if (!$modelClient || !$modelUser) {
            throw new \yii\web\NotFoundHttpException('User or client not found');
        }

        // Atualiza os dados do cliente
        $modelClient->name = $receivedUser['name'] ?? $modelClient->name;
        $modelClient->email = $receivedUser['email'] ?? $modelClient->email;
        $modelClient->phone = $receivedUser['phone'] ?? $modelClient->phone;
        $modelClient->iban = $receivedUser['iban'] ?? $modelClient->iban;
        $modelClient->balance = $receivedUser['balance'] ?? $modelClient->balance;

        $modelUser->username = $receivedUser['name'] ?? $modelUser->username;
        $modelUser->email = $receivedUser['email'] ?? $modelUser->email;

        if(isset($receivedUser['password'])) {
            $modelUser->setPassword($receivedUser['password']);
            $modelUser->generateAuthKey();
        }

        if ($modelClient->save() && $modelUser->save()) {
            \Yii::$app->response->format = \yii\web\Response::FORMAT_JSON;
            \Yii::$app->response->statusCode = 200;
            return [
                'status' => 'success',
                'token' => $getUserToken,
                'username' => $modelUser->username,
                'id' => $modelUser->id,
                'email' => $modelUser->email,
                'phone' => $modelClient->phone,
                'balance' => $modelClient->balance,
                'iban' => $modelClient->iban,
            ];
        } else {
            \Yii::$app->response->format = \yii\web\Response::FORMAT_JSON;
            \Yii::$app->response->statusCode = 400;
            return [
                'status' => 'error',
                'message' => 'Error updating user',
            ];
        }
    }


    public function actionDelete($id)
    {
        if (!\Yii::$app->request->isDelete) {
            throw new \yii\web\MethodNotAllowedHttpException('Invalid request method');
        }

        $modelClient = Client::findModel($id);
        $modelUser = User::findOne(['id' => $id]);

        if (!$modelClient || !$modelUser) {
            throw new \yii\web\NotFoundHttpException('User or client not found');
        }

        if ($modelClient->delete() && $modelUser->delete()) {
            \Yii::$app->response->statusCode = 201;
            return [
                'status' => 'success',
                'message' => 'User deleted successfully',
            ];
        } else {
            \Yii::$app->response->statusCode = 400;
            return [
                'status' => 'error',
                'message' => 'Error deleting user',
            ];
        }
    }
}
