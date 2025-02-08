<?php

use yii\db\Migration;

/**
 * Handles the creation of table `{{%usercars}}`.
 * Has foreign keys to the tables:
 *
 * - `{{%clients}}`
 */
class m241118_222507_create_usercars_table extends Migration
{
    /**
     * {@inheritdoc}
     */
    public function safeUp()
    {
        $this->createTable('{{%usercars}}', [
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
        ], 'ENGINE=InnoDB');

        // creates index for column `clientId`
        $this->createIndex(
            '{{%idx-usercars-clientId}}',
            '{{%usercars}}',
            'clientId'
        );

        // add foreign key for table `{{%clients}}`
        $this->addForeignKey(
            '{{%fk-usercars-clientId}}',
            '{{%usercars}}',
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
            '{{%fk-usercars-clientId}}',
            '{{%usercars}}'
        );

        // drops index for column `clientId`
        $this->dropIndex(
            '{{%idx-usercars-clientId}}',
            '{{%usercars}}'
        );

        $this->dropTable('{{%usercars}}');
    }
}
