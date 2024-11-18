<?php

use yii\db\Migration;

/**
 * Handles the creation of table `{{%localization}}`.
 * Has foreign keys to the tables:
 *
 * - `{{%userCars}}`
 */
class m241118_222719_create_localization_table extends Migration
{
    /**
     * {@inheritdoc}
     */
    public function safeUp()
    {
        $this->createTable('{{%localization}}', [
            'id' => $this->primaryKey(),
            'carId' => $this->integer()->notNull(),
            'locationCity' => $this->string(100)->notNull(),
            'locationX' => $this->float()->notNull(),
            'locationY' => $this->float()->notNull(),
        ]);

        // creates index for column `carId`
        $this->createIndex(
            '{{%idx-localization-carId}}',
            '{{%localization}}',
            'carId'
        );

        // add foreign key for table `{{%userCars}}`
        $this->addForeignKey(
            '{{%fk-localization-carId}}',
            '{{%localization}}',
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
        // drops foreign key for table `{{%userCars}}`
        $this->dropForeignKey(
            '{{%fk-localization-carId}}',
            '{{%localization}}'
        );

        // drops index for column `carId`
        $this->dropIndex(
            '{{%idx-localization-carId}}',
            '{{%localization}}'
        );

        $this->dropTable('{{%localization}}');
    }
}
