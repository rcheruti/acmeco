import { BrowserModule } from '@angular/platform-browser';
import { HttpClientModule, HTTP_INTERCEPTORS } from '@angular/common/http';
import { NgModule } from '@angular/core';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import { FormsModule } from '@angular/forms';
import { FlexLayoutModule } from '@angular/flex-layout';
import { RouterModule } from '@angular/router';

import { AppComponent } from './app.component';

import { rotas } from './rotas';
import { AppMaterialsModule } from './modules/app.materials.module';
import { ArquivoService } from './servicos/arquivo.service';
import { ArquivosComponent } from './paginas/arquivos/arquivos.component';
import { HostInter } from './interceptadores/host-inter';

@NgModule({
  declarations: [
    AppComponent,
    ArquivosComponent
  ],
  imports: [
    BrowserModule ,
    HttpClientModule ,
    FormsModule , 
    BrowserAnimationsModule ,
    FlexLayoutModule ,
    AppMaterialsModule ,
    RouterModule.forRoot(rotas) ,
  ],
  providers: [
    ArquivoService ,
    { provide: HTTP_INTERCEPTORS, useClass: HostInter, multi: true }
  ],
  bootstrap: [AppComponent]
})
export class AppModule { }
