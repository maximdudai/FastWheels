<?php

use yii\db\Migration;

/**
 * Handles the creation of table `{{%supportTickets}}`.
 * Has foreign keys to the tables:
 *
 * - `{{%clients}}`
 */
class m241118_222359_create_supportTickets_table extends Migration
{
    /**
     * {@inheritdoc}
     */
    public function safeUp()
    {
        $this->createTable('{{%supportTickets}}', [
            'id' => $this->primaryKey(),
            'clientId' => $this->integer()->notNull(),
            'content' => $this->string(1000)->notNull(),
            'createdAt' => $this->timestamp()->notNull(),
            'closed' => $this->boolean(),
        ]);

        // creates index for column `clientId`
        $this->createIndex(
            '{{%idx-supportTickets-clientId}}',
            '{{%supportTickets}}',
            'clientId'
        );

        // add foreign key for table `{{%clients}}`
        $this->addForeignKey(
            '{{%fk-supportTickets-clientId}}',
            '{{%supportTickets}}',
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
            '{{%fk-supportTickets-clientId}}',
            '{{%supportTickets}}'
        );

        // drops index for column `clientId`
        $this->dropIndex(
            '{{%idx-supportTickets-clientId}}',
            '{{%supportTickets}}'
        );

        $this->dropTable('{{%supportTickets}}');
    }
}
