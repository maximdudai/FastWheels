<?php

namespace common\models;

use Yii;

/**
 * This is the model class for table "usercars".
 *
 * @property int $id
 * @property int $clientId
 * @property string $carBrand
 * @property string $carModel
 * @property int $carYear
 * @property int $carDoors
 * @property string $createdAt
 * @property int|null $status
 * @property string $availableFrom
 * @property string $availableTo
 * @property string $address
 * @property string $postalCode
 * @property string $city
 * @property float $priceDay
 *
 * @property Carphoto[] $carphotos
 * @property Carreview[] $carreviews
 * @property Client $client
 * @property Favorite[] $favorites
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
            [['clientId', 'carBrand', 'carModel', 'carYear', 'carDoors', 'createdAt', 'availableFrom', 'availableTo', 'address', 'postalCode', 'city', 'priceDay'], 'required'],
            [['clientId', 'carYear', 'carDoors', 'status'], 'integer'],
            [['createdAt', 'availableFrom', 'availableTo'], 'safe'],
            [['priceDay'], 'number'],
            [['carBrand'], 'string', 'max' => 80],
            [['carModel', 'city'], 'string', 'max' => 30],
            [['address'], 'string', 'max' => 200],
            [['postalCode'], 'string', 'max' => 10],
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
            'carBrand' => 'Car Brand',
            'carModel' => 'Car Model',
            'carYear' => 'Car Year',
            'carDoors' => 'Car Doors',
            'createdAt' => 'Created At',
            'status' => 'Status',
            'availableFrom' => 'Available From',
            'availableTo' => 'Available To',
            'address' => 'Address',
            'postalCode' => 'Postal Code',
            'city' => 'City',
            'priceDay' => 'Price Day',
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
