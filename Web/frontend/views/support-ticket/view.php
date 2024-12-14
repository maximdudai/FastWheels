<?php

use common\models\SupportTicket;
use yii\helpers\Html;
use yii\widgets\DetailView;

/** @var yii\web\View $this */
/** @var common\models\SupportTicket $model */

$this->title = 'Support Ticket ' . $model->id;
$this->params['breadcrumbs'][] = ['label' => 'Support Tickets', 'url' => ['index']];
$this->params['breadcrumbs'][] = $this->title;
\yii\web\YiiAsset::register($this);
?>
<div class="support-ticket-view">

    <p>
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
            'content',
            'createdAt',
            'closed' => [
                'attribute' => 'closed',
                'value' => function (SupportTicket $model) {
                    return $model->closed ? 'Yes' : 'No';
                }
            ],
        ],
    ]) ?>

</div>
