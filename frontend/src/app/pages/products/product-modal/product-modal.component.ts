import { Component, OnInit } from '@angular/core';
import { FormControl, Validators } from '@angular/forms';
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

  product: Product = {
    id: "",
    name: "",
    description: "",
    price: 0,
    stock: 0,
    category: {
      id: "",
      name: "",
      description: ""
    }
  };

  id: FormControl = new FormControl(null);
  name: FormControl = new FormControl(null, Validators.required);
  description: FormControl = new FormControl(null, Validators.required);
  price: FormControl = new FormControl(null, [Validators.required, Validators.min(0)]);
  stock: FormControl = new FormControl(null, [Validators.required, Validators.min(0)]);
  category: FormControl = new FormControl(null, Validators.required);

  option = "Cadastrar";

  categories: Category[] = [];

  selected: string = '';

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
    }
  }

  findAllCategories(): void {
    this.categoryService.findAll().subscribe(response => {
      this.categories = response;
    });
  }

  findById(id: string): void {
    this.service.findById(id).subscribe(response => {
      this.product = response;
      this.selected = this.product.category.id;
    });
  }

  create(): void {
    const productRequest = this.transformProduct(this.product);
    if (!this.validate()) return;
    this.service.create(productRequest).subscribe(() => {
      this.toast.success("Produto cadastrado com sucesso!", "Cadastro");
      this.router.navigate(["products"]);
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

  update(): void {
    const productRequest = this.transformProduct(this.product);
    if (!this.validate()) return;
    this.service.update(productRequest).subscribe(() => {
      this.toast.success('Produto atualizado com sucesso!', 'Atualização');
      this.router.navigate(['products']);
    }, ex => {
      if (ex.error.errors) {
        ex.error.errors.forEach((element: { message: string | undefined; }) => {
          this.toast.error(element.message);
        });
      } else {
        this.toast.error(ex.error.message);
      }
    });
  }

  validate(): boolean {
    if (this.name.valid && this.description.valid
      && this.price.valid && this.stock.valid && this.category.valid) {
      return true;
    }
    if (!this.name.valid) this.toast.error("Nome inválido!")
    if (!this.description.valid) this.toast.error("Descrição inválida!")
    if (!this.price.valid) this.toast.error("Preço inválido!");
    if (!this.stock.valid) this.toast.error("Estoque inválido!");
    if (!this.category.valid) this.toast.error("Categoria inválida!");
    return false;
  }

  transformProduct(product: Product): ProductRequest {
    return {
      id: product.id,
      name: product.name,
      description: product.description,
      price: product.price,
      stock: product.stock,
      categoryId: this.category.value
    };
  }

}
