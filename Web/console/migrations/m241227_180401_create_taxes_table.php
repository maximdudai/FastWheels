<?php

use yii\db\Migration;

/**
 * Handles the creation of table `{{%taxes}}`.
 */
class m241227_180401_create_taxes_table extends Migration
{
    /**
     * {@inheritdoc}
     */
    public function safeUp()
    {
        $this->createTable('{{%taxes}}', [
            'id' => $this->primaryKey(),
            'tax_value' => $this->decimal(10,2)->notNull(),
        ]);

        $this->insert('{{%taxes}}', [
            'tax_value' => 10.0, // Default tax value
        ]);
    }

    /**
     * {@inheritdoc}
     */
    public function safeDown()
    {
        $this->dropTable('{{%taxes}}');
    }
}
