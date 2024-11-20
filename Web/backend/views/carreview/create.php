<?php

use yii\helpers\Html;

/** @var yii\web\View $this */
/** @var common\models\CarReview $model */

$this->title = 'Create Car Review';
$this->params['breadcrumbs'][] = ['label' => 'Car Reviews', 'url' => ['index']];
$this->params['breadcrumbs'][] = $this->title;
?>
<div class="car-review-create">

    <h1><?= Html::encode($this->title) ?></h1>

    <?= $this->render('_form', [
        'model' => $model,
    ]) ?>

</div>
