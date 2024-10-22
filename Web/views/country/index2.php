<?php

use app\models\Country;
use yii\helpers\Html;
use yii\helpers\Url;
use yii\grid\ActionColumn;
use yii\grid\GridView;

/** @var yii\web\View $this */
/** @var app\models\CountrySearch $searchModel */
/** @var yii\data\ActiveDataProvider $dataProvider */

$this->title = 'Countries';
$this->params['breadcrumbs'][] = $this->title;
?>
<div class="country-index">

    <h1><?= Html::encode($this->title) ?></h1>

    <p>
        <?= Html::a('Create Country', ['create'], ['class' => 'btn btn-success']) ?>
    </p>


    <table class="table table-striped">
        <thead>
        <th>Code</th>
        <th>Name</th>
        <th>Population</th>
        <th>Actions</th>
        </thead>
        <tbody>
        <?php foreach($countries as $country) { ?>
            <tr>
                <td><?php echo $country->code ?></td>
                <td><?php echo $country->name ?></td>
                <td><?php echo $country->population ?></td>
                <td>
                    <?= Html::a('View', ['index'], ['class' => 'btn btn-info']) ?>
                    <a href="#" class="btn btn-success" role="button">update</a>
                    <a href="<?php echo Url::to(['delete', 'id' => $country->id]); ?>" class="btn btn-warning" role="button">Delete</a>
                </td>
            </tr>
        <?php } ?>
        </tbody>
    </table>



</div>
