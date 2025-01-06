<?php

use common\models\User;
use yii\db\Migration;

/**
 * Class m250103_230230_add_admin_user
 */
class m250103_230230_add_admin_user extends Migration
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

        $this->insert('{{%clients}}', [
            'name' => 'admin',
            'email' => 'admin@example.com',
            'phone' => 'none',
            'roleId' => 3,
            'createdAt' => Date('Y-m-d H:i:s'),
            'balance' => 0,
            'iban' => 'none',
            'userId' => User::findByUsername('admin')->id,
        ]);
        
    }

    /**
     * {@inheritdoc}
     */
    public function safeDown()
    {
        $this->delete('{{%user}}', ['username' => 'admin']);
        $this->delete('{{%clients}}', ['name' => 'admin']);

        return false;
    }

    /*
    // Use up()/down() to run migration code without a transaction.
    public function up()
    {

    }

    public function down()
    {
        echo "m250103_230230_add_admin_user cannot be reverted.\n";

        return false;
    }
    */
}
