<?php

use yii\db\Migration;

/**
 * Class usercars
 */
class usercars extends Migration
{
    /**
     * {@inheritdoc}
     */
    public function safeUp()
    {
        $this->addColumn('{{%usercars}}', 'morada', $this->string(200)->notNull());
        $this->addColumn('{{%usercars}}', 'codigoPostal', $this->string(10)->notNull());
    }

    /**
     * {@inheritdoc}
     */
    public function safeDown()
    {
        $this->dropColumn('{{%usercars}}', 'morada');
        $this->dropColumn('{{%usercars}}', 'codigoPostal');
    }

}
