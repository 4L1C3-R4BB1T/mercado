import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable, first } from 'rxjs';
import { environment } from 'src/environments/environment.development';
import { Product } from '../models/product';
import { ProductRequest } from '../models/productRequest';

@Injectable({
  providedIn: 'root'
})
export class ProductService {

  constructor(private http: HttpClient) { }

  create(product: ProductRequest): Observable<ProductRequest> {
    return this.http.post<ProductRequest>(`${environment.apiUrl}/products`, product);
  }

  findAll(): Observable<Product[]> {
    return this.http.get<Product[]>(`${environment.apiUrl}/products`).pipe(first());
  }

  findById(id: string): Observable<Product> {
    return this.http.get<Product>(`${environment.apiUrl}/products/${id}`);
  }

  update(product: ProductRequest): Observable<ProductRequest> {
    return this.http.put<ProductRequest>(`${environment.apiUrl}/products/${product.id}`, product);
  }

  delete(id: string): Observable<Product> {
    return this.http.delete<Product>(`${environment.apiUrl}/products/${id}`);
  }

}
