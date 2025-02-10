<?php

use yii\helpers\Html;
use yii\widgets\DetailView;

/** @var yii\web\View $this */
/** @var common\models\Reservation $model */

$this->title = "Reservation Details";
$this->params['breadcrumbs'][] = ['label' => 'Reservations', 'url' => ['index']];
$this->params['breadcrumbs'][] = $this->title;
\yii\web\YiiAsset::register($this);
?>
<div class="reservation-view">

    <p>
        <?= Html::a('Update', ['update', 'id' => $model->id], ['class' => 'btn btn-primary']) ?>
        <?= Html::a('Delete', ['delete', 'id' => $model->id], [
            'class' => 'btn btn-danger',
            'data' => [
                'confirm' => 'Are you sure you want to delete this item?',
                'method' => 'post',
            ],
        ]) ?>
    </p>

    <?= DetailView::widget([
        'model' => $model,
        'attributes' => [
            'clientId' => [
                'attribute' => 'clientId',
                'label' => 'Client',
                'value' => function (common\models\Reservation $model) {
                    return $model->client->name;
                }
            ],
            'carId' => [
                'attribute' => 'carId',
                'label' => 'Car',
                'value' => function (common\models\Reservation $model) {
                    return $model->car->carBrand . ' ' . $model->car->carModel;
                }
            ],
            'dateStart',
            'dateEnd',
            'createAt',
            'filled' => [
                'attribute' => 'filled',
                'value' => function ($model) {
                    return $model->filled ? 'Finished' : 'Active';
                },
            ],
            'value',
        ],
    ]) ?>

</div>
