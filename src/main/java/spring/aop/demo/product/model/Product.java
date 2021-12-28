package spring.aop.demo.product.model;

import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "T_PRODUCT")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Product {

    @Id
    @GeneratedValue
    private Long id;
    private String name;
    private String category;
    private String detail;
    private BigDecimal buyPrice;
    private BigDecimal sellPrice;
    private String provider;
    @CreationTimestamp
    private Date onlineTime;
    @UpdateTimestamp
    private Date updateTime;
}
