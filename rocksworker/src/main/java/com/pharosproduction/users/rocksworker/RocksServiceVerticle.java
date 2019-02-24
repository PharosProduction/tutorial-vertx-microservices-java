package com.pharosproduction.users.rocksworker;

import com.pharosproduction.users.common.MicroserviceVerticle;
import io.vertx.core.AsyncResult;
import io.vertx.core.Future;
import io.vertx.core.eventbus.MessageConsumer;
import io.vertx.serviceproxy.ServiceBinder;

import static com.pharosproduction.users.rocksworker.RocksService.ROCKS_SERVICE_ADDR;
import static com.pharosproduction.users.rocksworker.RocksService.ROCKS_SERVICE_NAME;

public class RocksServiceVerticle extends MicroserviceVerticle {

  // Variables

  private ServiceBinder mServiceBinder;
  private MessageConsumer mRocksServiceConsumer;

  // Overrides

  @Override
  public void start() throws Exception {
    super.start();

    RocksService service = new RocksServiceImpl();
    mServiceBinder = new ServiceBinder(vertx)
      .setAddress(ROCKS_SERVICE_ADDR);
    mRocksServiceConsumer = mServiceBinder.register(RocksService.class, service);

    publishEventBusService(ROCKS_SERVICE_NAME, ROCKS_SERVICE_ADDR, RocksService.class, this::onBusServicePublished);
  }

  @Override
  public void stop(Future<Void> stopFuture) throws Exception {
    mServiceBinder.unregister(mRocksServiceConsumer);

    super.stop(stopFuture);
  }

  // Private

  private void onBusServicePublished(AsyncResult ar) {
    String val = ar.failed() ? ar.cause().getMessage() : "RocksDB service published: " + ar.succeeded();
    System.out.println(val);
  }
}
