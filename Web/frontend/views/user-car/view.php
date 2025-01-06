<?php

use yii\helpers\Html;
use yii\helpers\Url;

/** @var yii\web\View $this */
/** @var common\models\UserCar $model */

$this->title = $model->carBrand . ' ' . $model->carModel;
\yii\web\YiiAsset::register($this);
?>
<div class="user-car-view">
    <div class="view-card-container">
        <div class="view-card">
            <!-- Imagem com botão Favorite -->
            <div class="view-card-image">
                <?php
                $photos = $model->carphotos;
                if (!empty($photos)) {
                    echo Html::img($photos[0]->photoPath, ['alt' => 'Car Photo']);
                } else {
                    echo Html::img('/path/to/default-image.jpg', ['alt' => 'Default Car Photo']);
                }
                ?>
                <div class="card-favorite">
                    <?php if (Yii::$app->user->isGuest): ?>
                        <?= Html::button('❤', [
                            'class' => 'favorite-btn',
                            'onclick' => "alert('You need to log in to add to favorites.'); window.location.href = '" . Url::to(['/site/login']) . "';",
                        ]) ?>
                    <?php else: ?>
                        <?= Html::beginForm(['user-car/favorite'], 'post') ?>
                        <?= Html::hiddenInput('carId', $model->id) ?>
                        <?= Html::submitButton('❤', ['class' => 'favorite-btn']) ?>
                        <?= Html::endForm() ?>
                    <?php endif; ?>
                </div>
            </div>
            <div class="car-name-section">
                <div class="car-name-pair">
                    <strong>Car:</strong> <?= Html::encode($model->carBrand . ' ' . $model->carModel) ?>
                </div>
                <div class="car-name-pair">
                    <strong>Owner:</strong> <?= Html::encode($model->client->name) ?>
                </div>
            </div>
            <div class="details-label">Details:</div>
            <div class="separator-bar"></div>
            <div class="details">
                <div>
                    <strong>Year:</strong>
                    <span><?= Html::encode($model->carYear ?? '00/0000') ?></span>
                </div>
                <div>
                    <strong>Available</strong>
                    </div>
                <div>
                    <strong>Doors:</strong>
                    <span><?= Html::encode($model->carDoors ?? '0') ?></span>
                </div>
                <div>
                    <strong>From:</strong>
                    <span><?= Html::encode(date('m/Y', strtotime($model->availableFrom))) ?></span>
                </div>
                <div>
                    <strong>Reviews:</strong>
                    <span><?= !empty($model->carreviews) ? number_format(array_sum(array_column($model->carreviews, 'rating')) / count($model->carreviews), 1) : '0,0' ?></span>
                </div>
                <div>
                    <strong>To:</strong>
                    <span><?= Html::encode(date('m/Y', strtotime($model->availableTo))) ?></span>
                </div>
                <div>
                    <strong>Location:</strong>
                    <span><?= Html::encode($model->address ?? 'N/A') ?></span>
                </div>
                <div>
                    <strong>Created At:</strong>
                    <span><?= Html::encode(date('m/Y', strtotime($model->createdAt))) ?></span>
                </div>
                <div>
                    <strong>City:</strong>
                    <span><?= Html::encode($model->city ?? 'N/A') ?></span>
                </div>
                <div>
                    <strong>Postal Code:</strong>
                    <span><?= Html::encode($model->postalCode ?? 'N/A') ?></span>
                </div>
            </div>
        </div>
    </div>
</div>
