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

    <h1><?= Html::encode($this->title) ?></h1>

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

</div>
