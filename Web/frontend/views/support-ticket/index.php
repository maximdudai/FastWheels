<?php

use yii\helpers\Html;

/** @var yii\web\View $this */
/** @var array $userTickets */

$this->title = 'Main Tickets';
?>
<div class="support-ticket-index">

    <h1 class="page-title"><?= Html::encode($this->title) ?></h1>

    <div class="help-center-container">
        <input type="radio" id="driver-help" name="help-center" checked>
        <input type="radio" id="owner-help" name="help-center">

        <?php if (!Yii::$app->user->isGuest): ?>
            <input type="radio" id="my-tickets" name="help-center">
        <?php endif; ?>

        <div class="help-center-buttons">
            <label for="driver-help">Driver help center</label>
            <label for="owner-help">Owner help center</label>
            <?php if (!Yii::$app->user->isGuest): ?>
                <label for="my-tickets">My Tickets</label>
            <?php endif; ?>
        </div>
        <div class="help-section driver">
            <ul>
                <li><?= Html::a('Como posso criar uma conta na plataforma?', ['static-view', 'topic' => 'driver-create-account']) ?></li>
                <li><?= Html::a('É possível cancelar uma reserva?', ['static-view', 'topic' => 'driver-cancel-reservation']) ?></li>
                <li><?= Html::a('O que devo fazer em caso de acidente com o veículo alugado?', ['static-view', 'topic' => 'driver-accident']) ?></li>
                <li><?= Html::a('Existe um limite de quilómetros durante o período de aluguer?', ['static-view', 'topic' => 'driver-mileage-limit']) ?></li>
                <li><?= Html::a('Como posso reportar problemas com o veículo antes ou durante o aluguer?', ['static-view', 'topic' => 'driver-report-issues']) ?></li>
            </ul>
        </div>
        <div class="help-section owner">
            <ul>
                <li><?= Html::a('Como listar um veículo para aluguer na plataforma?', ['static-view', 'topic' => 'owner-list-vehicle']) ?></li>
                <li><?= Html::a('Quais são os métodos de pagamento aceites?', ['static-view', 'topic' => 'owner-payment-methods']) ?></li>
                <li><?= Html::a('Os veículos são verificados pela plataforma antes de serem listados?', ['static-view', 'topic' => 'owner-vehicle-verification']) ?></li>
                <li><?= Html::a('O que acontece se o veículo for devolvido com danos?', ['static-view', 'topic' => 'owner-vehicle-damages']) ?></li>
                <li><?= Html::a('Posso definir as condições do aluguer, como limite de quilómetros?', ['static-view', 'topic' => 'owner-set-conditions']) ?></li>
            </ul>
        </div>

        <?php if (!Yii::$app->user->isGuest): ?>
            <div class="help-section tickets">
                <ul>
                    <?php if (!empty($userTickets)): ?>
                        <?php foreach ($userTickets as $ticket): ?>
                            <li><?= Html::a(Html::encode($ticket['subject']), ['update', 'id' => $ticket['id']]) ?></li>
                        <?php endforeach; ?>
                    <?php else: ?>
                        <li>No tickets found.</li>
                    <?php endif; ?>
                </ul>
            </div>
        <?php endif; ?>
    </div>

    <?php if (Yii::$app->user->isGuest): ?>
        <div class="button-container">
            <?= Html::a('Login to create new ticket', ['site/login'], ['class' => 'btn btn-primary custom-btn']) ?>
        </div>
    <?php else: ?>
        <div class="button-container">
            <?= Html::a('Create Support Ticket', ['create'], ['class' => 'btn btn-primary custom-btn']) ?>
        </div>
    <?php endif; ?>

</div>
