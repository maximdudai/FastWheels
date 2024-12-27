<?php

use yii\db\Migration;

/**
 * Class m241227_205403_add_fields_feeValue_carValue_reservations
 */
class m241227_205403_add_fields_feeValue_carValue_reservations extends Migration
{
    /**
     * {@inheritdoc}
     */
    public function safeUp()
    {
        $this->addColumn('{{%reservations}}', 'feeValue', $this->decimal(10,2)->notNull());

        $this->addColumn('{{%reservations}}', 'carValue', $this->decimal(10,2)->notNull());
    }

    /**
     * {@inheritdoc}
     */
    public function safeDown()
    {
        $this->dropColumn('{{%reservations}}', 'feeValue');

        $this->dropColumn('{{%reservations}}', 'carValue');
    }
}
