import { Category } from 'src/app/models/category';
import { Product } from 'src/app/models/product';
import { Component, OnInit } from '@angular/core';
import { FormControl, Validators } from '@angular/forms';
import { ProductService } from 'src/app/services/product.service';
import { ToastrService } from 'ngx-toastr';
import { ActivatedRoute, Router } from '@angular/router';
import { CategoryService } from 'src/app/services/category.service';
import { ProductRequest } from 'src/app/models/productRequest';

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
    if (!(this.name.valid && this.description.valid
      && this.price.valid && this.stock.valid && this.category.valid)) {
      this.toast.error("Preencha todos os campos!");
      return false;
    }
    return true;
  }

  transformProduct(product: Product): ProductRequest {
    console.log("product", product)
    return {
      id: product.id,
      name: product.name,
      description: product.description,
      price: product.price,
      stock: product.stock,
      categoryId: this.category.value
    };
  }

  onCategoryChange(categoryId: string): void {
    const selectedCategory = this.categories.find(category => category.id === categoryId);
    if (selectedCategory) {
      this.product.category = selectedCategory;
    }
  }

}
