<?php

use common\models\Tarefa;
use yii\helpers\Html;
use yii\helpers\Url;
use yii\grid\ActionColumn;
use yii\grid\GridView;

/** @var yii\web\View $this */
/** @var app\models\TarefaSearch $searchModel */
/** @var yii\data\ActiveDataProvider $dataProvider */

$this->title = 'Tarefas';
$this->params['breadcrumbs'][] = $this->title;

$getCurrentIdFromUrl = Yii::$app->request->get('id');

?>
<div class="tarefa-index">

    <h1><?= Html::encode($this->title) ?></h1>

    <p>
        <?= Html::a('Create Tarefa', ['create?id=' . $getCurrentIdFromUrl], ['class' => 'btn btn-success']) ?>
    </p>

    <?php // echo $this->render('_search', ['model' => $searchModel]); ?>

    <?= GridView::widget([
        'dataProvider' => $dataProvider,
        'filterModel' => $searchModel,
        'columns' => [
            ['class' => 'yii\grid\SerialColumn'],

            'clientId' => 'client.name',
            'descricao:ntext',
            'feito' => [
                'attribute' => 'feito',
                'value' => function (Tarefa $model) {
                    return $model->feito ? 'Feito' : 'Nao feito';
                },
                'filter' => ['0' => 'Nao feito', '1' => 'Feito'],
            ],
            [
                'class' => ActionColumn::className(),
                'urlCreator' => function ($action, Tarefa $model, $key, $index, $column) {
                    return Url::toRoute([$action, 'id' => $model->id]);
                 }
            ],
        ],
    ]); ?>


</div>
