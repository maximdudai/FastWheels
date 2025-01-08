<?php

use yii\helpers\Html;

/** @var yii\web\View $this */

$this->title = 'Support Tickets';
?>
<div class="support-ticket-index">

    <h1 class="page-title"><?= Html::encode($this->title) ?></h1>

    <div class="help-center-container">
        <input type="radio" id="driver-help" name="help-center" checked>
        <input type="radio" id="owner-help" name="help-center">

        <div class="help-center-buttons">
            <label for="driver-help">Driver help center</label>
            <label for="owner-help">Owner help center</label>
        </div>

        <div class="help-section driver">
            <ul>
                <li>Como posso criar uma conta na plataforma?</li>
                <li>É possível cancelar uma reserva?</li>
                <li>O que devo fazer em caso de acidente com o veículo alugado?</li>
                <li>Existe um limite de quilómetros durante o período de aluguer?</li>
                <li>Como posso reportar problemas com o veículo antes ou durante o aluguer?</li>
            </ul>
        </div>
        <div class="help-section owner">
            <ul>
                <li>Como listar um veículo para aluguer na plataforma?</li>
                <li>Quais são os métodos de pagamento aceites?</li>
                <li>Os veículos são verificados pela plataforma antes de serem listados?</li>
                <li>O que acontece se o veículo for devolvido com danos?</li>
                <li>Posso definir as condições do aluguer, como limite de quilómetros?</li>
            </ul>
        </div>

    </div>

    <div class="button-container">
        <?= Html::a('Create Support Ticket', ['create'], ['class' => 'btn btn-primary custom-btn']) ?>
        <?= Html::a('My Tickets', ['view'], ['class' => 'btn btn-primary custom-btn']) ?>
    </div>

</div>
