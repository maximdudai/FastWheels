<?php

namespace common\models;

use Yii;

/**
 * This is the model class for table "notifications".
 *
 * @property int $id
 * @property int $clientId
 * @property string $content
 * @property string $createdAt
 * @property int|null $read
 *
 * @property Client $client
 */
class Notification extends \yii\db\ActiveRecord
{
    /**
     * {@inheritdoc}
     */
    public static function tableName()
    {
        return 'notifications';
    }

    /**
     * {@inheritdoc}
     */
    public function rules()
    {
        return [
            [['clientId', 'content', 'createdAt'], 'required'],
            [['clientId', 'read'], 'integer'],
            [['createdAt'], 'safe'],
            [['content'], 'string', 'max' => 300],
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
            'read' => 'Read',
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
