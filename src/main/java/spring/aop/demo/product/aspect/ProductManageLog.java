package spring.aop.demo.product.aspect;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.alibaba.fastjson.JSON;

import lombok.extern.slf4j.Slf4j;
import spring.aop.demo.product.model.Product;
import spring.aop.demo.product.model.ChangedItem;
import spring.aop.demo.product.repository.ProductRepository;

@Aspect
@Component
@Slf4j
public class ProductManageLog {

    @Autowired
    private ProductRepository productRepository;

    @Pointcut("execution(* spring.aop.demo.product.repository.ProductRepository.save(..))")
    public void needAdviceSave() {

    }

    @Pointcut("execution(* spring.aop.demo.product.repository.ProductRepository.delete(..))")
    public void needAdviceDelete() {

    }

    @Around("needAdviceSave() || needAdviceDelete()")
    public Object traceActionDetails(ProceedingJoinPoint pjp) {
        Object returnObj = null;

        // before
        Product oldObj = (Product) pjp.getArgs()[0];
        Product newObj = null;
        String method = pjp.getSignature().getName();

        String actionType = "";
        if (method.equals("delete")) {
            actionType = "delete";
        } else if (method.equals("save")) {
            // for insert and delete
            if (oldObj.getId() == null ) {
                actionType = "insert";
            } else {
                actionType = "update";
            }
        }
        log.info(System.currentTimeMillis() + "############# actionTrace ############# before "
                + actionType.toUpperCase());
        try {
            returnObj = pjp.proceed(pjp.getArgs());
            newObj = (Product) returnObj;
            log.info(">>>>>>" + productRepository.findAll().toString());
            if (actionType.equals("insert") || actionType.equals("delete")) {
                log.info(">>>Data Log >>> [" + actionType +"] "
                        + JSON.toJSONString(product2ChangedItem(oldObj)));
            } else {
                // compare before and after
                log.info(">>>Data Log >>> [" + actionType +"] "
                        + diffChangeItem(oldObj, newObj));
            }
            // after
            log.info(System.currentTimeMillis() + "############# actionTrace ############# after "
                    + actionType.toUpperCase());

        } catch (Throwable e) {
            log.error(e.getMessage(), e);
        }

        return returnObj;
    }

    private String diffChangeItem(Product oldObj, Product newObj)
            throws IllegalArgumentException, IllegalAccessException {
        List<ChangedItem> before = product2ChangedItem(oldObj);
        before.sort((a,b) -> a.getChangedColumnName().compareTo(b.getChangedColumnName()));
        List<ChangedItem> after = product2ChangedItem(newObj);
        after.sort((a,b) -> a.getChangedColumnName().compareTo(b.getChangedColumnName()));
        List<ChangedItem> result = new ArrayList<ChangedItem>();
        long index = 0;
        for (int i=0; i< before.size(); ++i) {
            if (!before.get(i).getNewValue().equals(after.get(i).getNewValue())) {
                result.add(ChangedItem.builder().id(index)
                        .changedColumnName(before.get(i).getChangedColumnName())
                        .oldValue(before.get(i).getNewValue())
                        .newValue(after.get(i).getNewValue())
                        .build());
                ++index;
            }
        }
        return JSON.toJSONString(result);
    }

    private List<ChangedItem> product2ChangedItem(Product obj)
            throws IllegalArgumentException, IllegalAccessException {
        List<ChangedItem> changeItems = new ArrayList<ChangedItem>();
        long index = 0;
        for (Field field : obj.getClass().getDeclaredFields()) {
            field.setAccessible(true);
            String fieldName = field.getName();
            Object value = field.get(obj);
            String stringValue = String.valueOf(value);
            changeItems.add(ChangedItem.builder().id(index)
                    .changedColumnName(fieldName).oldValue(null).newValue(stringValue).build());
            ++index;
        }
        return changeItems;
    }
}
