package br.com.rcc_dev.portifolio.acmeco.entidades;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import lombok.Data;

@Data
@Component
@ConfigurationProperties(prefix = "config")
public class Config {
  
  private String s3Region = "";
  private String s3AccessKeyId = "";
  private String s3SecretKeyId = "";
  private String s3BucketPadrao = "";
  private String s3DelimitadorPastas = "";
  
}