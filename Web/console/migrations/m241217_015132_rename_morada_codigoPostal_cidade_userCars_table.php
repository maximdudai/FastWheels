<?php

use yii\db\Migration;

/**
 * Class m241217_015132_rename_morada_codigoPostal_cidade_userCars_table
 */
class m241217_015132_rename_morada_codigoPostal_cidade_userCars_table extends Migration
{
    /**
     * {@inheritdoc}
     */
    public function safeUp()
    {
        $this->renameColumn('{{%userCars}}', 'morada', 'address');
        $this->renameColumn('{{%userCars}}', 'codigoPostal', 'postalCode');
        $this->renameColumn('{{%userCars}}', 'cidade', 'city');
    }

    /**
     * {@inheritdoc}
     */
    public function safeDown()
    {
        $this->renameColumn('{{%userCars}}', 'address', 'morada');
        $this->renameColumn('{{%userCars}}', 'postalCode', 'codigoPostal');
        $this->renameColumn('{{%userCars}}', 'city', 'cidade');
    }

}
