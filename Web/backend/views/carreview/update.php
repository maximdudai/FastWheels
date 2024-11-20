<?php

use yii\helpers\Html;

/** @var yii\web\View $this */
/** @var common\models\CarReview $model */

$this->title = 'Update Car Review: ' . $model->id;
$this->params['breadcrumbs'][] = ['label' => 'Car Reviews', 'url' => ['index']];
$this->params['breadcrumbs'][] = ['label' => $model->id, 'url' => ['view', 'id' => $model->id]];
$this->params['breadcrumbs'][] = 'Update';
?>
<div class="car-review-update">

    <h1><?= Html::encode($this->title) ?></h1>

    <?= $this->render('_form', [
        'model' => $model,
    ]) ?>

</div>
