<?php
namespace frontend\models;

use Yii;
use yii\base\Model;
use common\models\User;


class UserdataForm extends Model
{
    private $_user = false;

    public function rules()
    {
        return [
            [['name', 'email', 'phone', 'iban'], 'required'],
        ];
    }


    protected function getUser()
    {
        if ($this->_user === null) {
            $this->_user = User::findByUsername($this->username);
        }

        return $this->_user;
    }
}
