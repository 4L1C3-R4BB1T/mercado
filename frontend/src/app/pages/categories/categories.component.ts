import { Component, OnInit, ViewChild } from '@angular/core';
import { MatPaginator } from '@angular/material/paginator';
import { MatTableDataSource } from '@angular/material/table';
import { Category } from 'src/app/models/category';
import { CategoryService } from 'src/app/services/category.service';

@Component({
  selector: 'app-categories',
  templateUrl: './categories.component.html',
  styleUrls: ['./categories.component.scss']
})
export class CategoriesComponent implements OnInit {

  displayedColumns: string[] = ['id', 'name', 'description'];
  dataSource = new MatTableDataSource<Category>();

  @ViewChild(MatPaginator) paginator!: MatPaginator;

  constructor(private service: CategoryService) { }

  ngOnInit(): void {
    this.findAll();
  }

  findAll() {
    this.service.findAll().subscribe(response => {
      this.dataSource.data = response;
      this.dataSource.paginator = this.paginator;
    });
  }

}
