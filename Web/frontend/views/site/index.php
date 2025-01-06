<?php

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
        <div id="vehicle-carousel">
            <div id="carousel-inner">
                <?php foreach ($carPhotos as $index => $photo): ?>
                    <div class="carousel-item <?= $index === 0 ? 'active' : '' ?>">
                        <img src="<?= Yii::getAlias('@web') . '/' . $photo->photoUrl ?>" alt="Car Photo">
                        <div class="carousel-caption">
                            <h5><?= $photo->car->name ?? 'Unnamed Vehicle' ?></h5>
                            <p>Choose this car for your next adventure!</p>
                        </div>
                    </div>
                <?php endforeach; ?>
            </div>
            <a id="carousel-prev" href="#vehicle-carousel" role="button" data-bs-slide="prev">
                <span>Previous</span>
            </a>
            <a id="carousel-next" href="#vehicle-carousel" role="button" data-bs-slide="next">
                <span>Next</span>
            </a>
        </div>

        <div id="gallery-footer">
            <p id="gallery-description">
                These photos showcase our vehicle collection, generously shared by our valued users.
                Join us to share your own vehicle or rent the perfect car for your needs!
            </p>
            <a id="explore-button" href="user-car/index">Explore All Vehicles</a>
        </div>
    </div>
</div>
