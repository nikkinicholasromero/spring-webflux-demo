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

import static org.springframework.web.reactive.function.server.RequestPredicates.*;

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
                .route(GET("/{greeting}")
                        .and(accept(MediaType.APPLICATION_JSON)),
                        Application::hello);
    }

    public static Mono<ServerResponse> hello(ServerRequest request) {
        return ServerResponse.ok()
                .contentType(MediaType.APPLICATION_JSON)
                .body(BodyInserters.fromValue(request.pathVariable("greeting") + ", " + request.queryParam("title").orElse("World")));
    }
}
