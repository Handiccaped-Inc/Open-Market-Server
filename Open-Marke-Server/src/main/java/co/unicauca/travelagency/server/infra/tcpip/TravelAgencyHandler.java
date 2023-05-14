/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.unicauca.travelagency.server.infra.tcpip;

import co.unicauca.strategyserver.infra.ServerHandler;
import co.unicauca.travelagency.commons.domain.Customer;
import co.unicauca.travelagency.commons.infra.JsonError;
import co.unicauca.travelagency.commons.infra.Protocol;
import co.unicauca.travelagency.server.access.CustomerRepositoryImplArrays;
import co.unicauca.travelagency.server.domain.services.CustomerService;
import com.google.gson.Gson;
import java.util.ArrayList;
import java.util.List;


/**
 *
 * @author ahurtado
 */
public class TravelAgencyHandler extends ServerHandler {

     /**
     * Servicio de clientes
     */
    private static CustomerService service;

    public TravelAgencyHandler() {
    }

     /**
     * Procesar la solicitud que proviene de la aplicación cliente
     *
     * @param requestJson petición que proviene del cliente socket en formato
     * json que viene de esta manera:
     * "{"resource":"customer","action":"get","parameters":[{"name":"id","value":"1"}]}"
     *
     */
   
    
    @Override
    public String processRequest(String requestJson) {
        // Convertir la solicitud a objeto Protocol para poderlo procesar
        Gson gson = new Gson();  
        Protocol protocolRequest;
        protocolRequest = gson.fromJson(requestJson, Protocol.class);
        String response="";
        switch (protocolRequest.getResource()) {
            case "customer":
                if (protocolRequest.getAction().equals("get")) {
                    // Consultar un customer
                    response = processGetCustomer(protocolRequest);
                }

                if (protocolRequest.getAction().equals("post")) {
                    // Agregar un customer    
                    response = processPostCustomer(protocolRequest);

                }
                break;
        }
        return response;

    }

    /**
     * Procesa la solicitud de consultar un customer
     *
     * @param protocolRequest Protocolo de la solicitud
     */
    private String processGetCustomer(Protocol protocolRequest) {
        // Extraer la cedula del primer parámetro
        String id = protocolRequest.getParameters().get(0).getValue();
        Customer customer = service.findCustomer(id);
        if (customer == null) {
            String errorJson = generateNotFoundErrorJson();
            return errorJson;
        } else {
            return objectToJSON(customer);
        }
    }

    /**
     * Procesa la solicitud de agregar un customer
     *
     * @param protocolRequest Protocolo de la solicitud
     */
    private String processPostCustomer(Protocol protocolRequest) {
        Customer customer = new Customer();
        // Reconstruir el customer a partid de lo que viene en los parámetros
        customer.setId(protocolRequest.getParameters().get(0).getValue());
        customer.setFirstName(protocolRequest.getParameters().get(1).getValue());
        customer.setLastName(protocolRequest.getParameters().get(2).getValue());
        customer.setAddress(protocolRequest.getParameters().get(3).getValue());
        customer.setEmail(protocolRequest.getParameters().get(4).getValue());
        customer.setGender(protocolRequest.getParameters().get(5).getValue());
        customer.setMobile(protocolRequest.getParameters().get(6).getValue());

        String response = getService().createCustomer(customer);
        return response;
    }

    /**
     * Genera un ErrorJson de cliente no encontrado
     *
     * @return error en formato json
     */
    private String generateNotFoundErrorJson() {
        List<JsonError> errors = new ArrayList<>();
        JsonError error = new JsonError();
        error.setCode("404");
        error.setError("NOT_FOUND");
        error.setMessage("Cliente no encontrado. Cédula no existe");
        errors.add(error);

        Gson gson = new Gson();
        String errorsJson = gson.toJson(errors);

        return errorsJson;
    }

    /**
     * @return the service
     */
    public CustomerService getService() {
        return service;
    }

    /**
     * @param service the service to set
     */
    public void setService(CustomerService service) {
        this.service = service;
    } 
}
