package com.example.petstore.sale;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.value.ValueChangeMode;
import com.vaadin.flow.router.Route;

@Route
public class SaleMainView extends VerticalLayout {
    private final SaleRepository saleRepository;
    private final TextField textField;
    private final Grid<Sale> grid;
    private final Button newButton;
    private final SaleEditView saleEditView;

    public SaleMainView(SaleRepository saleRepository, SaleEditView saleEditview){
        this.saleRepository = saleRepository;
        this.saleEditView = saleEditview;

        this.textField = new TextField("Customer Name: ");
        this.textField.setValueChangeMode(ValueChangeMode.EAGER);
        this.textField.addValueChangeListener(e -> this.refreshGrid(e.getValue()));

        this.grid = new Grid<>(Sale.class);
        this.grid.setColumns("saleDate", "customer.name", "pet.name");
        this.grid.asSingleSelect().addValueChangeListener(e -> saleEditView.setSale(e.getValue()));
        this.refreshGrid(null);

        this.newButton = new Button("New Sale", VaadinIcon.PLUS.create());
        this.newButton.addClickListener(e -> this.saleEditView.setSale(new Sale()));

        this.saleEditView.setSaleEditViewHandler(() -> {
            this.refreshGrid(this.textField.getValue());
            this.saleEditView.setVisible(false);
            this.saleEditView.setSale(null);
        });

        this.add(this.textField);
        this.add(this.grid);
        this.add(this.newButton);
        this.add(this.saleEditView);
    }

    private void refreshGrid(String filter){
        this.grid.setItems(
                filter != null && !"".equals(filter.trim())?
                        saleRepository.findByCustomer_NameContainsIgnoreCase(filter)
                        :
                        saleRepository.findAll()
        );
    }

}
