package pe.escueladigital.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import pe.escueladigital.excepciones.ExcepcionGeneral;
import pe.escueladigital.modelos.Talla;

public class PSQLTalla {
    
    private final String OBTENERPORID = "SELECT id_talla, abreviatura, nombre FROM tallas WHERE id_talla = ?";
    private final String OBTENERTODOS = "SELECT id_talla, abreviatura, nombre FROM tallas";
    
    private Connection conexion;
    private PreparedStatement sentencia;
    private ResultSet resultados;
    
    private Talla convertir(ResultSet rs) throws SQLException {
        Talla talla = new Talla();
        talla.setId(rs.getShort("id_talla"));
        talla.setAbreviatura(rs.getString("abreviatura"));
        talla.setNombre(rs.getString("nombre"));
        return talla;
    }
    
    public Talla obtenerPorId(short id) throws ExcepcionGeneral {
        Talla talla = null;
        try {
            try {
                conexion = new PSQLConexion().conectar();
            } catch (ClassNotFoundException ex) {
                Logger.getLogger(PSQLTalla.class.getName()).log(Level.SEVERE, null, ex);
            }
            sentencia = conexion.prepareStatement(OBTENERPORID);
            sentencia.setShort(1, id);
            resultados = sentencia.executeQuery();
            if (resultados.next()) {
                talla = convertir(resultados);
            }
        } catch (SQLException sqle) {
            throw new ExcepcionGeneral("Error al obtener todos las Tallas: "+sqle.getMessage());
        } finally {
            cerrarRecursos();
        }
        return talla;
    }
    
    public List<Talla> obtenerTodos() throws ExcepcionGeneral {
        List<Talla> listado = new ArrayList<>();
        try {
            try {
                conexion = new PSQLConexion().conectar();
            } catch (ClassNotFoundException ex) {
                Logger.getLogger(PSQLTalla.class.getName()).log(Level.SEVERE, null, ex);
            }
            sentencia = conexion.prepareStatement(OBTENERTODOS);
            resultados = sentencia.executeQuery();
            while (resultados.next()) {
                Talla talla = convertir(resultados);
                listado.add(talla);
            }
        } catch (SQLException sqle) {
            throw new ExcepcionGeneral("Error al obtener todos las Tallas: "+sqle.getMessage());
        } finally {
            cerrarRecursos();
        }
        return listado;
    }
    
    private void cerrarRecursos() {
        try {
            if (resultados != null) {
                resultados.close();
            }
            if (sentencia != null) {
                sentencia.close();
            }
            if (conexion != null) {
                conexion.close();
            }
        } catch (SQLException sqle) {}
    }
}
