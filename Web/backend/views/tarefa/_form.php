<?php

use yii\helpers\Html;
use yii\widgets\ActiveForm;

/** @var yii\web\View $this */
/** @var common\models\Tarefa $model */
/** @var yii\widgets\ActiveForm $form */


$clickedUser = \Yii::$app->request->get('id');

?>

<div class="tarefa-form">

    <?php $form = ActiveForm::begin(); ?>

     <?= $form->field($model, 'clientId')->hiddenInput(['value' => $clickedUser])->label(false) ?>

    <?= $form->field($model, 'descricao')->textarea(['rows' => 6]) ?>

    <?= 
        $form->field($model, 'feito')->textInput()
        ->dropDownList(
            ['0' => 'Nao feito', '1' => 'Feito']
        );
     ?>
    <div class="form-group">
        <?= Html::submitButton('Save', ['class' => 'btn btn-success']) ?>
    </div>

    <?php ActiveForm::end(); ?>

</div>
