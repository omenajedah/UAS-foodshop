<?php

// print_r($_SERVER);
error_reporting(E_ALL & ~E_NOTICE & ~E_WARNING);

ini_set("session.gc_maxlifetime", 1800);
// Set the session cookie to timout
ini_set("session.cookie_lifetime", 1800);

include('core/includes.php');
include('application/'.DEFAULT_CONTROLLER.'.php');
require_once 'lib/XmlRequest.php';
require_once 'lib/JsonRequest.php';
require_once 'lib/FormDataRequest.php';
require_once 'lib/FormUrlEncodedRequest.php';


$app = new mobile();
$app->addRequestHandler('application/xml', new XmlRequest);
$app->addRequestHandler('application/json', new JsonRequest);
$app->addRequestHandler('multipart/form-data', new FormDataRequest);
$app->addRequestHandler('application/x-www-form-urlencoded', new FormUrlEncodedRequest);

$app->post('/register', function($request) use($app) {
	if (!$request->body->C_USERNAME || !$request->body->C_PASSWORD || !$request->body->C_NAME || !$request->body->C_ADDRESS) {
		return array('success'=>false, 'reason'=>'You must supplied data completely');
	}
	$model = $app->load->model('mst_user');
	$resultData = $model->getTable((object)array('fields'=>'C_USERNAME', 'criteria'=> "C_USERNAME = '{$request->body->C_USERNAME}'"));

	if ($resultData)
		return array('success'=>false, 'reason'=>'Username already taken.');

	$request->body->C_STATUS = 1;

	return $model->putData($request->body);
});

$app->post('/topup', function($request) use($app) {
	session_start();
	if (!$_SESSION['C_USERNAME'] && !$_SESSION['C_PASSWORD'] &&
		!$_SESSION['C_NAME'] && !$_SESSION['C_ADDRESS'] && !$_SESSION['C_SELLER_ID']) {
		$app->unauthorized();
		return array('success'=>false, 'reason'=>'You must login first');
	}

	$request->body->C_USERNAME = $_SESSION['C_USERNAME'];
	
	$app->load->model('trx_history_saldo')->putData($request->body);
	$mst_user = $app->load->model('mst_user');

	$request->body->N_SALDO += $mst_user->getData($request->body)->DataRow[0]->N_SALDO;

	return $app->load->model('mst_user')->putData($request->body);

});

$app->post('/login', function($request) use($app) {
	if (!isset($request->body->C_USERNAME) || !isset($request->body->C_PASSWORD)) {
		return array('success' => false, 'reason' => 'Username And Password cannot be empty');
	}
	$model = $app->load->model('mst_user');
	$request->body->Fields = 'C_NAME, C_ADDRESS, C_SELLER_ID, C_MAIL, C_PHONE, N_SALDO, C_BACKGROUND_IMAGE, C_PROFILE_IMAGE';
	$request->body->C_STATUS = '1';
	$request->body->query = '';

	$retData = $model->getData($request->body);
	if (!$retData->RowCount) {
		return array('success' => false, 'reason' => 'Wrong username or password');
	}
	session_start();

	$_SESSION['C_USERNAME'] = $request->body->C_USERNAME;
	$_SESSION['C_PASSWORD'] = $request->body->C_PASSWORD;
	$_SESSION['C_NAME'] = $retData->DataRow[0]->C_NAME;
	$_SESSION['C_ADDRESS'] = $retData->DataRow[0]->C_ADDRESS;
	$_SESSION['C_SELLER_ID'] = $retData->DataRow[0]->C_SELLER_ID;
	$_SESSION['C_MAIL'] = $retData->DataRow[0]->C_MAIL;
	$_SESSION['C_PHONE'] = $retData->DataRow[0]->C_PHONE;

	return array('success'=>true, 'profile'=>$retData->DataRow[0]);
});

$app->post('/images', function($parameter) use($app) {
	if ($_GET['N_ITENO'] && count($parameter->body)) {
		$body = $parameter->body;
		$parameter->body = array('N_ITENO' => $_GET['C_ITENO'], 'files'=>$body);
	} else if ($_GET['C_USERNAME'] && count($parameter->body)) {
		$body = $parameter->body;
		$parameter->body = array('C_USERNAME' => $_GET['C_USERNAME'], 'files'=>$body);
	} else if ($_GET['C_SELLER_ID'] && count($parameter->body)) {
		$body = $parameter->body;
		$parameter->body = array('C_SELLER_ID' => $_GET['C_SELLER_ID'], 'files'=>$body);
	} else
		return array('success'=>false);

	$model = $app->load->model('mst_image');
	$result = array_merge($parameter->body, $_GET);
	return $model->putData((object)$result);
});

