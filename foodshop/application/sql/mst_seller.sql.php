<?php

$DefaultFields = "*";
$Where = " WHERE 1 ";
$order = " Order By C_SELLER_NAME ";

if ($Fields) {
	$DefaultFields = $Fields;
}



if ($C_SELLER_ID) {
	$Where .= " AND C_SELLER_ID = '$C_SELLER_ID' ";
}

if ($C_SELLER_NAME) {
	$Where .= " AND C_SELLER_NAME = '$C_SELLER_NAME' ";
}

if ($C_SELLER_ALIAS) {
	$Where .= " AND C_SELLER_ALIAS = '$C_SELLER_ALIAS' ";
}

if ($query) {
	$Where .= " AND C_SELLER_NAME LIKE '%{$query}%' OR C_SELLER_NAME LIKE '%{$query}%' OR C_SELLER_ALIAS LIKE '%{$query}%' OR C_SELLER_ID LIKE '%{$query}%'";
}

if ($OrderBy) {
	$order = " ORDER BY {$OrderBy}";
}


if ($Mode == "Regular") {
	$SQL = "SELECT $DefaultFields FROM mst_seller ";

} else {
	$SQL = "SELECT Count(*) AS rowCount FROM mst_seller ";
}



$SQL.=$Where.=$order;

