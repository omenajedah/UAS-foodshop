<?php

$DefaultFields = " a.`N_ITENO`, a.`V_ITNAM`, a.`V_SHORTDESC`, b.`C_CATEGORY_NAME`, c.`N_PRICE`, d.`C_SELLER_NAME`, e.`N_QOH`, a.C_IMAGE_PATH ";
$Where = " WHERE 1 ";
$order = " Order By a.`V_ITNAM` ";

if ($Fields) {
	$DefaultFields = $Fields;
}


if ($N_ITENO) {
	$Where .= " AND a.`N_ITENO` = '$N_ITENO' ";
}

if ($C_CATEGORY_ID) {
	$Where .= " AND a.`C_CATEGORY_ID` = '$C_CATEGORY_ID' ";
}

if ($C_SELLER_ID) {
	$Where .= " AND d.`C_SELLER_ID` = '$C_SELLER_ID' ";
}

if (!$PRICE_TYPE) {
	$PRICE_TYPE = 1;
}

if ($query) {
	$Where .= " AND a.`N_ITENO` LIKE '%{$query}%' OR a.`V_ITNAM` LIKE '%{$query}%' OR C_SELLER_ID LIKE '%{$query}%' OR C_SELLER_NAME LIKE '%{$query}%'";
}

if ($OrderBy) {
	$order = " ORDER BY {$OrderBy}";
}


if ($Mode == "Regular") {
	$SQL = "
	  SET @PRICE_TYPE = '{$PRICE_TYPE}';
  
	  SELECT $DefaultFields FROM `mst_product` a
	  JOIN `mst_category` b ON a.`C_CATEGORY_ID`=b.`C_CATEGORY_ID`
	  JOIN `mst_price` c ON a.`N_ITENO` = c.`N_ITENO` AND c.`C_TYPE` = @PRICE_TYPE AND c.D_UPDATE < NOW() AND c.D_EXPIRED > NOW() AND c.`C_STATUS` = 1
	  JOIN `mst_seller` d ON a.`C_SELLER_ID` = d.`C_SELLER_ID`
	  JOIN `mst_stock_product` e ON a.`N_ITENO` = e.`N_ITENO`
  ";

} else {
	$SQL = "
	  SET @PRICE_TYPE = '{$PRICE_TYPE}';
  
	  SELECT count(*) as rowCount FROM `mst_product` a
	  JOIN `mst_category` b ON a.`C_CATEGORY_ID`=b.`C_CATEGORY_ID`
	  JOIN `mst_price` c ON a.`N_ITENO` = c.`N_ITENO` AND c.`C_TYPE` = @PRICE_TYPE AND c.D_UPDATE < NOW() AND c.D_EXPIRED > NOW() AND c.`C_STATUS` = 1
	  JOIN `mst_seller` d ON a.`C_SELLER_ID` = d.`C_SELLER_ID`
	  JOIN `mst_stock_product` e ON a.`N_ITENO` = e.`N_ITENO`
  ";}



$SQL.=$Where.=$order;

