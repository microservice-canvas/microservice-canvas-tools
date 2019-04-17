package io.microservices.canvas.extractor;

import io.microservices.canvas.model.Service;

public class ServiceModelHolder {
  private Service service;

  public void setService(Service service) {
    this.service = service;
  }

  public Service getService() {
    return service;
  }
}
