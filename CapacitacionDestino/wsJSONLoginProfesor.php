<?PHP
$hostname_localhost ="localhost";
$database_localhost ="db_cdestino";
$username_localhost ="root";
$password_localhost ="";
$json=array();
	if( isset($_GET["username"]) && isset($_GET["contrasenia"])) {
		$username=$_GET["username"];
        $contrasenia=$_GET["contrasenia"];        
        
		$conexion = mysqli_connect($hostname_localhost,$username_localhost,$password_localhost,$database_localhost);
		$consulta="select username,contrasenia, idProfesor from Profesor where username= '{$username}' and contrasenia= '{$contrasenia}'";
		$resultado=mysqli_query($conexion,$consulta);
			
		if($registro=mysqli_fetch_array($resultado)){
			$json['Profesor'][]=$registro;
		//	echo $registro['id'].' - '.$registro['nombre'].' - '.$registro['profesion'].'<br/>';
		}else{
			$resultar["idProfesor"]=0;
			$resultar["nombre"]='no registra';
			$json['Profesor'][]=$resultar;
		}
		
		mysqli_close($conexion);
		echo json_encode($json);
	}
	else{
		$resultar["success"]=0;
		$resultar["message"]='Ws no Retorna';
		$json['Profesor'][]=$resultar;
		echo json_encode($json);
	}
?>