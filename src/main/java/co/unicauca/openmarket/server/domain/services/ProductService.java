package co.unicauca.openmarket.server.domain.services;
import co.unicauca.openmarket.server.access.IProductRepository;
import co.unicauca.openmarket.commons.domain.Product;
import co.unicauca.openmarket.commons.infra.JsonError;
import co.unicauca.openmarket.commons.infra.Utilities;
import com.google.gson.Gson;
import java.util.ArrayList;
import java.util.List;


public class ProductService {
    IProductRepository repository;

    public ProductService(IProductRepository repository){
        this.repository = repository;
    }

    public synchronized List<Product> findAll(){
        return repository.findAll();
    }

    public String save(Product product) {

        List<JsonError> errors = new ArrayList<>();
        
        if(product.getName().isEmpty() || product.getDescription().isEmpty() || (product.getPrice() == 0)){
            errors.add(new JsonError("400", "BAD_REQUEST","nombre,Descripcion y precio son campos campo obligatorio. "));
        }

        if (!errors.isEmpty()) {
            Gson gson = new Gson();
            String errorsJson = gson.toJson(errors);
            return errorsJson;
        }     

        return repository.save(product);
    }


}
