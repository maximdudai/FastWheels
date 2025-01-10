<?php

use Random\Engine;
use yii\db\Migration;

/**
 * Handles the creation of table `{{%tarefas}}`.
 */
class m250110_150647_create_tarefas_table extends Migration
{
    /**
     * {@inheritdoc}
     */
    public function safeUp()
    {
        $this->createTable('{{%tarefas}}', [
            'id' => $this->primaryKey(),
            'clientId' => $this->integer()->notNull(),
            'titulo' => $this->string(255)->notNull(),
            'descricao' => $this->text()->notNull(),
            'feito' => $this->boolean()->notNull()->defaultValue(false),
        ], 'ENGINE=InnoDB');
        

        $this->createIndex(
            '{{%idx-tarefas-clientId}}',
            '{{%tarefas}}',
            'clientId'
        );

        $this->addForeignKey(
            '{{%fk-tarefas-clientId}}',
            '{{%tarefas}}',
            'clientId',
            '{{%clients}}',
            '{{%id}}',
            'CASCADE'
        );
    }

    /**
     * {@inheritdoc}
     */
    public function safeDown()
    {
        $this->dropForeignKey(
            '{{%fk-tarefas-clientId}}',
            '{{%tarefas}}'
        );
        $this->dropTable('{{%tarefas}}');
    }
}
