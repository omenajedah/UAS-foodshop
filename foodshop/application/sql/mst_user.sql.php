<?php

$DefaultFields = "*";
$Where = " WHERE 1 ";
$order = " Order By C_NAME ;";

if ($Fields) {
	$DefaultFields = $Fields;
}



if ($C_USERNAME) {
	$Where .= " AND C_USERNAME = '$C_USERNAME' ";
}

if ($C_PASSWORD) {
	$Where .= " AND C_PASSWORD = '$C_PASSWORD' ";
}

if ($C_STATUS) {
	$Where .= " AND C_STATUS = '$C_STATUS' ";
}

if ($query) {
	$Where .= " AND C_USERNAME LIKE '%{$query}%' OR C_PASSWORD LIKE '%{$query}%' OR C_NAME LIKE '%{$query}%' OR C_SELLER_ID LIKE '%{$query}%'";
}

if ($OrderBy) {
	$order = " ORDER BY {$OrderBy};";
}


if ($Mode == "Regular") {
	$SQL = "SET @TES = 'a'; SELECT $DefaultFields FROM mst_user ";

} else {
	$SQL = "SET @TES = 'a'; SELECT Count(*) AS rowCount FROM mst_user ";
}



$SQL.=$Where.=$order;