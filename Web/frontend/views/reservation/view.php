<?php

use yii\helpers\Html;
use yii\widgets\DetailView;

/** @var yii\web\View $this */
/** @var common\models\Reservation $model */

$this->title = $model->id;
$this->params['breadcrumbs'][] = ['label' => 'Reservations', 'url' => ['index']];
$this->params['breadcrumbs'][] = $this->title;
\yii\web\YiiAsset::register($this);
?>
<div class="reservation-view container mt-4">

    <div class="card shadow-lg">
        <div class="card-body">
            <div class="d-flex justify-content-between mb-3">
                <div>
                    <?= Html::a('<i class="fas fa-trash"></i> Apagar', ['delete', 'id' => $model->id], [
                        'class' => 'btn btn-danger',
                        'data' => [
                            'confirm' => 'Tem a certeza que deseja apagar este item?',
                            'method' => 'post',
                        ],
                    ]) ?>
                </div>
            </div>

            <div class="table-responsive">
                <?= DetailView::widget([
                    'model' => $model,
                    'options' => ['class' => 'table table-bordered table-hover'],
                    'attributes' => [
                        [
                            'attribute' => 'carId',
                            'label' => 'Veiculo',
                            'value' => $model->car->carBrand . ' ' . $model->car->carModel,
                        ],
                        [
                            'attribute' => 'dateStart',
                            'label' => 'Data de Início',
                            'format' => ['date', 'php:d/m/Y'],
                        ],
                        [
                            'attribute' => 'dateEnd',
                            'label' => 'Data de Fim',
                            'format' => ['date', 'php:d/m/Y'],
                        ],
                        [
                            'attribute' => 'createAt',
                            'label' => 'Criada em',
                            'format' => ['date', 'php:d/m/Y H:i'],
                        ],
                        [
                            'attribute' => 'filled',
                            'label' => 'Estado',
                            'value' => !$model->filled ? 'Em Reserva' : 'Finalizada',
                        ],
                        [
                            'attribute' => 'value',
                            'label' => 'Valor (€)',
                            'format' => ['decimal', 2],
                        ],
                    ],
                ]) ?>
            </div>
        </div>
    </div>

</div>
