<?php

use yii\db\Migration;

/**
 * Class m241205_183346_rename_carName_to_carBrand_table_userCars
 */
class m241205_183346_rename_carName_to_carBrand_table_userCars extends Migration
{
    /**
     * {@inheritdoc}
     */
    public function safeUp()
    {
        $this->renameColumn('{{%userCars}}', 'carName', 'carBrand');
    }

    /**
     * {@inheritdoc}
     */
    public function safeDown()
    {
        $this->renameColumn('{{%userCars}}', 'carBrand', 'carName');
    }
}
