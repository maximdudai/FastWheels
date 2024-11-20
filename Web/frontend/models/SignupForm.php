<?php

namespace frontend\models;

use Yii;
use yii\base\Model;
use common\models\User;

/**
 * Signup form
 */
class SignupForm extends Model
{
    public $username;
    public $email;
    public $password;
    public $confirmPassword;
    public $termsAccepted;



    /**
     * {@inheritdoc}
     */
    public function rules()
    {
        return [
            // Validação para o nome de utilizador
            ['username', 'trim'],
            ['username', 'required'],
            ['username', 'unique', 'targetClass' => '\common\models\User', 'message' => 'Este nome de utilizador já está a ser utilizado.'],
            ['username', 'string', 'min' => 2, 'max' => 255],

            // Validação para o email
            ['email', 'trim'],
            ['email', 'required'],
            ['email', 'email'],
            ['email', 'string', 'max' => 255],
            ['email', 'unique', 'targetClass' => '\common\models\User', 'message' => 'Este endereço de email já está a ser utilizado.'],

            // Validação para a palavra-passe
            ['password', 'required'],
            ['password', 'string', 'min' => Yii::$app->params['user.passwordMinLength']],

            // Validação para a confirmação da palavra-passe
            ['confirmPassword', 'required'],
            ['confirmPassword', 'compare', 'compareAttribute' => 'password', 'message' => 'As palavras-passe não coincidem.'],

            // Validação para aceitação dos termos de utilização
            ['termsAccepted', 'required', 'requiredValue' => 1, 'message' => 'Deve aceitar os termos de utilização.'],
        ];

    }

    /**
     * Signs user up.
     *
     * @return bool whether the creating new account was successful and email was sent
     */
    public function signup()
    {
        if (!$this->validate()) {
            return null;
        }
        
        $user = new User();
        $user->username = $this->username;
        $user->email = $this->email;
        $user->setPassword($this->password);
        $user->generateAuthKey();
        $user->generateEmailVerificationToken();

        return $user->save() && $this->sendEmail($user);
    }

    /**
     * Sends confirmation email to user
     * @param User $user user model to with email should be send
     * @return bool whether the email was sent
     */
    protected function sendEmail($user)
    {
        return Yii::$app
            ->mailer
            ->compose(
                ['html' => 'emailVerify-html', 'text' => 'emailVerify-text'],
                ['user' => $user]
            )
            ->setFrom([Yii::$app->params['supportEmail'] => Yii::$app->name . ' robot'])
            ->setTo($this->email)
            ->setSubject('Account registration at ' . Yii::$app->name)
            ->send();
    }
}
