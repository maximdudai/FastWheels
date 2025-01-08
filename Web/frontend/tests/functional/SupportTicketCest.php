<?php


namespace frontend\tests\Functional;

use frontend\tests\FunctionalTester;

class SupportTicketCest
{
    public function firstTestToMakeIncorrectLogin(FunctionalTester $I)
    {
        // $I->amOnPage(\Yii::$app->homeUrl);
        // $I->see('Login', 'a');
        // $I->click('Login');
        $I->amOnPage('/site/login');
        $I->see("Entre na sua conta");
        $I->see('Enviar', 'button');



        $I->fillField('input[name="LoginForm[username]"]', 'admin');
        $I->fillField('input[name="LoginForm[password]"]', 'admin');
        $I->click('login-button');

        $I->see('Incorrect username or password.');
    }

    public function firstTestToMakeAccountCreation(FunctionalTester $I)
    {
        $I->amOnPage('/site/signup');
        $I->see("Crie a sua conta");
        $I->see('Enviar', 'button');

        $I->fillField('input[name="SignupForm[username]"]', 'testuser');
        $I->fillField('input[name="SignupForm[email]"]', 'testfunctional@gmail.com');
        $I->fillField('input[name="SignupForm[password]"]', 'qweasdzxc');
        $I->fillField('input[name="SignupForm[confirmPassword]"]', 'qweasdzxc');
        // $I->click('SignupForm[termsAccepted]');
        // $I->checkOption('input[name="SignupForm[termsAccepted]"]');


        $I->click('signup-button');
    }

    public function firstTestToMakeCorrectLogin(FunctionalTester $I)
    {
        $I->amOnPage('/site/login');
        $I->see("Entre na sua conta");
        $I->see('Enviar', 'button');



        $I->fillField('input[name="LoginForm[username]"]', 'fouser');
        $I->fillField('input[name="LoginForm[password]"]', 'qweasdzxc');
        $I->click('login-button');

        $I->amOnPage(\Yii::$app->homeUrl);
        $I->see('Profile', 'a');
        $I->see('Logout');
    }

    public function firstTestToMakeSupportTicket(FunctionalTester $I)
    {
        $I->amOnPage('/supportticket');
        $I->see("Support Tickets", 'h1');
        $I->see('Create Support Ticket', 'a');

        $I->click('Create Support Ticket');

        $I->amOnPage('/supportticket/create');
        $I->see("Create Support Ticket", 'h1');


        $I->seeElement('input', ['name' => 'SupportTicket[subject]']);
        $I->seeElement('textarea', ['name' => 'SupportTicket[content]']);

        $I->fillField('input[name="SupportTicket[subject]"]', 'Teste ticket subject');
        $I->fillField('textarea[name="SupportTicket[content]"]', 'Test ticket content');

        $I->dontSee('Content cannot be blank.');
        $I->dontSee('Subject cannot be blank.');

        $I->click('Send', 'button');
    }

    public function firstTestToMakeEmptySupportTicket(FunctionalTester $I)
    {
        $I->amOnPage('/supportticket');
        $I->see("Support Tickets", 'h1');
        $I->see('Create Support Ticket', 'a');

        $I->click('Create Support Ticket');

        $I->amOnPage('/supportticket/create');
        
        $I->submitForm('#support-ticket-form', []); // Submitting empty form

        $I->amOnPage('/supportticket/create');
    }
}