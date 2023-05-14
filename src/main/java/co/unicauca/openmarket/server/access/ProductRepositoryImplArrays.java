package co.unicauca.openmarket.server.access;

import java.util.ArrayList;
import java.util.List;

import co.unicauca.openmarket.commons.domain.Product;

public class ProductRepositoryImplArrays implements IProductRepository {

    private List<Product> Products; 

    public ProductRepositoryImplArrays(){
        Incializar();

    }

    private void Incializar(){
        Products = new ArrayList<>();
        Products.add(new Product(1L,"Papas","Son Papas", 2000));
        Products.add(new Product(2L,"Gaseosa","Son Bombones", 2000));
        Products.add(new Product(3L,"Doritos","Son Papas", 2000));
        Products.add(new Product(4L,"Pablo","es homosexual", 2000));
        Products.add(new Product(5L,"Mario","es el novio de pablo", 2000));
    }



    @Override
    public String save(Product object) {
        // TODO Auto-generated method stub
        object.setProductId((long)(Products.size()+1));
        Products.add(object);
        return object.getProductId().toString();
    }

    @Override
    public boolean edit(Long id, Product object) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'edit'");
    }

    @Override
    public boolean delete(Long id) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'delete'");
    }

    @Override
    public Product findById(Long id) {
        // TODO Auto-generated method stub
        for (Product product : Products) {
            if(product.getProductId().equals(id)){
                return product;
            }
    
        }

        return null;
    }

    @Override
    public List<Product> findByName(String name) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'findByName'");
    }

    @Override
    public List<Product> findAll() {
        // TODO Auto-generated method stub
        return Products;
    }



    
}
