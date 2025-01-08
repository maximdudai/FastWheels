<?php

use common\models\Favorite;
use yii\helpers\Html;
use yii\helpers\Url;
use yii\grid\ActionColumn;
use yii\grid\GridView;
use yii\widgets\ListView;

/** @var yii\web\View $this */
/** @var frontend\models\FavoriteSearch $searchModel */
/** @var yii\data\ActiveDataProvider $dataProvider */

$this->title = 'Favorites';
$this->params['breadcrumbs'][] = $this->title;
?>
<div class="favorite-index">
    <h1><?= Html::encode($this->title) ?></h1>

    <?= ListView::widget([
        'dataProvider' => $dataProvider,
        'itemOptions' => ['class' => 'favorite-card-container'],
        'layout' => "{items}\n{pager}",
        'itemView' => function ($model) {
            return $this->render('_favorite_card', ['model' => $model]);
        },
    ]); ?>
</div>