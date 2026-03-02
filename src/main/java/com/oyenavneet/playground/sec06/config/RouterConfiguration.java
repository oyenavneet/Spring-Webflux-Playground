package com.oyenavneet.playground.sec06.config;

import com.oyenavneet.playground.sec06.exceptions.CustomerNotFoundException;
import com.oyenavneet.playground.sec06.exceptions.InvalidInputException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.RouterFunctions;
import org.springframework.web.reactive.function.server.ServerResponse;


/*
    Order of ROUTES must be in mind so that RouterFunctions didn't get confused
    filter will be executed first before going to ROUTES
    Multiple Web Fielders Can be achieved by method chaining i.g filter().filter()

 */
@Configuration
public class RouterConfiguration {

    @Autowired
    private CustomerRequestHandler customerRequestHandler;

    @Autowired
    private ApplicationExceptionHandler applicationExceptionHandler;

    @Bean
    public RouterFunction<ServerResponse> customerRouted() {
        return RouterFunctions.route()
//                .GET(request -> true,this.customerRequestHandler::allCustomers)

                .path("customers", this::customerRouted3) // to use nested routing and make more or organize
//                .GET("/customers", this.customerRequestHandler::allCustomers)
//                .GET("/customers/paginated", this.customerRequestHandler::getPaginatedCustomers)
//                .GET("/customers/{id}", this.customerRequestHandler::getCustomer)
                .POST("/customers", this.customerRequestHandler::saveCustomer)
//                .PUT("/customers/{id}", this.customerRequestHandler::updateCustomer)
                .DELETE("/customers/{id}", this.customerRequestHandler::deleteCustomer)
                .onError(CustomerNotFoundException.class, this.applicationExceptionHandler::handleException)
                .onError(InvalidInputException.class, this.applicationExceptionHandler::handleException)
//                .filter(((request, next) -> {
////                   return next.handle(request); // sending to next filter
//                    return ServerResponse.badRequest().build() // in case of throwing some rror
//                }))
                .build();
    }



    // we can have multiple RouterFunction to organize the APIs
//    @Bean
//    public RouterFunction<ServerResponse> customerRouted2() {
//        return RouterFunctions.route()
//                .GET("/customers", this.customerRequestHandler::allCustomers)
//                .GET("/customers/paginated", this.customerRequestHandler::getPaginatedCustomers)
//                .GET("/customers/{id}", this.customerRequestHandler::getCustomer)
//                .onError(CustomerNotFoundException.class, this.applicationExceptionHandler::handleException)
//                .onError(InvalidInputException.class, this.applicationExceptionHandler::handleException)
//                .build();
//    }


//    @Bean
    private RouterFunction<ServerResponse> customerRouted3() {
        return RouterFunctions.route()
                .GET("/paginated", this.customerRequestHandler::getPaginatedCustomers)
                .GET("/{id}", this.customerRequestHandler::getCustomer)
                .GET(this.customerRequestHandler::allCustomers) // "customers" already in parent RouterFunction
                .build();
    }
}
