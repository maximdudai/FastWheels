<?php

use yii\db\Migration;

/**
 * Handles the creation of table `{{%carReview}}`.
 * Has foreign keys to the tables:
 *
 * - `{{%userCars}}`
 */
class m241118_222656_create_carReview_table extends Migration
{
    /**
     * {@inheritdoc}
     */
    public function safeUp()
    {
        $this->createTable('{{%carReview}}', [
            'id' => $this->primaryKey(),
            'carId' => $this->integer()->notNull(),
            'comment' => $this->string(300)->notNull(),
            'createdAt' => $this->timestamp()->notNull(),
        ]);

        // creates index for column `carId`
        $this->createIndex(
            '{{%idx-carReview-carId}}',
            '{{%carReview}}',
            'carId'
        );

        // add foreign key for table `{{%userCars}}`
        $this->addForeignKey(
            '{{%fk-carReview-carId}}',
            '{{%carReview}}',
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
            '{{%fk-carReview-carId}}',
            '{{%carReview}}'
        );

        // drops index for column `carId`
        $this->dropIndex(
            '{{%idx-carReview-carId}}',
            '{{%carReview}}'
        );

        $this->dropTable('{{%carReview}}');
    }
}
