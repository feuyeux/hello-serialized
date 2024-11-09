package org.feuyeux.jprotobuf;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.ApplicationListener;

@SpringBootApplication
@Slf4j
public class HelloApplication implements ApplicationListener<ApplicationReadyEvent> {
  @Value("${server.port}")
  private int port;

  public static void main(String[] args) {
    SpringApplication.run(HelloApplication.class, args);
  }

  @Override
  public void onApplicationEvent(ApplicationReadyEvent event) {
    log.info("Started HelloApplication on port: {}", port);
  }
}
