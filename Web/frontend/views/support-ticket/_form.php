<?php


require_once __DIR__ . '/../../../common/utils/getCarBrandById.php';
use function CarBrand\getCarBrandById;

use common\models\Reservation;
use common\models\UserCar;
use yii\helpers\ArrayHelper;
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
        'id' => 'support-ticket-form',
    ]);  ?>

    <?= $form->field($model, 'subject')->textInput(['maxlength' => true, 'placeholder' => 'Enter the subject of your ticket']) ?>

    <?= $form->field($model, 'content')->textarea(['maxlength' => true, 'placeholder' => 'Describe your help request', 'style' => 'min-height:10rem;']) ?>
    <!-- verify if user has one ore more reservation/s, then show info about them  -->
    <?php if (!empty($reservationOptions)): ?>
        <?= $form->field($model, 'reservationId')->dropDownList(
            $reservationDropdownItems ?? ['None' => 'No reservations found'],
            ['prompt' => 'Select a reservation']
        ); ?>
    <?php endif; ?>

    <div class="form-group">
        <?= Html::submitButton('Send', ['class' => 'btn btn-success mt-2']) ?>
    </div>

    <?php ActiveForm::end(); ?>

</div>