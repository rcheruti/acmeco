import { HttpInterceptor, HttpRequest, HttpHandler } from "@angular/common/http";
import { Observable } from "rxjs";
import { Injectable } from "@angular/core";

/**
 * Interceptador para alterar a URL do servidor de APIs, caso seja necessário servir os dois projetos em
 * servidores direfentes (CORS).
 */
@Injectable()
export class HostInter implements HttpInterceptor {
  
  
  
  intercept(req: HttpRequest<any>, next: HttpHandler): Observable<any> {
    let request = req.clone({ url: ('' + req.url).replace(/\/+/g,'/') });
    return next.handle(request);
  }
  
}
