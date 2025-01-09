<?php


namespace frontend\tests\Acceptance;


use frontend\tests\AcceptanceTester;

class SupportTicketCest
{
    // public function firstTestToMakeIncorrectLogin(AcceptanceTester $I)
    // {
    //     $I->amOnPage('/site/login');
    //     $I->see("Entre na sua conta");
    //     $I->see('Enviar', 'button');

    //     $I->fillField('input[name="LoginForm[username]"]', 'admin');
    //     $I->fillField('input[name="LoginForm[password]"]', 'admin');
    //     $I->click('login-button');
    //     $I->wait(4);
    //     $I->see('Incorrect username or password.');
    //     $I->waitForElementVisible('.invalid-feedback', 5); 
    //     // wait 5 seconds
    // }

    public function firstTestToMakeAccountCreation(AcceptanceTester $I)
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

    public function firstTestToMakeCorrectLogin(AcceptanceTester $I)
    {
        $I->amOnPage('/site/login');
        $I->see("Entre na sua conta");
        $I->see('Enviar', 'button');

        $I->fillField('input[name="LoginForm[username]"]', 'fouser');
        $I->fillField('input[name="LoginForm[password]"]', 'qweasdzxc');
        $I->click('login-button');

        $I->wait(4);
        $I->see('Logout');

    }

    public function firstTestToMakeSupportTicket(AcceptanceTester $I)
    {

        $I->amOnPage(\Yii::$app->homeUrl);

        $I->amOnPage('/site/login');
        $I->see("Entre na sua conta");
        $I->see('Enviar', 'button');

        $I->fillField('input[name="LoginForm[username]"]', 'fouser');
        $I->fillField('input[name="LoginForm[password]"]', 'qweasdzxc');
        $I->click('login-button');

        $I->wait(5);

        $I->click('Profile');

        $I->wait(5);

        $I->click("Support Ticket");
        
        $I->wait(5);


        $I->see("Create Support Ticket");

        $I->click('Create Support Ticket');


        // $I->seeElement('input', ['name' => 'SupportTicket[subject]']);
        // $I->seeElement('textarea', ['name' => 'SupportTicket[content]']);

        $I->waitForElementVisible('#supportticket-subject', 10);
        $I->waitForElementVisible('#supportticket-content', 10);
        

        $I->fillField('#supportticket-subject', 'Teste ticket subject'); // Example with IDs
        $I->fillField('#supportticket-content', 'Teste ticket content'); // Example with IDs


        $I->wait(3);

        $I->dontSee('Content cannot be blank.');
        $I->dontSee('Subject cannot be blank.');

        $I->click('Send');
    }


    public function firstTestToMakeEmptySupportTicket(AcceptanceTester $I)
    {
        $I->amOnPage('/supportticket');
        $I->click('Create Support Ticket');
        
        // $I->waitForElementVisible('#support-ticket-form', 5); // Wait for form to load
        // $I->submitForm('#support-ticket-form', []); // Submitting empty form

        // Assert validation messages
        // $I->see('Content cannot be blank.');
        // $I->see('Subject cannot be blank.');
    }
}
