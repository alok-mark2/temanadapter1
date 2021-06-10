<?php
	
	$server = "localhost";
	$user = "root";
	$namadb = "tiumy";
	$password = "";
	
	$conn = mysql_connect($server, $user, $password, $namadb) or die ("Koneksi gagal");
	
	$id = $_POST['id'];
	$nama = $_POST['nama'];
	$telpon = $_POST ['telpon'];
	
	class emp()
		$query = mysql_connect($conn,"update teman set nama = '".$nama"', telpon='".telpon."' WHERE id = '".$id."'");
		
		if ($query)
			$response = new emp();
			$response -> success = 1;
			$response -> message = "Data berhasil dihapus";
			die(json_encode($response));
			
		}
		else {
			$response = new emp();
			$response -> success = 0;
			$response -> message = "Gagal menghapus data";
			die(json($response));
		}
?>