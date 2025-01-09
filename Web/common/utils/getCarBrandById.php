<?php

namespace CarBrand;

use common\models\UserCar;

function getCarBrandById($id) {
  $findCar = UserCar::findOne(['id' => $id]);

  $carName = "{$findCar->carBrand} {$findCar->carModel}";

  return $carName ?? 'Not Found';
}

?>