<?php

use yii\db\Migration;

/**
 * Class m241116_174250_init_rbac
 */
class m241116_174250_init_rbac extends Migration
{
    /**
     * {@inheritdoc}
     */
    public function safeUp()
    {
        /*
         * RBAC  will now be managed by RBac controller and not migrations

        $auth = Yii::$app->authManager;

        # ### PERMISSIONS ### #
        $addVehicle = $auth->createPermission('addVehicle');
        $addVehicle->description = 'Insert Vehicle for rent';
        $auth->add($addVehicle);

        $manageVehicle = $auth->createPermission('manageVehicle');
        $manageVehicle->description = 'Manage vehicle';
        $auth->add($manageVehicle);

        $deleteVehicle = $auth->createPermission('deleteVehicle');
        $deleteVehicle->description = 'Delete vehicle';
        $auth->add($deleteVehicle);

        $manageReservation = $auth->createPermission('manageReservations');
        $manageReservation->description = 'Manage car reservations';
        $auth->add($manageReservation);

        $manageTicket = $auth->createPermission('manageTicket');
        $manageTicket->description = 'Manage client support tickets';
        $auth->add($manageTicket);


        # ### END PERMISSIONS ### #

        # ### ROLES ### #
        $admin = $auth->createRole('admin');
        $auth->add($admin);
        $auth->addChild($admin, $addVehicle);
        $auth->addChild($admin, $manageVehicle);
        $auth->addChild($admin, $deleteVehicle);
        $auth->addChild($admin, $manageReservation);
        $auth->addChild($admin, $manageTicket);

        $employee = $auth->createRole('funcionario');
        $auth->add($employee);
        $auth->addChild($employee, $addVehicle);
        $auth->addChild($employee, $manageVehicle);
        $auth->addChild($employee, $manageReservation);
        $auth->addChild($employee, $manageTicket);

        $client = $auth->createRole('client');
        $auth->add($client);
        $auth->addChild($client, $addVehicle);
        $auth->addChild($client, $manageVehicle);
        # ### END ROLES ### #

        // Assign roles to users. 1 and 2 are IDs returned by IdentityInterface::getId()
        // usually implemented in your User model.
//        $auth->assign($author, 2);
    //    $auth->assign($admin, 1);
    }

    /**
     * {@inheritdoc}
     */
    public function safeDown()
    {
        /*
        $auth = Yii::$app->authManager;

        $auth->removeAll();
        return false;
        */
    }

    /*
    // Use up()/down() to run migration code without a transaction.
    public function up()
    {

    }

    public function down()
    {
        echo "m241116_174250_init_rbac cannot be reverted.\n";

        return false;
    }
    */
}
