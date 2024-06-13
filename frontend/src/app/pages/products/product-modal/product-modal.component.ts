import { Component, OnInit } from '@angular/core';
import { FormControl, FormGroup, Validators } from '@angular/forms';
import { ActivatedRoute, Router } from '@angular/router';
import { ToastrService } from 'ngx-toastr';
import { Category } from 'src/app/models/category';
import { Product } from 'src/app/models/product';
import { ProductRequest } from 'src/app/models/productRequest';
import { CategoryService } from 'src/app/services/category.service';
import { ProductService } from 'src/app/services/product.service';

@Component({
  selector: 'app-product-modal',
  templateUrl: './product-modal.component.html',
  styleUrls: ['./product-modal.component.sass']
})
export class ProductModalComponent implements OnInit {

  form = new FormGroup({
    id: new FormControl(''),
    name: new FormControl('', [Validators.required, Validators.pattern(/^(?!\s*$).+/)]),
    description: new FormControl('', [Validators.required, Validators.pattern(/^(?!\s*$).+/)]),
    price: new FormControl(0, [Validators.required, Validators.min(0)]),
    stock: new FormControl(0, [Validators.required, Validators.min(0)]),
    category: new FormControl({} as Category, Validators.required),
  })

  option = "Cadastrar";

  categories: Category[] = [];

  stopSelection = false;

  productRecovered!: Product;

  constructor(
    private service: ProductService,
    private categoryService: CategoryService,
    private toast: ToastrService,
    private router: Router,
    private route: ActivatedRoute
  ) { }

  ngOnInit(): void {
    const id = this.route.snapshot.paramMap.get('id');

    this.findAllCategories();

    if (id) {
      this.option = "Atualizar";
      this.findById(id);
      this.stopSelection = true;
    }
  }

  getMessageError(control: FormControl) {
    if (control.hasError('required') || control.hasError('pattern')) {
      return 'O campo é obrigatório';
    }
    if (control.hasError('min')) {
      return 'O campo não pode ser menor que zero';
    }
    return '';
  }

  findAllCategories(): void {
    this.categoryService.findAll().subscribe(response => {
      this.categories = response;
    });
  }

  findById(id: string): void {
    this.service.findById(id).subscribe(response => {
      this.form.patchValue(response);
      this.productRecovered = { ...response };
    });
  }

  selectCategory(category: Category) {
    if (this.stopSelection) {
      this.stopSelection = false;
      return;
    }
    this.form.controls.category.patchValue(category);
  }

  compareWithId(c1: Category, c2: Category) {
    return c1.id === c2.id;
  }

  create(): void {
    if (this.form.invalid) return;
    const payload = { ...this.form.value, categoryId: this.form.value.category!.id };
    this.service.create(payload as ProductRequest).subscribe(() => {
      this.toast.success("Produto cadastrado com sucesso!", "Cadastro");
      this.router.navigate(["products"]);
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
    const payload = { ...this.form.value, categoryId: this.form.value.category!.id };
    this.service.update(payload as ProductRequest).subscribe(() => {
      this.toast.success('Produto atualizado com sucesso!', 'Atualização');
      this.router.navigate(['products']);
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
