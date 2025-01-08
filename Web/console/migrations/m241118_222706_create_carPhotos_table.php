<?php

use yii\db\Migration;

/**
 * Handles the creation of table `{{%carphotos}}`.
 * Has foreign keys to the tables:
 *
 * - `{{%usercars}}`
 */
class m241118_222706_create_carphotos_table extends Migration
{
    /**
     * {@inheritdoc}
     */
    public function safeUp()
    {
        $this->createTable('{{%carphotos}}', [
            'id' => $this->primaryKey(),
            'carId' => $this->integer()->notNull(),
            'photoUrl' => $this->string(200)->notNull(),
        ]);

        // creates index for column `carId`
        $this->createIndex(
            '{{%idx-carphotos-carId}}',
            '{{%carphotos}}',
            'carId'
        );

        // add foreign key for table `{{%usercars}}`
        $this->addForeignKey(
            '{{%fk-carphotos-carId}}',
            '{{%carphotos}}',
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
            '{{%fk-carphotos-carId}}',
            '{{%carphotos}}'
        );

        // drops index for column `carId`
        $this->dropIndex(
            '{{%idx-carphotos-carId}}',
            '{{%carphotos}}'
        );

        $this->dropTable('{{%carphotos}}');
    }
}
