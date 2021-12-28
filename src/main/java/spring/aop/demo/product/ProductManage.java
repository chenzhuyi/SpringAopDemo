package spring.aop.demo.product;

import java.math.BigDecimal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import spring.aop.demo.product.model.Product;
import spring.aop.demo.product.repository.ProductRepository;

@Component
public class ProductManage {

    @Autowired
    private ProductRepository productRepository;

    public void insertProduct() {
//        Product book = Product.builder()
//                .name("book").category("study").detail("for students")
//                .buyPrice(BigDecimal.valueOf(10.25)).sellPrice(BigDecimal.valueOf(15.50))
//                .provider("Xinhua Store")
//                .build();
//        productRepository.save(book);

        Product makeup = Product.builder()
                .name("Tom Ford").category("makeup").detail("beauty products")
                .buyPrice(BigDecimal.valueOf(100.15)).sellPrice(BigDecimal.valueOf(225))
                .provider("Beijing SKP")
                .build();
        productRepository.save(makeup);
    }

    public void updateProduct() {
        Product makeup = findByName("Tom Ford");
        productRepository.save(Product.builder().id(makeup.getId())
                .name("Tom Ford").category("makeup").detail("beauty products")
                .buyPrice(BigDecimal.valueOf(100.15)).sellPrice(BigDecimal.valueOf(225))
                .onlineTime(makeup.getOnlineTime())
                .provider("Beijing SKP ****")
                .build());    }

    public void deleteProduct() {
        Product makeup = findByName("Tom Ford");
        productRepository.delete(makeup);
    }

    public Product findByName(String name) {
        return productRepository.findByName(name).get(0);
    }
}
