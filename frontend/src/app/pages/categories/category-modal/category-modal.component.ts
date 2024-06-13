import { Component, OnInit } from '@angular/core';
import { FormControl, FormGroup, Validators } from '@angular/forms';
import { ActivatedRoute, Router } from '@angular/router';
import { ToastrService } from 'ngx-toastr';
import { Category } from 'src/app/models/category';
import { CategoryService } from 'src/app/services/category.service';

@Component({
  selector: 'app-category-modal',
  templateUrl: './category-modal.component.html',
  styleUrls: ['./category-modal.component.sass']
})
export class CategoryModalComponent implements OnInit {

  form = new FormGroup({
    id: new FormControl(''),
    name: new FormControl('', [Validators.required, Validators.pattern(/^(?!\s*$).+/)]),
    description: new FormControl('', [Validators.required, Validators.pattern(/^(?!\s*$).+/)]),
  })

  categoryRecovered!: Category;

  option = "Cadastrar";

  constructor(
    private service: CategoryService,
    private toast: ToastrService,
    private router: Router,
    private route: ActivatedRoute
  ) { }

  ngOnInit(): void {
    const id = this.route.snapshot.paramMap.get('id');

    if (id) {
      this.option = "Atualizar";
      this.findById(id);
    }
  }

  findById(id: string): void {
    this.service.findById(id).subscribe(response => {
      this.form.patchValue(response);
      this.categoryRecovered = { ...response };
    });
  }

  hasSameValue(obj1: any, obj2: any) {
    let count = 0;
    for (const key in obj1) {
      if (obj1[key] && obj2[key] && obj1[key] === obj2[key]) {
        count++;
      }
    }
    return count === Object.keys(obj1).length;
  }

  create(): void {
    if (this.form.invalid) return;
    this.service.create(this.form.value as Category).subscribe(() => {
      this.toast.success("Categoria cadastrada com sucesso!", "Cadastro");
      this.router.navigate(["categories"]);
    }, (ex) => {
      if (ex.error.errors) {
        ex.error.errors.forEach((element: any) => {
          this.toast.error(element);
        });
      } else {
        this.toast.error(ex.error);
      }
    });
  }

  update(): void {
    if (this.form.invalid) return;
    if (this.hasSameValue(this.categoryRecovered, this.form.value)) {
      this.router.navigate(["categories"]);
      return;
    }
    this.service.update(this.form.value as Category).subscribe(() => {
      this.toast.success('Categoria atualizada com sucesso!', 'Atualização');
      this.router.navigate(['categories']);
    }, ex => {
      if (ex.error.errors) {
        ex.error.errors.forEach((element: any) => {
          this.toast.error(element);
        });
      } else {
        this.toast.error(ex.error);
      }
    });
  }

}
