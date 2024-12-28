<?php

use yii\db\Migration;

/**
 * Handles the creation of table `{{%userCars}}`.
 * Has foreign keys to the tables:
 *
 * - `{{%clients}}`
 */
class m241118_222507_create_userCars_table extends Migration
{
    /**
     * {@inheritdoc}
     */
    public function safeUp()
    {
        $this->createTable('{{%userCars}}', [
            'id' => $this->primaryKey(),
            'clientId' => $this->integer()->notNull(),
            'carBrand' => $this->string(80)->notNull(),
            'carModel' => $this->string(30)->notNull(),
            'carYear' => $this->integer()->notNull(),
            'carDoors' => $this->integer()->notNull(),
            'createdAt' => $this->timestamp()->notNull(),
            'status' => $this->boolean(),
            'availableFrom' => $this->timestamp()->notNull(),
            'availableTo' => $this->timestamp()->notNull(),
        ]);

        // creates index for column `clientId`
        $this->createIndex(
            '{{%idx-userCars-clientId}}',
            '{{%userCars}}',
            'clientId'
        );

        // add foreign key for table `{{%clients}}`
        $this->addForeignKey(
            '{{%fk-userCars-clientId}}',
            '{{%userCars}}',
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
            '{{%fk-userCars-clientId}}',
            '{{%userCars}}'
        );

        // drops index for column `clientId`
        $this->dropIndex(
            '{{%idx-userCars-clientId}}',
            '{{%userCars}}'
        );

        $this->dropTable('{{%userCars}}');
    }
}
