<?php

use yii\helpers\Html;
use yii\widgets\DetailView;
use yii\bootstrap4\Nav;
use yii\bootstrap4\NavBar;

/** @var yii\web\View $this */
/** @var common\models\Client $model */

$this->title = $model->name;
$this->params['breadcrumbs'][] = ['label' => 'Clients', 'url' => ['index']];
$this->params['breadcrumbs'][] = $this->title;
\yii\web\YiiAsset::register($this);
?>
<?php


$photoUrl = Yii::getAlias('@uploads') . '/profile_icon.png';

?>

<div class="client-profile rounded shadow-lg d-flex">

    <div class="row w-25 m-5 rounded bg-white shadow-lg flex justify-content-center align-items-center">
        <div class="avatar-section text-center">
            <h4 style="text-transform:uppercase; font-weight:bold; font-size:12px; background:rgba(0, 0, 0, 0.25); padding: 10px 2px; border-radius:10px;">Manage personal profile</h4>
            <div class="avatar-wrapper mb-3">
                <?= Html::img($photoUrl, ['width' => 150]) ?>
            </div>
        </div>
    </div>

    <!-- Profile Content -->
    <div class="row w-auto bg-white p-4 rounded shadow-lg">
        <!-- Left Column: Profile Info -->
        <div class="col-md-8">
            <div class="profile-section mb-4">
                <?= DetailView::widget([
                    'model' => $model,
                    'options' => ['class' => 'table table-borderless detail-view'],
                    'attributes' => [
                        'name',
                        [
                            'attribute' => 'email',
                            'format' => 'raw',
                            'label' => 'Email',
                            'value' => $model->email,
                        ],
                        [
                            'attribute' => 'phone',
                            'label' => 'Phone/Skype',
                            'value' => $model->phone ?? '-none-',
                        ],
                        [
                            'attribute' => 'roleId',
                            'label' => 'Role',
                            'value' => $model->roleId == 1 ? 'Admin' : 'User',
                        ],
                        [
                            'attribute' => 'createdAt',
                            'label' => 'Member Since',
                            'value' => date('d M Y', strtotime($model->createdAt)),
                        ],
                        [
                            'attribute' => 'balance',
                            'format' => ['currency'],
                            'value' => $model->balance ?? 0.00,
                        ],
                        'iban',
                    ],
                ]) ?>
            </div>
        </div>
        <div class="mt-4 d-flex justify-content-between">
            <?= Html::a('<i class="fas fa-edit"></i> Edit Profile', ['update', 'id' => $model->id], ['class' => 'btn btn-primary']) ?>
            <?= Html::a('<i class="fas fa-headset"></i> Support Ticket', ['support-ticket/index', 'id' => $model->id], ['class' => 'btn btn-success']) ?>
            <?= Html::a('<i class="fas fa-star"></i> Favorites', ['/favorite'], ['class' => 'btn btn-warning']) ?>
            <?= Html::a('<i class="fas fa-star"></i> Reservas', ['/reservation'], ['class' => 'btn btn-info']) ?>
        </div>
    </div>

    <!-- Action Buttons -->


</div>

</div>