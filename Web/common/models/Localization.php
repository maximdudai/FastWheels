<?php

namespace common\models;

use Yii;

/**
 * This is the model class for table "localizations".
 *
 * @property int $id
 * @property int $carId
 * @property string $locationCity
 * @property float $locationX
 * @property float $locationY
 *
 * @property Usercar $car
 */
class Localization extends \yii\db\ActiveRecord
{
    /**
     * {@inheritdoc}
     */
    public static function tableName()
    {
        return 'localizations';
    }

    /**
     * {@inheritdoc}
     */
    public function rules()
    {
        return [
            [['carId', 'locationCity', 'locationX', 'locationY'], 'required'],
            [['carId'], 'integer'],
            [['locationX', 'locationY'], 'number'],
            [['locationCity'], 'string', 'max' => 100],
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
            'locationCity' => 'Location City',
            'locationX' => 'Location X',
            'locationY' => 'Location Y',
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
