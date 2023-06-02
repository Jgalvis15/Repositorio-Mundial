/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package proyectomundial.DAO;

import java.util.ArrayList;
import java.util.List;
import proyectomundial.model.Resultados;
import proyectomundial.util.BasedeDatos;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;


public class ResultadosDAO {
    
    public List<Resultados> getResultados() {
        System.out.println("getresultados");
        String sql = "SELECT grupo,local,visitante,continente_local,continente_visitante,goles_local,goles_visitante FROM j_galvis15.partidos";
        List<Resultados> resultados = new ArrayList<Resultados>();

        try {
            var result = BasedeDatos.ejecutarSQL(sql);

            if (result != null) {

                while (result.next()) {
                    Resultados resultado = new Resultados(result.getString("grupo"), result.getString("local"), result.getString("visitante"), result.getString("continente_local"), result.getString("continente_visitante"), result.getString("goles_local"), result.getString("goles_visitante"));
                    resultados.add(resultado);
                }
            }
        } catch (SQLException e) {
    System.err.println("Error consultando resultados: " + e.getMessage());
    throw new RuntimeException(e);
}       catch (Exception ex) {
            Logger.getLogger(ResultadosDAO.class.getName()).log(Level.SEVERE, null, ex);
        }


        return resultados;
    }
    
    public List<Resultados> getresultadosbusquedas(String nombreS) {
        System.out.println("resultados selecion busqueda");
        String sql = "SELECT grupo,local,visitante,continente_local,continente_visitante,goles_local,goles_visitante FROM j_galvis15.partidos WHERE visitante LIKE ? or local LIKE ?";
        List<Resultados> resultados = new ArrayList<Resultados>();
        try {
            if (BasedeDatos.conexion == null) {
                System.out.println("no hay conexion");
                return resultados;
            }
            var stmt = BasedeDatos.conexion.prepareStatement(sql);
            stmt.setString(1, "%" + nombreS + "%");
            stmt.setString(2, "%" + nombreS + "%");
            var result = stmt.executeQuery();

            if (result != null) {
                while (result.next()) {
                    Resultados resultado = new Resultados(result.getString("grupo"), result.getString("local"), result.getString("visitante"), result.getString("continente_local"), result.getString("continente_visitante"), result.getString("goles_local"), result.getString("goles_visitante"));
                    resultados.add(resultado);
                }
            }
        } catch (Exception e) {
            System.out.println(e.toString());
            System.out.println("error en los resultados 3 ");
        }
        return resultados;
    }
    
     public String[][] getResultadosMatriz() {

         String[][] matrizResultados=new String[0][0];
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
