import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';

import * as moment from 'moment';
import { tap } from 'rxjs/operators';

@Injectable({
  providedIn: 'root'
})
export class ArquivoService {

  constructor(private http: HttpClient) { }
  
  // -------------------------------------
  
  listarBuckets(){
    return this.http.get('/bucket-padrao', { responseType: 'text' });
  }
  
  listarItens(bucket: string, pasta: string, pagina: string, qtd: number){
    return this.http.get('/arquivos', { params: { bucket, pasta, pagina, qtd: ''+qtd } } ).pipe(tap( (dados: any)=>{
      if( dados.arquivos ){
        for(let item of dados.arquivos){
          if( item.lastModified ){
            item.lastModified = moment( item.lastModified );
          }
        }
      }
    }));
  }
  
  upload(arquivo: any){
    let dados = new FormData();
    dados.append('arquivo', arquivo);
    return this.http.post('/arquivos', dados);
  }
  
  download(arquivo: string){
    return this.http.get('/download', {  params: { arquivo: arquivo } } );
  }
  
  deletar(...arquivos: string[]){
    return this.http.delete('/arquivos', { params: { arquivos: arquivos.join(',') } });
  }
  
}
