<?php
$params = array_merge(
    require __DIR__ . '/../../common/config/params.php',
    require __DIR__ . '/../../common/config/params-local.php',
    require __DIR__ . '/params.php',
    require __DIR__ . '/params-local.php'
);

return [
    'id' => 'app-backend',
    'basePath' => dirname(__DIR__),
    'controllerNamespace' => 'backend\controllers',
    'bootstrap' => ['log'],
    'modules' => [
        'api' => [
            'class' => 'backend\modules\api\ModuleAPI',
        ],
    ],
    'components' => [
        'request' => [
            'csrfParam' => '_csrf-backend',
            'parsers' => [
                'application/json' => 'yii\web\JsonParser',
            ]
        ],
        'user' => [
            'identityClass' => 'common\models\User',
            'enableAutoLogin' => true,
            'identityCookie' => ['name' => '_identity-backend', 'httpOnly' => true],
        ],
        'session' => [
            // this is the name of the session cookie used for login on the backend
            'name' => 'advanced-backend',
        ],
        'log' => [
            'traceLevel' => YII_DEBUG ? 3 : 0,
            'targets' => [
                [
                    'class' => \yii\log\FileTarget::class,
                    'levels' => ['error', 'warning'],
                ],
            ],
        ],
        'errorHandler' => [
            'errorAction' => 'site/error',
        ],
        'urlManager' => [
            'enablePrettyUrl' => true,
            'showScriptName' => false,
            'rules' => [
                'supportticket' => 'support-ticket/index',
                'supportticket/<action>' => 'support-ticket/<action>',
                'usercar' => 'user-car/index',
                'usercar/<action>' => 'user-car/<action>',
                'carreview' => 'car-review/index',
                'carreview/<action>' => 'car-review/<action>',
                // 'usercar/<action:\w+>/<id:\d+>' => 'user-car/<action>',
                [
                    'class' => 'yii\rest\UrlRule',
                    'controller' => 'api/clients',
                    'extraPatterns' => [
                        'POST login' => 'login',
                        'POST register' => 'register',
                        'PUT update/{id}' => 'update',
                        'DELETE {id}/delete' => 'delete',
                    ],
                    'tokens' => [
                        '{id}' => '<id:\\d+>',
                        '{username}' => '<username:\\w+>',
                    ]

                ],
                [
                    'class' => 'yii\rest\UrlRule',
                    'controller' => 'api/vehicles',
                    'extraPatterns' => [
                        'GET {id}/index' => 'index',
                        'POST create' => 'create',
                        'PUT {id}/update' => 'update',
                        'DELETE {id}/delete' => 'delete',
                    ],
                    'tokens' => [
                        '{id}' => '<id:\\d+>',
                    ],
                ],
                [
                    'class' => 'yii\rest\UrlRule',
                    'controller' => 'api/reservations',
                    'extraPatterns' => [
                        'GET index' => 'index',
                        'POST create' => 'create',
                        'PUT {id}/update' => 'update',
                        'DELETE delete/{id}' => 'delete',
                        'GET user/{id}' => 'user',
                    ],
                    'tokens' => [
                        '{id}' => '<id:\\d+>',
                    ],
                ],
                [
                    'class' => 'yii\rest\UrlRule',
                    'controller' => 'api/notifications',
                    'extraPatterns' => [
                        'GET search/{id}' => 'search',
                        'POST create' => 'create',
                        'PUT markread' => 'markread',
                        'DELETE delete/{id}' => 'delete',
                    ],
                    'tokens' => [
                        '{id}' => '<id:\\d+>',
                    ],
                ],
                [
                    'class' => 'yii\rest\UrlRule',
                    'controller' => 'api/review',
                    'extraPatterns' => [
                        'GET reviews' => 'reviews',
                        'GET search/{id}' => 'search',
                        'POST create' => 'create',
                    ],
                    'tokens' => [
                        '{id}' => '<id:\\d+>',
                    ],
                ],
            ],
        ],
    ],
    'params' => $params,
];
