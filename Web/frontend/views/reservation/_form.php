<?php

use yii\helpers\Html;
use yii\widgets\ActiveForm;

/** @var yii\web\View $this */
/** @var common\models\Reservation $model */
/** @var yii\widgets\ActiveForm $form */
?>

<div class="reservation-form">

    <?php $form = ActiveForm::begin(); ?>

    <?= $form->field($model, 'clientId')->textInput() ?>

    <?= $form->field($model, 'carId')->textInput() ?>

    <?= $form->field($model, 'dateStart')->textInput() ?>

    <?= $form->field($model, 'dateEnd')->textInput() ?>

    <?= $form->field($model, 'createAt')->textInput() ?>

    <?= $form->field($model, 'filled')->textInput() ?>

    <?= $form->field($model, 'value')->textInput(['maxlength' => true]) ?>

    <div class="form-group">
        <?= Html::submitButton('Save', ['class' => 'btn btn-success']) ?>
    </div>

    <?php ActiveForm::end(); ?>

</div>
