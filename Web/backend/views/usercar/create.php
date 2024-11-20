<?php

use yii\helpers\Html;

/** @var yii\web\View $this */
/** @var common\models\UserCar $model */

$this->title = 'Create User Car';
$this->params['breadcrumbs'][] = ['label' => 'User Cars', 'url' => ['index']];
$this->params['breadcrumbs'][] = $this->title;
?>
<div class="user-car-create">

    <h1><?= Html::encode($this->title) ?></h1>

    <?= $this->render('_form', [
        'model' => $model,
    ]) ?>

</div>
