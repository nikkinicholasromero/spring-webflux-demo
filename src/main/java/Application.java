import org.springframework.http.server.reactive.HttpHandler;
import org.springframework.http.server.reactive.ReactorHttpHandlerAdapter;
import org.springframework.web.reactive.function.server.RouterFunctions;
import reactor.netty.DisposableChannel;
import reactor.netty.http.server.HttpServer;

public class Application {
    public static void main(String[] args) {
        HttpHandler httpHandler = RouterFunctions.toHttpHandler(
                new MainRouter().route()
        );

        ReactorHttpHandlerAdapter reactorHttpHandler = new ReactorHttpHandlerAdapter(httpHandler);

        HttpServer.create()
                .port(8080)
                .handle(reactorHttpHandler)
                .bind()
                .flatMap(DisposableChannel::onDispose)
                .block();
    }
}
