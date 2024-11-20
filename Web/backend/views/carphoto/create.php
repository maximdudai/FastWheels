<?php

use yii\helpers\Html;

/** @var yii\web\View $this */
/** @var common\models\CarPhoto $model */

$this->title = 'Create Car Photo';
$this->params['breadcrumbs'][] = ['label' => 'Car Photos', 'url' => ['index']];
$this->params['breadcrumbs'][] = $this->title;
?>
<div class="car-photo-create">

    <h1><?= Html::encode($this->title) ?></h1>

    <?= $this->render('_form', [
        'model' => $model,
    ]) ?>

</div>
