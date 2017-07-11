<?php

ini_set('display_errors', 1);
ini_set('display_startup_errors', 1);
error_reporting(E_ALL);

// Getting the loom data
if (isset($_REQUEST['loom_no'])&&!empty($_REQUEST['loom_no'])) {

// receiving the post params
$loom_no = $_REQUEST['loom_no'];

define('DB_USER', "root"); // db user
define('DB_PASSWORD', ""); // db password (mention your db password here)
define('DB_DATABASE', "android_api"); // database name
define('DB_SERVER', "localhost"); // db server'

$conn1 = mysqli_connect(DB_SERVER, DB_USER, DB_PASSWORD, DB_DATABASE);

$stmt = "SELECT * FROM entry WHERE loom_no = '$loom_no' AND end_reading = ''";

$details=mysqli_query($conn1,$stmt);
     
// $details = $stmt->get_result()->fetch_assoc();
//$stmt->close();
while($row_data = mysqli_fetch_assoc($details)) {
    $loom_data=$row_data;
    $loom_c[]=$row_data['emp_name'];
    $loom_id[] = $row_data['id'];
    }

    if ($loom_data != false) {
        // data is found
        $response["error"] = FALSE;
        $response['date']=date("d-m-y",$loom_data['ts']);
        $response["end_reading"] = $loom_data["end_reading"];
        $response["start_reading"] = $loom_data["start_reading"];
        $response["end_reading"] = $loom_data["end_reading"];
        $response["loom_no"] = $loom_data["loom_no"];
        $response["status"] = $loom_data["status"];
        $response["quality"] = $loom_data["quality"];
        $response["type"] = $loom_data["type"];
        $response["shift"] = $loom_data["shift"];
        $response["time"] = $loom_data["ts"];

        foreach($loom_id as $dat) {
            $stmt1 = "SELECT * FROM entry WHERE id = '$dat'";
            // $stmt->bind_param("s", $loom_no);
            // $do =fetch_assoc($stmt);
            // print_r($do[loom_no]);
            $details1=mysqli_query($conn1,$stmt1);
            // $details = $stmt->get_result()->fetch_assoc();
            // $stmt->close();
            while($rd = mysqli_fetch_assoc($details1)) {
                $ld = $rd;
                $response["emplist"]["ID".$dat]["closeReading"] = $ld["end_reading"];
                $response["emplist"]["ID".$dat]["empCode"] = $ld["id"];
                $response["emplist"]["ID".$dat]["empName"] = $ld["emp_name"];
                $response["emplist"]["ID".$dat]["openReading"] = $ld["start_reading"];
                $response["emplist"]["ID".$dat]["remarks"] = $ld["remarks"];
            }
        }
        // encode it into JSON
        echo json_encode($response);
    } else {
        // loom_data is not found
        $response["error"] = TRUE;
        $response["error_msg"] = "NO DATA FOUND!";
        echo json_encode($response);
    }
} else {
    // required post params is missing
    $response["error"] = TRUE;
    $response["error_msg"] = "Required parameters are missing!";
    echo json_encode($response);
}
?>