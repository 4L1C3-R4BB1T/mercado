import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable, first } from 'rxjs';
import { API_CONFIG } from '../config/api.config';
import { Category } from '../models/category';

@Injectable({
  providedIn: 'root'
})
export class CategoryService {

  constructor(private http: HttpClient) { }

  create(category: Category): Observable<Category> {
    return this.http.post<Category>(`${API_CONFIG.baseUrl}/categories`, category);
  }

  findAll(): Observable<Category[]> {
    return this.http.get<Category[]>(`${API_CONFIG.baseUrl}/categories`).pipe(first());
  }

  findById(id: string): Observable<Category> {
    return this.http.get<Category>(`${API_CONFIG.baseUrl}/categories/${id}`);
  }

  update(category: Category): Observable<Category> {
    return this.http.put<Category>(`${API_CONFIG.baseUrl}/categories/${category.id}`, category);
  }

  delete(id: string): Observable<Category> {
    return this.http.delete<Category>(`${API_CONFIG.baseUrl}/categories/${id}`);
  }

}
