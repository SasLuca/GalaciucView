<?php

if(isset($_POST['sub'])){
    $fine = 1;
    foreach($_FILES['files']['error'] as $key => $error){
        
        $tmp_name = $_FILES['files']['tmp_name'][$key];
        $name = $_FILES['files']['name'][$key];
        
        if(!move_uploaded_file($tmp_name, "uploads/$name")) $fine = 0;
    }
    
    if($fine){
      echo "
    <style>
    #loader{
    background-image:url('loading.gif');
    }
    </style>";
    file_put_contents("incepe.txt","");
    } else echo "<script>alert('Eroare!');</script>";
}

?>
<head>
<link rel="stylesheet" href="css/bootstrap.css">
<link rel="stylesheet" href="css/style.css">
</head>
<nav class="navbar navbar-toggleable-md navbar-light bg-faded">
  <a class="navbar-brand" href="#"><b>OPEN InfoEducatie 2017  - Utilitar</b></a>
</nav>

<div id="loader">
    
    <?php
    
    if(!isset($_POST['sub'])){
    
    ?>
    <div class="card">
        <div class="title">
            <h2>
    Incarca imaginile:<br>
    </h2>
        </div>
        <div class="divider"></div>
    <form method="post" enctype="multipart/form-data" name="uploadFiles">
        <label for="a">Selecteaza imaginile:</label><br>
        <input type="file" name="files[]" multiple="multiple" id="a" /><br><br>
        <input type="submit" class="btn btn-primary" value="Incarca imaginile" name="sub" style="cursor: pointer;"/>
    </form>
    </div>
    <?php
    }
    ?>
</div>
<script src="js/jquery.js"></script>
<script src="js/bootstrap.js"></script>
<script>
$(document).ready(function(){
    
    function checkIfDone(){
        $.ajax({
            url: 'http://localhost/open/results.json',
            type: 'HEAD',
            error: function(){
                //Nu exista
                console.log("Nu exista fisierul");
            },
            success: function(){
                //Exista
                console.log("Exista fisierul");
                $("#loader").load("request.php");
                clearInterval(checkInterval); 
            }
        });
    }
    /*
    $(".btn.btn-primary").click(function(){
        $("body").css("background","red");
    });
    */
    var checkInterval = setInterval(checkIfDone, 100);
   
    
});
</script>