<?php

namespace common\models;

use Yii;

/**
 * This is the model class for table "clients".
 *
 * @property int $id
 * @property string $name
 * @property string $email
 * @property string $phone
 * @property int $roleId
 * @property string $createdAt
 * @property float $balance
 * @property string $iban
 * @property int $userId
 *
 * @property Favorite[] $favorites
 * @property Notification[] $notifications
 * @property Payment[] $payments
 * @property Reservation[] $reservations
 * @property Role $role
 * @property Supportticket[] $supporttickets
 * @property User $user
 * @property Usercar[] $usercars
 */
class Client extends \yii\db\ActiveRecord
{
    /**
     * {@inheritdoc}
     */
    public static function tableName()
    {
        return 'clients';
    }

    /**
     * {@inheritdoc}
     */
    public function rules()
    {
        return [
            [['name', 'email', 'phone', 'roleId', 'createdAt', 'balance', 'iban', 'userId'], 'required'],
            [['roleId', 'userId'], 'integer'],
            [['createdAt'], 'safe'],
            [['balance'], 'number'],
            [['name'], 'string', 'max' => 150],
            [['email'], 'string', 'max' => 80],
            [['phone'], 'string', 'max' => 20],
            [['iban'], 'string', 'max' => 30],
            [['roleId'], 'exist', 'skipOnError' => true, 'targetClass' => Role::class, 'targetAttribute' => ['roleId' => 'id']],
            [['userId'], 'exist', 'skipOnError' => true, 'targetClass' => User::class, 'targetAttribute' => ['userId' => 'id']],
        ];
    }

    /**
     * {@inheritdoc}
     */
    public function attributeLabels()
    {
        return [
            'id' => 'ID',
            'name' => 'Name',
            'email' => 'Email',
            'phone' => 'Phone',
            'roleId' => 'Role ID',
            'createdAt' => 'Created At',
            'balance' => 'Balance',
            'iban' => 'Iban',
            'userId' => 'User ID',
        ];
    }

    /**
     * Gets query for [[Favorites]].
     *
     * @return \yii\db\ActiveQuery
     */
    public function getFavorites()
    {
        return $this->hasMany(Favorite::class, ['clientId' => 'id']);
    }

    /**
     * Gets query for [[Notifications]].
     *
     * @return \yii\db\ActiveQuery
     */
    public function getNotifications()
    {
        return $this->hasMany(Notification::class, ['clientId' => 'id']);
    }

    /**
     * Gets query for [[Payments]].
     *
     * @return \yii\db\ActiveQuery
     */
    public function getPayments()
    {
        return $this->hasMany(Payment::class, ['clientId' => 'id']);
    }

    /**
     * Gets query for [[Reservations]].
     *
     * @return \yii\db\ActiveQuery
     */
    public function getReservations()
    {
        return $this->hasMany(Reservation::class, ['clientId' => 'id']);
    }

    /**
     * Gets query for [[Role]].
     *
     * @return \yii\db\ActiveQuery
     */
    public function getRole()
    {
        return $this->hasOne(Role::class, ['id' => 'roleId']);
    }

    /**
     * Gets query for [[Supporttickets]].
     *
     * @return \yii\db\ActiveQuery
     */
    public function getSupporttickets()
    {
        return $this->hasMany(Supportticket::class, ['clientId' => 'id']);
    }

    /**
     * Gets query for [[User]].
     *
     * @return \yii\db\ActiveQuery
     */
    public function getUser()
    {
        return $this->hasOne(User::class, ['id' => 'userId']);
    }

    /**
     * Gets query for [[Usercars]].
     *
     * @return \yii\db\ActiveQuery
     */
    public function getUsercars()
    {
        return $this->hasMany(Usercar::class, ['clientId' => 'id']);
    }
}
