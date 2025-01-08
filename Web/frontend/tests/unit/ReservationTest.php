<?php

namespace tests\unit\models;

use common\models\Client;
use common\models\Payment;
use common\models\Reservation;
use common\models\SupportTicket;
use common\models\User;
use common\models\UserCar;
use frontend\tests\UnitTester;

class ReservationTest extends \Codeception\Test\Unit
{
    protected UnitTester $tester;

    protected const VALID_USERNAME = 'fouser';

    public const STRING_99_CHARS = 'qwertyuiopqwertyuiopqwertyuiopqwertyuiopqwertyuiopqwertyuiopqwertyuiopq';

    protected function _before()
    {
        $this->createNewClient(true);
    }

    public function testSaveClient()
    {
        $client = $this->createNewClient(true);
        $this->assertTrue($client->save());
    }

    public function testCreateNewReservationforClient() {
        $client = $this->createNewClient(true);
        $userCar = $this->createNewUserCar(true);

        $reservation = new Reservation();
        $reservation->clientId = $client->id;
        $reservation->carId = $userCar->id;
        $reservation->dateStart = '2021-12-01';
        $reservation->dateEnd = '2021-12-31';
        $reservation->value = 1000;
        $reservation->feeValue = 100;
        $reservation->carValue = 9000;

        $this->assertTrue($reservation->save());
    }

    public function testFindUserInDatabase() {
        $client = $this->createNewClient();
        $isClientSaved = $client->save();

        $this->assertTrue($isClientSaved);
        // $this->tester->seeInDatabase('client', ['name' => $client->name]);
        $findInDatabase = Client::find()->where(['name' => $client->name])->one();
        $this->assertNotNull($findInDatabase);
    }

    public function testFindClientAndUpdateName(){
        $pessoa = $this->createNewClient(true);

        $pessoa = Client::find()->where(['name'=> $pessoa->name])->one();
        $newName = 'UserNewName';
 
        $pessoa->name = $newName;
        $pessoa->save();
 
        $pessoaFromDatabase = Client::find()->where(['name'=> $newName])->one();
        $this->assertNotNull($pessoaFromDatabase);
 
    }

    private function createNewUserCar(bool $save = false) {
        $uniqueString = uniqid();  // Generate a unique string

// clientId', 'carBrand', 'carModel', 'carYear', 'carDoors', 'createdAt', 'availableFrom', 'availableTo', 'address', 'postalCode', 'city', 'priceDay'
        $client = $this->createNewClient(true);

        $userCar = new UserCar();
        $userCar->clientId = $client->id;
        $userCar->carBrand = 'brand_' . $uniqueString;
        $userCar->carModel = 'model_' . $uniqueString;
        $userCar->carYear = 2021;
        $userCar->carDoors = 4;
        $userCar->createdAt = time();
        $userCar->status = 1;
        $userCar->availableFrom = '2021-12-01';
        $userCar->availableTo = '2021-12-31';
        $userCar->address = 'address_' . $uniqueString;
        $userCar->postalCode = '1234-567';
        $userCar->city = 'city_' . $uniqueString;
        $userCar->priceDay = 100;
        

        if ($save) {
            $userCar->save();
        }

        return $userCar;
    }

    public function testCreateNewSupportticket() {
        $client = $this->createNewClient(true);

// 'clientId', 'content', 'createdAt', 'subject', 'reservationId'

        $supportTicket = new SupportTicket();
        $supportTicket->clientId = $client->id;
        $supportTicket->content = 'NewSupportTicketForContent';
        $supportTicket->createdAt = time();
        $supportTicket->subject = 'NewSupportTicketForSubject';
        $supportTicket->reservationId = 0; // No reservation

        $this->assertTrue($supportTicket->save());
    }

    private function createNewClient(bool $save = false)
    {
        $uniqueString = uniqid();  // Generate a unique string

        $user = new User();
        $user->username = 'newuser_' . $uniqueString;
        $user->email = 'emailfornewuser_' . $uniqueString . '@gmail.com';

        $client = new Client();
        $client->name = 'newuser_' . $uniqueString;
        $client->email = 'emailfornewuser_' . $uniqueString . '@gmail.com';
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
