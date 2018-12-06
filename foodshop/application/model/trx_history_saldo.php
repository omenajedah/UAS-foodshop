<?php

class trx_history_saldo extends model {
	function __construct() {
		parent::__construct();
		$this->TableName = "trx_history_saldo";
		$this->SQLFile = "trx_history_saldo.sql";
	}




}