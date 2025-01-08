<?php

use yii\helpers\Html;
use yii\helpers\Url;
use yii\grid\GridView;
use yii\widgets\Pjax;

/** @var yii\web\View $this */
/** @var yii\data\ActiveDataProvider $dataProvider */
/** @var frontend\models\SupportTicketSearch $searchModel */

$this->title = 'My Support Tickets';
$this->params['breadcrumbs'][] = $this->title;
?>

<div class="support-ticket-index">

    <h1 class="page-title"><?= Html::encode($this->title) ?></h1>

    <p>
        <?= Html::a('Create New Ticket', ['create'], ['class' => 'btn btn-success']) ?>
    </p>

    <?php Pjax::begin(); ?>
    <?= GridView::widget([
        'dataProvider' => $dataProvider,
        'filterModel' => $searchModel,
        'columns' => [
            ['class' => 'yii\grid\SerialColumn'],

            'createdAt:datetime',
            'subject',
            'content:shortText',
            [
                'attribute' => 'closed',
                'format' => 'raw',
                'value' => function ($model) {
                    return $model->closed ?
                        '<span class="badge bg-success">Closed</span>' :
                        '<span class="badge bg-warning">Open</span>';
                },
                'filter' => [
                    0 => 'Open',
                    1 => 'Closed',
                ],
            ],
            [
                'attribute' => 'reservationId',
                'value' => function ($model) {
                    return $model->reservationId ? '#' . $model->reservationId : 'N/A';
                },
            ],
            [
                'class' => 'yii\grid\ActionColumn',
                'template' => '{view} {update} {delete}',
                'buttons' => [
                    'view' => function ($url, $model) {
                        return Html::a(
                            'View',
                            Url::to(['view', 'id' => $model->id]),
                            ['class' => 'btn btn-primary btn-sm']
                        );
                    },
                    'update' => function ($url, $model) {
                        return Html::a(
                            'Update',
                            Url::to(['update', 'id' => $model->id]),
                            ['class' => 'btn btn-warning btn-sm']
                        );
                    },
                    'delete' => function ($url, $model) {
                        return Html::a(
                            'Delete',
                            Url::to(['delete', 'id' => $model->id]),
                            [
                                'class' => 'btn btn-danger btn-sm',
                                'data' => [
                                    'confirm' => 'Are you sure you want to delete this ticket?',
                                    'method' => 'post',
                                ],
                            ]
                        );
                    },
                ],
            ],
        ],
    ]); ?>
    <?php Pjax::end(); ?>
</div>
