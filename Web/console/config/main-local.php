<?php

return [
    'bootstrap' => ['gii'],
    'components' => [
        'authManager' => [
            'class' => 'yii\rbac\DbManager',
        ],
    ],
    'modules' => [
        'gii' => 'yii\gii\Module',
    ],

];
