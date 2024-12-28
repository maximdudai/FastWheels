<?php

namespace backend\controllers;

use yii\web\Controller;
use common\models\Client;
use common\models\LoginForm;
use common\models\Payment;
use common\models\Reservation;
use common\models\SupportTicket;
use common\models\User;
use common\models\UserCar;
use Yii;
use yii\filters\VerbFilter;
use yii\filters\AccessControl;
use yii\web\Response;
/**
 * Site controller
 */
class SiteController extends Controller
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
        // Query total counts
        $userCount = User::find()->count(); // Total users
        $paymentCount = Payment::find()->count(); // Total payments
        $reservationCount = Reservation::find()->count(); // Total reservations
        $supportTicketCount = SupportTicket::find()->count(); // Total support tickets
        $userCarCount = UserCar::find()->count(); // Total user cars
    
        // Send all data to the view
        return $this->render('index', [
            'counts' => [
                'users' => $userCount,
                'payments' => $paymentCount,
                'reservations' => $reservationCount,
                'supportTickets' => $supportTicketCount,
                'userCars' => $userCarCount,
            ],
        ]);
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
        // get POST parameters
        $post = Yii::$app->request->post();

        if ($model->load($post) && $model->login()) {

            $username = $post['LoginForm']['username'];
            $getRoleId = Client::find()->select('roleId')->where(['name' => $username])->one();

            if(isset($getRoleId->roleId) && $getRoleId->roleId === 1) {
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
