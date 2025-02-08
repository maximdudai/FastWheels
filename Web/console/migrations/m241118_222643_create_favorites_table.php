<?php

use yii\db\Migration;

/**
 * Handles the creation of table `{{%favorites}}`.
 * Has foreign keys to the tables:
 *
 * - `{{%clients}}`
 * - `{{%usercars}}`
 */
class m241118_222643_create_favorites_table extends Migration
{
    /**
     * {@inheritdoc}
     */
    public function safeUp()
    {
        $this->createTable('{{%favorites}}', [
            'id' => $this->primaryKey(),
            'clientId' => $this->integer()->notNull(),
            'carId' => $this->integer()->notNull(),
            'createdAt' => $this->timestamp()->notNull(),
        ], 'ENGINE=InnoDB');

        // creates index for column `clientId`
        $this->createIndex(
            '{{%idx-favorites-clientId}}',
            '{{%favorites}}',
            'clientId'
        );

        // add foreign key for table `{{%clients}}`
        $this->addForeignKey(
            '{{%fk-favorites-clientId}}',
            '{{%favorites}}',
            'clientId',
            '{{%clients}}',
            'id',
            'CASCADE'
        );

        // creates index for column `carId`
        $this->createIndex(
            '{{%idx-favorites-carId}}',
            '{{%favorites}}',
            'carId'
        );

        // add foreign key for table `{{%usercars}}`
        $this->addForeignKey(
            '{{%fk-favorites-carId}}',
            '{{%favorites}}',
            'carId',
            '{{%usercars}}',
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
            '{{%fk-favorites-clientId}}',
            '{{%favorites}}'
        );

        // drops index for column `clientId`
        $this->dropIndex(
            '{{%idx-favorites-clientId}}',
            '{{%favorites}}'
        );

        // drops foreign key for table `{{%usercars}}`
        $this->dropForeignKey(
            '{{%fk-favorites-carId}}',
            '{{%favorites}}'
        );

        // drops index for column `carId`
        $this->dropIndex(
            '{{%idx-favorites-carId}}',
            '{{%favorites}}'
        );

        $this->dropTable('{{%favorites}}');
    }
}
