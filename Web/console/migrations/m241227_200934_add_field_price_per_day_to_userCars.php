<?php

use yii\db\Migration;

/**
 * Class m241227_200934_add_field_price_per_day_to_usercars
 */
class m241227_200934_add_field_price_per_day_to_usercars extends Migration
{
    /**
     * {@inheritdoc}
     */
    public function safeUp()
    {
        $this->addColumn('{{%usercars}}', 'priceDay', $this->decimal(10,2)->notNull());
    }

    /**
     * {@inheritdoc}
     */
    public function safeDown()
    {
        $this->dropColumn('{{%usercars}}', 'priceDay');
    }
}
