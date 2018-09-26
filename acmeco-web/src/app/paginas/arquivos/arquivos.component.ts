import { Component } from '@angular/core';
import { ArquivoService } from '../../servicos/arquivo.service';

@Component({
  selector: 'app-arquivos',
  templateUrl: './arquivos.component.html',
  styleUrls: ['./arquivos.component.scss']
})
export class ArquivosComponent {
  
  bucketNome: string = '';
  
  arquivos: any[] = [];
  
  pagina: string = '10';
  paginaProxima: string = '---'; // iniciar diferente da atual
  paginaAtual: string = '';
  paginaAnterior: string[] = [];
  
  buscandoArquivos: boolean = false;
  
  // ---------------------------------------------------------
  
  constructor(private arquivoServ: ArquivoService) {
    this.arquivoServ.listarBuckets().subscribe((dados:string)=>{
      this.bucketNome = dados;
    });
    this.buscarArquivos();
  }
  
  /**
   * Buscar lista de arquivos do servidor.
   * @param indoProx Se for 0 então carregando página atual, se maior que zero então próxima página, se menor que zero então página aterior
   */
  buscarArquivos(indoProx: number = 0 ){
    if( this.buscandoArquivos ) return; // verificar bloquei para vários cliques seguidas
    let paginaToken = '';
    
    if( indoProx > 0 ){
      if( !this.paginaProxima ) return;
      paginaToken = this.paginaProxima ;
    }else if( indoProx < 0 ){
      if( !this.paginaAnterior.length ) return;
      paginaToken = this.paginaAnterior[ this.paginaAnterior.length -1 ] ;
    }else{
      paginaToken = this.paginaAtual ;
    }
    
    this.buscandoArquivos = true; // bloquei temporario para evitar vários cliques seguida
    this.arquivoServ.listarItens('', '', paginaToken, parseInt(this.pagina) ).subscribe((dados: any)=>{
      this.buscandoArquivos = false; // liberando bloqueio
      this.arquivos = dados.arquivos;
      
      if( indoProx > 0 ){
        this.paginaAnterior.push( this.paginaAtual ); 
        this.paginaAtual = this.paginaProxima ;
        this.paginaProxima = dados.paginaProxima ? dados.paginaProxima : '';
      }else if( indoProx < 0 ){
        this.paginaProxima = this.paginaAtual ;
        this.paginaAtual = this.paginaAnterior.pop() ;
      }else{
        this.paginaProxima = dados.paginaProxima ? dados.paginaProxima : '';
      }
      console.log('paginas', {
        anterior: this.paginaAnterior ,
        atual: this.paginaAtual ,
        proxima: this.paginaProxima
      });
    }, 
    (erro)=>{
      this.buscandoArquivos = false; // liberando bloqueio
    });
  }
  
  /*
  buscarArquivosProxima(){
    if( !this.paginaProxima ) return;
    this.buscarArquivos( this.paginaProxima );
  }
  buscarArquivosAnterior(){
    if( !this.paginaAnterior.length ) return;
    this.buscarArquivos( this.paginaAnterior[ this.paginaAnterior.length -1 ] );
  }
  */
  
  alterarPaginacao(){
    this.paginaProxima =    ''; // iniciar diferente da atual
    this.paginaAtual =      '';
    this.paginaAnterior =   [];
    this.buscarArquivos();
  }
  
  download( item: any ){
    this.arquivoServ.download( item.key );
  }
  
  upload(event: any){
    console.log( event );
    if( event.target.files.length ){
      this.arquivoServ.upload( event.target.files[0] ).subscribe((resposta)=>{
        this.buscarArquivos();
      });
    }
  }
  
  deletar(item: any){
    this.arquivoServ.deletar( item.key ).subscribe((resposta)=>{
      this.buscarArquivos();
    });
  }
  
}
