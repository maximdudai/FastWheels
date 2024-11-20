<?php

namespace common\models;

use Yii;

/**
 * This is the model class for table "payments".
 *
 * @property int $id
 * @property int $reserveId
 * @property int $clientId
 * @property int $carId
 * @property string $method
 * @property float $value
 * @property string $status
 *
 * @property Usercar $car
 * @property Client $client
 * @property Reservation $reserve
 */
class Payment extends \yii\db\ActiveRecord
{
    /**
     * {@inheritdoc}
     */
    public static function tableName()
    {
        return 'payments';
    }

    /**
     * {@inheritdoc}
     */
    public function rules()
    {
        return [
            [['reserveId', 'clientId', 'carId', 'method', 'value', 'status'], 'required'],
            [['reserveId', 'clientId', 'carId'], 'integer'],
            [['value'], 'number'],
            [['method'], 'string', 'max' => 30],
            [['status'], 'string', 'max' => 50],
            [['carId'], 'exist', 'skipOnError' => true, 'targetClass' => Usercar::class, 'targetAttribute' => ['carId' => 'id']],
            [['clientId'], 'exist', 'skipOnError' => true, 'targetClass' => Client::class, 'targetAttribute' => ['clientId' => 'id']],
            [['reserveId'], 'exist', 'skipOnError' => true, 'targetClass' => Reservation::class, 'targetAttribute' => ['reserveId' => 'id']],
        ];
    }

    /**
     * {@inheritdoc}
     */
    public function attributeLabels()
    {
        return [
            'id' => 'ID',
            'reserveId' => 'Reserve ID',
            'clientId' => 'Client ID',
            'carId' => 'Car ID',
            'method' => 'Method',
            'value' => 'Value',
            'status' => 'Status',
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

    /**
     * Gets query for [[Reserve]].
     *
     * @return \yii\db\ActiveQuery
     */
    public function getReserve()
    {
        return $this->hasOne(Reservation::class, ['id' => 'reserveId']);
    }
}
