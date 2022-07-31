import org.springframework.http.MediaType;
import org.springframework.http.server.reactive.ReactorHttpHandlerAdapter;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.RouterFunctions;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;
import reactor.netty.DisposableChannel;
import reactor.netty.http.server.HttpServer;

import static org.springframework.web.reactive.function.server.RequestPredicates.GET;
import static org.springframework.web.reactive.function.server.RequestPredicates.accept;

public class Application {
    public static void main(String[] args) {
        HttpServer.create()
                .port(8080)
                .handle(new ReactorHttpHandlerAdapter(RouterFunctions.toHttpHandler(Application.route())))
                .bind()
                .flatMap(DisposableChannel::onDispose)
                .block();
    }

    public static RouterFunction<ServerResponse> route() {
        return RouterFunctions
                .route(GET("/world").and(accept(MediaType.APPLICATION_JSON)), Application::helloWorld)
                .andRoute(GET("/earth").and(accept(MediaType.APPLICATION_JSON)), Application::helloEarth);
    }

    public static Mono<ServerResponse> helloWorld(ServerRequest request) {
        return ServerResponse.ok()
                .contentType(MediaType.APPLICATION_JSON)
                .body(BodyInserters.fromValue("Hello, World"));
    }

    public static Mono<ServerResponse> helloEarth(ServerRequest request) {
        return ServerResponse.ok()
                .contentType(MediaType.APPLICATION_JSON)
                .body(BodyInserters.fromValue("Hello, Earth"));
    }
}
