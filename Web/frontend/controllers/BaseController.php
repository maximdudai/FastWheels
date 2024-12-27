<?php
namespace frontend\controllers;

use yii\web\Controller;
use yii\filters\AccessControl;

class BaseController extends Controller
{
    public function behaviors()
    {
        return [
            'access' => [
                'class' => AccessControl::class,
                'rules' => [
                    [
                        'allow' => true,
                        'roles' => ['client', 'guest'], // Only clients can access their profile
                    ],
                    [
                        'allow' => false,
                        'roles' => ['admin', 'funcionario'], 

                    ],
                ],
            ],
        ];
    }
}
