/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package proyectomundial.DAO;

import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import proyectomundial.model.Resultados;
import proyectomundial.model.Seleccion;
import proyectomundial.util.BasedeDatos;
import static proyectomundial.util.BasedeDatos.ejecutarSQL;

/**
 *
 * @author miguelropero
 */
public class SeleccionDAO {

    public SeleccionDAO() {
        BasedeDatos.conectar();
    }
    
    public static boolean registrarResultados(Resultados resultados) {
        System.out.println("registrar resultados");
        String sql = "INSERT INTO j_galvis15.partidos (grupo, local, visitante, continente_local, continente_visitante, goles_local, goles_visitante) values("
                + "'" + resultados.getGrupo() + "', "
                + "'" + resultados.getLocal() + "', "
                + "'" + resultados.getVisitante() + "', "
                + "'" + resultados.getContinente_local() + "',"
                + "'" + resultados.getContinente_visitante() + "',"
                + "'" + resultados.getGoles_locales() + "',"
                + "'" + resultados.getGoles_visitante() + "')";

        //BasedeDatos.conectar();
        boolean registro = BasedeDatos.ejecutarActualizacionSQL(sql);
        //BasedeDatos.desconectar();
        return registro;
    }

    
    public boolean registrarSeleccion(Seleccion seleccion) {
        
        String sql = "INSERT INTO j_galvis15.seleccion (nombre, continente, dt, nacionalidad) values("
                + "'" + seleccion.getNombre() + "', " 
                + "'" + seleccion.getContinente() + "', " 
                + "'" + seleccion.getDt() + "', " 
                + "'" + seleccion.getNacionalidad() + "')";
        
        //BasedeDatos.conectar();
        boolean registro = BasedeDatos.ejecutarActualizacionSQL(sql);
        //BasedeDatos.desconectar();
        return registro;
    }
    
    public List<Seleccion> getSelecciones() {
        
        String sql = "SELECT nombre, continente, dt, nacionalidad FROM j_galvis15.seleccion";
        List<Seleccion> selecciones = new ArrayList<Seleccion>();
        
        try {
            ResultSet result = BasedeDatos.ejecutarSQL(sql);
            
            if(result != null) {
            
                while (result.next()) { 
                    Seleccion seleccion = new Seleccion(result.getString("nombre"), result.getString("continente"), result.getString("dt"), result.getString("nacionalidad"));
                    selecciones.add(seleccion);
                }
            }
        } catch (Exception e) {
            System.out.println(e.toString());
            System.out.println("Error consultando selecciones");
        }
        
        return selecciones;
    }
    
public List<Seleccion> getSeleccionesBusqueda(String nombreSeleccion) {
        String sql = "SELECT nombres, continente, dt, nacionalidad FROM j_galvis15.seleccion WHERE nombres LIKE ?";
        List<Seleccion> selecciones = new ArrayList<Seleccion>();

        try {
            if (BasedeDatos.conexion == null) {
                // Mostrar un mensaje de error o lanzar una excepción
                System.out.println("No hay conexión a la base de datos");
                return selecciones;
            }
            // Preparar la consulta SQL y establecer el parámetro
            var stmt = BasedeDatos.conexion.prepareStatement(sql);
            stmt.setString(1, "%" + nombreSeleccion + "%");

            // Ejecutar la consulta y obtener el resultado
            ResultSet result = stmt.executeQuery();

            if (result != null) {
                while (result.next()) {
                    Seleccion seleccion = new Seleccion(result.getString("nombres"), result.getString("continente"), result.getString("dt"), result.getString("nacionalidad"));
                    selecciones.add(seleccion);
                }
            }
        } catch (Exception e) {
            System.out.println(e.toString());
            System.out.println("Error consultando selecciones");
        }

        return selecciones;
    } 

public int totalseleciones() {
    String sql = "SELECT COUNT(*) AS total_filas FROM j_galvis15.seleccion s;";
    int totalFilas = 0;

    try {
        var result = BasedeDatos.ejecutarSQL(sql);

        if (result.next()) {
            totalFilas = result.getInt("total_filas");
        }
    } catch (Exception e) {
        System.out.println("Error al obtener el total de selecciones: " + e.toString());
    }

    return totalFilas;
}

public String scontiente() {
    String sql = "SELECT continente, COUNT(*) AS total_selecciones " +
                 "FROM j_galvis15.seleccion s " +
                 "GROUP BY continente;";
    StringBuilder result = new StringBuilder();

    try {
        var resultSet = BasedeDatos.ejecutarSQL(sql);

        while (resultSet.next()) {
            String continente = resultSet.getString("continente");
            int totalSelecciones = resultSet.getInt("total_selecciones");

            result.append("\nContinente: ").append(continente)
                .append("\n, Total de selecciones: "+System.lineSeparator()).append(totalSelecciones)
                .append("\n");
        }
    } catch (Exception e) {
        System.out.println("Error al obtener las selecciones por continente: " + e.toString());
    }

    return result.toString();
}


public String nacionalidadesdt() {
    String sql = "SELECT nacionalidad, COUNT(*) AS cantidad_directores " +
                 "FROM j_galvis15.seleccion s " +
                 "GROUP BY nacionalidad;";
    StringBuilder result = new StringBuilder();

    try {
        var resultSet = BasedeDatos.ejecutarSQL(sql);

        while (resultSet.next()) {
            String nacionalidad = resultSet.getString("nacionalidad");
            int cantidadDirectores = resultSet.getInt("cantidad_directores");

            result.append("Nacionalidad: ").append(nacionalidad)
                .append(", Cantidad de directores técnicos: ").append(cantidadDirectores)
                .append("\n");
        }
    } catch (Exception e) {
        System.out.println("Error al obtener las nacionalidades de los directores técnicos: " + e.toString());
    }

    return result.toString();
}

public String RANKING() {
    String sql = "SELECT nacionalidad, COUNT(*) AS cantidad_directores " +
                 "FROM j_galvis15.seleccion s " +
                 "GROUP BY nacionalidad " +
                 "ORDER BY cantidad_directores DESC " +
                 "LIMIT 5;";
    StringBuilder result = new StringBuilder();

    try {
        var resultSet = BasedeDatos.ejecutarSQL(sql);
        int ranking = 1;

        while (resultSet.next()) {
            String nacionalidad = resultSet.getString("nacionalidad");
            int cantidadDirectores = resultSet.getInt("cantidad_directores");

            result.append("Ranking ").append(ranking).append(": ")
                .append("Nacionalidad: ").append(nacionalidad)
                .append(", Cantidad de directores técnicos: ").append(cantidadDirectores)
                .append("\n");

            ranking++;
        }
    } catch (Exception e) {
        System.out.println("Error al obtener el ranking de las nacionalidades de los directores técnicos: " + e.toString());
    }

    return result.toString();
}



    public String[][] getSeleccionesMatriz() {
        
        String[][] matrizSelecciones = null;
        List<Seleccion> selecciones = getSelecciones();
        
        
        if(selecciones != null && selecciones.size() > 0) {
            
        
            matrizSelecciones = new String[selecciones.size()][4];

            int x = 0;
            for (Seleccion seleccion : selecciones) {

                matrizSelecciones[x][0] = seleccion.getNombre();
                matrizSelecciones[x][1] = seleccion.getContinente();
                matrizSelecciones[x][2] = seleccion.getDt();
                matrizSelecciones[x][3] = seleccion.getNacionalidad();
                x++;
            }
        }
        
        return matrizSelecciones;
    }
}
