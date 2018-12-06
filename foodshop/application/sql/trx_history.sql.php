<?php

$DefaultFields = " a.N_TRX_ID, COUNT(b.N_ITENO) AS totalItem, SUM(b.N_TOTAL) AS totalPrice, a.D_TIME, d.C_SELLER_NAME ";
$Where = " WHERE 1 ";
$order = " Order By a.N_TRX_ID ;";

$C_USERNAME = $_SESSION['C_USERNAME'];

if ($Fields) {
	$DefaultFields = $Fields;
}

if ($N_TRX_ID) {
	$Where .= " AND a.N_TRX_ID = '{$N_TRX_ID}' ";
}


if ($C_USERNAME) {
	$Where .= " AND a.C_USERNAME = '{$C_USERNAME}' ";
}

if ($StartTime && $EndTime) {
	$Where .= " AND D_TIME BETWEEN '{$StartTime}' AND '{$EndTime}'";
} else if ($D_TIME) {
	$Where.=" AND a.D_TIME = '{$D_TIME}'";
}

if ($OrderBy) {
	$order = " ORDER BY {$OrderBy}";
}


if ($Mode == "Regular") {
	$SQL = "SELECT $DefaultFields
			FROM trx_trans a
			JOIN trx_trans_detail b ON a.`N_TRX_ID`=b.`N_TRX_ID`
			JOIN mst_product c ON b.N_ITENO=c.`N_ITENO`
			JOIN mst_seller d ON c.`C_SELLER_ID`=d.`C_SELLER_ID`
		";

} else {
	$SQL = "SELECT COUNT(DISTINCT a.N_TRX_ID) as rowCount
			FROM trx_trans a
			JOIN trx_trans_detail b ON a.`N_TRX_ID`=b.`N_TRX_ID`
			JOIN mst_product c ON b.N_ITENO=c.`N_ITENO`
			JOIN mst_seller d ON c.`C_SELLER_ID`=d.`C_SELLER_ID`
		";
}


if ($Mode == "Regular") {
	$SQL.=$Where." GROUP BY a.N_TRX_ID".$order;
} else
	$SQL.=$Where.=$order;