$app->get('/images', function() use($app) {
	$model = $app->load->model('mst_image');
	$retData = $model->getData((object)$_GET);

	$image = base64_decode($retData->DataRow[0]->T_IMAGE);

	header("Content-Type: image/png");

	echo $image;

});

$app->get('/product', function($request) use($app) {

	if ($_GET['N_ITENO']) {
		$_GET['Fields'] = " a.`N_ITENO`, a.`V_ITNAM`, a.`V_SHORTDESC`, a.`V_DESC`, b.`C_CATEGORY_ID`, b.`C_CATEGORY_NAME`, c.`N_PRICE`, d.`C_SELLER_ID`, d.`C_SELLER_NAME`, e.`N_QOH`, a.C_IMAGE_PATH ";
	}
	$model = $app->load->model('mst_product');
	$retData = $model->getData((object)$_GET);

	return $retData;
});

$app->get('/category', function($request) use($app) {
	session_start();
	if (!$_SESSION['C_USERNAME'] && !$_SESSION['C_PASSWORD'] &&
		!$_SESSION['C_NAME'] && !$_SESSION['C_ADDRESS'] && !$_SESSION['C_SELLER_ID']) {
		$app->unauthorized();
		return array('success'=>false, 'reason'=>'You must login first');
	}
	$model = $app->load->model('mst_category');
	$retData = $model->getData((object)$_GET);

	return $retData;
});

$app->get('/profile', function($request) use($app) {
	session_start();
	if (!$_SESSION['C_USERNAME'] && !$_SESSION['C_PASSWORD'] &&
		!$_SESSION['C_NAME'] && !$_SESSION['C_ADDRESS'] && !$_SESSION['C_SELLER_ID']) {
		$app->unauthorized();
		return array('success'=>false, 'reason'=>'You must login first');
	}

	$model = $app->load->model('mst_profile');
	$retData = $model->getData(null);
	return $retData;
});

$app->get('/seller', function($request) use($app) {
	$model = $app->load->model('mst_seller');
	return $model->getData((object)$_GET);
});

$app->get('/cart', function($request) use($app) {
	session_start();
	if (!$_SESSION['C_USERNAME'] && !$_SESSION['C_PASSWORD'] &&
		!$_SESSION['C_NAME'] && !$_SESSION['C_ADDRESS'] && !$_SESSION['C_SELLER_ID']) {
		$app->unauthorized();
		return array('success'=>false, 'reason'=>'You must login first');
	}

	$model = $app->load->model('trx_cart');
	return $model->getData(null);
});

$app->post('/cart', function($request) use($app) {
	session_start();
	if (!$_SESSION['C_USERNAME'] && !$_SESSION['C_PASSWORD'] &&
		!$_SESSION['C_NAME'] && !$_SESSION['C_ADDRESS'] && !$_SESSION['C_SELLER_ID']) {
		$app->unauthorized();
		return array('success'=>false, 'reason'=>'You must login first');
	}

	$model = $app->load->model('trx_cart');
	return $model->putData($request->body);

});

$app->post('/trans', function($request) use($app) {
	session_start();
	if (!$_SESSION['C_USERNAME'] && !$_SESSION['C_PASSWORD'] &&
		!$_SESSION['C_NAME'] && !$_SESSION['C_ADDRESS'] && !$_SESSION['C_SELLER_ID']) {
		$app->unauthorized();
		return array('success'=>false, 'reason'=>'You must login first');
	}
	$model = $app->load->model('trx_trans');
	return $model->putData((object)$_SESSION);
});

$app->post('/history', function($request) use($app) {
	session_start();
	if (!$_SESSION['C_USERNAME'] && !$_SESSION['C_PASSWORD'] &&
		!$_SESSION['C_NAME'] && !$_SESSION['C_ADDRESS'] && !$_SESSION['C_SELLER_ID']) {
		$app->unauthorized();
		return array('success'=>false, 'reason'=>'You must login first');
	}
	$model = $app->load->model('trx_history');
	return $model->getData($request->body);
});

$app->run();