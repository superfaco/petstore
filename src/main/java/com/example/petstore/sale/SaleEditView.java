package com.example.petstore.sale;

import com.example.petstore.customer.Customer;
import com.example.petstore.customer.CustomerRepository;
import com.example.petstore.pet.Pet;
import com.example.petstore.pet.PetRepository;
import com.example.petstore.util.ConfirmDialog;
import com.vaadin.flow.component.KeyNotifier;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.datetimepicker.DateTimePicker;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.select.Select;
import com.vaadin.flow.data.binder.Binder;
import com.vaadin.flow.spring.annotation.SpringComponent;
import com.vaadin.flow.spring.annotation.UIScope;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

@SpringComponent
@UIScope
public class SaleEditView extends VerticalLayout implements KeyNotifier {

    private final SaleRepository saleRepository;
    private final CustomerRepository customerRepository;
    private final PetRepository petRepository;
    private Sale sale;
    private final Button saveButton;
    private final Button deleteButton;
    private final Button cancelButton;
    private final HorizontalLayout buttonsHorizontalLayout;
    private SaleEditViewHandler saleEditViewHandler;
    private final DateTimePicker saleDate;
    private final Select<Customer> customer;
    private final Select<Pet> pet;
    private final ConfirmDialog confirmDialog;
    private final Binder<Sale> binder;

    @Autowired
    public SaleEditView(SaleRepository saleRepository, CustomerRepository customerRepository, PetRepository petRepository){
        this.saleRepository = saleRepository;
        this.customerRepository = customerRepository;
        this.petRepository = petRepository;

        this.saveButton = new Button("Save", VaadinIcon.CHECK.create());
        this.deleteButton = new Button("Delete", VaadinIcon.TRASH.create());
        this.cancelButton = new Button("Cancel");

        this.saveButton.getElement().getThemeList().add("primary");
        this.deleteButton.getElement().getThemeList().add("error");
        this.cancelButton.getElement().getThemeList().add("warning");

        this.saveButton.addClickListener(e -> save());
        this.deleteButton.addClickListener(e -> delete());
        this.cancelButton.addClickListener(e -> cancel());

        this.buttonsHorizontalLayout = new HorizontalLayout(this.saveButton, this.deleteButton, this.cancelButton);

        this.saleDate = new DateTimePicker("Sale Date: ");
        this.customer = new Select<>();
        this.pet = new Select<>();

        this.customer.setLabel("Customer: ");
        this.pet.setLabel("Pet: ");



        this.customer.setItemLabelGenerator(Customer::getName);
        this.pet.setItemLabelGenerator(Pet::getName);

        this.customer.setItems(this.customerRepository.findAll());
        this.pet.setItems(this.petRepository.findAll());

        this.binder = new Binder<>(Sale.class);
        this.binder.bindInstanceFields(this);

        this.confirmDialog = new ConfirmDialog("Are you sure you want to delete the item?", e -> {
            this.saleRepository.delete(this.sale);
            this.saleEditViewHandler.onChange();
        });

        this.add(this.saleDate);
        this.add(this.customer);
        this.add(this.pet);
        this.add(this.buttonsHorizontalLayout);
    }

    private void save(){
        this.saleRepository.save(sale);
        this.saleEditViewHandler.onChange();
    }

    private void delete(){
        this.confirmDialog.open();
    }

    private void cancel(){
        this.saleEditViewHandler.onChange();
    }

    public void setSale(Sale sale){
        if(sale != null){
            if(sale.getId() != null){
                this.sale = saleRepository.findById(sale.getId()).get();
            }else{
                this.sale = sale;
            }
            this.binder.setBean(this.sale);
            this.saleDate.focus();
            this.setVisible(true);
        }else{
            this.sale = null;
        }
    }

    public void setSaleEditViewHandler(SaleEditViewHandler saleEditViewHandler){
        this.saleEditViewHandler = saleEditViewHandler;
    }

    public interface SaleEditViewHandler{
        void onChange();
    }
}
