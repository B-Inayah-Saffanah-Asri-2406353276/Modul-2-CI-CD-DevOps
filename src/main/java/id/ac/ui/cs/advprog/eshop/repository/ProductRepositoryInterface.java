package id.ac.ui.cs.advprog.eshop.repository;
import id.ac.ui.cs.advprog.eshop.model.Product;

import java.util.Iterator;

public interface ProductRepositoryInterface {
    Product create(Product product);
    Product edit(Product updatedProduct);
    Product findById(String productId);
    Iterator<Product> findAll();
    void delete (String productId);
}
