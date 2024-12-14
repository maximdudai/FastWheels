<?php

/** @var \yii\web\View $this */
/** @var string $content */

use yii\helpers\Url;
use common\models\Client;
use frontend\assets\AppAsset;
use yii\bootstrap5\Breadcrumbs;
use yii\bootstrap5\Html;
use yii\bootstrap5\Nav;
use yii\bootstrap5\NavBar;

AppAsset::register($this);
?>
<?php $this->beginPage() ?>
<!DOCTYPE html>
<html lang="<?= Yii::$app->language ?>">

<head>
    <meta charset="<?= Yii::$app->charset ?>">
    <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
    <?php $this->registerCsrfMetaTags() ?>
    <title><?= Html::encode($this->title) ?></title>
    <?php $this->head() ?>
</head>

<body class="d-flex flex-column">
    <?php $this->beginBody() ?>

    <header class="navbar-container">
        <?php
        NavBar::begin([
            'brandLabel' => 'Fast Wheels',
            'brandUrl' => Yii::$app->homeUrl,
            'options' => [
                'class' => 'navbar navbar-expand-md navbar-dark bg-dark custom-navbar',
            ],
        ]);

        // If the user is a guest, the profile link should redirect to the home page
        $profileUrl = Yii::$app->user->isGuest ?
            Url::to(['/']) :
            Url::to(['/profile/view', 'id' => Client::findOne(['userId' => Yii::$app->user->id])->id]);

        // The menu items are the same for all users
        $menuItems = [
            ['label' => 'Vehicles', 'url' => ['/site/vehicles']],
            ['label' => 'Contact Us', 'url' => ['/supportticket']],
        ];

        // If the user is a guest, the login link should be displayed
        // if it's already logged in, the profile link and the logout button should be displayed
        
        if (Yii::$app->user->isGuest) {
            $menuItems[] = ['label' => 'Login', 'url' => ['/site/login']];
        } else {
            $menuItems[] = ['label' => 'Profile', 'url' => $profileUrl];
            $menuItems[] = '<li>'
                . Html::beginForm(['/site/logout'], 'post', ['class' => 'd-inline'])
                . Html::submitButton('Logout', ['class' => 'btn btn-link logout-btn text-decoration-none'])
                . Html::endForm()
                . '</li>';
        }

        echo Nav::widget([
            'options' => ['class' => 'navbar-nav ms-auto custom-navbar-nav'],
            'items' => $menuItems,
        ]);
        NavBar::end();
        ?>
    </header>

    <main role="main" class="flex-shrink-0" style="width: 100%;">
        <div class="container">
            <?= Breadcrumbs::widget([
                'links' => isset($this->params['breadcrumbs']) ? $this->params['breadcrumbs'] : [],
            ]) ?>
            <?= $content ?>
        </div>
    </main>

    <footer class="footer mt-auto py-3 text-muted">
        <div class="container">
            <p class="float-start">&copy; <?= Html::encode(Yii::$app->name) ?> <?= date('Y') ?></p>
            <p class="float-end"><?= Yii::powered() ?></p>
        </div>
    </footer>

    <?php $this->endBody() ?>
</body>

</html>
<?php $this->endPage();
