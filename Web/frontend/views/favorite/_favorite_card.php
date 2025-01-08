<?php
use yii\helpers\Html;
use yii\helpers\Url;
?>

<div class="favorite-card">
    <div class="card-image">
        <?= Html::img($model->car->getFirstPhoto(), ['alt' => 'Car Image', 'class' => 'car-photo']) ?>
        <div class="card-favorite">
            <?= Html::a('❤', ['/favorite/delete', 'id' => $model->id], [
                'class' => 'favorite-btn',
                'data-confirm' => 'Are you sure you want to remove this favorite?',
                'data-method' => 'post',
            ]) ?>
        </div>
    </div>
    <div class="card-details">
        <h3><?= Html::encode($model->car->carBrand . ' ' . $model->car->carModel) ?></h3>
        <div class="detail-item">
            <strong>Year:</strong> <?= Html::encode($model->car->carYear ?? 'N/A') ?>
        </div>
        <div class="detail-item">
            <strong>Doors:</strong> <?= Html::encode($model->car->carDoors ?? 'N/A') ?>
        </div>
        <div class="detail-item">
            <strong>Available from:</strong> <?= Html::encode($model->car->availableFrom) ?>
        </div>
        <div class="detail-item">
            <strong>Available to:</strong> <?= Html::encode($model->car->availableTo) ?>
        </div>
        <div class="detail-item">
            <strong>Location:</strong> <?= Html::encode($model->car->address ?? 'N/A') ?>
        </div>
    </div>
</div>
