<?php

namespace common\models;

use Yii;

/**
 * This is the model class for table "favorites".
 *
 * @property int $id
 * @property int $clientId
 * @property int $carId
 * @property string $createdAt
 *
 * @property Usercar $car
 * @property Client $client
 */
class Favorite extends \yii\db\ActiveRecord
{
    /**
     * {@inheritdoc}
     */
    public static function tableName()
    {
        return 'favorites';
    }

    /**
     * {@inheritdoc}
     */
    public function rules()
    {
        return [
            [['clientId', 'carId', 'createdAt'], 'required'],
            [['clientId', 'carId'], 'integer'],
            [['createdAt'], 'safe'],
            [['carId'], 'exist', 'skipOnError' => true, 'targetClass' => Usercar::class, 'targetAttribute' => ['carId' => 'id']],
            [['clientId'], 'exist', 'skipOnError' => true, 'targetClass' => Client::class, 'targetAttribute' => ['clientId' => 'id']],
        ];
    }

    /**
     * {@inheritdoc}
     */
    public function attributeLabels()
    {
        return [
            'id' => 'ID',
            'clientId' => 'Client ID',
            'carId' => 'Car ID',
            'createdAt' => 'Created At',
        ];
    }

    /**
     * Gets query for [[Car]].
     *
     * @return \yii\db\ActiveQuery
     */
    public function getCar()
    {
        return $this->hasOne(Usercar::class, ['id' => 'carId']);
    }

    /**
     * Gets query for [[Client]].
     *
     * @return \yii\db\ActiveQuery
     */
    public function getClient()
    {
        return $this->hasOne(Client::class, ['id' => 'clientId']);
    }
}
