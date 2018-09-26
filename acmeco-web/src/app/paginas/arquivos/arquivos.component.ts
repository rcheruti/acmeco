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
  paginaProxima: string = '';
  
  // ---------------------------------------------------------
  
  constructor(private arquivoServ: ArquivoService) {
    this.arquivoServ.listarBuckets().subscribe((dados:string)=>{
      this.bucketNome = dados;
    });
    this.buscarArquivos();
  }
  
  
  buscarArquivos(){
    this.arquivoServ.listarItens('', '', this.paginaProxima.replace('+', '%2B'), parseInt(this.pagina) ).subscribe((dados: any)=>{
      this.arquivos = dados.arquivos;
      this.paginaProxima = dados.paginaProxima ? dados.paginaProxima : '';
    });
  }
  
  alterarPaginacao(){
    this.paginaProxima = '';
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
