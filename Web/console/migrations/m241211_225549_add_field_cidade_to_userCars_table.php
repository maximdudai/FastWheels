<?php

use yii\db\Migration;

/**
 * Class m241211_225549_add_field_cidade_to_usercars_table
 */
class m241211_225549_add_field_cidade_to_usercars_table extends Migration
{
    /**
     * {@inheritdoc}
     */
    public function safeUp()
    {
        $this->addColumn('{{%usercars}}', 'city', $this->string(30)->notNull());
    }

    /**
     * {@inheritdoc}
     */
    public function safeDown()
    {
        $this->dropColumn('{{%usercars}}', 'city');
    }

}
