<?php

use yii\db\Migration;

/**
 * Handles the creation of table `{{%clients}}`.
 * Has foreign keys to the tables:
 *
 * - `{{%roles}}`
 */
class m241118_222258_create_clients_table extends Migration
{
    /**
     * {@inheritdoc}
     */
    public function safeUp()
    {
        $this->createTable('{{%clients}}', [
            'id' => $this->primaryKey(),
            'name' => $this->string(150)->notNull(),
            'email' => $this->string(80)->notNull(),
            'phone' => $this->string(20)->notNull(),
            'roleId' => $this->integer()->notNull(),
            'createdAt' => $this->timestamp()->notNull(),
            'balance' => $this->decimal(10,2)->notNull(),
            'iban' => $this->string(30)->notNull(),
        ] , 'ENGINE=InnoDB');

        // creates index for column `roleId`
        $this->createIndex(
            '{{%idx-clients-roleId}}',
            '{{%clients}}',
            'roleId'
        );

        // add foreign key for table `{{%roles}}`
        $this->addForeignKey(
            '{{%fk-clients-roleId}}',
            '{{%clients}}',
            'roleId',
            '{{%roles}}',
            'id',
            'CASCADE'
        );
    }

    /**
     * {@inheritdoc}
     */
    public function safeDown()
    {
        // drops foreign key for table `{{%roles}}`
        $this->dropForeignKey(
            '{{%fk-clients-roleId}}',
            '{{%clients}}'
        );

        // drops index for column `roleId`
        $this->dropIndex(
            '{{%idx-clients-roleId}}',
            '{{%clients}}'
        );

        $this->dropTable('{{%clients}}');
    }
}
