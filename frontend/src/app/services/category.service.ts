import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable, first } from 'rxjs';
import { environment } from 'src/environments/environment.development';
import { Category } from '../models/category';

@Injectable({
  providedIn: 'root'
})
export class CategoryService {

  constructor(private http: HttpClient) { }

  create(category: Category): Observable<Category> {
    return this.http.post<Category>(`${environment.apiUrl}/categories`, category);
  }

  findAll(): Observable<Category[]> {
    return this.http.get<Category[]>(`${environment.apiUrl}/categories`).pipe(first());
  }

  findById(id: string): Observable<Category> {
    return this.http.get<Category>(`${environment.apiUrl}/categories/${id}`);
  }

  update(category: Category): Observable<Category> {
    return this.http.put<Category>(`${environment.apiUrl}/categories/${category.id}`, category);
  }

  delete(id: string): Observable<Category> {
    return this.http.delete<Category>(`${environment.apiUrl}/categories/${id}`);
  }

}
