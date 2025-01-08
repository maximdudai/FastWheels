<?php


namespace frontend\tests\Unit;

use common\models\UserCar;
use frontend\tests\UnitTester;

class UserCarTest extends \Codeception\Test\Unit
{

    protected UnitTester $tester;

    public function testRequiredFieldsValidation()
    {
        $userCar = new UserCar();
        $this->assertFalse($userCar->validate(), 'Cannot create new car without required fields');

        $expectedErrors = ['clientId', 'carBrand', 'carModel', 'carYear', 'availableFrom', 'availableTo', 'address', 'postalCode', 'city', 'priceDay'];
        foreach ($expectedErrors as $field) {
            $this->assertArrayHasKey($field, $userCar->errors, "Field {$field} is required and should have an error.");
        }
    }

    public function testCarYearValidation()
    {
        $userCar = new UserCar(['carYear' => 1899]); // Ano inválido
        $userCar->validate();
        $this->assertArrayHasKey('carYear', $userCar->errors, 'Car year below 1900 should be considered invalid');

        $userCar->carYear = 2023; // Ano válido
        $userCar->validate();
        $this->assertArrayNotHasKey('carYear', $userCar->errors, 'Car year should be valid for modern years');
    }
}
