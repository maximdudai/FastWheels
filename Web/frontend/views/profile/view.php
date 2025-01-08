<?php

use yii\helpers\Html;
use yii\widgets\DetailView;

/** @var yii\web\View $this */
/** @var common\models\Client $model */

$this->title = $model->name;
$this->params['breadcrumbs'][] = ['label' => 'Clients', 'url' => ['index']];
$this->params['breadcrumbs'][] = $this->title;
\yii\web\YiiAsset::register($this);
?>
<div class="client-view">

    <?= DetailView::widget([
        'model' => $model,
        'attributes' => [
            'name',
            'email:email',
            'phone',
            'roleId',
            'createdAt',
            'balance',
            'iban',
        ],
    ]) ?>

    <p>
        <!-- button to going to edit page -->
        <?= Html::a('Edit', ['update', 'id' => $model->id], ['class' => 'btn btn-primary']) ?>
        <!-- button to going to support-ticket page -->
        <?= Html::a('Support Ticket', ['support-ticket/index', 'id' => $model->id], ['class' => 'btn btn-success']) ?>

        <?= Html::a('Favorites', ['/favorite'], ['class' => 'btn btn-warning']) ?>
    </p>

</div>