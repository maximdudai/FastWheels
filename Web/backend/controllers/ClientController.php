<?php

namespace backend\controllers;

use Yii;
use common\models\Client;
use backend\models\ClientSearch;
use common\models\User;
use yii\web\Controller;
use yii\web\NotFoundHttpException;
use yii\web\BadRequestHttpException;
use yii\filters\VerbFilter;

/**
 * ClientController implements the CRUD actions for Client model.
 */
class ClientController extends Controller
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
     * Lists all Client models.
     *
     * @return string
     */
    public function actionEmployees()
    {
        $searchModel = new ClientSearch();
        $dataProvider = $searchModel->search($this->request->queryParams);

        $dataProvider->query->andWhere(['roleId' => 2, 'roleId' => 3]);

        return $this->render('index', [
            'searchModel' => $searchModel,
            'dataProvider' => $dataProvider,
        ]);
    }

    public function actionClients()
    {
        $searchModel = new ClientSearch();
        $dataProvider = $searchModel->search($this->request->queryParams);

        $dataProvider->query->andWhere(['roleId' => 1]);

        return $this->render('index', [
            'searchModel' => $searchModel,
            'dataProvider' => $dataProvider,
        ]);
    }


    /**
     * Displays a single Client model.
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
     * Creates a new Client model.
     * If creation is successful, the browser will be redirected to the 'view' page.
     * @return string|\yii\web\Response
     */
    public function actionCreate()
    {

        $client = new Client();

        if ($this->request->isPost) {
            $postData = $this->request->post()['Client'];

            // Create and save user in User table
            $user = new User();
            $user->username = $postData['name'];
            $user->email = $postData['email'];
            $user->status = User::STATUS_ACTIVE;
            $user->setPassword($postData['password']);
            $user->generateAuthKey();
            $user->generateEmailVerificationToken();
            $user->status = 10;

            if (!$user->save()) {
                \Yii::$app->session->setFlash('error', 'Failed to create user. Please try again.');
                \Yii::error($user->getErrors(), 'signup');
                return $this->redirect(['client/employees']); // Redirect to avoid rendering the form
            }

            // Assign role to the user
            $auth = \Yii::$app->authManager;
            $getRoleName = $postData['roleId'] === 3 ? 'admin' : 'funcionario';
            $authorRole = $auth->getRole($getRoleName);
            $auth->assign($authorRole, $user->getId());

            // Create and save client
            $client->userId = $user->id;
            $client->name = $postData['name'];
            $client->email = $postData['email'];
            $client->phone = 'none';
            $client->roleId = $postData['roleId'];
            $client->createdAt = date('Y-m-d H:i:s');
            $client->balance = 0;
            $client->iban = 'none';

            if (!$client->save()) {
                \Yii::$app->session->setFlash('error', 'Failed to save client. Please try again.');
                \Yii::error($client->getErrors(), 'signup');
                return $this->redirect(['client/employees']); // Redirect to avoid rendering the form
            }

            \Yii::$app->session->setFlash('success', 'Client created successfully.');
            return $this->redirect(['client/employees']); // Redirect after successful creation
        }
        return $this->render('create', [
            'model' => $client,
        ]);
    }


    /**
     * Updates an existing Client model.
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
     * Deletes an existing Client model.
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
     * Finds the Client model based on its primary key value.
     * If the model is not found, a 404 HTTP exception will be thrown.
     * @param int $id ID
     * @return Client the loaded model
     * @throws NotFoundHttpException if the model cannot be found
     */
    protected function findModel($id)
    {
        if (($model = Client::findOne(['id' => $id])) !== null) {
            return $model;
        }

        throw new NotFoundHttpException('The requested page does not exist.');
    }
}
