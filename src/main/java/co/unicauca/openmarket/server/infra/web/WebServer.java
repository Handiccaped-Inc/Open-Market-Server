package co.unicauca.travelagency.server.infra.web;
/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import co.unicauca.travelagency.server.access.CustomerRepositoryImplArrays;
import co.unicauca.travelagency.server.domain.services.CustomerService;
import java.net.InetSocketAddress;
import com.sun.net.httpserver.HttpServer;
import java.util.concurrent.Executors;


public class WebServer {
    public static void main(String[] args) throws Exception {
        HttpServer server = HttpServer.create(new InetSocketAddress(8003), 0);
        TravelAgencyWebHandler webhandler = new TravelAgencyWebHandler();
        webhandler.setService(new CustomerService(new CustomerRepositoryImplArrays()));
        server.createContext("/client", webhandler);
        server.setExecutor(Executors.newCachedThreadPool()); // creates a default executor
        server.start();
        System.out.println("Servidor inicializado en el puerto 8003");
    }
}