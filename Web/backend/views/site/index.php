<?php

/** @var yii\web\View $this */

$this->title = 'Fast Wheels - Admin Dashboard';
?>

<div class="site-index">
    <div class="body-content p-2">
        <div class="row g-4">
            <div class="col-md-3">
                <div class="card bg-primary">
                    <div class="card-body">
                        <div class="d-flex justify-content-between">
                            <p class="card-text">Users</p>
                            <h5 class="card-title" style="background: rgba(67, 67, 67, 0.5); padding:2px 10px;font-size:1.25rem; color:white;"><?= $counts['users'] ?></h5>
                        </div>
                        <i class="bi bi-person fs-1"></i>
                    </div>
                    <a href="#" class="card-footer text-center text-white bg-primary">More info <i class="bi bi-arrow-right"></i></a>
                </div>
            </div>
            <div class="col-md-3">
                <div class="card bg-success">
                    <div class="card-body">
                        <div class="d-flex justify-content-between">
                            <p class="card-text">Payments</p>
                            <h5 class="card-title" style="background: rgba(67, 67, 67, 0.5); padding:2px 10px;font-size:1.25rem; color:white;"><?= $counts['payments'] ?></h5>
                        </div>
                        <i class="bi bi-cash fs-1"></i>
                    </div>
                    <a href="#" class="card-footer text-center text-white bg-success">More info <i class="bi bi-arrow-right"></i></a>
                </div>
            </div>
            <div class="col-md-3">
                <div class="card bg-warning">
                    <div class="card-body">
                        <div class="d-flex justify-content-between">
                            <p class="card-text text-white">Reservations</p>
                            <h5 class="card-title" style="background: rgba(67, 67, 67, 0.5); padding:2px 10px;font-size:1.25rem; color:white;"><?= $counts['reservations'] ?></h5>
                        </div>
                        <i class="bi bi-calendar-check fs-1"></i>
                    </div>
                    <a href="#" class="card-footer text-center text-white bg-warning">More info <i class="bi bi-arrow-right"></i></a>
                </div>
            </div>
            <div class="col-md-3">
                <div class="card bg-danger">
                    <div class="card-body">
                        <div class="d-flex justify-content-between">
                            <p class="card-text">Support Tickets</p>
                            <h5 class="card-title" style="background:rgba(67, 67, 67, 0.5); padding:2px 10px;"><?= $counts['supportTickets'] ?></h5>
                        </div>
                        <i class="bi bi-chat-dots fs-1"></i>
                    </div>
                    <a href="#" class="card-footer text-center text-white bg-danger">More info <i class="bi bi-arrow-right"></i></a>
                </div>
            </div>
            <div class="col-md-3">
                <div class="card bg-info">
                    <div class="card-body">
                        <div class="d-flex justify-content-between">
                            <p class="card-text">User Cars</p>
                            <h5 class="card-title" style="background: rgba(67, 67, 67, 0.5); padding:2px 10px;font-size:1.25rem; color:white;"><?= $counts['userCars'] ?></h5>
                        </div>
                        <i class="bi bi-car-front fs-1"></i>
                    </div>
                    <a href="#" class="card-footer text-center text-white bg-info">More info <i class="bi bi-arrow-right"></i></a>
                </div>
            </div>
        </div>
        <div class="row">
            <div class="col-lg-4">
                <h2>Taxes</h2>

                <p>Manage taxes for the system.</p>

                <p><a class="btn btn-default" href="/taxes">Manage Taxes &raquo;</a></p>
            </div>
            <div class="col-lg-4">
                <h2>Employees</h2>

                <p>Manage employees for the system.</p>

                <p><a class="btn btn-default" href="/employees">Manage Employees &raquo;</a></p>
            </div>
            <div class="col-lg-4">
                <h2>Clients</h2>

                <p>Manage clients for the system.</p>

                <p><a class="btn btn-default" href="/clients">Manage Clients &raquo;</a></p>
            </div>
        </div>
    </div>

</div>