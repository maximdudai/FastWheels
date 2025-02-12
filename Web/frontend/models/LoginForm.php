<?php
namespace frontend\models;

use Yii;
use yii\base\Model;
use common\models\User;


class LoginForm extends Model
{
    public $username;
    public $password;
    public $rememberMe = true;

    private $_user = false;

    public function rules()
    {
        return [
            [['username', 'password'], 'required'],
            ['rememberMe', 'boolean'],
            ['password', 'validatePassword'],
        ];
    }

    public function validatePassword($attribute, $params)
    {
        if (!$this->hasErrors()) {
            $user = $this->getUser();

            if (!$user || !$user->validatePassword($this->password)) {
                $this->addError($attribute, 'Nome de usuário ou senha incorretos.');
            }
        }
    }

    public function login()
    {
        if ($this->validate()) {
            $user = $this->getUser();
            if ($user) {
                return Yii::$app->user->login($user, $this->rememberMe ? 3600 * 24 * 30 : 0);
            }
            return false;
        }
        return false;
    }

    protected function getUser()
    {
        if ($this->_user === null) {
            $this->_user = User::findByUsername($this->username);
        }

        return $this->_user;
    }
}
