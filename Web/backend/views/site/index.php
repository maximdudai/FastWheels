<?php

/** @var yii\web\View $this */

use common\models\Client;

$this->title = 'Fast Wheels - Admin Dashboard';

$isUserAdmin = Client::isUserAdmin(Yii::$app->user->identity->id);

?>

<div class="site-index">
    <div class="body-content p-2">
        <div class="row g-4">
            <div class="col-md-4">
                <div class="card bg-primary">
                    <div class="card-body">
                        <div class="d-flex justify-content-between">
                            <p class="card-text text-lg">Users</p>
                            <h5 class="card-title" style="background: rgba(67, 67, 67, 0.5); padding:2px 10px;font-size:1.25rem; color:white;"><?= $counts['users'] ?></h5>
                        </div>
                        <i class="bi bi-person fs-1"></i>
                    </div>
                    <a href="client/clients" class="card-footer text-center text-white bg-primary">More info <i class="bi bi-arrow-right"></i></a>
                </div>
            </div>
            <div class="col-md-4">
                <div class="card bg-success">
                    <div class="card-body">
                        <div class="d-flex justify-content-between">
                            <p class="card-text text-lg">Payments</p>
                            <h5 class="card-title" style="background: rgba(67, 67, 67, 0.5); padding:2px 10px;font-size:1.25rem; color:white;"><?= $counts['payments'] ?></h5>
                        </div>
                        <i class="bi bi-cash fs-1"></i>
                    </div>
                    <a href="payment" class="card-footer text-center text-white bg-success">More info <i class="bi bi-arrow-right"></i></a>
                </div>
            </div>
            <div class="col-md-4">
                <div class="card bg-warning">
                    <div class="card-body">
                        <div class="d-flex justify-content-between">
                            <p class="card-text text-lg text-white">Reservations</p>
                            <h5 class="card-title" style="background: rgba(67, 67, 67, 0.5); padding:2px 10px;font-size:1.25rem; color:white;"><?= $counts['reservations'] ?></h5>
                        </div>
                        <i class="bi bi-calendar-check fs-1"></i>
                    </div>
                    <a href="reservation" class="card-footer text-center text-white bg-warning">More info <i class="bi bi-arrow-right"></i></a>
                </div>
            </div>
            <div class="col-md-4">
                <div class="card bg-danger">
                    <div class="card-body">
                        <div class="d-flex justify-content-between">
                            <p class="card-text text-lg">Support Tickets</p>
                            <h5 class="card-title" style="background:rgba(67, 67, 67, 0.5); padding:2px 10px;"><?= $counts['supportTickets'] ?></h5>
                        </div>
                        <i class="bi bi-chat-dots fs-1"></i>
                    </div>
                    <a href="support-ticket" class="card-footer text-center text-white bg-danger">More info <i class="bi bi-arrow-right"></i></a>
                </div>
            </div>
            <div class="col-md-4">
                <div class="card bg-info">
                    <div class="card-body">
                        <div class="d-flex justify-content-between">
                            <p class="card-text text-lg">User Cars</p>
                            <h5 class="card-title" style="background: rgba(67, 67, 67, 0.5); padding:2px 10px;font-size:1.25rem; color:white;"><?= $counts['userCars'] ?></h5>
                        </div>
                        <i class="bi bi-car-front fs-1"></i>
                    </div>
                    <a href="user-car" class="card-footer text-center text-white bg-info">More info <i class="bi bi-arrow-right"></i></a>
                </div>
            </div>
        </div>
        <div class="row">
            <?php if (!Yii::$app->user->isGuest && $isUserAdmin): ?>
                <div class="col-lg-4">
                    <h2>Taxes</h2>

                    <p>Manage taxes for the system.</p>

                    <p><a class="btn btn-default" href="taxes">Manage Taxes &raquo;</a></p>
                </div>

                <div class="col-lg-4">
                    <h2>Employees</h2>

                    <p>Manage employees for the system.</p>

                    <p><a class="btn btn-default" href="client/employees">Manage Employees &raquo;</a></p>
                </div>
            <?php endif; ?>

            <div class="col-lg-4">
                <h2>Clients</h2>

                <p>Manage clients for the system.</p>

                <p><a class="btn btn-default" href="client/clients">Manage Clients &raquo;</a></p>
            </div>
        </div>
    </div>

</div>