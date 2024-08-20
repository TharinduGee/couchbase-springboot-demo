package me.tharindu.couchbase_demo_project.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.couchbase.core.mapping.event.ValidatingCouchbaseEventListener;
import org.springframework.validation.beanvalidation.LocalValidatorFactoryBean;

@Configuration
public class ValidationConfig {

    @Bean
    public LocalValidatorFactoryBean validator() {
        return new LocalValidatorFactoryBean();
    }

    //Constraint validation exception thrown in db level of the couchbase
    @Bean
    public ValidatingCouchbaseEventListener validatingCouchbaseEventListener(){
        return new ValidatingCouchbaseEventListener(validator());
    }

}
