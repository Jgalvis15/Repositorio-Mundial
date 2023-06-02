/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package proyectomundial.DAO;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import proyectomundial.model.Resultados;
import proyectomundial.util.BasedeDatos;

/**
 *
 * @author LAB205PC16
 */
public class ResulltadosDAO {

    public ResulltadosDAO() {
        BasedeDatos.conectar();
    }

    public static boolean registrarResultados(Resultados resultados) {
        System.out.println("registrar resultados");
        String sql = "INSERT INTO j_monsalve3.partidos (grupo, local, visitante, continente_local, continente_visitante, goles_local, goles_visitante) values("
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

    public  List<Resultados> getResultados() {
        System.out.println("getresultados");
        String sql = "SELECT grupo,local,visitante,continente_local,continente_visitante,goles_local,goles_visitante FROM j_monsalve3.partidos";
        List<Resultados> resultados = new ArrayList<Resultados>();

        try {
            ResultSet result = BasedeDatos.ejecutarSQL(sql);

            if (result != null) {

                while (result.next()) {
                    Resultados resultado = new Resultados(result.getString("grupo"), result.getString("local"), result.getString("visitante"), result.getString("continente_local"), result.getString("continente_visitante"), result.getString("goles_local"), result.getString("goles_visitante"));
                    resultados.add(resultado);
                }
            }
        } catch (Exception e) {
            System.out.println(e.toString());
            System.out.println("Error consultando resultados 2 ");
        }

        return resultados;
    }
    
    public List<Resultados> getresultadosbusquedas (String nombreS){
        System.out.println("resultados selecion busqueda");
         String sql = "SELECT grupo,local,visitante,continente_local,continente_visitante,goles_local,goles_visitante FROM j_monsalve3.partidos WHERE visitante LIKE ? or local LIKE ?";
         List<Resultados> resultados = new ArrayList<Resultados>();
         try{
             if (BasedeDatos.conexion==null) {
                 System.out.println("no hay conexion");
                 return resultados;
             }
             PreparedStatement stmt = BasedeDatos.conexion.prepareStatement(sql);
             stmt.setString(1,"%"+nombreS+"%");
             stmt.setString(2,"%"+nombreS+"%");
             ResultSet result = stmt.executeQuery();
             
             if (result!=null) {
                 while(result.next()){
                     Resultados resultado = new Resultados(result.getString("grupo"),result.getString("local"),result.getString("visitante"),result.getString("continente_local"),result.getString("continente_visitante"),result.getString("goles_local"),result.getString("goles_visitante"));
                     resultados.add(resultado);
                 }
             }
         }catch(Exception e){
             System.out.println(e.toString());
             System.out.println("error en los resultados 3 ");
         }
         return resultados;
    }

    public String[][] getResultadosMatriz() {

        String[][] matrizResultados = null;
        List<Resultados> resultados = getResultados();

        if (resultados != null && resultados.size() > 0) {

            matrizResultados = new String[resultados.size()][7];

            int x = 0;
            for (Resultados resultado : resultados) {

                matrizResultados[x][0] = resultado.getGrupo();
                matrizResultados[x][1] = resultado.getLocal();
                matrizResultados[x][2] = resultado.getVisitante();
                matrizResultados[x][3] = resultado.getContinente_local();
                matrizResultados[x][4] = resultado.getContinente_visitante();
                matrizResultados[x][5] = resultado.getGoles_locales();
                matrizResultados[x][6] = resultado.getGoles_visitante();
                x++;
            }
        }

        return matrizResultados;
    }
}
