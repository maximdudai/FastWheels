<?php

namespace frontend\controllers;

use Cassandra\Date;
use common\models\UserCar;
use frontend\models\UserCarSearch;
use yii\web\Controller;
use yii\web\NotFoundHttpException;
use yii\filters\VerbFilter;
use Yii;

/**
 * UserCarController implements the CRUD actions for UserCar model.
 */
class UserCarController extends Controller
{
    /**
     * @inheritDoc
     */
    public function behaviors()
    {
        return array_merge(
            parent::behaviors(),
            [
                'verbs' => [
                    'class' => VerbFilter::className(),
                    'actions' => [
                        'delete' => ['POST'],
                    ],
                ],
            ]
        );
    }

    /**
     * Lists all UserCar models.
     *
     * @return string
     */
    public function actionIndex()
    {
        $searchModel = new UserCarSearch();
        $dataProvider = $searchModel->search($this->request->queryParams);

        $dataProvider->query->with('carphotos');

        return $this->render('index', [
            'searchModel' => $searchModel,
            'dataProvider' => $dataProvider,
        ]);
    }

    /**
     * Displays a single UserCar model.
     * @param int $id ID
     * @return string
     * @throws NotFoundHttpException if the model cannot be found
     */
    public function actionView($id)
    {
        return $this->render('view', [
            'model' => $this->findModel($id),
        ]);
    }

    /**
     * Creates a new UserCar model.
     * If creation is successful, the browser will be redirected to the 'view' page.
     * @return string|\yii\web\Response
     */
    public function actionCreate()
    {
        $model = new UserCar();

        if ($this->request->isPost) {
            if ($model->load($this->request->post()) && $model->save()) {
                return $this->redirect(['view', 'id' => $model->id]);
            }
        } else {
            $model->loadDefaultValues();
        }

        return $this->render('create', [
            'model' => $model,
        ]);
    }

    /**
     * Updates an existing UserCar model.
     * If update is successful, the browser will be redirected to the 'view' page.
     * @param int $id ID
     * @return string|\yii\web\Response
     * @throws NotFoundHttpException if the model cannot be found
     */
    public function actionUpdate($id)
    {
        $model = $this->findModel($id);

        if ($this->request->isPost && $model->load($this->request->post()) && $model->save()) {
            return $this->redirect(['view', 'id' => $model->id]);
        }

        return $this->render('update', [
            'model' => $model,
        ]);
    }

    /**
     * Deletes an existing UserCar model.
     * If deletion is successful, the browser will be redirected to the 'index' page.
     * @param int $id ID
     * @return \yii\web\Response
     * @throws NotFoundHttpException if the model cannot be found
     */
    public function actionDelete($id)
    {
        $this->findModel($id)->delete();

        return $this->redirect(['index']);
    }

    /**
     * Finds the UserCar model based on its primary key value.
     * If the model is not found, a 404 HTTP exception will be thrown.
     * @param int $id ID
     * @return UserCar the loaded model
     * @throws NotFoundHttpException if the model cannot be found
     */
    protected function findModel($id)
    {
        if (($model = UserCar::findOne(['id' => $id])) !== null) {
            return $model;
        }

        throw new NotFoundHttpException('The requested page does not exist.');
    }
    public function actionFavorite()
    {
        if (Yii::$app->user->isGuest) {
            Yii::$app->session->setFlash('error', 'You need to be logged in to add cars to favorites.');
            return $this->redirect(['/site/login']);
        }

        $carId = Yii::$app->request->post('carId');
            $userId = Yii::$app->user->id;

        if (!$carId || !$userId) {
            Yii::$app->session->setFlash('error', 'Invalid request.');
            return $this->redirect(['index']);
        }

        $exists = \common\models\Favorite::find()
            ->where(['clientId' => $userId, 'carId' => $carId])
            ->exists();

        if ($exists) {
            Yii::$app->session->setFlash('warning', 'This car is already in your favorites.');
            return $this->redirect(['index']);
        }

        $favorite = new \common\models\Favorite();
        $favorite->clientId = $userId;
        $favorite->carId = $carId;
        $favorite->createdAt = date("Y/m/d H/m/s");


        if ($favorite->save()) {
            Yii::$app->session->setFlash('success', 'Car added to favorites!');
        } else {
            Yii::$app->session->setFlash('error', 'Failed to add car to favorites.');
        }

        return $this->redirect(['index']);
    }
}
