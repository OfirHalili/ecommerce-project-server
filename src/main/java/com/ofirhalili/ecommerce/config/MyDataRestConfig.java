package com.ofirhalili.ecommerce.config;


import com.ofirhalili.ecommerce.entity.Product;
import com.ofirhalili.ecommerce.entity.ProductCategory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.rest.core.config.RepositoryRestConfiguration;
import org.springframework.data.rest.webmvc.config.RepositoryRestConfigurer;
import org.springframework.http.HttpMethod;

import javax.persistence.EntityManager;
import javax.persistence.metamodel.EntityType;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Configuration
public class MyDataRestConfig implements RepositoryRestConfigurer {
    private EntityManager entityManager;
    @Autowired
    public MyDataRestConfig(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    @Override
    public void configureRepositoryRestConfiguration(RepositoryRestConfiguration config){
        HttpMethod[] theUnsupportedActions =  {HttpMethod.DELETE, HttpMethod.POST, HttpMethod.PUT};
        //disable http methods for product: put, post and delete
        config.getExposureConfiguration().forDomainType(Product.class)
                .withItemExposure(((metdata, httpMethods) -> httpMethods.disable(theUnsupportedActions)))
                .withCollectionExposure(((metdata, httpMethods) -> httpMethods.disable(theUnsupportedActions)));
        //disable http methods for product category: put, post and delete
        config.getExposureConfiguration().forDomainType(ProductCategory.class)
                .withItemExposure(((metdata, httpMethods) -> httpMethods.disable(theUnsupportedActions)))
                .withCollectionExposure(((metdata, httpMethods) -> httpMethods.disable(theUnsupportedActions)));
        //call an internal helper method
        exposeIds(config);
    }

    private void exposeIds(RepositoryRestConfiguration config) {
        // - get a list of all entity classes from the entity manager
        Set<EntityType<?>> entityTypes = entityManager.getMetamodel().getEntities();
        List<Class> entityClasses = new ArrayList<>();
        // - get the entity types for the entities
        entityTypes.forEach(entityType -> entityClasses.add(entityType.getJavaType()));
        //expose the entity ids for the array of entity/domain types
        Class[] domainTypes = entityClasses.toArray(new Class[0]);
        config.exposeIdsFor(domainTypes);

    }


}
