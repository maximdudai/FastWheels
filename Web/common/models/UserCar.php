<?php

namespace common\models;

use Yii;

/**
 * This is the model class for table "usercars".
 *
 * @property int $id
 * @property int $clientId
 * @property string $carName
 * @property string $carModel
 * @property int $carYear
 * @property int $carDoors
 * @property string $createdAt
 * @property int|null $status
 * @property string $availableFrom
 * @property string $availableTo
 *
 * @property Carphoto[] $carphotos
 * @property Carreview[] $carreviews
 * @property Client $client
 * @property Favorite[] $favorites
 * @property Localization[] $localizations
 * @property Payment[] $payments
 * @property Reservation[] $reservations
 */
class UserCar extends \yii\db\ActiveRecord
{
    /**
     * {@inheritdoc}
     */
    public static function tableName()
    {
        return 'usercars';
    }

    /**
     * {@inheritdoc}
     */
    public function rules()
    {
        return [
            [['clientId', 'carName', 'carModel', 'carYear', 'carDoors', 'createdAt', 'availableFrom', 'availableTo'], 'required'],
            [['clientId', 'carYear', 'carDoors', 'status'], 'integer'],
            [['createdAt', 'availableFrom', 'availableTo'], 'safe'],
            [['carName'], 'string', 'max' => 80],
            [['carModel'], 'string', 'max' => 30],
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
            'carName' => 'Car Name',
            'carModel' => 'Car Model',
            'carYear' => 'Car Year',
            'carDoors' => 'Car Doors',
            'createdAt' => 'Created At',
            'status' => 'Status',
            'availableFrom' => 'Available From',
            'availableTo' => 'Available To',
        ];
    }

    /**
     * Gets query for [[Carphotos]].
     *
     * @return \yii\db\ActiveQuery
     */
    public function getCarphotos()
    {
        return $this->hasMany(Carphoto::class, ['carId' => 'id']);
    }

    /**
     * Gets query for [[Carreviews]].
     *
     * @return \yii\db\ActiveQuery
     */
    public function getCarreviews()
    {
        return $this->hasMany(Carreview::class, ['carId' => 'id']);
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
     * Gets query for [[Favorites]].
     *
     * @return \yii\db\ActiveQuery
     */
    public function getFavorites()
    {
        return $this->hasMany(Favorite::class, ['carId' => 'id']);
    }

    /**
     * Gets query for [[Localizations]].
     *
     * @return \yii\db\ActiveQuery
     */
    public function getLocalizations()
    {
        return $this->hasMany(Localization::class, ['carId' => 'id']);
    }

    /**
     * Gets query for [[Payments]].
     *
     * @return \yii\db\ActiveQuery
     */
    public function getPayments()
    {
        return $this->hasMany(Payment::class, ['carId' => 'id']);
    }

    /**
     * Gets query for [[Reservations]].
     *
     * @return \yii\db\ActiveQuery
     */
    public function getReservations()
    {
        return $this->hasMany(Reservation::class, ['carId' => 'id']);
    }
}
