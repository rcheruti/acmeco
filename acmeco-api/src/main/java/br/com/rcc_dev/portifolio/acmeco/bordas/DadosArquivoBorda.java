package br.com.rcc_dev.portifolio.acmeco.bordas;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.amazonaws.services.s3.model.Bucket;
import com.amazonaws.services.s3.model.ListObjectsV2Result;
import com.amazonaws.services.s3.model.S3ObjectSummary;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.method.annotation.StreamingResponseBody;

import br.com.rcc_dev.portifolio.acmeco.controles.S3Controle;
import br.com.rcc_dev.portifolio.acmeco.entidades.Config;
import lombok.extern.slf4j.Slf4j;

@RestController
@Slf4j
public class DadosArquivoBorda {
  
  @Autowired
  private S3Controle s3Controle;
  
  // --------------------------------------
  
  
  @GetMapping("/bucket-padrao")
  public String bucketPadrao(){
    return s3Controle.bucketPadrao();
  }
  
  @GetMapping("/buckets")
  public List<String> listarBuckets(){
    List<Bucket> buckets = s3Controle.getBuckets();
    List<String> bucketsNomes = new ArrayList<>();
    buckets.forEach( b -> bucketsNomes.add( b.getName() ) );
    return bucketsNomes;
  }
  
  @GetMapping("/arquivos")
  public Map<String, Object> listarItens(
    @RequestParam(value = "pasta", defaultValue = "") String pasta ,
    @RequestParam(value = "pagina", defaultValue = "") String pagina ,
    @RequestParam(value = "qtd", defaultValue = "10") int qtd 
  ){
    ListObjectsV2Result result = s3Controle.getItens(null, pasta, pagina, qtd);
    List<S3ObjectSummary> objects = result.getObjectSummaries();
    Map<String, Object> resposta = new HashMap<>();
    resposta.put("arquivos", objects);
    resposta.put("pagina", result.getContinuationToken());
    resposta.put("paginaProxima", result.getNextContinuationToken());
    return resposta;
  }
  
  @GetMapping("/download")
  public StreamingResponseBody download(
    @RequestParam(value = "arquivo", defaultValue = "") String arquivo 
  ){
    return out -> s3Controle.downloadItem(null, arquivo, out) ;
  }
  
  @PostMapping("/arquivos")
  public ResponseEntity upload(
    @RequestParam(value = "arquivo") MultipartFile arquivo 
  ){
    try{
      s3Controle.upload(null, arquivo.getOriginalFilename(), arquivo.getInputStream() );
      return ResponseEntity.ok("");
    }catch(IOException ex){
      log.error("Não foi possível obter o arquivo para upload da requisição feita ao servidor!", ex);
    }
    return ResponseEntity.badRequest().build();
  }
  
  @DeleteMapping("/arquivos")
  public ResponseEntity excluir(
    @RequestParam(value = "arquivos") String[] arquivos
  ){
    s3Controle.excluir(null, arquivos);
    return ResponseEntity.ok("");
  }
  
}