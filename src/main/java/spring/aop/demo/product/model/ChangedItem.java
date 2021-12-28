package spring.aop.demo.product.model;

import javax.persistence.GeneratedValue;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ChangedItem {

    private Long id;
    private String changedColumnName;
    private String oldValue;
    private String newValue;
}
