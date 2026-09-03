package com.testwheel.api_gateway.config;

import lombok.Builder;
import org.springframework.cloud.gateway.server.mvc.filter.CircuitBreakerFilterFunctions;
import org.springframework.cloud.gateway.server.mvc.filter.FilterFunctions;
import org.springframework.cloud.gateway.server.mvc.filter.LoadBalancerFilterFunctions;

import org.springframework.cloud.gateway.server.mvc.handler.GatewayRouterFunctions;
import org.springframework.cloud.gateway.server.mvc.handler.HandlerFunctions;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.function.RequestPredicates;
import org.springframework.web.servlet.function.RouterFunction;
import org.springframework.web.servlet.function.RouterFunctions;
import org.springframework.web.servlet.function.ServerResponse;

import java.net.URI;
import java.util.List;

@Configuration
@Builder
public class GatewayRoutesConfig {

    @Bean
    public RouterFunction<ServerResponse> testWheelRoute() {
        return GatewayRouterFunctions.route("test-wheel")
                .route(RequestPredicates.path("/wheel/**"), HandlerFunctions.http())
                .filter(LoadBalancerFilterFunctions.lb("SPRING-SECURITY"))
                .filter(FilterFunctions.stripPrefix(1))
                .filter(CircuitBreakerFilterFunctions.circuitBreaker("wheelServiceCB", URI.create("forward:/fallback/wheel")))
                .build();
    }

    @Bean
    public RouterFunction<ServerResponse> testServiceRoute() {
        return GatewayRouterFunctions.route("test-service")
                .route(RequestPredicates.path("/test/**"), HandlerFunctions.http())
                .filter(LoadBalancerFilterFunctions.lb("TEST-SERVICE"))
                .filter(FilterFunctions.stripPrefix(1))
                .filter(CircuitBreakerFilterFunctions.circuitBreaker("testServiceCB", URI.create("forward:/fallback/test")))
                .build();
    }



    @Bean
    public RouterFunction<ServerResponse> oauth2Routes() {
        return GatewayRouterFunctions.route("oauth2-routes")
                // initial authorization request
                .route(RequestPredicates.path("/oauth2/**"), HandlerFunctions.http())
                // callback after provider login
                .route(RequestPredicates.path("/login/oauth2/**"), HandlerFunctions.http())
                .filter(LoadBalancerFilterFunctions.lb("SPRING-SECURITY"))
                .build();
    }


    private RouterFunction<ServerResponse> buildDirectRoutes(String id, List<String> paths, String serviceId) {
        RouterFunctions.Builder builder = GatewayRouterFunctions.route(id);
        for (String path : paths) {
            builder.route(RequestPredicates.path(path), HandlerFunctions.http());
        }
        return builder.filter(LoadBalancerFilterFunctions.lb(serviceId)).build();
    }

    @Bean
    public RouterFunction<ServerResponse> appDirectRoutes() {
        return buildDirectRoutes(
                "app-direct-routes",
                List.of(
                        "/dashboard", "/login", "/perform_login", "/signup",
                        "/projects", "/createproject","/saveproject", "/alltest", "/testlist",
                        "/testrequest", "/logout", "/perform_logout", "/verify-otp", "/forgot-password-phone",
                        "/reset-password", "/welcome", "/testwheel", "/createtest2","/createtest",
                        "/timeout", "/csrf-token", "/saveWizard", "/whoami",
                        "/user", "/notifications","/oauth2/**", "/login/oauth2/**"
                ),
                "SPRING-SECURITY"
        );
    }


    @Bean
    public RouterFunction<ServerResponse> staticAssetsRoute() {
        return GatewayRouterFunctions.route("static-assets")
                .route(RequestPredicates.path("/assets/**"), HandlerFunctions.http())
                .filter(LoadBalancerFilterFunctions.lb("SPRING-SECURITY"))
                .build();
    }

    @Bean
    public RouterFunction<ServerResponse> staticWheelAssetsRoute() {
        return GatewayRouterFunctions.route("static-wheel-assets")
                .route(RequestPredicates.path("/wheel/assets/**"), HandlerFunctions.http())
                .filter(LoadBalancerFilterFunctions.lb("SPRING-SECURITY"))
                .filter(FilterFunctions.stripPrefix(1))
                .build();
    }

    @Bean
    public RouterFunction<ServerResponse> staticCssRoute() {
        return GatewayRouterFunctions.route("static-css")
                .route(RequestPredicates.path("/css/**"), HandlerFunctions.http())
                .filter(LoadBalancerFilterFunctions.lb("SPRING-SECURITY"))
                .filter(FilterFunctions.rewritePath("/css/(?<segment>.*)", "/assets/css/${segment}"))
                .build();
    }

    @Bean
    public RouterFunction<ServerResponse> staticWheelCssRoute() {
        return GatewayRouterFunctions.route("static-wheel-css")
                .route(RequestPredicates.path("/wheel/css/**"), HandlerFunctions.http())
                .filter(LoadBalancerFilterFunctions.lb("SPRING-SECURITY"))
                .filter(FilterFunctions.rewritePath("/wheel/css/(?<segment>.*)", "/assets/css/${segment}"))
                .build();
    }

    @Bean
    public RouterFunction<ServerResponse> staticJsRoute() {
        return GatewayRouterFunctions.route("static-js")
                .route(RequestPredicates.path("/js/**"), HandlerFunctions.http())
                .filter(LoadBalancerFilterFunctions.lb("SPRING-SECURITY"))
                .filter(FilterFunctions.rewritePath("/js/(?<segment>.*)", "/assets/js/${segment}"))
                .build();
    }

    @Bean
    public RouterFunction<ServerResponse> staticWheelJsRoute() {
        return GatewayRouterFunctions.route("static-wheel-js")
                .route(RequestPredicates.path("/wheel/js/**"), HandlerFunctions.http())
                .filter(LoadBalancerFilterFunctions.lb("SPRING-SECURITY"))
                .filter(FilterFunctions.rewritePath("/wheel/js/(?<segment>.*)", "/assets/js/${segment}"))
                .build();
    }

    @Bean
    public RouterFunction<ServerResponse> staticImagesRoute() {
        return GatewayRouterFunctions.route("static-images")
                .route(RequestPredicates.path("/images/**"), HandlerFunctions.http())
                .filter(LoadBalancerFilterFunctions.lb("SPRING-SECURITY"))
                .filter(FilterFunctions.rewritePath("/images/(?<segment>.*)", "/assets/images/${segment}"))
                .build();
    }

    @Bean
    public RouterFunction<ServerResponse> staticWheelImagesRoute() {
        return GatewayRouterFunctions.route("static-wheel-images")
                .route(RequestPredicates.path("/wheel/images/**"), HandlerFunctions.http())
                .filter(LoadBalancerFilterFunctions.lb("SPRING-SECURITY"))
                .filter(FilterFunctions.rewritePath("/wheel/images/(?<segment>.*)", "/assets/images/${segment}"))
                .build();
    }

    @Bean
    public RouterFunction<ServerResponse> faviconRoute() {
        return GatewayRouterFunctions.route("favicon")
                .route(RequestPredicates.path("/favicon.png"), HandlerFunctions.http())
                .filter(LoadBalancerFilterFunctions.lb("SPRING-SECURITY"))
                .build();
    }
}
