<?php

use yii\db\Migration;

/**
 * Class m241227_200934_add_field_price_per_day_to_userCars
 */
class m241227_200934_add_field_price_per_day_to_userCars extends Migration
{
    /**
     * {@inheritdoc}
     */
    public function safeUp()
    {
        $this->addColumn('{{%userCars}}', 'priceDay', $this->decimal(10,2)->notNull());
    }

    /**
     * {@inheritdoc}
     */
    public function safeDown()
    {
        $this->dropColumn('{{%userCars}}', 'priceDay');
    }
}
