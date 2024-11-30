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
                    <div class="card-header bg-primary text-white">
                        <h5>Profile Details</h5>
                    </div>
                    <div class="card-body text-black">
                        <div class="row mb-3">
                            <div class="col-4 text-end fw-bold"><?= 'ID:' ?></div>
                            <div class="col-8"><?= Html::encode($dataProvider->id ?? '-') ?></div>
                        </div>
                        <div class="row mb-3">
                            <div class="col-4 text-end fw-bold"><?= 'Name:' ?></div>
                            <div class="col-8"><?= Html::encode($dataProvider->name ?? '-') ?></div>
                        </div>
                        <div class="row mb-3">
                            <div class="col-4 text-end fw-bold"><?= 'Email:' ?></div>
                            <div class="col-8"><?= Html::encode($dataProvider->email ?? '-') ?></div>
                        </div>
                        <div class="row mb-3">
                            <div class="col-4 text-end fw-bold"><?= 'Phone:' ?></div>
                            <div class="col-8"><?= Html::encode($dataProvider->phone ?? '-') ?></div>
                        </div>
                        <div class="row mb-3">
                            <div class="col-4 text-end fw-bold"><?= 'Role Name:' ?></div>
                            <div class="col-8"><?= Html::encode($dataProvider->role->roleName ?? '-') ?></div>
                        </div>
                        <div class="row mb-3">
                            <div class="col-4 text-end fw-bold"><?= 'Created At:' ?></div>
                            <div class="col-8"><?= Html::encode($dataProvider->createdAt ?? '-') ?></div>
                        </div>
                        <div class="row mb-3">
                            <div class="col-4 text-end fw-bold"><?= 'Balance:' ?></div>
                            <div class="col-8"><?= Html::encode($dataProvider->balance ?? '-') ?></div>
                        </div>
                        <div class="row mb-3">
                            <div class="col-4 text-end fw-bold"><?= 'IBAN:' ?></div>
                            <div class="col-8"><?= Html::encode($dataProvider->iban ?? '-') ?></div>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>
</div>

</div>