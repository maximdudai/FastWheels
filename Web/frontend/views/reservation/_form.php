<?php

use backend\models\Taxes;
use yii\helpers\Html;
use yii\widgets\ActiveForm;

$loggedUser = Yii::$app->user->id;
$carId = Yii::$app->request->get('id');
$currentTimpestamp = date('Y-m-d H:i:s');
$getCarValue = \common\models\UserCar::find()->where(['id' => $carId])->one();

$businessFee = Taxes::find()->where(['id' => 1])->one()->tax_value;
$calculateFee = $getCarValue->priceDay * $businessFee;

$currentTimestamp = date('Y-m-d H:i:s');

/** @var yii\web\View $this */
/** @var common\models\Reservation $model */
/** @var yii\widgets\ActiveForm $form */
?>

<div class="reservation-form">

    <?php $form = ActiveForm::begin(); ?>

    <?= $form->field($model, 'clientId')->hiddenInput(['value' => $loggedUser])->label(false) ?>
    <?= $form->field($model, 'carId')->hiddenInput(['value' => $carId])->label(false) ?>
    <div class="row mt-5">
        <div class="col">
            <?= $form->field($model, 'dateStart')->textInput(['type' => 'date', 'value' => $currentTimpestamp])->label('Start Date') ?>
        </div>
    </div>
    <div class="row mt-5">
        <div class="col">
            <?= $form->field($model, 'dateEnd')->textInput(['type' => 'date', 'value' => $currentTimpestamp])->label('End Date') ?>
        </div>
    </div>

    <div class="row mt-5">
        <div class="col">
            <div class="form-group">
                <?= Html::label("Contacto", "phone", ['class' => 'form-label']) ?>
                <?= Html::textInput('phone', '', ['class' => 'form-control', 'maxlength' => true]) ?>
            </div>
        </div>
    </div>

    <div class="row mt-5">
        <div class="col">
            <div class="form-group">
                <?= Html::label("Localização", "address", ['class' => 'form-label']) ?>
                <?= Html::textInput('address', '', ['class' => 'form-control', 'maxlength' => true]) ?>
            </div>
        </div>
    </div>


    <?= $form->field($model, 'filled')->hiddenInput(['value' => 0])->label(false) ?>
    <?= $form->field($model, 'createAt')->hiddenInput(['value' => $currentTimestamp])->label(false) ?>

    <?= $form->field($model, 'carValue')->hiddenInput(['value' => $getCarValue->priceDay])->label(false) ?>
    <?= $form->field($model, 'feeValue')->hiddenInput(['value' => $businessFee])->label(false) ?>
    <?= $form->field($model, 'value')->hiddenInput(['value' => $calculateFee])->label(false) ?>

    <div class="form-group mt-5">
        <?= Html::submitButton('Save', ['class' => 'btn btn-success']) ?>
    </div>

    <?php ActiveForm::end(); ?>

</div>