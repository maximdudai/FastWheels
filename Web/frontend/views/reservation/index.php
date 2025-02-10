<?php

use common\models\Reservation;
use yii\helpers\Html;
use yii\helpers\Url;
use yii\grid\ActionColumn;
use yii\grid\GridView;

/** @var yii\web\View $this */
/** @var frontend\models\ReservationSearch $searchModel */
/** @var yii\data\ActiveDataProvider $dataProvider */

$this->title = 'Reservations';
$this->params['breadcrumbs'][] = $this->title;

?>
<div class="reservation-index">

    <div class="container">
        <?php if($dataProvider->models == null) { ?>
            <div class="alert alert-warning" role="alert">
                You don't have any reservations yet.
            </div>
        <?php } ?>
        <div class="row">
            <?php foreach ($dataProvider->models as $reservation): ?>
                <?php $car = $reservation->car; ?>
                <div class="col-md-4 mb-3">
                    <div class="card">
                        <img class="card-img-top" src="<?= $car->getFirstPhoto() ?>" alt="<?= Html::encode($car->carBrand . ' ' . $car->carModel) ?>">
                        <div class="card-body">
                            <h5 class="card-title"><?= Html::encode($car->carBrand . ' ' . $car->carModel) ?></h5>
                            <p class="card-text">
                                <strong>Inicio:</strong> <?= date('Y-m-d', strtotime($reservation->dateStart)) ?><br>
                                <strong>Fim:</strong> <?= date('Y-m-d', strtotime($reservation->dateEnd)) ?>
                            </p>
                            <?= Html::a('Details', ['view', 'id' => $reservation->id], ['class' => 'btn btn-primary']) ?>
                        </div>
                    </div>
                </div>
            <?php endforeach; ?>
        </div>
    </div>

</div>