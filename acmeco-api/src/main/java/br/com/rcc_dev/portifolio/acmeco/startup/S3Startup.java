package br.com.rcc_dev.portifolio.acmeco.startup;

import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import br.com.rcc_dev.portifolio.acmeco.entidades.Config;
import lombok.extern.slf4j.Slf4j;

@Configuration
@Slf4j
public class S3Startup {
  
  @Autowired
  private Config config;
  
  @Bean
  public AmazonS3 configS3(){
    BasicAWSCredentials awsCreds = new BasicAWSCredentials( config.getS3AccessKeyId() , config.getS3SecretKeyId() );
    AmazonS3 s3 = AmazonS3ClientBuilder.standard()
      .withRegion(Regions.fromName(config.getS3Region()))
      .withCredentials(new AWSStaticCredentialsProvider(awsCreds))
      .build()
      ;
    return s3;
  }
  
}
