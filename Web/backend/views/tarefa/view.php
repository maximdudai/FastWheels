<?php

use common\models\Tarefa;
use yii\helpers\Html;
use yii\widgets\DetailView;

/** @var yii\web\View $this */
/** @var common\models\Tarefa $model */

$this->title = $model->id;
$this->params['breadcrumbs'][] = ['label' => 'Tarefas', 'url' => ['index']];
$this->params['breadcrumbs'][] = $this->title;
\yii\web\YiiAsset::register($this);
?>
<div class="tarefa-view">

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
            'clientId' => 'client.name',
            'descricao:ntext',
            'feito' => [
                'attribute' => 'feito',
                'value' => function (Tarefa $model) {
                    return $model->feito ? 'Feito' : 'Nao feito';
                },
                'filter' => ['0' => 'Nao feito', '1' => 'Feito'],
            ],
        ],
    ]) ?>

</div>
