<?php

use yii\helpers\Html;
use yii\widgets\DetailView;
use common\models\Client;
use common\models\User;
use common\models\UserCar;

/** @var yii\web\View $this */
/** @var common\models\UserCar $model */

$this->title = "Vehicle: {$model->carBrand} {$model->carModel}";
$this->params['breadcrumbs'][] = ['label' => 'User Cars', 'url' => ['index']];
$this->params['breadcrumbs'][] = $this->title;
\yii\web\YiiAsset::register($this);

// Get the current user's role
$client = Client::findOne(['userId' => Yii::$app->user->id]);
// Check if the user is an administrator
// If the user is an administrator (roleId: 3), they can delete the car
$isUserHasAccessToDelete = ($client && $client->roleId === 3);
?>
<div class="user-car-view">

    <p>
        <?= Html::a('Update', ['update', 'id' => $model->id], ['class' => 'btn btn-primary']) ?>

        <!-- Check if the user is an administrator -->
        <?php if ($isUserHasAccessToDelete): ?>
            <?= Html::a('Delete', ['delete', 'id' => $model->id], [
                'class' => 'btn btn-danger',
                'data' => [
                    'confirm' => 'Are you sure you want to delete this item?',
                    'method' => 'post',
                ],
            ]) ?>
        <?php endif; ?>
    </p>

    <?= DetailView::widget([
        'model' => $model,
        'attributes' => [
            'clientId' => [
                'attribute' => 'clientId',
                'label' => 'Owner',
                'value' => function (UserCar $model) {
                    $carOwner = Client::findOne(['id' => $model->clientId]);
                    return $carOwner ? $carOwner->name : 'N/A'; // Check if the relation exists
                },
            ],

            'carBrand',
            'carModel',
            'carYear',
            'carDoors',
            'createdAt',
            'status' => [
                'attribute' => 'status',
                'value' => function ($model) {
                    return $model->status === 1 ? 'Rented' : 'Unrented';
                },
            ],
            'availableFrom',
            'availableTo',
        ],
    ]) ?>

</div>