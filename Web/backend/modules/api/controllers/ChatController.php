<?php

namespace backend\modules\api\controllers;

use common\models\Client;
use yii\rest\ActiveController;

class ClientsController extends ActiveController
{
    public function actionRequest($client, $owner) {
        $checkIfOwnerIsOnline = Client::find()
            ->where(['id' => $owner, 'online' => 1])
            ->andWhere(['chat' => 0])
            ->one();

        $getOwner = Client::find()
            ->where(['id' => $owner])
            ->one();

        $getClient = Client::find()
            ->where(['id' => $client])
            ->one();

        if ($checkIfOwnerIsOnline === null) {
            //update database for both client and owner to chat = 1
            $getOwner->chat = 1;
            $getOwner->save();

            $getClient->chat = 1;
            $getClient->save();
            
            $generateChatId = "chat_" . uniqid();

            return [
                'inchat' => 0,
                'client' => $getClient->id,
                'owner' => $getOwner->id,
                'chatId' => $generateChatId
            ];
        } else {
            return [
                'inchat' => 1,
                'client' => $getClient->id,
                'owner' => $getOwner->id,
                'chatId' => null
            ];
        }
    }

    public function actionUpdate($client, $owner) {
        $getOwner = Client::find()
            ->where(['id' => $owner])
            ->one();

        $getClient = Client::find()
            ->where(['id' => $client])
            ->one();

        $getOwner->chat = 0;
        $getOwner->save();

        $getClient->chat = 0;
        $getClient->save();

        return [
            'status' => 'success',
        ];
    }
}
