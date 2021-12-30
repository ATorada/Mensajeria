package BBDD;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 *
 * @author Ángel Torada
 */
public class ConexionBBDD {

    /**
     * Método que se encarga de devolver una conexión con la base de datos
     *
     * @return Devuele la conexión con la base de datos
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
