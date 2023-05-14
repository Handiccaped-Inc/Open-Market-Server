package co.unicauca.openmarket.server.infra.tcpip;

import co.unicauca.openmarket.server.domain.services.ProductService;
import co.unicauca.strategyserver.infra.ServerHandler;
import co.unicauca.openmarket.commons.domain.Product;
import co.unicauca.openmarket.commons.infra.JsonError;
import co.unicauca.openmarket.commons.infra.Protocol;
import com.google.gson.Gson;

import java.security.Provider.Service;
import java.util.ArrayList;
import java.util.List;


public class OpenMarketHandler extends ServerHandler {

    private static ProductService productService;

    public OpenMarketHandler()
    {

    }



    @Override
    public String processRequest(String requestJson) {
        // TODO Auto-generated method stub
        Gson gson = new Gson();  
        Protocol protocolRequest;
        protocolRequest = gson.fromJson(requestJson, Protocol.class);
        String response="";
        switch (protocolRequest.getResource()) {
            case "product":
                if (protocolRequest.getAction().equals("findAll")) {
                    // Consultar un customer
                    response = processGetAllProducts(protocolRequest);
                }

                if (protocolRequest.getAction().equals("post")) {
                    // Agregar un customer    
                    response = processPostProduct(protocolRequest);

                }
                break;
        }
        return response;
    }

    private String generateNotFoundErrorJson() {
        List<JsonError> errors = new ArrayList<>();
        JsonError error = new JsonError();
        error.setCode("404");
        error.setError("NOT_FOUND");
        error.setMessage("El Producto No Existe");
        errors.add(error);

        Gson gson = new Gson();
        String errorsJson = gson.toJson(errors);

        return errorsJson;
    }

      /**
     * Procesa la solicitud de consultar un customer
     *
     * @param protocolRequest Protocolo de la solicitud
     */
    private String processGetAllProducts(Protocol protocolRequest) {
        // Extraer la cedula del primer parámetro
        List<Product> products;
        products = productService.findAll();

        if (products == null) {
            String errorJson = generateNotFoundErrorJson();
            return errorJson;
        } else {
            return objectToJSON(products);
        }
    }

    private String processPostProduct(Protocol protocolRequest) {
        Product product = new Product();
        // Reconstruir el customer a partid de lo que viene en los parámetros
        product.setName(protocolRequest.getParameters().get(0).getValue());
        product.setDescription(protocolRequest.getParameters().get(1).getValue());
        product.setDescription(protocolRequest.getParameters().get(2).getValue());

        String response = productService.save(product);

        return response;
    }

    public void setProductService(ProductService productService){
        this.productService = productService;

    }

    
}
