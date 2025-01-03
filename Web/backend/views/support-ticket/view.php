<?php

use yii\helpers\Html;
use yii\widgets\DetailView;

require_once __DIR__ . '/../../../common/utils/ticketstatus.php';
use function TicketStatus\getTicketStatus;

/** @var yii\web\View $this */
/** @var common\models\SupportTicket $model */



$this->title = $model->id;
$this->params['breadcrumbs'][] = ['label' => 'Support Tickets', 'url' => ['index']];
$this->params['breadcrumbs'][] = $this->title;
\yii\web\YiiAsset::register($this);
?>
<div class="support-ticket-view">

    <h1><?= Html::encode($this->title) ?></h1>

    <p>
        <?= Html::a('Update', ['update', 'id' => $model->id], ['class' => 'btn btn-primary']) ?>
        <?= Html::a('Delete', ['delete', 'id' => $model->id], [
            'class' => 'btn btn-danger',
            'data' => [
                'confirm' => 'Are you sure you want to delete this item?',
                'method' => 'post',
            ],
        ]) ?>
    </p>

    <?= DetailView::widget([
        'model' => $model,
        'attributes' => [
            'id',
            'clientId',
            'content',
            'createdAt',
            'closed',
            'status' => [
                'attribute' => 'status',
                'value' => function ($model) {
                    return getTicketStatus($model->status);
                }
            ],
        ],
    ]) ?>

</div>
