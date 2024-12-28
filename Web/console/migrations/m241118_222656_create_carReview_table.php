<?php

use yii\db\Migration;

/**
 * Handles the creation of table `{{%carReviews}}`.
 * Has foreign keys to the tables:
 *
 * - `{{%userCars}}`
 */
class m241118_222656_create_carReviews_table extends Migration
{
    /**
     * {@inheritdoc}
     */
    public function safeUp()
    {
        $this->createTable('{{%carReviews}}', [
            'id' => $this->primaryKey(),
            'carId' => $this->integer()->notNull(),
            'comment' => $this->string(300)->notNull(),
            'createdAt' => $this->timestamp()->notNull(),
        ]);

        // creates index for column `carId`
        $this->createIndex(
            '{{%idx-carReviews-carId}}',
            '{{%carReviews}}',
            'carId'
        );

        // add foreign key for table `{{%userCars}}`
        $this->addForeignKey(
            '{{%fk-carReviews-carId}}',
            '{{%carReviews}}',
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
            '{{%fk-carReviews-carId}}',
            '{{%carReviews}}'
        );

        // drops index for column `carId`
        $this->dropIndex(
            '{{%idx-carReviews-carId}}',
            '{{%carReviews}}'
        );

        $this->dropTable('{{%carReviews}}');
    }
}
