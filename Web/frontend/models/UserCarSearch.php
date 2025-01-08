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
    public $location;
    /**
     * {@inheritdoc}
     */
    public function rules()
    {
        return [
            [['id', 'clientId', 'carYear', 'carDoors', 'status'], 'integer'],
            [['carBrand', 'carModel', 'createdAt', 'availableFrom', 'availableTo', 'location'], 'safe'],
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

        $dataProvider = new ActiveDataProvider([
            'query' => $query,
        ]);

        $this->load($params);

        if (!$this->validate()) {
            return $dataProvider;
        }

        if (!empty($this->availableFrom)) {
            $query->andFilterWhere(['<=', 'availableFrom', $this->availableFrom]);
        }

        if (!empty($this->availableTo)) {
            $query->andFilterWhere(['>=', 'availableTo', $this->availableTo]);
        }

        $query->andFilterWhere([
            'id' => $this->id,
            'carYear' => $this->carYear,
            'carDoors' => $this->carDoors,
        ]);

        $query->andFilterWhere(['like', 'carBrand', $this->carBrand])
            ->andFilterWhere(['like', 'carModel', $this->carModel]);

        $query->andFilterWhere(['or',
            ['like', 'city', $this->location],
            ['like', 'address', $this->location],
        ]);

        return $dataProvider;
    }

}
