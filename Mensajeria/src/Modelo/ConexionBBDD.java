package Modelo;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

//@SuppressWarnings("deprecation")
public class ConexionBBDD {

	public Connection conexion() {
		Connection conexion = null;
		 
		try {
		  //Class.forName("org.postgresql.Driver").newInstance();
		  conexion = DriverManager.getConnection(
		    "jdbc:postgresql://localhost:5432/mensajeria",
		    "mensajeria", "mensajeria");
		} catch (SQLException sqle) {
		  sqle.printStackTrace();
		}
		
		return conexion;
	}
	
}

