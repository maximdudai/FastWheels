<?php

use common\models\Client;
use common\models\Role;

$getCurrentUser = Yii::$app->user->identity;
$userData = Client::find()->where(['userId' => $getCurrentUser->id])->one();
$findRole = Role::find()->where(['id' => $userData->roleId])->one();

$loggedUserName = $userData?->name;
$loggedUserRole = $findRole?->roleName;

?>

<aside class="main-sidebar sidebar-dark-primary elevation-4">
    <!-- Brand Logo -->
    <a href="<?= \yii\helpers\Url::home() ?>" class="brand-link">
        <img src="<?= $assetDir ?>/img/AdminLTELogo.png" alt="AdminLTE Logo" class="brand-image img-circle elevation-3" style="opacity: .8">
        <span class="brand-text font-weight-light">Fast Wheels</span>
    </a>

    <!-- Sidebar -->
    <div class="sidebar">
        <!-- Sidebar user panel (optional) -->
        <div class="user-panel mt-3 pb-3 mb-3 d-flex flex-row align-items-center">
            <div class="image">
                <img src="<?= $assetDir ?>/img/user2-160x160.jpg" class="img-circle elevation-2" alt="User Image">
            </div>
            <div class="info">
                <p class="mb-0"> <?= $loggedUserName; ?> </p>
                <span style="font-size:12px; background:#434343; padding: 0px 5px;"> <?= $loggedUserRole; ?> </span>
            </div>
        </div>

        <!-- SidebarSearch Form -->
        <!-- href be escaped -->
        <!-- <div class="form-inline">
            <div class="input-group" data-widget="sidebar-search">
                <input class="form-control form-control-sidebar" type="search" placeholder="Search" aria-label="Search">
                <div class="input-group-append">
                    <button class="btn btn-sidebar">
                        <i class="fas fa-search fa-fw"></i>
                    </button>
                </div>
            </div>
        </div> -->

        <!-- Sidebar Menu -->
        <nav class="mt-2">
            <?php
            echo \hail812\adminlte\widgets\Menu::widget([
                'items' => [
                    ['label' => 'Management', 'header' => true],
                    ['label' => 'Funcionarios', 'icon' => 'fa-regular fa-user', 'url' => ['/client/employees']],
                    ['label' => 'Clientes', 'icon' => 'fa-regular fa-user', 'url' => ['/client/clients']],
                    ['label' => 'Veiculos', 'icon' => 'fa-solid fa-car', 'url' => ['/user-car']], // Absolute URL

                    ['label' => 'Company', 'header' => true],
                    ['label' => 'Support ticket', 'icon' => 'fa-solid fa-headset', 'url' => ['/support-ticket']],
                    ['label' => 'Taxes', 'icon' => 'fa-solid fa-percent', 'url' => ['/taxes']],
                    ['label' => 'Tarefas', 'icon' => 'fa-solid fa-percent', 'url' => ['/tarefa']]
                ],
            ]);
            ?>
        </nav>

        <!-- /.sidebar-menu -->
    </div>
    <!-- /.sidebar -->
</aside>

<!-- // FIM SECCAO: Yii2 PROVIDED (EXEMPLO)
//
//                    ['label' => 'MULTI LEVEL EXAMPLE', 'header' => true],
//                    ['label' => 'Level1'],
//                    [
//                        'label' => 'Level1',
//                        'items' => [
//                            ['label' => 'Level2', 'iconStyle' => 'far'],
//                            [
//                                'label' => 'Level2',
//                                'iconStyle' => 'far',
//                                'items' => [
//                                    ['label' => 'Level3', 'iconStyle' => 'far', 'icon' => 'dot-circle'],
//                                    ['label' => 'Level3', 'iconStyle' => 'far', 'icon' => 'dot-circle'],
//                                    ['label' => 'Level3', 'iconStyle' => 'far', 'icon' => 'dot-circle']
//                                ]
//                            ],
//                            ['label' => 'Level2', 'iconStyle' => 'far']
//                        ]
//                    ],
//                    ['label' => 'Level1'],
//                    ['label' => 'LABELS', 'header' => true],
//                    ['label' => 'Important', 'iconStyle' => 'far', 'iconClassAdded' => 'text-danger'],
//                    ['label' => 'Warning', 'iconClass' => 'nav-icon far fa-circle text-warning'],
//                    ['label' => 'Informational', 'iconStyle' => 'far', 'iconClassAdded' => 'text-info'], -->