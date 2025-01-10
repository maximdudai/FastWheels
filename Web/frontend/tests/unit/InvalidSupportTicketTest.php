<?php


namespace frontend\tests\Unit;

use common\models\Client;
use common\models\SupportTicket;
use common\models\User;
use frontend\tests\UnitTester;

class InvalidSupportTicketTest extends \Codeception\Test\Unit
{

    protected UnitTester $tester;


    protected const STRING_257_CHARS = 'hqizWdVYmr0XJFqdh0V5bBC6xjbOwjIrFxiZLyIlb5bS0DDXDpMzGenMxLH6Vhn4kZRuHYTLLtFaqtMlRhqizWdVYmr0XJFqdh0V5bBC6xjbOwjIrFxiZLyIlb5bS0DDXDpMzGenMxLH6Vhn4kZRuHYTLLtFaqtMlRhqizWdVYmr0XJFqdh0V5bBC6xjbOwjIrFxiZLyIlb5bS0DDXDpMzGenMxLH6Vhn4kZRuHYTLLtFaqtMlRhqizWdVYmr0XJF';
    protected const STRING_1028_CHARS = 'hqizWdVYmr0XJFqdh0V5bBC6xjbOwjIrFxiZLyIlb5bS0DDXDpMzGenMxLH6Vhn4kZRuHYTLLtFaqtMlRhqizWdVYmr0XJFqdh0V5bBC6xjbOwjIrFxiZLyIlb5bS0DDXDpMzGenMxLH6Vhn4kZRuHYTLLtFaqtMlRhqizWdVYmr0XJFqdh0V5bBC6xjbOwjIrFxiZLyIlb5bS0DDXDpMzGenMxLH6Vhn4kZRuHYTLLtFaqtMlRhqizWdVYmr0XJFhqizWdVYmr0XJFqdh0V5bBC6xjbOwjIrFxiZLyIlb5bS0DDXDpMzGenMxLH6Vhn4kZRuHYTLLtFaqtMlRhqizWdVYmr0XJFqdh0V5bBC6xjbOwjIrFxiZLyIlb5bS0DDXDpMzGenMxLH6Vhn4kZRuHYTLLtFaqtMlRhqizWdVYmr0XJFqdh0V5bBC6xjbOwjIrFxiZLyIlb5bS0DDXDpMzGenMxLH6Vhn4kZRuHYTLLtFaqtMlRhqizWdVYmr0XJFhqizWdVYmr0XJFqdh0V5bBC6xjbOwjIrFxiZLyIlb5bS0DDXDpMzGenMxLH6Vhn4kZRuHYTLLtFaqtMlRhqizWdVYmr0XJFqdh0V5bBC6xjbOwjIrFxiZLyIlb5bS0DDXDpMzGenMxLH6Vhn4kZRuHYTLLtFaqtMlRhqizWdVYmr0XJFqdh0V5bBC6xjbOwjIrFxiZLyIlb5bS0DDXDpMzGenMxLH6Vhn4kZRuHYTLLtFaqtMlRhqizWdVYmr0XJFhqizWdVYmr0XJFqdh0V5bBC6xjbOwjIrFxiZLyIlb5bS0DDXDpMzGenMxLH6Vhn4kZRuHYTLLtFaqtMlRhqizWdVYmr0XJFqdh0V5bBC6xjbOwjIrFxiZLyIlb5bS0DDXDpMzGenMxLH6Vhn4kZRuHYTLLtFaqtMlRhqizWdVYmr0XJFqdh0V5bBC6xjbOwjIrFxiZLyIlb5bS0DDXDpMzGenMxLH6Vhn4kZRuHYTLLtFaqtMlRhqizWdVYmr0XJF';

    protected function _before()
    {
        $this->createNewClient(true);
    }

    // tests
    public function testNullSupportTicket()
    {
        $ticket = new SupportTicket();
        $ticket->clientId = null;
        $ticket->content = null;
        $ticket->createdAt = null;
        $ticket->closed = ' ';
        $ticket->subject = null;
        $ticket->reservationId = ' ';
        $ticket->status = false;

        $this->assertFalse($ticket->validate(['clientId']));
        $this->assertFalse($ticket->validate(['content']));
        $this->assertFalse($ticket->validate(['createdAt']));
        $this->assertFalse($ticket->validate(['closed']));
        $this->assertFalse($ticket->validate(['subject']));
        $this->assertFalse($ticket->validate(['reservationId']));
        $this->assertFalse($ticket->validate(['status']));

        $this->assertFalse($ticket->save());

    }

    public function testInvalidSupportTicket() {
        $ticket = new SupportTicket();
        $ticket->clientId = '1aads';
        $ticket->content = self::STRING_1028_CHARS;
        $ticket->createdAt = ' ';
        $ticket->closed = ' ';
        $ticket->subject = self::STRING_257_CHARS;
        $ticket->reservationId = self::STRING_1028_CHARS;
        $ticket->status = self::STRING_257_CHARS;

        $this->assertFalse($ticket->validate(['clientId']));
        $this->assertFalse($ticket->validate(['content']));
        $this->assertFalse($ticket->validate(['createdAt']));
        $this->assertFalse($ticket->validate(['closed']));
        $this->assertFalse($ticket->validate(['subject']));
        $this->assertFalse($ticket->validate(['reservationId']));
        $this->assertFalse($ticket->validate(['status']));

        $this->assertFalse($ticket->save());
    }

    private function createNewClient(bool $save = false)
    {
        $uniqueString = uniqid();  // Generate a unique string

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
