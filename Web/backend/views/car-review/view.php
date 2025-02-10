<?php

use yii\helpers\Html;
use yii\widgets\DetailView;

/** @var yii\web\View $this */
/** @var common\models\CarReview $model */

$this->title = $model->id;
$this->params['breadcrumbs'][] = ['label' => 'Car Reviews', 'url' => ['index']];
$this->params['breadcrumbs'][] = $this->title;
\yii\web\YiiAsset::register($this);
?>
<div class="car-review-view">

    <p>
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
            'carId' => [
                'attribute' => 'carId',
                'label' => 'Car',
                'value' => function (common\models\CarReview $model) {
                    return $model->car->carBrand . ' ' . $model->car->carModel;
                }
            ],
            'comment',
            'createdAt',
        ],
    ]) ?>

</div>
