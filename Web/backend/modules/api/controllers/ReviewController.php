<?php

namespace backend\modules\api\controllers;

use common\models\CarReview;
use common\models\Notification;
use Yii;
use yii\rest\ActiveController;
use yii\web\BadRequestHttpException;
use yii\web\NotFoundHttpException;

class ReviewController extends ActiveController
{
    public $modelClass = 'common\models\CarReview';

   
    public function actionReviews() {
        $reviews = CarReview::find()->all();
        return $reviews;
    }

    public function actionSearch($id)
    {
        $searchByCar = CarReview::find()->where(['carId' => $id])->all();
        return $searchByCar;
    }

    public function actionCreate() {
        $model = new CarReview();
        $model->load(Yii::$app->request->post(), '');
        $model->createdAt = date('Y-m-d H:i:s');
        if ($model->save()) {
            return $model;
        } else {
            throw new BadRequestHttpException('Failed to create notification');
        }
    }
}
