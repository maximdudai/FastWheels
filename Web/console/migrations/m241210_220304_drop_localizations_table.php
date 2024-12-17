<?php

use yii\db\Migration;

/**
 * Handles the dropping of table `{{%localizations}}`.
 */
class m241210_220304_drop_localizations_table extends Migration
{
    /**
     * {@inheritdoc}
     */
    public function safeUp()
    {
        // drops foreign key for table `{{%userCars}}`
        $this->dropForeignKey(
            '{{%fk-localization-carId}}',
            '{{%localizations}}'
        );

        // drops index for column `carId`
        $this->dropIndex(
            '{{%idx-localization-carId}}',
            '{{%localizations}}'
        );

        // drops the table
        $this->dropTable('{{%localizations}}');
    }

    /**
     * {@inheritdoc}
     */
    public function safeDown()
    {
        // recreates the table
        $this->createTable('{{%localizations}}', [
            'id' => $this->primaryKey(),
            'carId' => $this->integer()->notNull(),
            'locationCity' => $this->string(100)->notNull(),
            'locationX' => $this->float()->notNull(),
            'locationY' => $this->float()->notNull(),
        ]);

        // creates index for column `carId`
        $this->createIndex(
            '{{%idx-localization-carId}}',
            '{{%localizations}}',
            'carId'
        );

        // adds foreign key for table `{{%userCars}}`
        $this->addForeignKey(
            '{{%fk-localization-carId}}',
            '{{%localizations}}',
            'carId',
            '{{%userCars}}',
            'id',
            'CASCADE'
        );
    }
}
