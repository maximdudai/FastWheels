<?php

use yii\db\Migration;

/**
 * Class m241115_195725_add_admin_user
 */
class m241115_195725_add_admin_user extends Migration
{
    /**
     * {@inheritdoc}
     */
    public function safeUp()
    {
        $authKey = Yii::$app->security->generateRandomString();
        $passwordHash = Yii::$app->security->generatePasswordHash('admin123');

        $this->insert('{{%user}}', [
            'username' => 'admin',
            'auth_key' => $authKey,
            'password_hash' => $passwordHash, // Replace with a secure password
            'email' => 'admin@example.com',
            'status' => 10, // Active
            'created_at' => time(),
            'updated_at' => time(),
        ]);
    }

    /**
     * {@inheritdoc}
     */
    public function safeDown()
    {
        $this->delete('{{%user}}', ['username' => 'admin']);

        return false;
    }

    /*
    // Use up()/down() to run migration code without a transaction.
    public function up()
    {

    }

    public function down()
    {
        echo "m241115_195725_add_admin_user cannot be reverted.\n";

        return false;
    }
    */
}
