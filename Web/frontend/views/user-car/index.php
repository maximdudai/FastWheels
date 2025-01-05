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
                <h2 class="car-name">
                    <?= Html::encode($car->carBrand) ?> <?= Html::encode($car->carModel) ?>
                </h2>

                <div class="year-trips">
                    <span><strong>Year:</strong> <?= Html::encode($car->carYear) ?></span>
                    <span><strong>Doors:</strong> <?= Html::encode($car->carDoors) ?></span>
                </div>

                <div class="availability">
                    <span><strong>From:</strong> <?= Html::encode($car->availableFrom) ?></span>
                    <span><strong>To:</strong> <?= Html::encode($car->availableTo) ?></span>
                </div>

                <div class="card-favorite">
                    <div class="card-favorite">
                        <?php if (Yii::$app->user->isGuest): ?>
                            <?= Html::button('❤', [
                                'class' => 'favorite-btn active-favorite',
                                'onclick' => "alert('You need to log in to add to favorites.'); window.location.href = '" . Url::to(['/site/login']) . "';",
                            ]) ?>
                        <?php else: ?>
                            <?= Html::beginForm(['user-car/favorite'], 'post') ?>
                            <?= Html::hiddenInput('carId', $car->id) ?>
                            <?= Html::submitButton('❤', ['class' => 'favorite-btn active-favorite']) ?>
                            <?= Html::endForm() ?>
                        <?php endif; ?>
                    </div>
                </div>
            </div>
        <?php endforeach; ?>
    </div>
</div>