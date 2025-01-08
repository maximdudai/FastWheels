<?php

use yii\db\Migration;

/**
 * Class m241217_015132_rename_morada_codigoPostal_cidade_usercars_table
 */
class m241217_015132_rename_morada_codigoPostal_cidade_usercars_table extends Migration
{
    /**
     * {@inheritdoc}
     */
    public function safeUp()
    {
        $this->renameColumn('{{%usercars}}', 'morada', 'address');
        $this->renameColumn('{{%usercars}}', 'codigoPostal', 'postalCode');
        $this->renameColumn('{{%usercars}}', 'city', 'city');
    }

    /**
     * {@inheritdoc}
     */
    public function safeDown()
    {
        $this->renameColumn('{{%usercars}}', 'address', 'morada');
        $this->renameColumn('{{%usercars}}', 'postalCode', 'codigoPostal');
        $this->renameColumn('{{%usercars}}', 'city', 'city');
    }

}
