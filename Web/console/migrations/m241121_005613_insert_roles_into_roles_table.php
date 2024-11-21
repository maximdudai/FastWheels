<?php

use yii\db\Migration;

/**
 * Class m241121_005613_insert_roles_into_roles_table
 */
class m241121_005613_insert_roles_into_roles_table extends Migration
{
    /**
     * {@inheritdoc}
     */
    public function safeUp()
    {
        $this->batchInsert('roles', ['roleName'], [
            ['client'],
            ['funcionario'],
            ['administrador'],
        ]);
    }

    /**
     * {@inheritdoc}
     */
    public function safeDown()
    {
        $this->delete('roles', ['roleName' => ['client', 'funcionario', 'administrador']]);
    }

    /*
    // Use up()/down() to run migration code without a transaction.
    public function up()
    {

    }

    public function down()
    {
        echo "m241121_005613_insert_roles_into_roles_table cannot be reverted.\n";

        return false;
    }
    */
}
