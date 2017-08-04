<?php

$handler = file_get_contents("results.json");
$handler = json_decode($handler, true);
echo "<script>
$('#loader').css('background','rgb(40,40,40)');
</script><table cellpadding=4 class='table-bordered'><tr><td><strong>Poza</strong></td><td><strong>Scor</strong></td><td><strong>Timp</strong></td></tr>";

file_put_contents("results.csv","Filename,Score,Time", FILE_APPEND);

for($i = 0; $i < count($handler); $i++){
    $picName = $handler[$i]["name"];
    $picScore =$handler[$i]["score"];
    $picTime =$handler[$i]["time"];
    
    $picTimeS = $picTime / 1000;
    
echo "<tr><td>$picName</td><td>$picScore</td><td>".$picTimeS."s </td></tr>";

if(!file_exists("results.csv")){
    
}
file_put_contents("results.csv",",\r\n".$picName.",".$picScore.",".$picTime, FILE_APPEND);
    
}
# in cazul in care Luca se razgandeste <img src=uploads/{$handler['scores'][$i]['name']} style='width:80px;'>
echo "</table>";
?>
<head>
<link rel="stylesheet" href="css/bootstrap.css">
<link rel="stylesheet" href="css/style.css">
</head>
<a href="results.csv" class="btn btn-primary" id="download">Download results.csv</a>
<script src="js/jquery.js"></script>
<script src="js/bootstrap.js"></script>