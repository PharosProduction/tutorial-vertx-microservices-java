package com.pharosproduction.users.api_mobile;

import com.pharosproduction.users.common.MicroserviceVerticle;
import io.vertx.core.AsyncResult;
import io.vertx.core.DeploymentOptions;
import io.vertx.core.json.JsonObject;

public class ApiMobileVerticle extends MicroserviceVerticle {

  // Constants

  private static final String ENDPOINT = "users";

  // Overrides

  @Override
  public void start() throws Exception {
    super.start();

    JsonObject configObj = config();
    deployServer(configObj);
    publishEndpoint(configObj);
  }

  // Private

  private void deployServer(JsonObject configObj) {
    String className = ServerVerticle.class.getName();
    DeploymentOptions options = new DeploymentOptions()
      .setConfig(configObj);

    vertx.deployVerticle(className, options);
  }

  private void publishEndpoint(JsonObject configObj) throws Exception {
    Config config = new Config(configObj);
    String host = config.getHttpOptions().getHost();
    int port = config.getHttpOptions().getPort();

    publishHttpEndpoint(ENDPOINT, host, port, this::onEndpointPublished);
  }

  private void onEndpointPublished(AsyncResult<Void> ar) {
    String res = ar.failed() ? ar.cause().getMessage() : "Successfully Published";
    System.out.println(ApiMobileVerticle.class.getName() + ": " + res);
  }
}
