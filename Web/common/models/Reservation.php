<?php

namespace common\models;

use Yii;

/**
 * This is the model class for table "reservations".
 *
 * @property int $id
 * @property int $clientId
 * @property int $carId
 * @property string $dateStart
 * @property string $dateEnd
 * @property string|null $createAt
 * @property int|null $filled
 * @property float $value
 *
 * @property Usercar $car
 * @property Client $client
 * @property Payment[] $payments
 */
class Reservation extends \yii\db\ActiveRecord
{
    /**
     * {@inheritdoc}
     */
    public static function tableName()
    {
        return 'reservations';
    }

    /**
     * {@inheritdoc}
     */
    public function rules()
    {
        return [
            [['clientId', 'carId', 'dateStart', 'dateEnd', 'value'], 'required'],
            [['clientId', 'carId', 'filled'], 'integer'],
            [['dateStart', 'dateEnd', 'createAt'], 'safe'],
            [['value'], 'number'],
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
            'dateStart' => 'Date Start',
            'dateEnd' => 'Date End',
            'createAt' => 'Create At',
            'filled' => 'Filled',
            'value' => 'Value',
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
     * Gets query for [[Payments]].
     *
     * @return \yii\db\ActiveQuery
     */
    public function getPayments()
    {
        return $this->hasMany(Payment::class, ['reserveId' => 'id']);
    }
}
