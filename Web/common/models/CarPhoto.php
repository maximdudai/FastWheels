<?php

namespace common\models;

use Yii;

/**
 * This is the model class for table "carphotos".
 *
 * @property int $id
 * @property int $carId
 * @property string $photoUrl
 *
 * @property Usercar $car
 */
class CarPhoto extends \yii\db\ActiveRecord
{
    /**
     * {@inheritdoc}
     */
    public static function tableName()
    {
        return 'carphotos';
    }

    /**
     * {@inheritdoc}
     */
    public function rules()
    {
        return [
            [['carId', 'photoUrl'], 'required'],
            [['carId'], 'integer'],
            [['photoUrl'], 'string', 'max' => 200],
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
            'photoUrl' => 'Photo Url',
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
}
