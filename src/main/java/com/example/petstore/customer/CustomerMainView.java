package com.example.petstore.customer;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.value.ValueChangeMode;
import com.vaadin.flow.router.Route;

@Route
public class CustomerMainView extends VerticalLayout {
    private final CustomerRepository customerRepository;
    private final TextField textField;
    private final Grid<Customer> grid;
    private final Button newButton;
    private final CustomerEditView customerEditView;

    public CustomerMainView(CustomerRepository customerRepository, CustomerEditView customerEditView) {
        this.customerRepository = customerRepository;
        this.customerEditView = customerEditView;

        this.textField = new TextField("Customer Name: ");
        this.textField.setValueChangeMode(ValueChangeMode.EAGER);
        this.textField.addValueChangeListener(e -> this.refreshGrid(e.getValue()));

        this.grid = new Grid<>(Customer.class);
        this.grid.setColumns("name", "birthDate", "gender");
        this.grid.asSingleSelect().addValueChangeListener(e -> customerEditView.setCustomer(e.getValue()));
        refreshGrid(null);

        this.newButton = new Button("New Customer");
        this.newButton.addClickListener(e -> customerEditView.setCustomer(new Customer()));

        this.customerEditView.setCustomerEditViewHandler(() -> {
            refreshGrid(this.textField.getValue());
            this.customerEditView.setVisible(false);
        });

        this.add(this.textField);
        this.add(this.grid);
        this.add(this.newButton);
        this.add(customerEditView);
    }

    private void refreshGrid(String name) {
        this.grid.setItems(
                name != null && !"".equals(name.trim()) ?
                        this.customerRepository.findByNameContainsIgnoreCase(name)
                        :
                        this.customerRepository.findAll()
        );
    }

}
