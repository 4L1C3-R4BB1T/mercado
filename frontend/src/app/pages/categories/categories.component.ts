import { Component, OnInit, ViewChild } from '@angular/core';
import { MatPaginator } from '@angular/material/paginator';
import { MatTableDataSource } from '@angular/material/table';
import { NavigationEnd, Router } from '@angular/router';
import { ToastrService } from 'ngx-toastr';
import { Subscription } from 'rxjs';
import { Category } from 'src/app/models/category';
import { CategoryService } from 'src/app/services/category.service';

@Component({
  selector: 'app-categories',
  templateUrl: './categories.component.html',
  styleUrls: ['./categories.component.scss']
})
export class CategoriesComponent implements OnInit {

  private routerSubscription: Subscription | undefined;

  displayedColumns: string[] = ['id', 'name', 'description', 'actions'];
  dataSource = new MatTableDataSource<Category>();

  @ViewChild(MatPaginator) paginator!: MatPaginator;

  constructor(
    private service: CategoryService,
    private toast: ToastrService,
    private router: Router
  ) { }

  ngOnInit(): void {
    this.findAll();
    this.routerSubscription = this.router.events.subscribe(event => {
      if (event instanceof NavigationEnd && this.router.url === '/categories') {
        this.findAll();
      }
    });
  }

  ngOnDestroy(): void {
    if (this.routerSubscription) {
      this.routerSubscription.unsubscribe();
    }
  }

  findAll() {
    this.service.findAll().subscribe(response => {
      this.dataSource.data = response;
      this.dataSource.paginator = this.paginator;
    });
  }

  delete(id: string): void {
    this.service.delete(id).subscribe(() => {
      this.findAll();
      this.toast.success('Categoria deletada com sucesso!', 'Delete');
      this.router.navigate(['categories']);
    }, ex => {
      if (ex.error.errors) {
        ex.error.errors.forEach((element: { message: string | undefined; }) => {
          this.toast.error(element.message);
        });
      } else {
        this.toast.error(ex.error.message);
      }
    })
  }

}
