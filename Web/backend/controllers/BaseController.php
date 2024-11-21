<?php
namespace backend\controllers;

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
                        'roles' => ['administrador', 'funcionario'], // Backend accessible only to admins and employees
                    ],
                    [
                        'allow' => false, // Deny other roles
                    ],
                ],
            ],
        ];
    }
}
