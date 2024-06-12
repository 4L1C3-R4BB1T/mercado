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

  create(product: Product): Observable<Product> {
    return this.http.post<Product>(`${API_CONFIG.baseUrl}/products`, product);
  }

  findAll(): Observable<Product[]> {
    return this.http.get<Product[]>(`${API_CONFIG.baseUrl}/products`).pipe(first());
  }

  findById(id: number): Observable<Product> {
    return this.http.get<Product>(`${API_CONFIG.baseUrl}/products/${id}`);
  }

  update(product: Product): Observable<Product> {
    return this.http.put<Product>(`${API_CONFIG.baseUrl}/products/${product.id}`, product);
  }

  delete(id: number): Observable<Product> {
    return this.http.delete<Product>(`${API_CONFIG.baseUrl}/products/${id}`);
  }

}
