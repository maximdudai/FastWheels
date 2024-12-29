<?php

use yii\db\Migration;

/**
 * Class m241211_225549_add_field_cidade_to_userCars_table
 */
class m241211_225549_add_field_cidade_to_userCars_table extends Migration
{
    /**
     * {@inheritdoc}
     */
    public function safeUp()
    {
        $this->addColumn('{{%userCars}}', 'city', $this->string(30)->notNull());
    }

    /**
     * {@inheritdoc}
     */
    public function safeDown()
    {
        $this->dropColumn('{{%userCars}}', 'city');
    }

}
