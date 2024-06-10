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

  findAll(): Observable<Category[]> {
    return this.http.get<Category[]>(`${API_CONFIG.baseUrl}/categories`).pipe(first());
  }

}
