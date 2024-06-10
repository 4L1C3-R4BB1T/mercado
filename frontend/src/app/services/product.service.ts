import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable, first } from 'rxjs';
import { API_CONFIG } from '../config/api.config';
import { Product } from '../models/product';

@Injectable({
  providedIn: 'root'
})
export class ProductService {

  constructor(private http: HttpClient) { }

  findAll(): Observable<Product[]> {
    return this.http.get<Product[]>(`${API_CONFIG.baseUrl}/products`).pipe(first());
  }

}
