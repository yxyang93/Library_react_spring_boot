package top.yuxiangyang.springbootlibrary.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.data.rest.core.config.RepositoryRestConfiguration;
import org.springframework.data.rest.webmvc.config.RepositoryRestConfigurer;
import org.springframework.http.HttpMethod;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import top.yuxiangyang.springbootlibrary.dto.HistoryDTO;
import top.yuxiangyang.springbootlibrary.entity.Book;
import top.yuxiangyang.springbootlibrary.entity.History;
import top.yuxiangyang.springbootlibrary.entity.Message;
import top.yuxiangyang.springbootlibrary.entity.Review;

@Configuration
public class MyDataRestConfig implements RepositoryRestConfigurer {

    // Request from the frontend
    private String theAllowedOrigins = "https://localhost:3000";

    public void configureRepositoryRestConfiguration(RepositoryRestConfiguration config,
                                                     CorsRegistry cors) {
        HttpMethod[] theUnsupportedActions = {
                HttpMethod.PUT,
                HttpMethod.POST,
                HttpMethod.DELETE,
                HttpMethod.PATCH};

        // Already exposed in RestConfiguration.java
//        config.exposeIdsFor(Book.class);
//        config.exposeIdsFor(Review.class);
//        config.exposeIdsFor(History.class);
//        config.exposeIdsFor(HistoryDTO.class);
//        config.exposeIdsFor(Message.class);

        // Disable HTTP methods for Product: PUT, POST, DELETE and PATCH
        disableHttpMethods(Book.class, config, theUnsupportedActions);
        disableHttpMethods(Review.class, config, theUnsupportedActions);
        disableHttpMethods(Message.class, config, theUnsupportedActions);

        /* Configure CORS mapping */
        cors.addMapping(config.getBasePath() + "/**").allowedOrigins(theAllowedOrigins);
    }

    private void disableHttpMethods(Class theClass,
                                    RepositoryRestConfiguration config,
                                    HttpMethod[] theUnsupportedActions) {
        config.getExposureConfiguration()
                .forDomainType(theClass)
                .withItemExposure((metadata, httpMethods) -> httpMethods.disable(theUnsupportedActions))
                .withCollectionExposure((metadata, httpMethods) -> httpMethods.disable(theUnsupportedActions));
    }
}
