<?php

use yii\helpers\Html;

/** @var yii\web\View $this */
/** @var common\models\Localization $model */

$this->title = 'Create Localization';
$this->params['breadcrumbs'][] = ['label' => 'Localizations', 'url' => ['index']];
$this->params['breadcrumbs'][] = $this->title;
?>
<div class="localization-create">

    <h1><?= Html::encode($this->title) ?></h1>

    <?= $this->render('_form', [
        'model' => $model,
    ]) ?>

</div>
