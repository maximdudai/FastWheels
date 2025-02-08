<?php

use yii\db\Migration;

/**
 * Handles the creation of table `{{%carreviews}}`.
 * Has foreign keys to the tables:
 *
 * - `{{%usercars}}`
 */
class m241118_222656_create_carreviews_table extends Migration
{
    /**
     * {@inheritdoc}
     */
    public function safeUp()
    {
        $this->createTable('{{%carreviews}}', [
            'id' => $this->primaryKey(),
            'carId' => $this->integer()->notNull(),
            'comment' => $this->string(300)->notNull(),
            'createdAt' => $this->timestamp()->notNull(),
        ], 'ENGINE=InnoDB');

        // creates index for column `carId`
        $this->createIndex(
            '{{%idx-carreviews-carId}}',
            '{{%carreviews}}',
            'carId'
        );

        // add foreign key for table `{{%usercars}}`
        $this->addForeignKey(
            '{{%fk-carreviews-carId}}',
            '{{%carreviews}}',
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
        // drops foreign key for table `{{%usercars}}`
        $this->dropForeignKey(
            '{{%fk-carreviews-carId}}',
            '{{%carreviews}}'
        );

        // drops index for column `carId`
        $this->dropIndex(
            '{{%idx-carreviews-carId}}',
            '{{%carreviews}}'
        );

        $this->dropTable('{{%carreviews}}');
    }
}
