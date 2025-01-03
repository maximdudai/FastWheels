<?php

use common\models\SupportTicket;
use yii\helpers\Html;
use yii\helpers\Url;
use yii\grid\ActionColumn;
use yii\grid\GridView;

require_once __DIR__ . '/../../../common/utils/ticketstatus.php';
use function TicketStatus\getTicketStatus;

/** @var yii\web\View $this */
/** @var backend\models\SupportTicketSearch $searchModel */
/** @var yii\data\ActiveDataProvider $dataProvider */


$this->title = 'Support Tickets';
$this->params['breadcrumbs'][] = $this->title;
?>
<div class="support-ticket-index">

    <h1><?= Html::encode($this->title) ?></h1>

    <?php // echo $this->render('_search', ['model' => $searchModel]); ?>

    <?= GridView::widget([
        'dataProvider' => $dataProvider,
        'filterModel' => $searchModel,
        'columns' => [
            ['class' => 'yii\grid\SerialColumn'],

            'clientId' => [
                'attribute' => 'clientId',
                'value' => function (SupportTicket $model) {
                    return $model->client->name;
                }
            ],
            'createdAt',
            'closed' => [
                'attribute' => 'closed',
                'value' => function (SupportTicket $model) {
                    return $model->closed ? 'Yes' : 'No';
                }
            ],
            'status' => [
                'attribute' => 'status',
                'value' => function (SupportTicket $model) {
                    $ticketStatus = getTicketStatus($model->status);
                    return $ticketStatus;
                }
            ],
            [
                'class' => ActionColumn::className(),
                'urlCreator' => function ($action, SupportTicket $model, $key, $index, $column) {
                    return Url::toRoute([$action, 'id' => $model->id]);
                 }
            ],
        ],
    ]); ?>


</div>
