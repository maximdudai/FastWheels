<?php

use common\models\Client;
use yii\db\Migration;

/**
 * Class m241214_225818_insert_new_car_into_usercars
 */
class m241214_225818_insert_new_car_into_usercars extends Migration
{
    /**
     * {@inheritdoc}
     */
    public function safeUp()
    {
        // $clientId = Client::findOne(['name' => 'maximdudai']) -> userId;

        // if(!$clientId) {
        //     echo "Client not found";
        //     return false;
        // }

        // $this->insert('usercars', [
        //     'id' => 1, 
        //     'clientId' => $clientId,
        //     'carBrand' => 'Toyota Corolla',
        //     'carModel' => 'LE', 
        //     'carYear' => 2020,
        //     'carDoors' => 4,
        //     'createdAt' => date('Y-m-d H:i:s'), 
        //     'status' => 'available',
        //     'availableFrom' => '2024-01-01', 
        //     'availableTo' => '2024-12-31',
        // ]);
    }

    /**
     * {@inheritdoc}
     */
    public function safeDown()
    {
        $this->delete('usercars', ['id' => 1]);
    }
}
