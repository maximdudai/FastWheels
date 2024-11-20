<?php

use yii\helpers\Html;

/** @var yii\web\View $this */
/** @var common\models\Favorite $model */

$this->title = 'Create Favorite';
$this->params['breadcrumbs'][] = ['label' => 'Favorites', 'url' => ['index']];
$this->params['breadcrumbs'][] = $this->title;
?>
<div class="favorite-create">

    <h1><?= Html::encode($this->title) ?></h1>

    <?= $this->render('_form', [
        'model' => $model,
    ]) ?>

</div>
