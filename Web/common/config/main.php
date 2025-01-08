<?php
return [
    'aliases' => [
        '@bower' => '@vendor/bower-asset',
        '@npm'   => '@vendor/npm-asset',
        '@uploads' => '/uploads',
        '@carphotos' => '@uploads/car-photos',
    ],
    'vendorPath' => dirname(dirname(__DIR__)) . '/vendor',
    'components' => [
        'authManager' => [
            'class' => 'yii\rbac\DbManager', // Use DbManager for database-based RBAC
            'defaultRoles' => ['guest'],    // Optional: Define default roles
        ],
        'cache' => [
            'class' => \yii\caching\FileCache::class,
        ],
    ],
];