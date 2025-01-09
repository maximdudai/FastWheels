<?php


namespace frontend\tests\Functional;

use common\models\Client;
use common\models\User;
use frontend\tests\FunctionalTester;

class SupportTicketCest
{
    protected $newPassword;

    protected function _before()
    {
        $this->createNewClient(true);
    }
    public function testUpdateProfileWithData(FunctionalTester $I)
    {
        $I->amOnPage('/site/login');
        $I->fillField('#loginform-username', 'fouser');
        $I->fillField('#loginform-password', 'qweasdzxc');

        $I->click('login-button');
    
        $I->dontSee('Incorrect username or password.');

        $I->see('Profile', 'a');
        $I->click('Profile');

        $I->click('Edit Profile');

        $I->fillField('#client-name', 'fouser');
        $I->fillField('#client-email', 'newemailaddress@gmail.com');
        $I->fillField('#client-phone', '123456789');
    }
    
    public function testLoginAsAdministrator(FunctionalTester $I)
    {
        $I->amOnPage('/site/login');
        $I->fillField('#loginform-username', 'admin');
        $I->fillField('#loginform-password', 'admin123');

        $I->click('login-button');
    
        $I->see('Access denied: You do not have permission to access the front office as employee or admin.');
    }


    public function testCreateSupportTicket(FunctionalTester $I)
    {
        $I->amOnPage('/site/login');
        $I->fillField('#loginform-username', 'fouser');
        $I->fillField('#loginform-password', 'qweasdzxc');

        $I->click('login-button');
    
        $I->dontSee('Incorrect username or password.');

        $I->click('Contact Us');

        $I->click('Create Support Ticket');

        $I->fillField('#supportticket-subject', 'Test Subject');
        $I->fillField('#supportticket-content', 'Test Body');

        $I->click('Send');
    }

    public function testCreateEmptySupportTicket(FunctionalTester $I)
    {
        $I->amOnPage('/site/login');
        $I->fillField('#loginform-username', 'fouser');
        $I->fillField('#loginform-password', 'qweasdzxc');

        $I->click('login-button');
    
        $I->dontSee('Incorrect username or password.');

        $I->click('Contact Us');

        $I->click('Create Support Ticket');

        $I->fillField('#supportticket-subject', '');
        $I->fillField('#supportticket-content', '');

        $I->click('Send');

        $I->seeInCurrentUrl('/supportticket/create');
    }


    
    private function createNewClient(bool $save = false)
    {
        $uniqueString = uniqid();  

        $user = new User();
        $user->username = 'newuser_' . $uniqueString;
        $user->email = 'emailfornewuser_' . $uniqueString . '@gmail.com';

        $user->setPassword("password_" . $uniqueString);
        $user->generateAuthKey();
        $user->generateEmailVerificationToken();
        $user->created_at = time();
        $user->updated_at = time();

        $client = new Client();
        $client->name = $user->username;
        $client->email = $user->email;
        $client->phone = '112233445';
        $client->roleId = 1;
        $client->createdAt = time();
        $client->balance = 0;
        $client->iban = 'PT50000201231234567890154';

        if (!$user->save()) {
            print_r($user->getErrors());
        }

        $client->userId = $user->id;

        if ($save) {
            $user->save();
            $client->save();
        }

        return $client;
    }
}
