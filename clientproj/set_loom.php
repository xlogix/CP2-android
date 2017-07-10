<?php

ini_set('display_errors', 1);
ini_set('display_startup_errors', 1);
error_reporting(E_ALL);

// json response array
$response = array("error" => FALSE);

// Getting the loom data
if (isset($_REQUEST['loom_no'])&&!empty($_REQUEST['loom_no'])) {

// receiving the REQ$_REQUEST params
$loom_no = $_REQUEST['loom_no'];

define('DB_USER', "root"); // db user
define('DB_PASSWORD', ""); // db password (mention your db password here)
define('DB_DATABASE', "android_api"); // database name
define('DB_SERVER', "localhost"); // db server'

$conn1 = mysqli_connect(DB_SERVER, DB_USER, DB_PASSWORD, DB_DATABASE);
     
if (isset($_REQUEST['date']) && isset($_REQUEST['shift']) && isset($_REQUEST['loom_no']) && isset($_REQUEST['quality'])
	&& isset($_REQUEST['type']) && isset($_REQUEST['emp_code']) && isset($_REQUEST['emp_name']) 
	&& isset($_REQUEST['start_reading']) && isset($_REQUEST['end_reading']) && isset($_REQUEST['remarks']) 
	&& isset($_REQUEST['time']))
	 {
		// Setting the paramters
		$date = $_REQUEST['date'];
		$shift = $_REQUEST['shift'];
		$loom_no = $_REQUEST['loom_no'];
		$quality = $_REQUEST['quality'];
		$type = $_REQUEST['type']; //mess value
		$emp_code = $_REQUEST['emp_code'];
		$emp_name = $_REQUEST['emp_name'];
		$start_reading = $_REQUEST['start_reading'];
		$end_reading = $_REQUEST['end_reading'];
		$remarks = $_REQUEST['remarks'];
		$time = $_REQUEST['time'];

		$stmt = "INSERT INTO entry (entry_date, shift, loom_no, quality, emp_code, emp_name, start_reading, 
		end_reading, type, remarks) VALUES ('$date', '$shift', '$loom_no', '$quality', '$emp_code', '$emp_name', 
		'$start_reading', '$end_reading', '$type', '$remarks', '$time')";

		$data=mysqli_query($conn1,$stmt);

    	if ($data != false) {
        	// use is found
        	$response["error"] = FALSE;
        	echo json_encode($response);
    	} else {
        	// loom_data is not found with the credentials
        	$response["error"] = TRUE;
        	$response["error_msg"] = "Something went wrong. Try again!";
        	echo json_encode($response);
    	}
	} else {
    	// required REQ$_REQUEST params is missing
    	$response["error"] = TRUE;
    	$response["error_msg"] = "Required parameters are missing!";
    	echo json_encode($response);
	}
}

?>