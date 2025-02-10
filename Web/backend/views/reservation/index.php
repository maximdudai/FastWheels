<?php

use common\models\Reservation;
use yii\helpers\Html;
use yii\helpers\Url;
use yii\grid\ActionColumn;
use yii\grid\GridView;

/** @var yii\web\View $this */
/** @var backend\models\ReservationSearch $searchModel */
/** @var yii\data\ActiveDataProvider $dataProvider */

$this->title = 'Reservations';
$this->params['breadcrumbs'][] = $this->title;
?>
<div class="reservation-index">

    <?= GridView::widget([
        'dataProvider' => $dataProvider,
        'filterModel' => $searchModel,
        'columns' => [
            ['class' => 'yii\grid\SerialColumn'],

            'clientId' => [
                'attribute' => 'clientId',
                'label' => 'Client',
                'value' => function (Reservation $model) {
                    return $model->client->name;
                }
            ],
            'carId' => [
                'attribute' => 'carId',
                'label' => 'Car',
                'value' => function (Reservation $model) {
                    return $model->car->carBrand . ' ' . $model->car->carModel;
                }
            ],
            'dateStart',
            'dateEnd',
            //'createAt',
            //'filled',
            //'value',
            [
                'class' => ActionColumn::className(),
                'urlCreator' => function ($action, Reservation $model, $key, $index, $column) {
                    return Url::toRoute([$action, 'id' => $model->id]);
                 }
            ],
        ],
    ]); ?>


</div>
