package br.com.rcc_dev.portifolio.acmeco.startup;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import lombok.extern.slf4j.Slf4j;

@Configuration
@Slf4j
public class CORS implements WebMvcConfigurer {
  
  @Override
  public void addCorsMappings(CorsRegistry registry) {
    registry.addMapping("/**").allowedOrigins("*").allowedMethods("GET", "POST","PUT", "DELETE", "PATCH");
    log.info("Servidor configurado para permitir CORS de todas as origens.");
  }
  
}