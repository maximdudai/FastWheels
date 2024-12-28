<?php

use yii\helpers\Html;
use yii\widgets\ActiveForm;

/** @var yii\web\View $this */
/** @var frontend\models\UserCarSearch $model */
/** @var yii\widgets\ActiveForm $form */
?>

<div class="user-car-search">

    <?php $form = ActiveForm::begin([
        'action' => ['index'],
        'method' => 'get',
    ]); ?>

    <?= $form->field($model, 'id') ?>

    <?= $form->field($model, 'clientId') ?>

    <?= $form->field($model, 'carBrand') ?>

    <?= $form->field($model, 'carModel') ?>

    <?= $form->field($model, 'carYear') ?>

    <?php // echo $form->field($model, 'carDoors') ?>

    <?php // echo $form->field($model, 'createdAt') ?>

    <?php // echo $form->field($model, 'status') ?>

    <?php // echo $form->field($model, 'availableFrom') ?>

    <?php // echo $form->field($model, 'availableTo') ?>

    <div class="form-group">
        <?= Html::submitButton('Search', ['class' => 'btn btn-primary']) ?>
        <?= Html::resetButton('Reset', ['class' => 'btn btn-outline-secondary']) ?>
    </div>

    <?php ActiveForm::end(); ?>

</div>
