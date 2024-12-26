<?php

use yii\db\Migration;

/**
 * Class m241210_212544_add_fields_to_userCars
 */
class m241210_212544_add_fields_to_userCars extends Migration
{
    /**
     * {@inheritdoc}
     */
    public function safeUp()
    {
        $this->addColumn('{{%userCars}}', 'morada', $this->string(200)->notNull());
        $this->addColumn('{{%userCars}}', 'codigoPostal', $this->string(10)->notNull());
    }

    /**
     * {@inheritdoc}
     */
    public function safeDown()
    {
        $this->dropColumn('{{%userCars}}', 'morada');
        $this->dropColumn('{{%userCars}}', 'codigoPostal');
    }

}
