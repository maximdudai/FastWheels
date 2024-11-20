<?php

use yii\helpers\Html;
use yii\widgets\ActiveForm;

/** @var yii\web\View $this */
/** @var common\models\Localization $model */
/** @var yii\widgets\ActiveForm $form */
?>

<div class="localization-form">

    <?php $form = ActiveForm::begin(); ?>

    <?= $form->field($model, 'carId')->textInput() ?>

    <?= $form->field($model, 'locationCity')->textInput(['maxlength' => true]) ?>

    <?= $form->field($model, 'locationX')->textInput() ?>

    <?= $form->field($model, 'locationY')->textInput() ?>

    <div class="form-group">
        <?= Html::submitButton('Save', ['class' => 'btn btn-success']) ?>
    </div>

    <?php ActiveForm::end(); ?>

</div>
