<?php

use common\models\Client;
use common\models\Role;
use yii\helpers\Html;
use yii\helpers\Url;
use yii\grid\ActionColumn;
use yii\grid\GridView;

/** @var yii\web\View $this */
/** @var backend\models\ClientSearch $searchModel */
/** @var yii\data\ActiveDataProvider $dataProvider */

$this->title = 'Clients';
$this->params['breadcrumbs'][] = $this->title;

$isUserAdmin = Client::isUserAdmin(Yii::$app->user->identity->id);

?>
<div class="client-index">

    <h1><?= Html::encode($this->title) ?></h1>

    <p>
        <?php if (Yii::$app->controller->action->id === 'employees' && (!Yii::$app->user->isGuest && $isUserAdmin)): ?>
            <?= Html::a('Create Employee', ['create'], ['class' => 'btn btn-success']) ?>
        <?php endif; ?>
    </p>

    <?php // echo $this->render('_search', ['model' => $searchModel]); 
    ?>

    <?= GridView::widget([
        'dataProvider' => $dataProvider,
        'filterModel' => $searchModel,
        'columns' => [
            ['class' => 'yii\grid\SerialColumn'],

            'name',
            'email:email',
            'phone',
            'roleId' => [
                'attribute' => 'Role',
                'value' => function ($model) {
                    return $model->role ? $model->role->roleName : 'No role assigned';
                },
            ],
            'createdAt',
            'balance',
            'iban',
            // 'userId',
            [
                'class' => ActionColumn::className(),
                'urlCreator' => function ($action, Client $model, $key, $index, $column) {
                    return Url::toRoute([$action, 'id' => $model->id]);
                }
            ],
        ],
    ]); ?>  
</div>