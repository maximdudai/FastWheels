<?php

use yii\helpers\Html;

/** @var yii\web\View $this */
/** @var app\models\Taxes $model */

$this->title = 'Update Taxes: ' . $model->id;
$this->params['breadcrumbs'][] = ['label' => 'Taxes', 'url' => ['index']];
$this->params['breadcrumbs'][] = ['label' => $model->id, 'url' => ['view', 'id' => $model->id]];
$this->params['breadcrumbs'][] = 'Update';
?>
<div class="taxes-update">

    <h1><?= Html::encode($this->title) ?></h1>

    <?= $this->render('_form', [
        'model' => $model,
    ]) ?>

</div>
