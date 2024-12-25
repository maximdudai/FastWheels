<?php

use common\models\Client;
use common\models\UserCar;
use yii\helpers\Html;
use yii\helpers\Url;
use yii\grid\ActionColumn;
use yii\grid\GridView;


/** @var yii\web\View $this */
/** @var backend\models\UserCarSearch $searchModel */
/** @var yii\data\ActiveDataProvider $dataProvider */


$this->title = 'User Cars';
$this->params['breadcrumbs'][] = "Available Cars";

// Fetch the current user's role
$client = Client::findOne(['userId' => Yii::$app->user->id]);
$isUserHasAccessToDelete = ($client && $client->roleId === 3); // Check if the user is an administrator
?>
<div class="user-car-index">

    <?= GridView::widget([
        'dataProvider' => $dataProvider,
        'filterModel' => $searchModel,
        'columns' => [
            ['class' => 'yii\grid\SerialColumn'],
            'clientId' => [
                'attribute' => 'clientId',
                'label' => 'Owner',
                'value' => function (UserCar $model) {
                    dd($model);
                    dd($username = Client::findOne(['userId' => $model->clientId]));
                    $username = Client::findOne(['userId' => $model->clientId])->name;
                    return $username ? $username : 'N/A'; // Check if the relation exists
                },
            ],
            'carBrand',
            'carModel',
            'carYear',
            'status' => [
                'attribute' => 'status',
                'value' => function (UserCar $model) {
                    return $model->status === 1 ? 'Rented' : 'Unrented';
                }
            ],
            //'carDoors',
            //'createdAt',
            //'status',
            //'availableFrom',
            //'availableTo',
            [
                'class' => ActionColumn::className(),
                'template' => '{view} {update}',
                'urlCreator' => function ($action, UserCar $model, $key, $index, $column) {
                    return Url::toRoute([$action, 'id' => $model->id]);
                },
            ],
        ],
    ]); ?>

</div>
