<?php

namespace frontend\models;

use yii\base\Model;
use yii\data\ActiveDataProvider;
use common\models\UserCar;

/**
 * UserCarSearch represents the model behind the search form of `common\models\UserCar`.
 */
class UserCarSearch extends UserCar
{
    /**
     * {@inheritdoc}
     */
    public function rules()
    {
        return [
            [['id', 'clientId', 'carYear', 'carDoors', 'status'], 'integer'],
            [['carName', 'carModel', 'createdAt', 'availableFrom', 'availableTo'], 'safe'],
        ];
    }

    /**
     * {@inheritdoc}
     */
    public function scenarios()
    {
        // bypass scenarios() implementation in the parent class
        return Model::scenarios();
    }

    /**
     * Creates data provider instance with search query applied
     *
     * @param array $params
     *
     * @return ActiveDataProvider
     */
    public function search($params)
    {
        $query = UserCar::find();

        // add conditions that should always apply here

        $dataProvider = new ActiveDataProvider([
            'query' => $query,
        ]);

        $this->load($params);

        if (!$this->validate()) {
            // uncomment the following line if you do not want to return any records when validation fails
            // $query->where('0=1');
            return $dataProvider;
        }

        // grid filtering conditions
        $query->andFilterWhere([
            'id' => $this->id,
            'clientId' => $this->clientId,
            'carYear' => $this->carYear,
            'carDoors' => $this->carDoors,
            'createdAt' => $this->createdAt,
            'status' => $this->status,
            'availableFrom' => $this->availableFrom,
            'availableTo' => $this->availableTo,
        ]);

        $query->andFilterWhere(['like', 'carName', $this->carName])
            ->andFilterWhere(['like', 'carModel', $this->carModel]);

        return $dataProvider;
    }
}
