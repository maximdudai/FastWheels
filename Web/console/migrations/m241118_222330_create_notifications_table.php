<?php

use yii\db\Migration;

/**
 * Handles the creation of table `{{%notifications}}`.
 * Has foreign keys to the tables:
 *
 * - `{{%clients}}`
 */
class m241118_222330_create_notifications_table extends Migration
{
    /**
     * {@inheritdoc}
     */
    public function safeUp()
    {
        $this->createTable('{{%notifications}}', [
            'id' => $this->primaryKey(),
            'clientId' => $this->integer()->notNull(),
            'content' => $this->string(300)->notNull(),
            'createdAt' => $this->timestamp()->notNull(),
            'read' => $this->boolean(),
        ] , 'ENGINE=InnoDB');

        // creates index for column `clientId`
        $this->createIndex(
            '{{%idx-notifications-clientId}}',
            '{{%notifications}}',
            'clientId'
        );

        // add foreign key for table `{{%clients}}`
        $this->addForeignKey(
            '{{%fk-notifications-clientId}}',
            '{{%notifications}}',
            'clientId',
            '{{%clients}}',
            'id',
            'CASCADE'
        );
    }

    /**
     * {@inheritdoc}
     */
    public function safeDown()
    {
        // drops foreign key for table `{{%clients}}`
        $this->dropForeignKey(
            '{{%fk-notifications-clientId}}',
            '{{%notifications}}'
        );

        // drops index for column `clientId`
        $this->dropIndex(
            '{{%idx-notifications-clientId}}',
            '{{%notifications}}'
        );

        $this->dropTable('{{%notifications}}');
    }
}
