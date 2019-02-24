package com.pharosproduction.users.rocksworker;

import io.vertx.codegen.annotations.ProxyGen;
import io.vertx.codegen.annotations.VertxGen;
import io.vertx.core.AsyncResult;
import io.vertx.core.Handler;
import io.vertx.core.json.JsonObject;

@VertxGen
@ProxyGen
public interface RocksService {

  // Constants

  String ROCKS_SERVICE_NAME = "rocksservice.users.name";
  String ROCKS_SERVICE_ADDR = "rocksservice.users.addr";

  // Methods

  void getUser(Handler<AsyncResult<JsonObject>> resultHandler);

  void createUser(String userId, Handler<AsyncResult<JsonObject>> resultHandler);
}
