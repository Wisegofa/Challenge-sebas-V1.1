package com.challenge.literalura;

import com.challenge.literalura.application.Principal;
import com.challenge.literalura.service.CatalogoService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@Slf4j
@RequiredArgsConstructor
public class LiteraluraApplication implements CommandLineRunner {

  private final CatalogoService catalogoService;

  public static void main(String[] args) {
    SpringApplication.run(LiteraluraApplication.class, args);
  }

  @Override
  public void run(String... args) {
    log.info("Iniciando Aplicación...");
    new Principal(catalogoService).execMenu();
    log.info("Finalizando Aplicación...");
  }
}
