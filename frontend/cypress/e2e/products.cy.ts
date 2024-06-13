describe('ProductsComponent', () => {
  const baseUrl = 'http://localhost:4200/products';

  const selectors = {
    btnRemove: '[data-testId="btnRemove"]',
    btnCreate: '[data-testId="btnCreate"]',
    model: {
      name: '[data-testId="name"]  input',
      description: '[data-testId="description"] textarea',
      price: '[data-testId="price"] input',
      stock: '[data-testId="stock"] input',
      category: '[data-testId="category"]',
    }
  };

  it('The elements can be seen in the table', () => {
    cy.visit(baseUrl);
    const rows = cy.get('mat-table').find('mat-row');
    rows.should('have.length.greaterThan', 0);
    rows.find('mat-cell').each(e => {
      expect(e.html()).not.be.empty;
    });
  });

  it('It\'s possible to remove a product', () => {
    cy.visit(baseUrl);
    cy.intercept('DELETE', '**/products/**', {
      statusCode: 204,
    }).as('intercepted');
    cy.get(selectors.btnRemove).first().click();
    cy.get('button[color=primary]').click();
    cy.wait('@intercepted').then(({ response }) => {
      expect(response?.statusCode).to.be.equal(204);
    });
  });

  it('Can a new product be created', () => {
    cy.visit(`${baseUrl}/create`);
    cy.intercept('POST', '**/products', {
      statusCode: 201,
    }).as('intercepted');
    cy.get(selectors.model.name).invoke('val', 'Criado no Teste').trigger('input');
    cy.get(selectors.model.description).invoke('val', 'Criado no Teste Descrição').trigger('input');
    cy.get(selectors.model.price).invoke('val', 1500).trigger('input');
    cy.get(selectors.model.stock).invoke('val', 10).trigger('input');
    cy.get(selectors.model.category).click();
    cy.get('mat-option').first().click();
    cy.get('button').contains('Cadastrar').click();
    cy.wait('@intercepted').then(({ response }) => {
      expect(response?.statusCode).to.be.equal(201);
    });
  });

  it('Can edit a product', () => {
    cy.visit(`${baseUrl}/update/1`);
    cy.intercept('PUT', '**/products/*', {
      statusCode: 200,
    }).as('intercepted');
    cy.wait(300);
    cy.get(selectors.model.name).should('not.have.value', 'some value');
    cy.get(selectors.model.description).should('not.have.value', '');
    cy.get(selectors.model.price).should('not.have.value', '');
    cy.get(selectors.model.stock).should('not.have.value', '');
    cy.get('.mat-mdc-select-min-line').should('not.have.text', '');
    cy.get('button').contains('Atualizar').click();
    cy.wait('@intercepted').then(({ response }) => {
      expect(response?.statusCode).to.be.equal(200);
    });
  });

  it('Can\'t a new product be created with invalid states', () => {
    cy.visit(`${baseUrl}/create`);
    cy.intercept('POST', '**/products/*', {
      statusCode: 400,
    }).as('intercepted');
    cy.get(selectors.model.name).invoke('val', ' '.repeat(10)).trigger('input');
    cy.get(selectors.model.description).invoke('val', ' '.repeat(10)).trigger('input');
    cy.get(selectors.model.price).invoke('val', 0).trigger('input');
    cy.get(selectors.model.stock).invoke('val', 0).trigger('input');
    cy.get(selectors.model.category).click();
    cy.get('mat-option').first().click();
    cy.get('button').contains('Cadastrar').click();
    // a requisição ao backend não é feita pois há dados inválidos no frontend
    cy.get('@intercepted.all').should('have.length', 0);
  });

});
