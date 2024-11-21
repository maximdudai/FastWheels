<?php

namespace backend\controllers;

use common\models\Client;
use common\models\LoginForm;
use Yii;
use yii\filters\VerbFilter;
use yii\filters\AccessControl;
use yii\web\Response;

/**
 * Site controller
 */
class SiteController extends BaseController
{
    /**
     * {@inheritdoc}
     */
    public function behaviors()
    {
        return [
            'access' => [
                'class' => AccessControl::class,
                'rules' => [
                    [
                        'actions' => ['login', 'error'],
                        'allow' => true,
                    ],
                    [
                        'actions' => ['logout', 'index'],
                        'allow' => true,
                        'roles' => ['@'],
                    ],
                ],
            ],
            'verbs' => [
                'class' => VerbFilter::class,
                'actions' => [
                    'logout' => ['post'],
                ],
            ],
        ];
    }

    /**
     * {@inheritdoc}
     */
    public function actions()
    {
        return [
            'error' => [
                'class' => \yii\web\ErrorAction::class,
            ],
        ];
    }

    /**
     * Displays homepage.
     *
     * @return string
     */
    public function actionIndex()
    {
        return $this->render('index');
    }

    /**
     * Login action.
     *
     * @return string|Response
     */
    public function actionLogin()
    {
        if (!Yii::$app->user->isGuest) {
            return $this->goHome();
        }
    
        // $this->layout = 'blank';
    
        $model = new LoginForm();
        if ($model->load(Yii::$app->request->post()) && $model->login()) {

            $findAsClient = \common\models\Client::find()->where(['userId' => Yii::$app->user->id])->one();
            if($findAsClient->roleId === 1) {
                // allow only 'funcionario & administrador' roles
                Yii::$app->user->logout(); // Log the user out
                Yii::$app->session->setFlash('error', 'Access denied: You do not have permission to access the back office.');
                return $this->redirect(['site/login']);
            }

            // Redirect to home or dashboard
            return $this->goBack();
        }
    
        $model->password = '';
    
        return $this->render('login', [
            'model' => $model,
        ]);
    }
    
    

    /**
     * Logout action.
     *
     * @return Response
     */
    public function actionLogout()
    {
        Yii::$app->user->logout();

        return $this->goHome();
    }
}
