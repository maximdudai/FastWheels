<?php

use yii\helpers\Html;

/** @var yii\web\View $this */
/** @var common\models\SupportTicket $model */

$this->title = 'Update Support Ticket: ' . $model->id;
$this->params['breadcrumbs'][] = ['label' => 'Support Tickets', 'url' => ['index']];
$this->params['breadcrumbs'][] = ['label' => $model->id, 'url' => ['view', 'id' => $model->id]];
$this->params['breadcrumbs'][] = 'Update';
?>
<div class="support-ticket-update">

    <h1><?= Html::encode($this->title) ?></h1>

    <?= $this->render('_form', [
        'model' => $model,
    ]) ?>

</div>
