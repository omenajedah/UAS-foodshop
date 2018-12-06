<?php

class mst_image extends model {
	function __construct() {
		parent::__construct();
		$this->TableName = "mst_image";
		$this->SQLFile = "mst_image.sql";	
	}


	function putData($parameter) {
		$token = bin2hex(openssl_random_pseudo_bytes(16));

		while ($this->getData(array('C_IMAGE_PATH'=>$token))->RowCount != 0) {
			$token = bin2hex(openssl_random_pseudo_bytes(16));
		}

		if ($parameter->N_ITENO) {
			$model = $this->load->model('mst_product');
			$prm = array('fields'=>'C_IMAGE_PATH', 'criteria'=>' N_ITENO = '.$parameter->N_ITENO);
			$product = $model->getTable((object)$prm);
			if ($product['C_IMAGE_PATH'] != NULL)
				$token = $product['C_IMAGE_PATH'];
			
			if ($parameter->files) {
				$filename = $parameter->files->{0}['name'];
				$file = base64_encode(file_get_contents($parameter->files->{0}['tmp_name']));
				$param = array('C_IMAGE_PATH'=>$token, 'C_IMAGE_NAME'=>$filename, 'T_IMAGE'=>$file);
				parent::putData((object)$param);
			}

			return $model->putData((object)array('N_ITENO'=>$parameter->N_ITENO, 'C_IMAGE_PATH'=>$token));
		}

		return $parameter;
	}

}