<?php


require_once __DIR__ . '/../../../common/utils/getCarBrandById.php';
use function CarBrand\getCarBrandById;

use common\models\Reservation;
use common\models\UserCar;
use yii\helpers\Html;
use yii\widgets\ActiveForm;

/** @var yii\web\View $this */
/** @var common\models\SupportTicket $model */
/** @var yii\widgets\ActiveForm $form */

$reservationOptions = Reservation::find()->where(['clientId' => Yii::$app->user->id])->all();

$reservationDropdownItems = []; // Add "None" as the first option

if (!empty($reservationOptions)) {
    foreach ($reservationOptions as $index => $reservation) {
        
        $carBrand = getCarBrandById($reservation->carId);
        $reservationDropdownItems[$reservation->id] = "Car Model: {$carBrand}, Start: {$reservation->dateStart}, End: {$reservation->dateEnd}";
    }
}

?>

<div class="support-ticket-form">

    <?php $form = ActiveForm::begin([
        'action' => ['create'], // Define apenas para criação
        'method' => 'post',
        'options' => ['class' => 'custom-form'],
    ]); ?>

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

    <div class="form-group">
        <?= Html::submitButton('Send', ['class' => 'btn btn-primary custom-button mt-2']) ?>
    </div>

    <?php ActiveForm::end(); ?>

</div>