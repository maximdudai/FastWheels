<?php

use yii\db\Migration;

/**
 * Class m250103_210153_add_field_to_supporttickets
 */
class m250103_210153_add_field_to_supporttickets extends Migration
{
    /**
     * {@inheritdoc}
     */
    public function safeUp()
    {
        $this->addColumn('{{%supporttickets}}', 'status', $this->string(256)->notNull()->defaultValue(0));
    }

    /**
     * {@inheritdoc}
     */
    public function safeDown()
    {
        $this->dropColumn('{{%supporttickets}}', 'status');
    }

    /*
    // Use up()/down() to run migration code without a transaction.
    public function up()
    {

    }

    public function down()
    {
        echo "m250103_210153_add_field_to_supporttickets cannot be reverted.\n";

        return false;
    }
    */
}
