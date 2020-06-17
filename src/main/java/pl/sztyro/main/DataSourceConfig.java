package pl.sztyro.main;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.JdbcTemplate;

import javax.sql.DataSource;

@Configuration
public class DataSourceConfig {
    @Bean(name = "dsCustom")
    public DataSource dataSource() {
        return DataSourceBuilder
                .create()
                .username("root")
                .password("123456")
                .url("jdbc:mysql://localhost:3306/test?useSSL=false&useUnicode=true&characterEncoding=UTF-8")
                .driverClassName("com.mysql.cj.jdbc.Driver")
                .build();

        /*.create()
                .username("rfamro")
                .password("")
                .url("jdbc:mysql://mysql-rfam-public.ebi.ac.uk:4497/Rfam")
                .driverClassName("com.mysql.cj.jdbc.Driver")
                .build();*/
    }

    @Bean(name = "jdbcCustom")
    @Autowired
    public JdbcTemplate jdbcTemplate(@Qualifier("dsCustom") DataSource dsCustom) {
        return new JdbcTemplate(dsCustom);
    }
}