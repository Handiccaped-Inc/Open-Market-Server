package co.unicauca.openmarket.server.access;


import co.unicauca.openmarket.commons.domain.Product;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Libardo, Julio
 */
public interface IProductRepository extends IRepository<Product> {
    List<Product> findByCategoryID(Long id);
}
