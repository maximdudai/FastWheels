<?php

namespace backend\controllers;

use Yii;
use app\models\Taxes;
use app\models\TaxesSearch;
use yii\web\Controller;
use yii\web\NotFoundHttpException;
use yii\filters\VerbFilter;
use yii\web\BadRequestHttpException;

/**
 * TaxesController implements the CRUD actions for Taxes model.
 */
class TaxesController extends Controller
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
     * Lists all Taxes models.
     *
     * @return string
     */
    public function actionIndex()
    {
        $searchModel = new TaxesSearch();
        $dataProvider = $searchModel->search($this->request->queryParams);

        // Fetch tax_value for id = 1
        $currentTax = Taxes::findOne(1);

        return $this->render('index', [
            'searchModel' => $searchModel,
            'dataProvider' => $dataProvider,
            'currentTaxValue' => $currentTax ? $currentTax->tax_value : null, // Pass tax value
        ]);
    }
    /**
     * Updates an existing Taxes model.
     * If update is successful, the browser will be redirected to the 'view' page.
     * @param int $id ID
     * @return string|\yii\web\Response
     * @throws NotFoundHttpException if the model cannot be found
     */
    public function actionUpdate()
    {
        if (!$this->request->isPost) {
            throw new BadRequestHttpException('Invalid request.');
        }

        $postData = $this->request->post();
        $taxId = 1;

        $model = Taxes::findOne($taxId);

        if (!$model) {
            throw new NotFoundHttpException('Tax record not found.');
        }

        if (isset($postData['TaxesSearch']['tax_value'])) {
            $model->tax_value = $postData['TaxesSearch']['tax_value'];

            if ($model->save()) {
                Yii::$app->session->setFlash('success', 'Tax value updated successfully.');
            } else {
                Yii::$app->session->setFlash('error', 'Failed to update the tax value.');
                Yii::debug($model->getErrors(), 'modelErrors'); // Log errors
            }
        } else {
            Yii::$app->session->setFlash('error', 'Invalid form data.');
        }

        return $this->redirect(['index']);
    }



    /**
     * Finds the Taxes model based on its primary key value.
     * If the model is not found, a 404 HTTP exception will be thrown.
     * @param int $id ID
     * @return Taxes the loaded model
     * @throws NotFoundHttpException if the model cannot be found
     */
    protected function findModel($id)
    {
        if (($model = Taxes::findOne(['id' => $id])) !== null) {
            return $model;
        }

        throw new NotFoundHttpException('The requested page does not exist.');
    }
}
