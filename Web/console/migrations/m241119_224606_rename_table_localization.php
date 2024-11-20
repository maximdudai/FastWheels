<?php

use yii\db\Migration;

/**
 * Class m241119_224606_rename_table_localization
 */
class m241119_224606_rename_table_localization extends Migration
{
    /**
     * {@inheritdoc}
     */
    public function safeUp()
    {
        $this->renameTable('localization', 'localizations');
    }

    /**
     * {@inheritdoc}
     */
    public function safeDown()
    {
        echo "m241119_224606_rename_table_localization cannot be reverted.\n";

        return false;
    }
}
