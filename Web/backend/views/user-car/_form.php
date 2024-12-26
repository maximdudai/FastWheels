<?php

use yii\helpers\Html;
use yii\widgets\ActiveForm;

/** @var yii\web\View $this */
/** @var common\models\UserCar $model */
/** @var yii\widgets\ActiveForm $form */
?>

<div class="user-car-form">

    <?php $form = ActiveForm::begin(); ?>

    <?= $form->field($model, 'clientId')->textInput() ?>

    <?= $form->field($model, 'carBrand')->textInput(['maxlength' => true]) ?>

    <?= $form->field($model, 'carModel')->textInput(['maxlength' => true]) ?>

    <?= $form->field($model, 'carYear')->textInput() ?>

    <?= $form->field($model, 'carDoors')->textInput() ?>

    <?= $form->field($model, 'createdAt')->textInput() ?>

    <?= $form->field($model, 'status')->textInput() ?>

    <?= $form->field($model, 'availableFrom')->textInput() ?>

    <?= $form->field($model, 'availableTo')->textInput() ?>

    <div class="form-group">
        <?= Html::submitButton('Save', ['class' => 'btn btn-success']) ?>
    </div>

    <?php ActiveForm::end(); ?>

</div>
