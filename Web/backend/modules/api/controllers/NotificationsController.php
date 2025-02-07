<?php

namespace backend\modules\api\controllers;

use BadMethodCallException;
use common\models\Notification;
use common\models\User;
use Yii;
use yii\rest\ActiveController;
use yii\filters\auth\HttpBasicAuth;
use yii\web\BadRequestHttpException;
use yii\web\NotFoundHttpException;
use yii\web\UnauthorizedHttpException;

class NotificationsController extends ActiveController
{
    public $modelClass = 'common\models\Notification';

    public function actions()
    {
        $actions = parent::actions();
        unset($actions['index']); 
        return $actions;
    }

    public function actionSearch($id)
    {
        $searchByClientId = Notification::find()->where(['clientId' => $id])->all();

        return $searchByClientId;
    }

    public function actionCreate() {
        $model = new Notification();
        $model->load(Yii::$app->request->post(), '');
        $model->createdAt = date('Y-m-d H:i:s');
        if ($model->save()) {
            return $model;
        } else {
            throw new BadRequestHttpException('Failed to create notification');
        }
    }

    public function actionMarkread() {
        if(!Yii::$app->request->isPut) {
            throw new BadRequestHttpException('Incorrect request method');
        }
        $data = Yii::$app->request->post();

        $model = Notification::find()->where(['id' => $data['id']])->one();

        if (!$model) {
            throw new NotFoundHttpException('Notification not found');
        }

        $model->read = 1;

        if ($model->save()) {
            return $model;
        } else {
            throw new BadRequestHttpException('Failed to update notification');
        }
    }

    public function actionDelete($id) {
        $model = Notification::findOne($id);
        if (!$model) {
            throw new NotFoundHttpException('Notification not found');
        }
        if ($model->delete()) {
            return ['status' => 'success'];
        } else {
            throw new BadRequestHttpException('Failed to delete notification');
        }
    }
}
