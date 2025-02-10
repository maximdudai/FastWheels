<?php

namespace backend\models;

use Yii;

/**
 * This is the model class for table "taxes".
 *
 * @property int $id
 * @property float $tax_value
 */
class Taxes extends \yii\db\ActiveRecord
{
    /**
     * {@inheritdoc}
     */
    public static function tableName()
    {
        return 'taxes';
    }

    /**
     * {@inheritdoc}
     */
    public function rules()
    {
        return [
            [['tax_value'], 'required'],
            [['tax_value'], 'number'],
        ];
    }

    /**
     * {@inheritdoc}
     */
    public function attributeLabels()
    {
        return [
            'id' => 'ID',
            'tax_value' => 'Tax Value',
        ];
    }
}
