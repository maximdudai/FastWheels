<?php

namespace backend\tests\Acceptance;

use backend\tests\AcceptanceTester;

class AuthenticateTestCest
{
    public function testAdminAuthentication(AcceptanceTester $I)
    {
        $I->amOnPage('/site/login');
        $I->waitForText('Sign in to start your session', 10);
        $I->see('Sign In', 'button');

        $I->fillField('input[name="LoginForm[username]"]', 'admin');
        $I->fillField('input[name="LoginForm[password]"]', 'admin123');
        $I->click('Sign In');

        $I->dontSee('Incorrect username or password.');
    }

    // public function testUserAuthentication(AcceptanceTester $I)
    // {
    //     $I->amOnPage('/site/login');
    //     $I->waitForText('Sign in to start your session', 10);
    //     $I->see('Sign In', 'button');

    //     $I->fillField('input[name="LoginForm[username]"]', 'fouser');
    //     $I->fillField('input[name="LoginForm[password]"]', 'qweasdzxc');
    //     $I->click('Sign In', 'button');

    //     $I->waitForText('Access denied: You do not have permission to access the back office.', 10);
    //     $I->amOnPage('/site/login');
    // }
}
