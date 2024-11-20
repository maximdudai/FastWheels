<?php

use yii\helpers\Html;

/** @var yii\web\View $this */
/** @var common\models\Localization $model */

$this->title = 'Update Localization: ' . $model->id;
$this->params['breadcrumbs'][] = ['label' => 'Localizations', 'url' => ['index']];
$this->params['breadcrumbs'][] = ['label' => $model->id, 'url' => ['view', 'id' => $model->id]];
$this->params['breadcrumbs'][] = 'Update';
?>
<div class="localization-update">

    <h1><?= Html::encode($this->title) ?></h1>

    <?= $this->render('_form', [
        'model' => $model,
    ]) ?>

</div>
