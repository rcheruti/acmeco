package br.com.rcc_dev.portifolio.acmeco.controles;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.List;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import com.amazonaws.services.s3.model.Bucket;
import com.amazonaws.services.s3.model.DeleteObjectsRequest;
import com.amazonaws.services.s3.model.ListObjectsV2Request;
import com.amazonaws.services.s3.model.ListObjectsV2Result;
import com.amazonaws.services.s3.model.S3Object;
import com.amazonaws.services.s3.model.S3ObjectInputStream;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.rcc_dev.portifolio.acmeco.entidades.Config;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class S3Controle {
  
  @Autowired
  private AmazonS3 s3;
  
  @Autowired
  private Config config;
  
  // ---------------------------------------------------------
  
  public String bucketPadrao(){
    return config.getS3BucketPadrao();
  }
  
  public List<Bucket> getBuckets(){
    log.info("Buscando Buckets no AWS S3");
    List<Bucket> buckets = s3.listBuckets();
    log.info("Encontrados {} Buckets no AWS S3", buckets.size() );
    return buckets;
  }
  
  public ListObjectsV2Result getItens(String bucket, String pasta, String pagina, int qtd){
    if( bucket == null || bucket.isEmpty() ) bucket = config.getS3BucketPadrao();
    if( pasta == null || pasta.isEmpty() ) pasta = null;
    if( pagina == null || pagina.isEmpty() ) pagina = null;
    log.info("Buscando arquivos com (bucket = {}), (pasta = {}), (delimitador = {}), (pagina = {}), (quantidade = {})",
      bucket, pasta, config.getS3DelimitadorPastas(), pagina, qtd);
    
    ListObjectsV2Request request = new ListObjectsV2Request();
    request.setBucketName( bucket );
    request.setDelimiter( config.getS3DelimitadorPastas() );
    request.setPrefix( pasta );
    request.setMaxKeys( qtd );
    request.setContinuationToken( pagina );
    ListObjectsV2Result result = s3.listObjectsV2(request);
    log.info("Encontrados {} arquivos/pastas nesta busca no AWS S3", result.getObjectSummaries().size() );
    return result;
  }
  
  public void downloadItem(String bucket, String arquivo, OutputStream out){
    if( bucket == null || bucket.isEmpty() ) bucket = config.getS3BucketPadrao();
    log.info("Download do arquivo {}, no bucket {}", arquivo, bucket);
    if( arquivo == null || arquivo.isEmpty() ){
      log.warn("Uma requisição de download foi requisitada, mas não foi informado o nome do arquivo!");
      return;
    }
    S3Object obj = s3.getObject(bucket, arquivo);
    S3ObjectInputStream s3is = obj.getObjectContent();
    
    try{
      log.info("Arquivo {}, no bucket {}, possui {} bytes", arquivo, bucket, s3is.available() );
      byte[] buffer = new byte[32000];
      int lidos = 0;
      while( (lidos = s3is.read(buffer) ) > 0 ){
        out.write(buffer, 0, lidos);
      }
    }catch(IOException ex){
      log.error("Um erro ao ler o arquivo no AWS aconteceu!", ex);
    }
  }
  
  public void upload(String bucket, String arquivo, InputStream in){
    if( bucket == null || bucket.isEmpty() ) bucket = config.getS3BucketPadrao();
    log.info("Upload do arquivo {}, no bucket {}", arquivo, bucket);
    s3.putObject(bucket, arquivo, in, null);
    log.info("Upload do arquivo {}, no bucket {}, finalizado", arquivo, bucket);
  }
  
  public void excluir(String bucket, String... arquivos){
    if( bucket == null || bucket.isEmpty() ) bucket = config.getS3BucketPadrao();
    log.info("Excluindo arquivos {} do bucket {}", arquivos, bucket);
    DeleteObjectsRequest requisicao = new DeleteObjectsRequest(bucket).withKeys(arquivos);
    s3.deleteObjects(requisicao);
    log.info("Exclusão dos arquivos {}, do bucket {}, finalizada", arquivos, bucket);
  }
  
}