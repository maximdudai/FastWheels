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
        <div class="contentCtoButton">
            <h1 id="companyName">Fast Wheels</h1>
            <p id="companyDescription">Rent a car for your next adventure!</p>
            <a id="explore-button" href="<?= Url::to(['user-car/index']) ?>" class="btn btn-primary">Explore</a>
            <a id="explore-button" href="<?= Url::to(['site/contact']) ?>" class="btn btn-primary">Contact Us</a>

        </div>
        <div class="demonstrativeImage">
            <img id="companyImageSource" src="<?= Yii::getAlias('@web') . '/uploads/landingImage.png' ?>" alt="Fast Wheels Logo">
        </div>
    </div>

    <section class="pt-5 pb-5">
        <div class="container">
            <div class="row">
                <div class="col-12">
                    <div id="carouselExampleIndicators2" class="carousel slide" data-ride="carousel">
                        <?php if (!empty($randomCars)): ?>
                            <div class="row">
                                <?php foreach ($randomCars as $car): ?>
                                    <div class="col-md-4 mb-3">
                                        <div class="card">
                                            <img class="img-fluid" alt="100%x280" src="<?= $car->getFirstPhoto() ?>">
                                            <div class="card-body">
                                                <h4 class="card-title"><?= Html::encode($car->carBrand . ' ' . $car->carModel) ?></h4>
                                                <div class="carAvailable">
                                                    <strong>Inicio </strong> <span><?= date('Y-m-d', strtotime($car->availableFrom)) ?></span>
                                                    <strong> Fim </strong> <span><?= date('Y-m-d', strtotime($car->availableTo)) ?></span>
                                                </div>
                                                <a href="<?= Url::to(['user-car/view', 'id' => $car->id]) ?>" class="my-2 btn btn-outline-warning">Detalhes</a>
                                            </div>
                                        </div>
                                    </div>
                                <?php endforeach; ?>
                            </div>
                        <?php endif; ?>
                    </div>
                </div>
            </div>

            <div class="row">
                <div class="col-lg-12">
                    <div class="benefitsForBeingWithUs">
                        <span>Why choose us ?</span>
                    </div>
                </div>
                <div class="col-md-4 mb-3">
                    <div class="card">
                        <img class="img-fluid benefitsImage" alt="100%x280" src="<?= Yii::getAlias('/uploads') . '/winwin.png' ?>">
                        <div class="card-body">
                            <h4 class="card-title">Why choose us ?</h4>
                            <div class="whyChooseUs">
                                Our rental service that offers a wide range of vehicles for you to choose from. Whether you need a car for a day or a month, we have you covered. Our team is dedicated to providing you with
                            </div>
                        </div>
                    </div>
                </div>
                <div class="col-md-4 mb-3">
                    <div class="card">
                        <img class="img-fluid benefitsImage" alt="100%x280" src="<?= Yii::getAlias('/uploads') . '/benefits.png' ?>">
                        <div class="card-body">
                            <h4 class="card-title">Benefits ? We have some</h4>
                            <div class="whyChooseUs">
                                Our rental service that offers a wide range of vehicles for you to choose from. Whether you need a car for a day or a month, we have you covered. Our team is dedicated to providing you with
                            </div>
                        </div>
                    </div>
                </div>
                <div class="col-md-4 mb-3">
                    <div class="card">
                        <img class="img-fluid benefitsImage" alt="100%x280" src="<?= Yii::getAlias('/uploads') . '/safety.png' ?>">
                        <div class="card-body">
                            <h4 class="card-title">Safety ? We have some</h4>
                            <div class="whyChooseUs">
                                Our rental service that offers a wide range of vehicles for you to choose from. Whether you need a car for a day or a month, we have you covered. Our team is dedicated to providing you with
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>
</div>
</section>

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
                    Yii::getAlias('/uploads') . '/default_nocar.jpg',
                    Yii::getAlias('/uploads') . '/default_nocar2.jpg',
                    Yii::getAlias('/uploads') . '/default_nocar3.jpg',
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