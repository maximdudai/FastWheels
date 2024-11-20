<?php

use yii\helpers\Html;
use yii\widgets\ActiveForm;

/** @var yii\web\View $this */
/** @var common\models\CarPhoto $model */
/** @var yii\widgets\ActiveForm $form */
?>

<div class="car-photo-form">

    <?php $form = ActiveForm::begin(); ?>

    <?= $form->field($model, 'carId')->textInput() ?>

    <?= $form->field($model, 'photoUrl')->textInput(['maxlength' => true]) ?>

    <div class="form-group">
        <?= Html::submitButton('Save', ['class' => 'btn btn-success']) ?>
    </div>

    <?php ActiveForm::end(); ?>

</div>
