/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.unicauca.travelagency.server.infra.tcpip;

import co.unicauca.strategyserver.infra.ServerSocketMultiThread;
import co.unicauca.openmarket.server.access.CustomerRepositoryImplArrays;
import co.unicauca.openmarket.server.domain.services.CustomerService;
import java.util.Scanner;

/**
 *
 * @author ahurtado
 */
public class TravelAgencyServer {
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here
        Scanner teclado = new Scanner(System.in);
        System.out.println("Ingrese el puerto de escucha");
        int port = teclado.nextInt();
        ServerSocketMultiThread myServer = new ServerSocketMultiThread(port);
        TravelAgencyHandler myHandler = new TravelAgencyHandler();
        myHandler.setService(new CustomerService(new CustomerRepositoryImplArrays()));
        myServer.setServerHandler(myHandler);
        myServer.startServer();
    }

}
