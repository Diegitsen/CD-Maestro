
<?PHP
$hostname_localhost="localhost";
$database_localhost="db_cdestino";
$username_localhost="root";
$password_localhost="";
$json=array();
    if(isset($_GET["nombre"]) && isset($_GET["apellido"]) && isset($_GET["ministerio"])  
    && isset($_GET["nivel"]) ){
		$nombre=$_GET['nombre'];
        $apellido=$_GET['apellido'];
        $ministerio=$_GET['ministerio'];
		$nivel=$_GET['nivel'];
		
		$conexion=mysqli_connect($hostname_localhost,$username_localhost,$password_localhost,$database_localhost);
		
		$insert="INSERT INTO Alumno( nombre, apellido, ministerio, nivel) 
        VALUES ('{$nombre}','{$apellido}' ,'{$ministerio}' ,'{$nivel}' )";
		$resultado_insert=mysqli_query($conexion,$insert);
		
		if($resultado_insert){
			$consulta="SELECT * FROM Alumno WHERE apellido = '{$apellido}'";
			$resultado=mysqli_query($conexion,$consulta);
			
			if($registro=mysqli_fetch_array($resultado)){
				$json['Alumno'][]=$registro;
			}
			mysqli_close($conexion);
			echo json_encode($json);
		}
		else{
			$resulta["idAlumno"]=0;
			$resulta["nombre"]='No Registra';
            $resulta["apellido"]='No Registra';
            $resulta["ministerio"]='No Registra';
            $resulta["nivel"]='No Registra';
			$json['Alumno'][]=$resulta;
			echo json_encode($json);
		}
		
	}
	else{
			$resulta["idAlumno"]=0;
			$resulta["nombre"]='WS No retorna';
            $resulta["apellido"]='WS No retorna';
            $resulta["nombre"]='WS No retorna';
            $resulta["ministerio"]='WS No retorna';
            $resulta["nivel"]='WS No retorna';
			$json['Alumno'][]=$resulta;
			echo json_encode($json);
		}
?>