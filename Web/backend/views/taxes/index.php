<?php

use common\models\Client;
use yii\helpers\Html;
use yii\widgets\ActiveForm;

/** @var yii\web\View $this */
/** @var app\models\TaxesSearch $searchModel */
/** @var yii\data\ActiveDataProvider $dataProvider */

$this->title = 'Taxes';
$this->params['breadcrumbs'][] = $this->title;

$isUserAdmin = Client::isUserAdmin(Yii::$app->user->identity->id);

?>
<div class="taxes-index" style="max-width: 600px; margin: 0 auto; padding: 20px; border: 1px solid #ddd; border-radius: 8px; background-color: #f9f9f9;">
    <h2 style="text-align: center; color: #333; margin-bottom: 20px;">Taxes</h2>

    <ul style="color: #555; font-size: 16px; margin-top: 20px;">
        <li><strong>Manutenção da Plataforma:</strong> Ajuda a cobrir os custos operacionais da plataforma, garantindo um serviço contínuo e de qualidade para todos os utilizadores.</li>
        <li><strong>Suporte ao Cliente:</strong> Financia o suporte ao cliente para resolver dúvidas e problemas relacionados aos alugueres.</li>
        <li><strong>Seguro e Proteção:</strong> Contribui para a cobertura de seguros que protegem tanto os locadores quanto os locatários em caso de imprevistos.</li>
        <li><strong>Desenvolvimento e Melhorias:</strong> Investe no desenvolvimento de novas funcionalidades e melhorias na interface da plataforma.</li>
        <li><strong>Segurança e Confiabilidade:</strong> Garante processos seguros, como pagamentos protegidos e verificação de informações dos utilizadores.</li>
        <li><strong>Promoção de Veículos:</strong> Permite maior visibilidade e alcance para os veículos listados na plataforma.</li>
    </ul>


    <p style="background-color: #eef; color: #333; font-size: 16px; padding: 10px; border: 1px solid #aac; border-radius: 5px; text-align: center;">
        <strong>Current Tax Value:</strong> <?= Html::encode($currentTaxValue) ?>
        <span>€</span>
    </p>

    <?php if (!Yii::$app->user->isGuest && $isUserAdmin): ?>
        <?php $form = ActiveForm::begin([
            'action' => ['update'], // Points to the 'update' action
            'method' => 'post',
        ]); ?>

        <?= $form->field($searchModel, 'tax_value')->textInput([
            'type' => 'number',
            'step' => '0.01',
            'min' => '1.00',
            'placeholder' => 'Enter tax value',
            'style' => 'margin-top: 15px; font-size: 16px;',
        ])->label(false) ?>

        <div class="form-group" style="text-align: center; margin-top: 20px;">
            <?= Html::submitButton('Save', ['class' => 'btn btn-success', 'style' => 'font-size: 16px; padding: 10px 20px;']) ?>
        </div>

        <?php ActiveForm::end(); ?>
    <?php else: ?>
        <p style="color:red;">You do not have permission to manage company taxes.</p>
    <?php endif; ?>


    <?php if (Yii::$app->session->hasFlash('success')): ?>
        <div class="alert alert-success">
            <?= Yii::$app->session->getFlash('success') ?>
        </div>
    <?php endif; ?>

    <?php if (Yii::$app->session->hasFlash('error')): ?>
        <div class="alert alert-danger">
            <?= Yii::$app->session->getFlash('error') ?>
        </div>
    <?php endif; ?>
</div>