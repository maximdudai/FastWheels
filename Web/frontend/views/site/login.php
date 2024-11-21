<?php

/** @var yii\web\View $this */
/** @var yii\bootstrap5\ActiveForm $form */
/** @var \frontend\models\LoginForm $model */

use yii\bootstrap5\Html;
use yii\bootstrap5\ActiveForm;

$this->title = 'Log In';
?>
<div class="container login-container">
    <div class="row login-row">
        <!-- Painel Esquerdo -->
        <div class="col-md-6 login-left-panel">
            <h2>Entre na sua conta</h2>
            <p>Guarde e avalie todas as suas experiências</p>
            <?= Html::a('Voltar', Yii::$app->homeUrl, ['class' => 'btn btn-secondary login-back-btn']) ?>
        </div>

        <!-- Painel Direito -->
        <div class="col-md-6 login-right-panel">
            <div class="login-form-container">
                <h1><?= Html::encode($this->title) ?></h1>

                <!-- Error Messages -->
                <?php if (Yii::$app->session->hasFlash('error')): ?>
                    <div class="alert alert-danger">
                        <?= Yii::$app->session->getFlash('error') ?>
                    </div>
                <?php endif; ?>

                <!-- Formulário de Login -->
                <?php $form = ActiveForm::begin(['id' => 'login-form']); ?>

                <!-- Campo Nome -->
                <?= $form->field($model, 'username')->textInput([
                    'placeholder' => 'Nome',
                ])->label(false) ?>

                <!-- Campo Palavra-passe -->
                <?= $form->field($model, 'password')->passwordInput([
                    'placeholder' => 'Palavra-passe',
                ])->label(false) ?>

                <!-- Checkbox Manter o Login -->
                <div class="form-group login-checkbox-container">
                    <?= $form->field($model, 'rememberMe')->checkbox(['label' => 'Manter o login']) ?>
                </div>

                <!-- Botão de Submissão -->
                <div class="form-group">
                    <?= Html::submitButton('Enviar', [
                        'class' => 'btn btn-primary login-submit-btn',
                        'name' => 'login-button',
                    ]) ?>
                </div>

                <?php ActiveForm::end(); ?>

                <!-- Links adicionais -->
                <div class="login-link">
                    <p>Esqueceu a password? <?= Html::a('Recuperar password aqui', ['/site/request-password-reset']) ?></p>
                </div>
                <div class="login-link">
                    <p>Ainda não criou conta? <?= Html::a('Criar conta aqui', ['/site/signup']) ?></p>
                </div>
            </div>
        </div>
    </div>
</div>

