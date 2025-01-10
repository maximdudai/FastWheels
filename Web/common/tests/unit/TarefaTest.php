<?php


namespace common\tests\Unit;

use common\models\Client;
use common\models\Tarefa;
use common\models\User;
use common\tests\UnitTester;

class TarefaTest extends \Codeception\Test\Unit
{

    protected UnitTester $tester;

    protected const STRING_257_CHARS = 'hqizWdVYmr0XJFqdh0V5bBC6xjbOwjIrFxiZLyIlb5bS0DDXDpMzGenMxLH6Vhn4kZRuHYTLLtFaqtMlRhqizWdVYmr0XJFqdh0V5bBC6xjbOwjIrFxiZLyIlb5bS0DDXDpMzGenMxLH6Vhn4kZRuHYTLLtFaqtMlRhqizWdVYmr0XJFqdh0V5bBC6xjbOwjIrFxiZLyIlb5bS0DDXDpMzGenMxLH6Vhn4kZRuHYTLLtFaqtMlRhqizWdVYmr0XJF';
    protected const STRING_1028_CHARS = 'hqizWdVYmr0XJFqdh0V5bBC6xjbOwjIrFxiZLyIlb5bS0DDXDpMzGenMxLH6Vhn4kZRuHYTLLtFaqtMlRhqizWdVYmr0XJFqdh0V5bBC6xjbOwjIrFxiZLyIlb5bS0DDXDpMzGenMxLH6Vhn4kZRuHYTLLtFaqtMlRhqizWdVYmr0XJFqdh0V5bBC6xjbOwjIrFxiZLyIlb5bS0DDXDpMzGenMxLH6Vhn4kZRuHYTLLtFaqtMlRhqizWdVYmr0XJFhqizWdVYmr0XJFqdh0V5bBC6xjbOwjIrFxiZLyIlb5bS0DDXDpMzGenMxLH6Vhn4kZRuHYTLLtFaqtMlRhqizWdVYmr0XJFqdh0V5bBC6xjbOwjIrFxiZLyIlb5bS0DDXDpMzGenMxLH6Vhn4kZRuHYTLLtFaqtMlRhqizWdVYmr0XJFqdh0V5bBC6xjbOwjIrFxiZLyIlb5bS0DDXDpMzGenMxLH6Vhn4kZRuHYTLLtFaqtMlRhqizWdVYmr0XJFhqizWdVYmr0XJFqdh0V5bBC6xjbOwjIrFxiZLyIlb5bS0DDXDpMzGenMxLH6Vhn4kZRuHYTLLtFaqtMlRhqizWdVYmr0XJFqdh0V5bBC6xjbOwjIrFxiZLyIlb5bS0DDXDpMzGenMxLH6Vhn4kZRuHYTLLtFaqtMlRhqizWdVYmr0XJFqdh0V5bBC6xjbOwjIrFxiZLyIlb5bS0DDXDpMzGenMxLH6Vhn4kZRuHYTLLtFaqtMlRhqizWdVYmr0XJFhqizWdVYmr0XJFqdh0V5bBC6xjbOwjIrFxiZLyIlb5bS0DDXDpMzGenMxLH6Vhn4kZRuHYTLLtFaqtMlRhqizWdVYmr0XJFqdh0V5bBC6xjbOwjIrFxiZLyIlb5bS0DDXDpMzGenMxLH6Vhn4kZRuHYTLLtFaqtMlRhqizWdVYmr0XJFqdh0V5bBC6xjbOwjIrFxiZLyIlb5bS0DDXDpMzGenMxLH6Vhn4kZRuHYTLLtFaqtMlRhqizWdVYmr0XJF';

    protected function _before()
    {
        $user = $this->createNewClient(true);
    }

    // tests
    public function testEmptyTarefa()
    {

        $user = $this->createNewClient(false);

        $tarefa = new Tarefa();
        $tarefa->clientId = $user->id;
        $tarefa->descricao = null;
        $tarefa->feito = ' ';

        $this->assertFalse($tarefa->validate(['clientId']));
        $this->assertFalse($tarefa->validate(['descricao']));
        $this->assertFalse($tarefa->validate(['feito']));

        $this->assertFalse($tarefa->save());
    }

    public function testNullTarefa() {
        $user = $this->createNewClient(false);

        $tarefa = new Tarefa();

        $tarefa->clientId = null;
        $tarefa->descricao = null;
        $tarefa->feito = null;

        $this->assertFalse($tarefa->validate(['clientId']));
        $this->assertFalse($tarefa->validate(['descricao']));
        $this->assertTrue($tarefa->validate(['feito']));

        $this->assertFalse($tarefa->save()); 
    }

    public function testCreateCorrectTarefa() {
        $user = $this->createNewClient(true);

        $tarefa = new Tarefa();

        $tarefa->clientId = $user->id;
        $tarefa->descricao = 'Tarefa test unit';
        $tarefa->feito = 0;

        $this->assertTrue($tarefa->validate(['clientId']));
        $this->assertTrue($tarefa->validate(['descricao']));
        $this->assertTrue($tarefa->validate(['feito']));

        $this->assertTrue($tarefa->save()); 
    }

    public function testUpdateTarefa() {
        $tarefa = Tarefa::findOne(['clientId' => 2]);

        $tarefa->descricao = 'Tarefa test unit update';
        $tarefa->feito = 0;

        $this->assertTrue($tarefa->validate(['descricao']));
        $this->assertTrue($tarefa->validate(['feito']));

        $this->assertTrue($tarefa->save()); 
    }

    public function testDeleteTarefa() {
        $user = $this->createNewClient(true);

        $tarefa = new Tarefa();
        $tarefa->clientId = $user->id;
        $tarefa->descricao = 'Tarefa test unit';
        $tarefa->feito = 0;
        $tarefa->save();

        $tarefa = Tarefa::findOne(['id' => $tarefa->id]);

        $tarefa->deleteAll();

        $this->assertTrue($tarefa->save()); 
    }

    public function testLongStringTarefa() {
        $tarefa = new Tarefa();
        $tarefa->clientId = self::STRING_257_CHARS;
        $tarefa->descricao = self::STRING_257_CHARS;
        $tarefa->feito = self::STRING_257_CHARS;

        $this->assertFalse($tarefa->validate(['clientId']));
        $this->assertFalse($tarefa->validate(['descricao']));
        $this->assertFalse($tarefa->validate(['feito']));
    }

    public function testReadTarefa() {
        $user = $this->createNewClient(true);

        $tarefa = new Tarefa();
        $tarefa->clientId = $user->id;
        $tarefa->descricao = 'Tarefa test unit';
        $tarefa->feito = 0;
        $tarefa->save();

        $result = $tarefa->save();
        $this->assertTrue($result);

        
        $tarefaFromDataBase = Tarefa::findOne($tarefa->id);
        $this->assertNotNull($tarefaFromDataBase);
        $this->assertNotEquals(123456789, $tarefaFromDataBase->clientId);
    }

    public function testDifferentDataFromExpectedTarefa() {
        $tarefa = new Tarefa();
        $tarefa->clientId = 'maxim';
        $tarefa->descricao = 231151;
        $tarefa->feito = 4.50;

        $this->assertFalse($tarefa->validate(['clientId']));
        $this->assertFalse($tarefa->validate(['descricao']));
        $this->assertFalse($tarefa->validate(['feito']));
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
