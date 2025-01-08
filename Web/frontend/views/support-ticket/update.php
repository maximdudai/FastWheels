<?php

use common\models\Reservation;
use common\models\UserCar;
use yii\helpers\Html;
use yii\widgets\ActiveForm;

/** @var yii\web\View $this */
/** @var common\models\SupportTicket $model */
/** @var array $reservationDropdownItems */

$this->title = 'Update Support Ticket'
?>

<div class="support-ticket-container">
    <h1 class="page-title"><?= Html::encode($this->title) ?></h1>

    <div class="support-ticket-form">
        <?php $form = ActiveForm::begin([
            'action' => ['update', 'id' => $model->id],
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
            <?= Html::submitButton('Save', ['class' => 'btn btn-primary custom-button mt-2']) ?>
        </div>

        <?php ActiveForm::end(); ?>
    </div>

    <div class="button-container">
        <?= Html::a('Back', ['index'], ['class' => 'btn btn-secondary']) ?>
        <?= Html::a(
            'Delete',
            ['delete', 'id' => $model->id],
            [
                'class' => 'btn btn-danger',
                'data' => [
                    'confirm' => 'Are you sure you want to delete this ticket?',
                    'method' => 'post',
                ],
            ]
        ) ?>
    </div>
</div>
