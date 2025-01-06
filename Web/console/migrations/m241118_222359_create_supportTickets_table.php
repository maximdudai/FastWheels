<?php

use yii\db\Migration;

/**
 * Handles the creation of table `{{%supporttickets}}`.
 * Has foreign keys to the tables:
 *
 * - `{{%clients}}`
 */
class m241118_222359_create_supporttickets_table extends Migration
{
    /**
     * {@inheritdoc}
     */
    public function safeUp()
    {
        $this->createTable('{{%supporttickets}}', [
            'id' => $this->primaryKey(),
            'clientId' => $this->integer()->notNull(),
            'content' => $this->string(1000)->notNull(),
            'createdAt' => $this->timestamp()->notNull(),
            'closed' => $this->boolean(),
        ]);

        // creates index for column `clientId`
        $this->createIndex(
            '{{%idx-supporttickets-clientId}}',
            '{{%supporttickets}}',
            'clientId'
        );

        // add foreign key for table `{{%clients}}`
        $this->addForeignKey(
            '{{%fk-supporttickets-clientId}}',
            '{{%supporttickets}}',
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
            '{{%fk-supporttickets-clientId}}',
            '{{%supporttickets}}'
        );

        // drops index for column `clientId`
        $this->dropIndex(
            '{{%idx-supporttickets-clientId}}',
            '{{%supporttickets}}'
        );

        $this->dropTable('{{%supporttickets}}');
    }
}
