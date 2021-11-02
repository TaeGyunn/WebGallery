package WebGallery.Gallery.util;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.persistence.EntityManagerFactory;
import javax.persistence.PersistenceUnitUtil;

@Configuration
public class BeanRegistry implements ApplicationContextAware {

    private static ApplicationContext applicationContext;

    public static <T> T lookup(Class<T> type) throws BeansException{
        return applicationContext.getBean(type);
    }

    @Bean
    public PersistenceUnitUtil persistenceUnitUtil(EntityManagerFactory emf){
        return emf.getPersistenceUnitUtil();
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        BeanRegistry.applicationContext = applicationContext;
    }
}
