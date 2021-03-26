package edu.pucmm.eict;

import edu.pucmm.eict.db.BootStrapServices;
import edu.pucmm.eict.util.*;
import io.javalin.Javalin;


import java.sql.SQLException;

public class Main {
    //indica el modo de operacion para la base de datos.
    private static String modoConexion = "";

    public static void main(String[] args) throws SQLException {

        Administracion administracion = new Administracion(); /*
        -Lista de usuarios visible por todo el sistema (Apartado 2)
        -Lista de productos visible por todo el sistema (Apartado 3)
        */

        if(args.length >= 1){
            modoConexion = args[0];
            System.out.println("Modo de Operacion: "+modoConexion);
        }

        //Iniciando la base de datos.
        if(modoConexion.isEmpty()) {
            BootStrapServices.getInstancia().init();
        }

        //Creando la instancia del servidor.
        Javalin app = Javalin.create(config ->{
            config.addStaticFiles("/publico"); //desde la carpeta de resources
        });

        app.start(getHerokuAssignedPort());

        new Ruta(app,administracion).ejecutarRutas();


        //Iniciando el servicio db.
        BootStrapServices.getInstancia().init();

    }

    /**
     * Metodo para indicar el puerto en Heroku
     * @return
     */
    static int getHerokuAssignedPort() {
        ProcessBuilder processBuilder = new ProcessBuilder();
        if (processBuilder.environment().get("PORT") != null) {
            return Integer.parseInt(processBuilder.environment().get("PORT"));
        }
        return 7000; //Retorna el puerto por defecto en caso de no estar en Heroku.
    }

    /**
     * Nos
     * @return
     */
    public static String getModoConexion(){
        return modoConexion;
    }
}

