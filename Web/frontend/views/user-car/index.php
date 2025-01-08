<?php

use yii\helpers\Html;
use yii\widgets\ActiveForm;
use yii\helpers\Url;
use yii\grid\GridView;

/** @var yii\web\View $this */
/** @var frontend\models\UserCarSearch $searchModel */
/** @var yii\data\ActiveDataProvider $dataProvider */

$this->title = 'Available Cars';

?>
<div class="user-car-index">
    <div class="filter-form">
        <?php $form = ActiveForm::begin([
            'method' => 'get',
            'action' => Url::to(['user-car/index']),
            'options' => ['data-pjax' => 1],
        ]); ?>

        <div class="filter-fields">
            <div class="filter-field">
                <?= $form->field($searchModel, 'location')->textInput([
                    'placeholder' => 'Ex: Lisboa, Porto, etc...',
                    'onchange' => 'this.form.submit()',
                ])->label('Localização') ?>
            </div>
            <div class="filter-field">
                <?= $form->field($searchModel, 'availableFrom')->input('date', [
                    'onchange' => 'this.form.submit()',
                ])->label('Data de Início') ?>
            </div>
            <!--div class="filter-field">
                <?= $form->field($searchModel, 'availableTo')->input('date', [
                    'onchange' => 'this.form.submit()',
                ])->label('Data de Fim') ?>
            </div-->
            <div class="filter-field">
                <label for="clear-filters" class="form-label">Limpar Filtros</label>
                <?= Html::a('Clear Filters', Url::to(['user-car/index']), [
                    'id' => 'clear-filters',
                    'class' => 'btn btn-secondary',
                ]) ?>
            </div>

        <?php ActiveForm::end(); ?>
    </div>

    <div class="cards-container">
        <?php foreach ($dataProvider->models as $car): ?>
            <div class="card">
                <a href="<?= Url::to(['user-car/view', 'id' => $car->id]) ?>" class="card-link">
                    <div class="card-content">
                        <div class="card-image">
                            <?= Html::img($car->getFirstPhoto(), ['alt' => 'Car Photo']) ?>
                        </div>
                        <div class="car-name-location">
                            <h2 class="car-name"><?= Html::encode($car->carBrand . ' ' . $car->carModel) ?></h2>
                        </div>
                        <div class="details">
                            <div class="year">
                                <strong>Ano:</strong> <span><?= Html::encode($car->carYear) ?></span>
                            </div>
                            <div class="doors">
                                <strong>Portas:</strong> <span><?= Html::encode($car->carDoors) ?></span>
                            </div>
                            <div class="reviews">
                                <strong>Avaliações:</strong>
                                <span><?= !empty($car->carreviews)
                                        ? number_format(array_sum(array_column($car->carreviews, 'rating')) / count($car->carreviews), 1)
                                        : '0.0' ?></span>
                            </div>
                            <div class="location">
                                <strong>Localização:</strong> <span><?= Html::encode($car->address) ?></span>
                            </div>
                            <div class="availability">
                                <strong>Início:</strong> <span><?= date('Y-m-d', strtotime($car->availableFrom)) ?></span>
                            </div>
                            <div class="availability">
                                <strong>Fim:</strong> <span><?= date('Y-m-d', strtotime($car->availableTo)) ?></span>
                            </div>
                        </div>
                    </div>
                </a>
                <div class="card-favorite">
                    <?php if (Yii::$app->user->isGuest): ?>
                        <?= Html::button('❤', [
                            'class' => 'favorite-btn',
                            'onclick' => "alert('You need to log in to add to favorites.'); window.location.href = '" . Url::to(['/site/login']) . "';",
                        ]) ?>
                    <?php else: ?>
                        <?= Html::beginForm(['user-car/favorite'], 'post') ?>
                        <?= Html::hiddenInput('carId', $car->id) ?>
                        <?= Html::submitButton('❤', ['class' => 'favorite-btn']) ?>
                        <?= Html::endForm() ?>
                    <?php endif; ?>
                </div>
            </div>
        <?php endforeach; ?>
    </div>
</div>
