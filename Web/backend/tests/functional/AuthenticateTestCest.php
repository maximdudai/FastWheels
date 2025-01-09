<?php


namespace backend\tests\Functional;

use backend\tests\FunctionalTester;

class AuthenticateTestCest
{
    // tests
    public function testAdminAuthentication(FunctionalTester $I)
    {
        $I->amOnPage('/site/login');
        $I->see("Sign in to start your session");
        $I->see('Sign In', 'button');

        $I->fillField('input[name="LoginForm[username]"]', 'admin');
        $I->fillField('input[name="LoginForm[password]"]', 'admin123');
        $I->click('Sign In', 'button');

        $I->dontSee('Incorrect username or password.');

        $I->amOnPage('/backend/web/');
        // $I->see('Logout');
    }

    public function testUserAuthentication(FunctionalTester $I)
    {
        $I->amOnPage('/site/login');
        $I->see("Sign in to start your session");
        $I->see('Sign In', 'button');

        $I->fillField('input[name="LoginForm[username]"]', 'fouser');
        $I->fillField('input[name="LoginForm[password]"]', 'qweasdzxc');
        $I->click('Sign In', 'button');

        $I->see('Access denied: You do not have permission to access the back office.');

        $I->amOnPage('/site/login');
    }

    
}
