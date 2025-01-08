<?php

use yii\db\Migration;

/**
 * Class m241227_202043_add_field_subject_addfk_supporttickets
 */
class m241227_202043_add_field_subject_addfk_supporttickets extends Migration
{
    /**
     * {@inheritdoc}
     */
    public function safeUp()
    {
        $this->addColumn('{{%supporttickets}}', 'subject', $this->string(144)->notNull());

        // Allow reservationId to be NULL
        $this->addColumn('{{%supporttickets}}', 'reservationId', $this->integer()->null());

        // creates index for column `reservationId`
        $this->createIndex(
            '{{%idx-supporttickets-reservationId}}',
            '{{%supporttickets}}',
            'reservationId'
        );

        // add foreign key for table `{{%supporttickets}}`
        $this->addForeignKey(
            '{{%fk-supporttickets-reservationId}}',
            '{{%supporttickets}}',
            'reservationId',
            '{{%reservations}}',
            'id',
            'SET NULL' // When reservation is deleted, set reservationId to NULL
        );
    }

    /**
     * {@inheritdoc}
     */
    public function safeDown()
    {
        // drops foreign key for table `{{%supporttickets}}`
        $this->dropForeignKey(
            '{{%fk-supporttickets-reservationId}}',
            '{{%supporttickets}}'
        );

        // drops index for column `reservationId`
        $this->dropIndex(
            '{{%idx-supporttickets-reservationId}}',
            '{{%supporttickets}}'
        );

        $this->dropColumn('{{%supporttickets}}', 'reservationId');

        $this->dropColumn('{{%supporttickets}}', 'subject');
    }
}
