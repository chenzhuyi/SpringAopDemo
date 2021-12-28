package spring.aop.demo.product;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import spring.aop.demo.product.repository.ProductRepository;

@SpringBootApplication
public class ProductApplication implements ApplicationRunner {

    @Autowired
    private ProductManage productManage;

    @Autowired
    private ProductRepository productRepository;

	public static void main(String[] args) {
		SpringApplication.run(ProductApplication.class, args);
	}

    @Override
    public void run(ApplicationArguments args) throws Exception {
        productRepository.deleteAll();

        productManage.insertProduct();
        productManage.updateProduct();
        productManage.deleteProduct();
    }

   
}
