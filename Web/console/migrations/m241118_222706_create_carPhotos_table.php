<?php

use yii\db\Migration;

/**
 * Handles the creation of table `{{%carPhotos}}`.
 * Has foreign keys to the tables:
 *
 * - `{{%userCars}}`
 */
class m241118_222706_create_carPhotos_table extends Migration
{
    /**
     * {@inheritdoc}
     */
    public function safeUp()
    {
        $this->createTable('{{%carPhotos}}', [
            'id' => $this->primaryKey(),
            'carId' => $this->integer()->notNull(),
            'photoUrl' => $this->string(200)->notNull(),
        ]);

        // creates index for column `carId`
        $this->createIndex(
            '{{%idx-carPhotos-carId}}',
            '{{%carPhotos}}',
            'carId'
        );

        // add foreign key for table `{{%userCars}}`
        $this->addForeignKey(
            '{{%fk-carPhotos-carId}}',
            '{{%carPhotos}}',
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
            '{{%fk-carPhotos-carId}}',
            '{{%carPhotos}}'
        );

        // drops index for column `carId`
        $this->dropIndex(
            '{{%idx-carPhotos-carId}}',
            '{{%carPhotos}}'
        );

        $this->dropTable('{{%carPhotos}}');
    }
}
