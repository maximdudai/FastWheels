<?php

namespace backend\modules\api\controllers;

use common\models\Client;
use Yii;
use yii\rest\ActiveController;

class ChatController extends ActiveController
{
    public $modelClass = 'common\models\Client';

    public function behavios()
    {
        $behaviors = parent::behaviors();

        $behaviors['access'] = [
            'class' => \yii\filters\AccessControl::class,
            'rules' => [
                [
                    'allow' => true,
                    'actions' => ['request'],
                    'verbs' => ['GET'],
                    'roles' => ['@'],
                ],
                [
                    'allow' => true,
                    'actions' => ['update'],
                    'verbs' => ['PUT'],
                    'roles' => ['@'],
                ],
            ],
        ];

        return $behaviors;
    }

    public function actions()
    {
        $actions = parent::actions();
        unset($actions['request'], $actions['update']);
        return $actions;
    }


    public function actionRequest()
    {
        if (!\Yii::$app->request->isPost) {
            throw new \yii\web\MethodNotAllowedHttpException('Invalid request method');
        }

        $receivedUser = \Yii::$app->request->post();

        $owner = $receivedUser['owner'];
        $client = $receivedUser['client'];

        $ownerRecord = Client::findOne($owner);
        $clientRecord = Client::findOne($client);

        if (!$ownerRecord || !$clientRecord) {
            return ['status' => 'error', 'message' => 'Invalid client or owner ID'];
        }

        $isOwnerAvailable = Client::find()
            ->where(['id' => $owner, 'online' => 1, 'chat' => 0])
            ->exists();

        if (!$isOwnerAvailable) {
            return [
                'inchat' => 1,
            ];
        }

        // Use transactions to ensure both updates succeed together
        $transaction = Yii::$app->db->beginTransaction();
        try {
            $ownerRecord->chat = 1;
            $clientRecord->chat = 1;

            if ($ownerRecord->save() && $clientRecord->save()) {
                $transaction->commit();

                return [
                    'inchat' => 0,
                    'client' => $clientRecord->id,
                    'owner' => $ownerRecord->id,
                    'chatId' => "chat_" . uniqid()
                ];
            } else {
                $transaction->rollBack();
                return ['status' => 'error', 'message' => 'Failed to update chat status'];
            }
        } catch (\Exception $e) {
            $transaction->rollBack();
            return ['status' => 'error', 'message' => $e->getMessage()];
        }
    }

    public function actionAccept() {
        if (!\Yii::$app->request->isPost) {
            throw new \yii\web\MethodNotAllowedHttpException('Invalid request method');
        }

        $receivedUser = \Yii::$app->request->post();

        $owner = $receivedUser['owner'];
        $client = $receivedUser['client'];

        $ownerRecord = Client::findOne($owner);
        $clientRecord = Client::findOne($client); 

        // Check if the client is in chat with someone
        $isClientInChatWithSomeOne = Client::find()
            ->where(['id' => $client, 'chat' => 1])
            ->whereNot(['id' => $owner])
            ->exists();

        if(!$isClientInChatWithSomeOne) {
            return [
                'status' => 'error',
                'message' => 'Client is not in chat with anyone'
            ];
        }

        // Check if the owner is in chat with client who made the request
        $isOwnerInChatWithSomeOne = Client::find()
            ->where(['id' => $owner, 'chat' => $owner])
            ->whereNot(['id' => $client])
            ->exists();

        if(!$isOwnerInChatWithSomeOne) {
            return [
                'status' => 'error',
                'message' => 'Owner is not in chat with the client'
            ];
        }
            
    }

    public function actionUpdate($client, $owner)
    {
        if (!Yii::$app->request->isPut) {
            throw new \yii\web\MethodNotAllowedHttpException('Invalid request method');
        }


        $ownerRecord = Client::findOne($owner);
        $clientRecord = Client::findOne($client);

        if (!$ownerRecord || !$clientRecord) {
            return ['status' => 'error', 'message' => 'Invalid client or owner ID'];
        }

        $transaction = Yii::$app->db->beginTransaction();
        try {
            $ownerRecord->chat = 0;
            $clientRecord->chat = 0;

            if ($ownerRecord->save() && $clientRecord->save()) {
                $transaction->commit();
                return ['status' => 'success'];
            } else {
                $transaction->rollBack();
                return ['status' => 'error', 'message' => 'Failed to update chat status'];
            }
        } catch (\Exception $e) {
            $transaction->rollBack();
            return ['status' => 'error', 'message' => $e->getMessage()];
        }
    }
}
