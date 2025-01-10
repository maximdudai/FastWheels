<?php

namespace common\models;

use Yii;

/**
 * This is the model class for table "tarefas".
 *
 * @property int $id
 * @property int $clientId
 * @property string $titulo
 * @property string $descricao
 * @property int $feito
 *
 * @property Clients $client
 */
class Tarefa extends \yii\db\ActiveRecord
{
    /**
     * {@inheritdoc}
     */
    public static function tableName()
    {
        return 'tarefas';
    }

    /**
     * {@inheritdoc}
     */
    public function rules()
    {
        return [
            [['clientId', 'titulo', 'descricao'], 'required'],
            [['clientId', 'feito'], 'integer'],
            [['descricao'], 'string'],
            [['titulo'], 'string', 'max' => 255],
            [['clientId'], 'exist', 'skipOnError' => true, 'targetClass' => Client::class, 'targetAttribute' => ['clientId' => 'id']],
        ];
    }

    /**
     * {@inheritdoc}
     */
    public function attributeLabels()
    {
        return [
            'id' => 'ID',
            'clientId' => 'Client ID',
            'titulo' => 'Titulo',
            'descricao' => 'Descricao',
            'feito' => 'Feito',
        ];
    }

    /**
     * Gets query for [[Client]].
     *
     * @return \yii\db\ActiveQuery
     */
    public function getClient()
    {
        return $this->hasOne(Client::class, ['id' => 'clientId']);
    }
}
