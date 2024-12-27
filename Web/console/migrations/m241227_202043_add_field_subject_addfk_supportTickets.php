<?php

use yii\db\Migration;

/**
 * Class m241227_202043_add_field_subject_addfk_supportTickets
 */
class m241227_202043_add_field_subject_addfk_supportTickets extends Migration
{
    /**
     * {@inheritdoc}
     */
    public function safeUp()
    {
        $this->addColumn('{{%supportTickets}}', 'subject',$this->string(144)->notNull());

        $this->addColumn('{{%supportTickets}}', 'reservationId', $this->integer()->notNull());

        // creates index for column `reservationId`
        $this->createIndex(
            '{{%idx-supportTickets-reservationId}}',
            '{{%supportTickets}}',
            'reservationId'
        );

        // add foreign key for table `{{%supportTickets}}`
        $this->addForeignKey(
            '{{%fk-supportTickets-reservationId}}',
            '{{%supportTickets}}',
            'reservationId',
            '{{%reservations}}',
            'id',
            'CASCADE'
        );

    }

    /**
     * {@inheritdoc}
     */
    public function safeDown()
    {
        // drops foreign key for table `{{%supportTickets}}`
        $this->dropForeignKey(
            '{{%fk-supportTickets-reservationId}}',
            '{{%supportTickets}}'
        );

        // drops index for column `reservationId`
        $this->dropIndex(
            '{{%idx-supportTickets-reservationId}}',
            '{{%supportTickets}}'
        );

        $this->dropColumn('{{%supportTickets}}', 'reservationId');

        $this->dropColumn('{{%supportTickets}}', 'subject');
    }
}
