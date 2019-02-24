package com.pharosproduction.users.rocksworker;

import io.vertx.core.AsyncResult;
import io.vertx.core.Future;
import io.vertx.core.Handler;
import io.vertx.core.json.JsonObject;

public class RocksServiceImpl implements RocksService {

  // Overrides

  @Override
  public void getUser(Handler<AsyncResult<JsonObject>> handler) {
    JsonObject resp = new JsonObject()
      .put("firstName", "John")
      .put("lastName", "Doe");
    handler.handle(Future.succeededFuture(resp));
  }

  @Override
  public void createUser(String userId, Handler<AsyncResult<JsonObject>> handler) {
    JsonObject resp = new JsonObject()
      .put("id", "skdjfas89-asdfdsf-asdf-asd-fad-sfasdfdsfas");
    handler.handle(Future.succeededFuture(resp));
  }
}

