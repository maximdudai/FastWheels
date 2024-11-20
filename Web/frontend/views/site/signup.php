<?php

/** @var yii\web\View $this */
/** @var yii\bootstrap5\ActiveForm $form */
/** @var \frontend\models\SignupForm $model */

use yii\bootstrap5\Html;
use yii\bootstrap5\ActiveForm;

$this->title = 'Sign Up';
?>
<div class="container signup-container">
    <div class="row signup-row">
        <!-- Painel Esquerdo -->
        <div class="col-md-6 signup-left-panel">
            <h2>Crie a sua conta</h2>
            <p>Guarde e avalie todas as suas experiências</p>
            <?= Html::a('Voltar', Yii::$app->homeUrl, ['class' => 'btn btn-secondary signup-back-btn']) ?>
        </div>

        <!-- Painel Direito -->
        <div class="col-md-6 signup-right-panel">
            <div class="signup-form-container">
                <h1><?= Html::encode($this->title) ?></h1>

                <?php $form = ActiveForm::begin(['id' => 'form-signup']); ?>

                <!-- Nome -->
                <?= $form->field($model, 'username')->textInput(['placeholder' => 'Nome'])->label(false) ?>

                <!-- Email -->
                <?= $form->field($model, 'email')->textInput(['type' => 'email', 'placeholder' => 'Email'])->label(false) ?>

                <!-- Palavra-passe -->
                <?= $form->field($model, 'password')->passwordInput(['placeholder' => 'Palavra-passe'])->label(false) ?>

                <!-- Confirmar Palavra-passe -->
                <?= $form->field($model, 'confirmPassword')->passwordInput(['placeholder' => 'Confirmar Palavra-passe'])->label(false) ?>

                <!-- Checkbox de Termos de Utilização -->
                <div class="form-group signup-checkbox-container">
                    <?= $form->field($model, 'termsAccepted')->checkbox(['label' => 'Aceito os termos de utilização']) ?>
                </div>

                <!-- Botão de Submeter -->
                <div class="form-group signup-submit-group">
                    <?= Html::submitButton('Enviar', ['class' => 'btn btn-primary signup-submit-btn', 'name' => 'signup-button']) ?>
                </div>

                <?php ActiveForm::end(); ?>

                <!-- Link para Login -->
                <div class="signup-login-link">
                    <p>Já tem uma conta? <?= Html::a('Inicie sessão aqui', ['/site/login']) ?></p>
                </div>
            </div>
        </div>
    </div>
</div>
