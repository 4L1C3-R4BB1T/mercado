import { Component } from '@angular/core';
import { FormControl, Validators } from '@angular/forms';
import { ActivatedRoute, Router } from '@angular/router';
import { ToastrService } from 'ngx-toastr';
import { Category } from 'src/app/models/category';
import { CategoryService } from 'src/app/services/category.service';

@Component({
  selector: 'app-category-modal',
  templateUrl: './category-modal.component.html',
  styleUrls: ['./category-modal.component.sass']
})
export class CategoryModalComponent {

  category: Category = { id: "", name: "", description: "" };

  id: FormControl = new FormControl(null);
  name: FormControl = new FormControl(null, Validators.required);
  description: FormControl = new FormControl(null, Validators.required);

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
      this.id.setValue(this.category.id);
      this.name.setValue(this.category.name);
      this.description.setValue(this.category.description);
    }
  }

  create(): void {
    if (!this.validate()) return;
    this.service.create(this.category).subscribe(() => {
      this.toast.success("Categoria cadastrada com sucesso!", "Cadastro");
      this.router.navigate(["categories"]);
    }, (ex) => {
      if (ex.error.errors) {
        ex.error.errors.forEach((element: { message: string | undefined; }) => {
          this.toast.error(element.message)
        });
      } else {
        this.toast.error(ex.error.message);
      }
    });
  }

  findById(id: string): void {
    this.service.findById(id).subscribe(response => {
      this.category = response;
    })
  }

  update(): void {
    this.service.update(this.category).subscribe(() => {
      this.toast.success('Categoria atualizada com sucesso!', 'Atualização');
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

  validate(): boolean {
    if (!(this.name.valid && this.description.valid)) {
      this.toast.error("Preencha todos os campos!");
      return false;
    }
    return true;
  }

}
