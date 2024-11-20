<?php

use yii\db\Migration;

/**
 * Class m241119_223434_rename_table_carReview
 */
class m241119_223434_rename_table_carReview extends Migration
{
    /**
     * {@inheritdoc}
     */
    public function safeUp()
    {
        $this->renameTable('carReview', 'carReviews');
    }

    /**
     * {@inheritdoc}
     */
    public function safeDown()
    {
        echo "m241119_223434_rename_table_carReview cannot be reverted.\n";

        return false;
    }
}
