<?php

use yii\db\Migration;

/**
 * Class m241119_232018_addfk_table_clients
 */
class m241119_232018_addfk_table_clients extends Migration
{
    /**
     * {@inheritdoc}
     */
    public function safeUp()
    {
        // add new column
        $this->addColumn(
            '{{%clients}}',
            'userId',
            $this->integer()->notNull()
        );

        // creates index for column `userId`
        $this->createIndex(
            '{{%idx-clients-userId}}',
            '{{%clients}}',
            'userId'
        );

        // add foreign key for table `{{%user}}`
        $this->addForeignKey(
            '{{%idx-clients-userId}}',
            '{{%clients}}',
            'userId',
            '{{%user}}',
            'id',
            'CASCADE'
        );
    }

    /**
     * {@inheritdoc}
     */
    public function safeDown()
    {
        // drops foreign key for table `{{%user}}`
        $this->dropForeignKey(
            '{{%idx-clients-userId}}',
            '{{%user}}'
        );

        // drops index for column `userId`
        $this->dropIndex(
            '{{%idx-clients-userId}}',
            '{{%user}}'
        );

        // drops column `userId`
        $this->dropColumn(
            '{{%clients}}',
            'userId'
        );
    }
}
