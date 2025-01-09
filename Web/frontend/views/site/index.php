<?php

use yii\helpers\Html;
use yii\helpers\Url;

/** @var yii\web\View $this */
/** @var yii\web\User $user */
/** @var common\models\CarPhoto[] $carPhotos */

$this->title = 'Fast Wheels';
?>
<div id="site-index">
    <div id="hero-section">
        <div id="hero-container">
            <h1 id="hero-title">Welcome to Fast Wheels!</h1>
            <p id="hero-subtitle">Renting a car has never been this easy — start your journey now!</p>
            <p>
                <?php if (Yii::$app->user->isGuest): ?>
                    <a id="login-button" href="site/login">Login Now</a>
                <?php endif; ?>
                <a id="about-button" href="site/about">About Us</a>
            </p>
        </div>
    </div>

    <div id="main-content">
        <h2 id="gallery-title">Our Vehicles</h2>
        <div id="vehicle-carousel" class="carousel slide" data-bs-ride="carousel">
            <div class="carousel-inner">
                <?php foreach ($carPhotos as $index => $photo): ?>
                    <div class="carousel-item <?= $index === 0 ? 'active' : '' ?>">
                        <img src="<?= Yii::getAlias('@carphotos') . '/' . $photo->photoUrl ?>" class="d-block w-100" alt="Car Photo">
                        <div class="carousel-caption">
                            <h5><?= Html::encode($photo->car->carBrand . ' ' . $photo->car->carModel ?? 'Unnamed Vehicle') ?></h5>
                            <p>Choose this car for your next adventure!</p>
                            <a id="explore-button" href="<?= Url::to(['user-car/view', 'id' => $photo->car->id]) ?>" class="btn btn-primary">See</a>
                        </div>
                    </div>
                <?php endforeach; ?>

                <?php if (empty($carPhotos)): ?>
                    <?php
                    $defaultImages = [
                        Yii::getAlias('@uploads') . '/default_nocar.jpg',
                        Yii::getAlias('@uploads') . '/default_nocar2.jpg',
                        Yii::getAlias('@uploads') . '/default_nocar3.jpg',
                    ];
                    ?>
                    <?php foreach ($defaultImages as $index => $defaultImage): ?>
                        <div class="carousel-item <?= $index === 0 ? 'active' : '' ?>">
                            <img src="<?= $defaultImage ?>" class="d-block w-100" alt="Default Car Photo <?= $index + 1 ?>">
                            <div class="carousel-caption">
                                <h5>Join our collection!</h5>
                            </div>
                        </div>
                    <?php endforeach; ?>
                <?php endif; ?>
            </div>

            <button class="carousel-control-prev" type="button" data-bs-target="#vehicle-carousel" data-bs-slide="prev">
                <span class="carousel-control-prev-icon" aria-hidden="true"></span>
                <span class="visually-hidden">Previous</span>
            </button>
            <button class="carousel-control-next" type="button" data-bs-target="#vehicle-carousel" data-bs-slide="next">
                <span class="carousel-control-next-icon" aria-hidden="true"></span>
                <span class="visually-hidden">Next</span>
            </button>
        </div>

        <div id="gallery-footer">
            <h3 id="assistance-title">Need Assistance?</h3>
            <p id="assistance-description">
                We are here to help! If you have any questions about renting or listing a vehicle, feel free to reach out to our support center.
                Our team is dedicated to ensuring your experience is smooth and hassle-free.
            </p>
            <a id="support-button" href="<?= Url::to(['support-ticket/index']) ?>" class="btn btn-secondary">Contact Support</a>
        </div>

    </div>
</div>
