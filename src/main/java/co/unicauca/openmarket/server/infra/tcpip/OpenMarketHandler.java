package co.unicauca.openmarket.server.infra.tcpip;

import co.unicauca.openmarket.server.domain.services.CategoryService;
import co.unicauca.openmarket.server.domain.services.ProductService;
import co.unicauca.strategyserver.infra.ServerHandler;
import co.unicauca.openmarket.commons.domain.Category;
import co.unicauca.openmarket.commons.domain.Product;
import co.unicauca.openmarket.commons.infra.JsonError;
import co.unicauca.openmarket.commons.infra.Protocol;
import com.google.gson.Gson;

import java.security.Provider.Service;
import java.util.ArrayList;
import java.util.List;

public class OpenMarketHandler extends ServerHandler {

    private static ProductService productService;
    private static CategoryService categoryService;

    public OpenMarketHandler() {

    }

    @Override
    public String processRequest(String requestJson) {
        // TODO Auto-generated method stub
        Gson gson = new Gson();
        Protocol protocolRequest;
        protocolRequest = gson.fromJson(requestJson, Protocol.class);
        String response = "";
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

                if (protocolRequest.getAction().equals("get")) {
                    response = processFinbyIdProduct(protocolRequest);
                }

                if (protocolRequest.getAction().equals("findName")) {
                    response = processFindByNameProducts(protocolRequest);
                }

                
                if (protocolRequest.getAction().equals("findCategory")) {
                    response = processfindByCategoryIdProducts(protocolRequest);
                }

                if (protocolRequest.getAction().equals("delete")) {
                    response = processDeleteProducts(protocolRequest);
                }
                break;
            case "category":
                if (protocolRequest.getAction().equals("findAll")) {
                    // Consultar un customer
                    response = processGetAllCategories(protocolRequest);
                }

                if (protocolRequest.getAction().equals("post")) {
                    // Agregar un customer
                    response = processPostCategory(protocolRequest);

                }

                if (protocolRequest.getAction().equals("get")) {
                    response = processFinbyIdCategory(protocolRequest);
                }

                if (protocolRequest.getAction().equals("findName")) {
                    response = processFindByNameCategories(protocolRequest);
                }

                if (protocolRequest.getAction().equals("delete")) {
                    response = processDeleteCategories(protocolRequest);
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

        for (Product product : products) {
            Category category = categoryService.findById(product.getCategory().getCategoryId());
            product.setCategory(category);
        }

        if (products == null) {
            String errorJson = generateNotFoundErrorJson();
            return errorJson;
        } else {
            return objectToJSON(products);
        }
    }

    private String processfindByCategoryIdProducts(Protocol protocolRequest) {
        // Extraer la cedula del primer parámetro
        List<Product> products;
        products = productService.findByCategoryId(Long.parseLong(protocolRequest.getParameters().get(0).getValue()));
        System.out.println(protocolRequest.getParameters().get(0).getValue());

        for (Product product : products) {
            Category category = categoryService.findById(product.getCategory().getCategoryId());
            product.setCategory(category);
        }

        if (products == null) {
            String errorJson = generateNotFoundErrorJson();
            return errorJson;
        } else {
            return objectToJSON(products);
        }
    }

    private String processDeleteProducts(Protocol protocolRequest) {
        // Extraer la cedula del primer parámetro
        Long Id = Long.parseLong(protocolRequest.getParameters().get(0).getValue());
        Product product = productService.findById(Id);
        if (product == null) {
            String errorJson = generateNotFoundErrorJson();
            return errorJson;
        } else {
            return productService.delete(Id);
        }
    }

    private String processFindByNameProducts(Protocol protocolRequest) {
        // Extraer la cedula del primer parámetro
        List<Product> products;
        String name = protocolRequest.getParameters().get(0).getValue();
        products = productService.findByName(name);

        for (Product product : products) {
            Category category = categoryService.findById(product.getCategory().getCategoryId());
            product.setCategory(category);
        }
        
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
        product.setPrice(Double.parseDouble(protocolRequest.getParameters().get(2).getValue()));
        Category category = categoryService.findById(Long.parseLong(protocolRequest.getParameters().get(3).getValue()));

        if (category == null) {
            String errorJson = generateNotFoundErrorJson();
            return errorJson;
        }

        product.setCategory(category);

        String response = productService.save(product);

        return response;
    }

    private String processFinbyIdProduct(Protocol protocolRequest) {
        Long Id = Long.parseLong(protocolRequest.getParameters().get(0).getValue());
        Product product = productService.findById(Id);
        Category category = categoryService.findById(product.getCategory().getCategoryId());
        product.setCategory(category);
        if (product == null || category == null) {
            String errorJson = generateNotFoundErrorJson();
            return errorJson;
        } else {
            return objectToJSON(product);
        }

    }

    private String processDeleteCategories(Protocol protocolRequest) {//
        Long Id = Long.parseLong(protocolRequest.getParameters().get(0).getValue());
        Category category = categoryService.findById(Id);
        if (category == null) {
            String errorJson = generateNotFoundErrorJson();
            return errorJson;
        } else {
            return categoryService.delete(Id);
        }
    }

    private String processFindByNameCategories(Protocol protocolRequest) {//
        // Extraer la cedula del primer parámetro
        List<Category> categories;
        String name = protocolRequest.getParameters().get(0).getValue();
        categories = categoryService.findByName(name);

        if (categories == null) {
            String errorJson = generateNotFoundErrorJson();
            return errorJson;
        } else {
            return objectToJSON(categories);
        }
    }

    private String processFinbyIdCategory(Protocol protocolRequest) {//
        Long Id = Long.parseLong(protocolRequest.getParameters().get(0).getValue());
        Category category = categoryService.findById(Id);
        if (category == null) {
            String errorJson = generateNotFoundErrorJson();
            return errorJson;
        } else {
            return objectToJSON(category);
        }
    }

    private String processPostCategory(Protocol protocolRequest) {//
        Category category = new Category();
        // Reconstruir el customer a partid de lo que viene en los parámetros
        category.setName(protocolRequest.getParameters().get(0).getValue());
        String response = categoryService.save(category);
        return response;
    }

    private String processGetAllCategories(Protocol protocolRequest) {//
        // Extraer la cedula del primer parámetro
        List<Category> categories;
        categories = categoryService.findAll();

        if (categories == null) {
            String errorJson = generateNotFoundErrorJson();
            return errorJson;
        } else {
            return objectToJSON(categories);
        }
    }

    public void setProductService(ProductService productService) {
        this.productService = productService;

    }
    public void setCategoryService(CategoryService categoryService) {
        this.categoryService = categoryService;

    }
}
