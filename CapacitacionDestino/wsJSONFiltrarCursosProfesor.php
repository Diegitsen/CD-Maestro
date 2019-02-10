<?PHP
$hostname_localhost ="localhost";
$database_localhost ="db_cdestino";
$username_localhost ="root";
$password_localhost ="";
$json=array();
	if(isset($_GET["idProfesor"])){
		$idProfesor=$_GET["idProfesor"];
				
		$conexion = mysqli_connect($hostname_localhost,$username_localhost,$password_localhost,$database_localhost);
		$consulta="select * from Curso where idProfesor= '{$idProfesor}'";
		$resultado=mysqli_query($conexion,$consulta);
			
		while($registro=mysqli_fetch_array($resultado)){
			$json['curso'][]=$registro;
		//	echo $registro['id'].' - '.$registro['nombre'].' - '.$registro['profesion'].'<br/>';
		}
		mysqli_close($conexion);
		echo json_encode($json);
	}
	else{
		$resultar["success"]=0;
		$resultar["message"]='Ws no Retorna';
		$json['usuario'][]=$resultar;
		echo json_encode($json);
	}
?>