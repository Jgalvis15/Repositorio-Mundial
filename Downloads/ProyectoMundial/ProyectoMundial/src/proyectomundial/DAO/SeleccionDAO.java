/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package proyectomundial.DAO;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
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
    
    public boolean registrarSeleccion(Seleccion seleccion) {
        
        String sql = "INSERT INTO j_monsalve3.seleccion (nombres, continente, dt, nacionalidad) values("
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
        
        String sql = "SELECT nombres, continente, dt, nacionalidad FROM j_monsalve3.seleccion";
        List<Seleccion> selecciones = new ArrayList<Seleccion>();
        
        try {
            ResultSet result = BasedeDatos.ejecutarSQL(sql);
            
            if(result != null) {
            
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
    
 public List<Seleccion> getSeleccionesBusqueda(String nombreSeleccion) {
        System.out.println("LLEGAMOS HASTA GETSELECCIONESBUSQUEDA");
    String sql = "SELECT nombres, continente, dt, nacionalidad FROM j_monsalve3.seleccion WHERE nombres LIKE ?";
    List<Seleccion> selecciones = new ArrayList<Seleccion>();

    try {
        if (BasedeDatos.conexion == null) {
            // Mostrar un mensaje de error o lanzar una excepción
            System.out.println("No hay conexión a la base de datos");
            return selecciones;
        }
        // Preparar la consulta SQL y establecer el parámetro
        PreparedStatement stmt =BasedeDatos.conexion.prepareStatement(sql);
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
    public List<Resultados> cantidad_de_equipos(){
         String sql ="select count(*) from j_monsalve3.seleccion";
         List<Resultados> resu = new ArrayList<Resultados>();
       try{
             if (BasedeDatos.conexion==null) {
                 System.out.println("no hay conexion");
                 return resu;
             }
             PreparedStatement stmt = BasedeDatos.conexion.prepareStatement(sql);
             ResultSet result = stmt.executeQuery();
             
             if (result!=null) {
                 while(result.next()){
                     Resultados resultado = new Resultados(result.getString("equipos_totales"));
                     resu.add(resultado);
                 }
             }
         }catch(Exception e){
             System.out.println(e.toString());
             System.out.println("error en los resultados 3 ");
         }
         return resu;
    }
}
