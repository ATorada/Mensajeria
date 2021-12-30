package BBDD;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 *
 * @author �ngel Torada
 */
public class ConexionBBDD {

    /**
     * M�todo que se encarga de devolver una conexi�n con la base de datos
     *
     * @return Devuele la conexi�n con la base de datos
     */
    public Connection conexion() {
        Connection conexion = null;

        try {
            conexion = DriverManager.getConnection(
                    "jdbc:postgresql://localhost:5432/mensajeria",
                    "mensajeria", "mensajeria");
        } catch (SQLException sqle) {
        }

        return conexion;
    }

}
