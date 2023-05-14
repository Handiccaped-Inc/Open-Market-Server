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
    public boolean save(Product object) {
        // TODO Auto-generated method stub
        object.setProductId((long)(Products.size()+1));
        Products.add(object);
        return true;
    }

    @Override
    public boolean edit(Long id, Product object) {
        // TODO Auto-generated method stub
        for (Product product : Products) {
            if(product.getProductId().equals(id)){
                product.setProductId(object.getProductId());
                product.setName(object.getName());
                product.setDescription(object.getDescription());
                product.setPrice(object.getPrice());
                return true;
            }
        }

        return false;
    }

    @Override
    public boolean delete(Long id) {
        // TODO Auto-generated method stub
        for (Product product : Products) {
            if(product.getProductId().equals(id)){
                return Products.remove(product);
            }
    
        }

        return false;
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
        List<Product> ProductsFindName = new ArrayList<>();
        for (Product product : Products) {
            if(product.getName().equals(name)){
                ProductsFindName.add(product);
            }
        }

        return ProductsFindName;
    }

    @Override
    public List<Product> findAll() {
        // TODO Auto-generated method stub
        return Products;
    }



    
}
