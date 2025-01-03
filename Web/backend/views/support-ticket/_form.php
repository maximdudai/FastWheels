<?php

use yii\helpers\Html;
use yii\widgets\ActiveForm;

/** @var yii\web\View $this */
/** @var common\models\SupportTicket $model */
/** @var yii\widgets\ActiveForm $form */
?>

<div class="support-ticket-form">

    <?php $form = ActiveForm::begin(); ?>

    <?= $form->field($model, 'clientId')->textInput() ?>

    <?= $form->field($model, 'content')->textInput(['maxlength' => true]) ?>

    <?= $form->field($model, 'createdAt')->textInput() ?>

    <?= $form->field($model, 'closed')->textInput() ?>

    <?= 
        $form->field($model, 'status')->textInput()
        ->dropDownList(
            [
                '0' => "Aberto",
                '1' => "Em analise",
                '2' => "A decorrer",
                '3' => "Em processamento",
                '4' => "Fechado",
            ]
        );
     ?>

    <div class="form-group">
        <?= Html::submitButton('Save', ['class' => 'btn btn-success']) ?>
    </div>

    <?php ActiveForm::end(); ?>

</div>
