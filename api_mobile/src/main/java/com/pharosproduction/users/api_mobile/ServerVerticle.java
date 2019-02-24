package com.pharosproduction.users.api_mobile;

import com.pharosproduction.users.rocksworker.RocksService;
import io.vertx.core.AbstractVerticle;
import io.vertx.core.AsyncResult;
import io.vertx.core.http.HttpServer;
import io.vertx.core.http.HttpServerOptions;
import io.vertx.core.http.HttpServerRequest;
import io.vertx.core.http.HttpServerResponse;
import io.vertx.core.json.Json;
import io.vertx.core.json.JsonObject;
import io.vertx.serviceproxy.ServiceProxyBuilder;

import static com.pharosproduction.users.rocksworker.RocksService.ROCKS_SERVICE_ADDR;

public class ServerVerticle extends AbstractVerticle {

  // Variables

  private RocksService mRocksService;

  // Overrides

  @Override
  public void start() throws Exception {
    super.start();

    createServer();

    mRocksService = new ServiceProxyBuilder(vertx)
      .setAddress(ROCKS_SERVICE_ADDR)
      .build(RocksService.class);
  }

  // Private

  private void createServer() {
    Config config = new Config(config());
    HttpServerOptions options = config.getHttpOptions();
    int port = config.getHttpOptions().getPort();

    vertx.createHttpServer(options)
      .requestHandler(this::handleRequest)
      .listen(port, this::handleListener);
  }

  private void handleListener(AsyncResult<HttpServer> ar) {
    String res = ar.succeeded() ? "Server started" : "Cannot start server: " + ar.cause();
    System.out.println(res);
  }

  private void handleRequest(HttpServerRequest request) {
    HttpServerResponse response = request.response()
      .putHeader("content-type", "application/json");

    switch (request.method()) {
      case GET: userGet(response); break;
      case POST: userCreate(request, response); break;
    }
  }

  private void userGet(HttpServerResponse response) {
    mRocksService.getUser(ar -> userGetResponse(ar, response));
  }

  private void userGetResponse(AsyncResult<JsonObject> ar, HttpServerResponse response) {
    if (ar.succeeded()) {
      JsonObject json = ar.result();

      response.setStatusCode(200)
        .end(json.toString());
    } else {
      response.setStatusCode(404)
        .end();
    }
  }

  private void userCreate(HttpServerRequest request, HttpServerResponse response) {
    String userId = request.params()
      .get("userId");
    mRocksService.createUser(userId, ar -> userCreateResponse(ar, response));
  }

  private void userCreateResponse(AsyncResult<JsonObject> ar, HttpServerResponse response) {
    if (ar.succeeded()) {
      JsonObject json = ar.result();

      response.setStatusCode(201)
        .end(json.toString());
    } else {
      response.setStatusCode(422)
        .end();
    }
  }
}
