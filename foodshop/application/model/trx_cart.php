<?php

class trx_cart extends model {
	function __construct() {
		parent::__construct();
		$this->TableName = "trx_cart";
		$this->SQLFile = "trx_cart.sql";	
	}

	function putData($parameter) {
		if ($parameter->L_DELETE) {
			return $this->deleteData($parameter);
		}

		$n_qoh = $this->db->query("SELECT N_QOH FROM mst_stock_product WHERE N_ITENO = '{$parameter->N_ITENO}'")['N_QOH'];

		if ($parameter->N_BOOK > $n_qoh) {
			$parameter->N_BOOK = $n_qoh;
		}

		parent::putData($parameter);
		return $this->getData($parameter)->DataRow[0];
	}


}