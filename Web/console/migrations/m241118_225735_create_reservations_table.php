<?php

use yii\db\Migration;

/**
 * Handles the creation of table `{{%reservations}}`.
 * Has foreign keys to the tables:
 *
 * - `{{%clients}}`
 * - `{{%usercars}}`
 */
class m241118_225735_create_reservations_table extends Migration
{
    /**
     * {@inheritdoc}
     */
    public function safeUp()
    {
        $this->createTable('{{%reservations}}', [
            'id' => $this->primaryKey(),
            'clientId' => $this->integer()->notNull(),
            'carId' => $this->integer()->notNull(),
            'dateStart' => $this->timestamp()->notNull(),
            'dateEnd' => $this->timestamp()->notNull(),
            'createAt' => $this->timestamp(),
            'filled' => $this->boolean(),
            'value' => $this->decimal(10,2)->notNull(),
        ], 'ENGINE=InnoDB');

        // creates index for column `clientId`
        $this->createIndex(
            '{{%idx-reservations-clientId}}',
            '{{%reservations}}',
            'clientId'
        );

        // add foreign key for table `{{%clients}}`
        $this->addForeignKey(
            '{{%fk-reservations-clientId}}',
            '{{%reservations}}',
            'clientId',
            '{{%clients}}',
            'id',
            'CASCADE'
        );

        // creates index for column `carId`
        $this->createIndex(
            '{{%idx-reservations-carId}}',
            '{{%reservations}}',
            'carId'
        );

        // add foreign key for table `{{%usercars}}`
        $this->addForeignKey(
            '{{%fk-reservations-carId}}',
            '{{%reservations}}',
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
            '{{%fk-reservations-clientId}}',
            '{{%reservations}}'
        );

        // drops index for column `clientId`
        $this->dropIndex(
            '{{%idx-reservations-clientId}}',
            '{{%reservations}}'
        );

        // drops foreign key for table `{{%usercars}}`
        $this->dropForeignKey(
            '{{%fk-reservations-carId}}',
            '{{%reservations}}'
        );

        // drops index for column `carId`
        $this->dropIndex(
            '{{%idx-reservations-carId}}',
            '{{%reservations}}'
        );

        $this->dropTable('{{%reservations}}');
    }
}
