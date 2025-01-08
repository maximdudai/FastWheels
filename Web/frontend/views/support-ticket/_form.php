<?php

use common\models\Reservation;
use common\models\UserCar;
use yii\helpers\ArrayHelper;
use yii\helpers\Html;
use yii\widgets\ActiveForm;

/** @var yii\web\View $this */
/** @var common\models\SupportTicket $model */
/** @var yii\widgets\ActiveForm $form */

$reservationOptions = Reservation::find()->where(['clientId' => Yii::$app->user->id])->all();

$reserverCarInfo = [];

if (!empty($reservationOptions)) {
    foreach ($reservationOptions as $reservation) {
        $carInfo = UserCar::find()->where(['id' => $reservation->carId])->one();
        if ($carInfo) {
            $reserverCarInfo[] = $carInfo;
        }
    }
}

$reservationDropdownItems = [];

if (!empty($reservationOptions)) {
    foreach ($reservationOptions as $index => $reservation) {
        $carBrand = $reserverCarInfo[$reservation->id]->carBrand ?? 'Not Found';
        $reservationDropdownItems[$reservation->id] = "Car Model: {$carBrand}, Start: {$reservation->dateStart}, End: {$reservation->dateEnd}";
    }
}

?>

<div class="support-ticket-form">

    <?php $form = ActiveForm::begin([
        'action' => $model->isNewRecord ? ['create'] : ['update', 'id' => $model->id],
        'method' => 'post',
        'options' => ['class' => 'custom-form'],
    ]); ?>


    <?php if (!$model->isNewRecord): ?>
        <?= Html::hiddenInput('_method', 'PUT'); ?>
    <?php endif; ?>

    <?= $form->field($model, 'subject', [
        'options' => ['class' => 'form-group custom-field'],
        'inputOptions' => [
            'class' => 'form-control custom-input',
            'maxlength' => true,
            'placeholder' => 'Enter the subject of your ticket',
        ],
    ]) ?>

    <?= $form->field($model, 'content', [
        'options' => ['class' => 'form-group custom-field'],
        'inputOptions' => [
            'class' => 'form-control custom-textarea',
            'style' => 'min-height:10rem;',
            'placeholder' => 'Describe your help request',
        ],
    ])->textarea() ?>

    <?= $form->field($model, 'reservationId', [
        'options' => ['class' => 'form-group custom-field'],
    ])->dropDownList(
        $reservationDropdownItems,
        ['class' => 'form-control custom-dropdown', 'prompt' => 'Select a reservation']
    ) ?>

    <?php if (!$model->isNewRecord): ?>
        <?= Html::hiddenInput('_method', 'PUT') ?>
    <?php endif; ?>

    <div class="form-group">
        <?= Html::submitButton(
            $model->isNewRecord ? 'Send' : 'Save',
            ['class' => 'btn btn-primary custom-button mt-2']
        ) ?>
    </div>

    <?php if (!$model->isNewRecord): ?>
        <div class="form-group">
            <?= Html::a(
                'Delete',
                ['delete', 'id' => $model->id],
                [
                    'class' => 'btn btn-danger custom-button mt-2',
                    'data' => [
                        'confirm' => 'Are you sure you want to delete this ticket?',
                        'method' => 'post',
                    ],
                ]
            ) ?>
        </div>
    <?php endif; ?>

    <?php ActiveForm::end(); ?>

</div>