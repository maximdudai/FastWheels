<?php

use yii\db\Migration;

/**
 * Handles the creation of table `{{%payments}}`.
 * Has foreign keys to the tables:
 *
 * - `{{%reservations}}`
 * - `{{%clients}}`
 * - `{{%userCars}}`
 */
class m241118_225843_create_payments_table extends Migration
{
    /**
     * {@inheritdoc}
     */
    public function safeUp()
    {
        $this->createTable('{{%payments}}', [
            'id' => $this->primaryKey(),
            'reserveId' => $this->integer()->notNull(),
            'clientId' => $this->integer()->notNull(),
            'carId' => $this->integer()->notNull(),
            'method' => $this->string(30)->notNull(),
            'value' => $this->decimal(10,2)->notNull(),
            'status' => $this->string(50)->notNull(),
        ]);

        // creates index for column `reserveId`
        $this->createIndex(
            '{{%idx-payments-reserveId}}',
            '{{%payments}}',
            'reserveId'
        );

        // add foreign key for table `{{%reservations}}`
        $this->addForeignKey(
            '{{%fk-payments-reserveId}}',
            '{{%payments}}',
            'reserveId',
            '{{%reservations}}',
            'id',
            'CASCADE'
        );

        // creates index for column `clientId`
        $this->createIndex(
            '{{%idx-payments-clientId}}',
            '{{%payments}}',
            'clientId'
        );

        // add foreign key for table `{{%clients}}`
        $this->addForeignKey(
            '{{%fk-payments-clientId}}',
            '{{%payments}}',
            'clientId',
            '{{%clients}}',
            'id',
            'CASCADE'
        );

        // creates index for column `carId`
        $this->createIndex(
            '{{%idx-payments-carId}}',
            '{{%payments}}',
            'carId'
        );

        // add foreign key for table `{{%userCars}}`
        $this->addForeignKey(
            '{{%fk-payments-carId}}',
            '{{%payments}}',
            'carId',
            '{{%userCars}}',
            'id',
            'CASCADE'
        );
    }

    /**
     * {@inheritdoc}
     */
    public function safeDown()
    {
        // drops foreign key for table `{{%reservations}}`
        $this->dropForeignKey(
            '{{%fk-payments-reserveId}}',
            '{{%payments}}'
        );

        // drops index for column `reserveId`
        $this->dropIndex(
            '{{%idx-payments-reserveId}}',
            '{{%payments}}'
        );

        // drops foreign key for table `{{%clients}}`
        $this->dropForeignKey(
            '{{%fk-payments-clientId}}',
            '{{%payments}}'
        );

        // drops index for column `clientId`
        $this->dropIndex(
            '{{%idx-payments-clientId}}',
            '{{%payments}}'
        );

        // drops foreign key for table `{{%userCars}}`
        $this->dropForeignKey(
            '{{%fk-payments-carId}}',
            '{{%payments}}'
        );

        // drops index for column `carId`
        $this->dropIndex(
            '{{%idx-payments-carId}}',
            '{{%payments}}'
        );

        $this->dropTable('{{%payments}}');
    }
}
