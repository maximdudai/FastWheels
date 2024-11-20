<?php

use yii\helpers\Html;
use yii\widgets\ActiveForm;

/** @var yii\web\View $this */
/** @var backend\models\ReservationSearch $model */
/** @var yii\widgets\ActiveForm $form */
?>

<div class="reservation-search">

    <?php $form = ActiveForm::begin([
        'action' => ['index'],
        'method' => 'get',
    ]); ?>

    <?= $form->field($model, 'id') ?>

    <?= $form->field($model, 'clientId') ?>

    <?= $form->field($model, 'carId') ?>

    <?= $form->field($model, 'dateStart') ?>

    <?= $form->field($model, 'dateEnd') ?>

    <?php // echo $form->field($model, 'createAt') ?>

    <?php // echo $form->field($model, 'filled') ?>

    <?php // echo $form->field($model, 'value') ?>

    <div class="form-group">
        <?= Html::submitButton('Search', ['class' => 'btn btn-primary']) ?>
        <?= Html::resetButton('Reset', ['class' => 'btn btn-outline-secondary']) ?>
    </div>

    <?php ActiveForm::end(); ?>

</div>
