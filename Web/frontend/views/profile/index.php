<?php

/** @var yii\web\View $this */
/** @var yii\bootstrap5\ActiveForm $form */
/** @var \frontend\models\ContactForm $model */

use yii\bootstrap5\Html;
use yii\bootstrap5\ActiveForm;
use yii\captcha\Captcha;


/** @var common\models\Client $model */

$this->title = 'Profile';
$this->params['breadcrumbs'][] = $this->title;
?>
<div class="site-contact">
    <h1><?= Html::encode($this->title) ?></h1>


    <div class="container mt-5">
        <div class="row">
            <!-- Sidebar -->
            <div class="col-md-3">
                <div class="card shadow">
                    <div class="card-body text-center">
                        <img src="https://via.placeholder.com/150" alt="Profile Image" class="img-fluid rounded-circle mb-3">
                        <h5 class="card-title"><?= Html::encode($dataProvider->name ?? 'Name') ?></h5>
                        <p class="text-muted"><?= Html::encode($dataProvider->email ?? 'Email') ?></p>
                    </div>
                </div>
            </div>

            <!-- Profile Details -->

            <div class="col-lg">
                <div class="card shadow">
                    <ul class="nav nav-tabs" id="myTab" role="tablist">
                        <li class="nav-item">
                            <button class="nav-link active" id="profile-tab" data-bs-toggle="tab" data-bs-target="#profile-content" type="button" role="tab" aria-controls="profile-content" aria-selected="true">
                                Account Details
                            </button>
                        </li>
                        <li class="nav-item">
                            <button class="nav-link" id="vehicles-tab" data-bs-toggle="tab" data-bs-target="#vehicles" type="button" role="tab" aria-controls="vehicles" aria-selected="false">
                                Vehicles
                            </button>
                        </li>
                        <li class="nav-item">
                            <button class="nav-link" id="favorites-tab" data-bs-toggle="tab" data-bs-target="#favorites" type="button" role="tab" aria-controls="favorites" aria-selected="false">
                                Favorites
                            </button>
                        </li>
                        <li class="nav-item">
                            <button class="nav-link" id="reservations" data-bs-toggle="tab" data-bs-target="#reservations" type="button" role="tab" aria-controls="reservations" aria-selected="false">
                                Reservations
                            </button>
                        </li>
                    </ul>

                    <div class="tab-content mt-3" id="myTabContent">
                        <!-- Profile Details Tab -->
                        <div class="tab-pane fade show active" id="profile-content" role="tabpanel" aria-labelledby="profile-tab">
                            <div class="card shadow text-black">
                                <div class="card-header bg-primary text-white">
                                    <h5>Profile Details</h5>
                                </div>

                                <div class="row mt-3">

                                    <?php $form = ActiveForm::begin(['id' => 'personal-data']); ?>
                                    <div class="row mb-3 d-flex align-items-center">
                                        <div class="col-4 text-end fw-bold"><?= 'Name' ?></div>
                                        <div class="col-8">
                                            <?= $form->field($dataProvider, 'name')->textInput(['class' => 'form-control', 'placeholder' => 'Name'])->label(false) ?>
                                        </div>
                                    </div>

                                    <div class="row mb-3 d-flex align-items-center">
                                        <div class="col-4 text-end fw-bold"><?= 'Email' ?></div>
                                        <div class="col-8">
                                            <?= $form->field($dataProvider, 'email')->textInput(['class' => 'form-control', 'placeholder' => 'Email'])->label(false) ?>
                                        </div>
                                    </div>

                                    <div class="row mb-3 d-flex align-items-center">
                                        <div class="col-4 text-end fw-bold"><?= 'Phone' ?></div>
                                        <div class="col-8">
                                            <?= $form->field($dataProvider, 'phone')->textInput(['class' => 'form-control', 'placeholder' => 'Phone'])->label(false) ?>
                                        </div>
                                    </div>

                                    <div class="row mb-3 d-flex align-items-center">
                                        <div class="col-4 text-end fw-bold"><?= 'IBAN' ?></div>
                                        <div class="col-8">
                                            <?= $form->field($dataProvider, 'iban')->textInput(['class' => 'form-control', 'placeholder' => 'IBAN'])->label(false) ?>
                                        </div>
                                    </div>


                                    <div class="row mb-3 d-flex align-items-center">
                                        <div class="col-4 text-end fw-bold"><?= 'Role Name' ?></div>
                                        <div class="col-8"><?= Html::encode($dataProvider->role->roleName ?? '-') ?></div>
                                    </div>

                                    <div class="row mb-3 d-flex align-items-center">
                                        <div class="col-4 text-end fw-bold"><?= 'Created At' ?></div>
                                        <div class="col-8"><?= Html::encode($dataProvider->createdAt ?? '-') ?></div>
                                    </div>

                                    <div class="row mb-3 d-flex align-items-center">
                                        <div class="col-4 text-end fw-bold"><?= 'Balance' ?></div>
                                        <div class="col-8"><?= Html::encode($dataProvider->balance ?? '-') ?></div>
                                    </div>
                                    <?php ActiveForm::end(); ?>
                                </div>

                            </div>
                        </div>

                        <!-- Vehicles  -->
                        <div class="tab-pane fade" id="vehicles" role="tabpanel" aria-labelledby="vehicles">
                            <p>This is the content for the Link tab.</p>
                        </div>

                        <!-- Favorites -->
                        <div class="tab-pane fade" id="favorites" role="tabpanel" aria-labelledby="favorites">
                            <p>This is the content for another link tab.</p>
                        </div>

                        <!-- Reservations -->
                        <div class="tab-pane fade" id="reservations" role="tabpanel" aria-labelledby="reservations">
                            <p>This is the content for another link tab.</p>
                        </div>

                    </div>
                </div>
            </div>
        </div>
    </div>
</div>

</div>