<?php

use yii\helpers\Html;
use yii\helpers\Url;

/** @var yii\web\View $this */
/** @var frontend\models\UserCarSearch $searchModel */
/** @var yii\data\ActiveDataProvider $dataProvider */

$this->title = 'Available Cars';

?>
<div class="user-car-index">
    <h1 class="page-title"><?= Html::encode($this->title) ?></h1>

    <div class="cards-container">
        <?php foreach ($dataProvider->models as $car): ?>
            <div class="card">
                <a href="<?= Url::to(['user-car/view', 'id' => $car->id]) ?>" class="card-link">
                    <div class="card-content">
                        <div class="card-image">
                            <?php
                            $photos = $car->carphotos;
                            if (!empty($photos)) {
                                echo Html::img($photos[0]->photoPath, ['alt' => 'Car Photo']);
                            } else {
                                echo Html::img('/path/to/default-image.jpg', ['alt' => 'Default Car Photo']);
                            }
                            ?>
                        </div>
                        <div class="car-name-location">
                            <h2 class="car-name"><?= Html::encode($car->carBrand . ' ' . $car->carModel) ?></h2>
                        </div>
                        <div class="details">
                            <div class="year">
                                <strong>Year:</strong> <span><?= Html::encode($car->carYear) ?></span>
                            </div>
                            <div class="doors">
                                <strong>Doors:</strong> <span><?= Html::encode($car->carDoors) ?></span>
                            </div>
                            <div class="reviews">
                                <strong>Reviews:</strong> <span><?= !empty($car->carreviews) ? number_format(array_sum(array_column($car->carreviews, 'rating')) / count($car->carreviews), 1) : '0.0' ?></span>
                            </div>
                            <div class="location">
                                <strong>Location:</strong> <span><?= Html::encode($car->address) ?></span>
                            </div>
                            <div class="availability">
                                <strong>Start:</strong> <span><?= date('Y-m-d', strtotime($car->availableFrom)) ?></span>
                            </div>
                            <div class="availability">
                                <strong>End:</strong> <span><?= date('Y-m-d', strtotime($car->availableTo)) ?></span>
                            </div>
                        </div>
                    </div>
                </a>
                <div class="card-favorite">
                    <?php if (Yii::$app->user->isGuest): ?>
                        <?= Html::button('❤', [
                            'class' => 'favorite-btn',
                            'onclick' => "alert('You need to log in to add to favorites.'); window.location.href = '" . Url::to(['/site/login']) . "';",
                        ]) ?>
                    <?php else: ?>
                        <?= Html::beginForm(['user-car/favorite'], 'post') ?>
                        <?= Html::hiddenInput('carId', $car->id) ?>
                        <?= Html::submitButton('❤', ['class' => 'favorite-btn']) ?>
                        <?= Html::endForm() ?>
                    <?php endif; ?>
                </div>
            </div>
        <?php endforeach; ?>
    </div>
</div>
