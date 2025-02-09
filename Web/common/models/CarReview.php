<?php

namespace common\models;

use Yii;

/**
 * This is the model class for table "carreviews".
 *
 * @property int $id
 * @property int $carId
 * @property string $comment
 * @property string $createdAt
 *
 * @property UserCar $car
 */
class CarReview extends \yii\db\ActiveRecord
{
    /**
     * {@inheritdoc}
     */
    public static function tableName()
    {
        return 'carreviews';
    }

    /**
     * {@inheritdoc}
     */
    public function rules()
    {
        return [
            [['carId', 'comment', 'createdAt'], 'required'],
            [['carId'], 'integer'],
            [['createdAt'], 'safe'],
            [['comment'], 'string', 'max' => 300],
            [['carId'], 'exist', 'skipOnError' => true, 'targetClass' => Usercar::class, 'targetAttribute' => ['carId' => 'id']],
        ];
    }

    /**
     * {@inheritdoc}
     */
    public function attributeLabels()
    {
        return [
            'id' => 'ID',
            'carId' => 'Car ID',
            'comment' => 'Comment',
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
        return $this->hasOne(UserCar::class, ['id' => 'carId']);
    }
}
