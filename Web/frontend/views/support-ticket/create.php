<?php

use yii\helpers\Html;

/** @var yii\web\View $this */
/** @var common\models\SupportTicket $model */

$this->title = 'How can we help';

if (Yii::$app->session->hasFlash('success')) {
    echo '<div class="alert alert-success">' . Yii::$app->session->getFlash('success') . '</div>';
}
?>

<div class="support-ticket-container">

    <h1><?= Html::encode($this->title) ?></h1>

    <div class="support-ticket-form">
        <?= $this->render('_form', [
            'model' => $model
        ]) ?>
    </div>

</div>
