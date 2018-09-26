package br.com.rcc_dev.portifolio.acmeco.testes;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.util.List;

import com.amazonaws.services.s3.model.Bucket;
import com.amazonaws.services.s3.model.ListObjectsV2Result;
import com.amazonaws.services.s3.model.S3ObjectSummary;

import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import br.com.rcc_dev.portifolio.acmeco.Main;
import br.com.rcc_dev.portifolio.acmeco.controles.S3Controle;
import lombok.extern.slf4j.Slf4j;

//@Ignore
@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
@Slf4j
@ActiveProfiles("testes")
public class S3Testes {
  
  @Autowired
  private S3Controle s3Controle;
  
  @Before
  public void before(){
    
  }
  
  @Test
  public void listarItens(){
    log.info("Buscando itens no AWS S3");
    ListObjectsV2Result result = s3Controle.getItens(null, null, null, 10);
    List<S3ObjectSummary> objects = result.getObjectSummaries();
    for(S3ObjectSummary obj : objects){
      log.info("Iten name: {} ({} bytes)", obj.getKey(), obj.getSize() );
    }
    assertTrue( objects.size() > 0 );
  }
  
  @Test
  public void listarBuckets(){
    log.info("Buscando Buckets no AWS S3");
    List<Bucket> buckets = s3Controle.getBuckets();
    for(Bucket bucket : buckets){
      log.info("Bucket name: {}", bucket.getName() );
    }
    assertTrue( buckets.size() > 0 );
  }
  
  @Test
  public void downloadItem() throws FileNotFoundException {
    String arquivo = "a7.txt";
    log.info("Buscando o item '{}' no AWS S3", arquivo);
    String caminho = "C:/Users/1513 MXTI/Desktop/s3_resp/" + arquivo;
    log.info("O arquivo será gravado em: {}", caminho);
    FileOutputStream out = new FileOutputStream(caminho);
    s3Controle.downloadItem(null, arquivo, out);
  }
  
}