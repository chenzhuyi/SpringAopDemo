package spring.aop.demo.product;

import org.springframework.boot.autoconfigure.jdbc.DataSourceProperties;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;

@ConfigurationProperties
public class OracleConnectConfiguration {

    @Bean
    public DataSourceProperties oracleDataSource() {
        return new DataSourceProperties();
    }
}
