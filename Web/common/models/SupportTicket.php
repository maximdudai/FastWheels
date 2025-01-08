<?php

namespace common\models;

use Yii;

/**
 * This is the model class for table "supporttickets".
 *
 * @property int $id
 * @property int $clientId
 * @property string $content
 * @property string $createdAt
 * @property int|null $closed
 * @property string $subject
 * @property int $reservationId
 *
 * @property Client $client
 * @property Reservation $reservation
 */
class SupportTicket extends \yii\db\ActiveRecord
{
    /**
     * {@inheritdoc}
     */
    public static function tableName()
    {
        return 'supporttickets';
    }

    /**
     * {@inheritdoc}
     */
    public function rules()
    {
        return [
            [['clientId', 'content', 'createdAt', 'subject'], 'required'],  // Exclude 'reservationId'
            [['clientId', 'closed', 'reservationId'], 'integer'],
            [['createdAt'], 'safe'],
            [['content'], 'string', 'max' => 1000],
            [['subject'], 'string', 'max' => 144],
            [['status'], 'string', 'max' => 256],
            [['reservationId'], 'default', 'value' => null],  // Allow null by default
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
            'content' => 'Content',
            'createdAt' => 'Created At',
            'closed' => 'Closed',
            'subject' => 'Subject',
            'reservationId' => 'Reservation ID',
            'status' => 'Status',
        ];
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
     * Gets query for [[Reservation]].
     *
     * @return \yii\db\ActiveQuery
     */
    public function getReservation()
    {
        return $this->hasOne(Reservation::class, ['id' => 'reservationId']);
    }
}
