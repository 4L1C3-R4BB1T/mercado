describe('CategoriesComponent', () => {
  const baseUrl = 'http://localhost:4200/categories';

  const selectors = {
    btnRemove: '[data-testId="btnRemove"]',
    btnCreate: '[data-testId="btnCreate"]',
    model: {
      name: '[data-testId="name"]  input',
      description: '[data-testId="description"] textarea',
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

  it('It\'s possible to remove a category', () => {
    cy.visit(baseUrl);
    cy.intercept('DELETE', '**/categories/**', {
      statusCode: 204,
    }).as('intercepted');
    cy.get(selectors.btnRemove).first().click();
    cy.get('button[color=primary]').click();
    cy.wait('@intercepted').then(({ response }) => {
      expect(response?.statusCode).to.be.equal(204);
    });
  });

  it('Can a new category be created', () => {
    cy.visit(`${baseUrl}/create`);
    cy.intercept('POST', '**/categories', {
      statusCode: 201,
    }).as('intercepted');
    cy.get(selectors.model.name).invoke('val', 'Criado no Teste').trigger('input');
    cy.get(selectors.model.description).invoke('val', 'Criado no Teste').trigger('input');
    cy.get('button').contains('Cadastrar').click();
    cy.wait('@intercepted').then(({ response }) => {
      expect(response?.statusCode).to.be.equal(201);
    });
  });

  it('Can edit a category', () => {
    cy.visit(`${baseUrl}/update/1`);
    cy.intercept('PUT', '**/categories/*', {
      statusCode: 200,
    }).as('intercepted');
    cy.wait(300);
    cy.get(selectors.model.name).should('not.have.value', '');
    cy.get(selectors.model.description).should('not.have.value', '');
    cy.get('button').contains('Atualizar').click();
    cy.wait('@intercepted').then(({ response }) => {
      expect(response?.statusCode).to.be.equal(200);
    });
  });

  it('Can\'t a new category be created with invalid states', () => {
    cy.visit(`${baseUrl}/create`);
    cy.intercept('POST', '**/categories/*', {
      statusCode: 201,
    }).as('intercepted');
    cy.get(selectors.model.name).invoke('val', ' '.repeat(10)).trigger('input');
    cy.get(selectors.model.description).invoke('val', ' '.repeat(10)).trigger('input');
    cy.get('button').contains('Cadastrar').click();
    // a requisição ao backend não é feita pois há dados inválidos no frontend
    cy.get('@intercepted.all').should('have.length', 0);
  });

});
