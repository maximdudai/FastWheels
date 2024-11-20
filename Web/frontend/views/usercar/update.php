<?php

use yii\helpers\Html;

/** @var yii\web\View $this */
/** @var common\models\UserCar $model */

$this->title = 'Update User Car: ' . $model->id;
$this->params['breadcrumbs'][] = ['label' => 'User Cars', 'url' => ['index']];
$this->params['breadcrumbs'][] = ['label' => $model->id, 'url' => ['view', 'id' => $model->id]];
$this->params['breadcrumbs'][] = 'Update';
?>
<div class="user-car-update">

    <h1><?= Html::encode($this->title) ?></h1>

    <?= $this->render('_form', [
        'model' => $model,
    ]) ?>

</div>
