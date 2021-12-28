package spring.aop.demo.product.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import spring.aop.demo.product.model.Product;

public interface ProductRepository extends CrudRepository<Product, Long> {
    List<Product> findByName(String name);
}
