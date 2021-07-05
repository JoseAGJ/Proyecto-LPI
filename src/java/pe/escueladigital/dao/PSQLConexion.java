package pe.escueladigital.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;
import pe.escueladigital.utilidades.Utilidades;

public class PSQLConexion {

    Connection conectar() throws ClassNotFoundException {
        Connection conexion = null;
        
      Class.forName("org.postgresql.Driver");

        Properties propiedades = Utilidades.getInstancia().getConfiguracionBaseDeDatos();
        String url = "jdbc:postgresql://" +
                        propiedades.getProperty("servidor") +
                        ":" + propiedades.getProperty("puerto") +
                        "/" + propiedades.getProperty("basededatos");
        String user = propiedades.getProperty("usuariobd");
        String pass = "nuevo";
        
        
        try {
            
            System.out.println("Logueado PSQLConexion");
            conexion = DriverManager.getConnection(url, user, pass);
            
        } catch (SQLException sqle) {
            System.out.println("algo esta mal");
            System.out.println(sqle.getMessage());
        }
        return conexion;
    }
    
}
