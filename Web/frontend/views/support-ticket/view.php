<?php

use yii\helpers\Html;

/** @var yii\web\View $this */
/** @var string $topic */
/** @var string $content */

$this->title = 'Help Topic';
?>
<div class="support-ticket-view">
    <h1 class="page-title"><?= Html::encode($this->title) ?></h1>

    <div class="ticket-details">
        <p><?= Html::encode($content) ?></p>
    </div>

    <div class="button-container">
        <?= Html::a('Back', ['index'], ['class' => 'btn btn-primary']) ?>
    </div>
</div>
