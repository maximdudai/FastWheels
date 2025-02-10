<?php

use common\models\CarReview;
use yii\helpers\Html;
use yii\helpers\Url;
use yii\grid\ActionColumn;
use yii\grid\GridView;

/** @var yii\web\View $this */
/** @var backend\models\CarReviewSearch $searchModel */
/** @var yii\data\ActiveDataProvider $dataProvider */

$this->title = 'Car Reviews';
$this->params['breadcrumbs'][] = $this->title;
?>
<div class="car-review-index">

    <?= GridView::widget([
        'dataProvider' => $dataProvider,
        'filterModel' => $searchModel,
        'columns' => [
            ['class' => 'yii\grid\SerialColumn'],

            'carId' => [
                'attribute' => 'carId',
                'label' => 'Car',
                'value' => function (CarReview $model) {
                    return $model->car->carBrand . ' ' . $model->car->carModel;
                }
            ],
            'comment',
            'createdAt',
            [
                'class' => ActionColumn::className(),
                'template' => '{view} {delete}',
                'urlCreator' => function ($action, CarReview $model, $key, $index, $column) {
                    return Url::toRoute([$action, 'id' => $model->id]);
                 }
            ],
        ],
    ]); ?>


</div>
