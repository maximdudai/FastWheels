<?php

use yii\helpers\Html;
use yii\widgets\ActiveForm;

/** @var yii\web\View $this */
/** @var common\models\SupportTicket $model */
/** @var yii\widgets\ActiveForm $form */
?>

<div class="support-ticket-form">

    <?php $form = ActiveForm::begin(); ?>

    <?= $form->field($model, 'content')->textarea(['placeholder' => 'Describe your help request', 'style' => 'min-height:10rem;']) ?>

    <div class="form-group">
        <?= Html::submitButton('Send', ['class' => 'btn btn-success mt-2']) ?>
    </div>

    <?php ActiveForm::end(); ?>

</div>
