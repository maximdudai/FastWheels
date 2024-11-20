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
 *
 * @property Client $client
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
            [['clientId', 'content', 'createdAt'], 'required'],
            [['clientId', 'closed'], 'integer'],
            [['createdAt'], 'safe'],
            [['content'], 'string', 'max' => 1000],
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
            'content' => 'Content',
            'createdAt' => 'Created At',
            'closed' => 'Closed',
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
}
