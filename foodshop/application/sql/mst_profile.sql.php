<?php

$DefaultFields = " * ";
$Where = " WHERE 1 ";
$order = " Order By C_USERNAME ";

$C_USERNAME = $_SESSION['C_USERNAME'];

if ($Fields) {
	$DefaultFields = $Fields;
}

if ($OrderBy) {
	$order = " ORDER BY {$OrderBy}";
}

if ($C_USERNAME) {
	$Where.=" AND C_USERNAME = '$C_USERNAME' ";
}


if ($Mode == "Regular") {
	$SQL = "
	  SELECT $DefaultFields FROM mst_user
  ";

} else {
	$SQL = "
	  SELECT count(*) AS rowCount FROM mst_user

  ";}



$SQL.=$Where.=$order;

