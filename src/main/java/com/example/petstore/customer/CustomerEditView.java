package com.example.petstore.customer;

import com.example.petstore.util.ConfirmDialog;
import com.vaadin.flow.component.Key;
import com.vaadin.flow.component.KeyNotifier;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.datepicker.DatePicker;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.select.Select;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.binder.Binder;
import com.vaadin.flow.spring.annotation.SpringComponent;
import com.vaadin.flow.spring.annotation.UIScope;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.LocalDate;

@SpringComponent
@UIScope
public class CustomerEditView extends VerticalLayout implements KeyNotifier {
    private final CustomerRepository customerRepository;
    private Customer customer;
    private final Button saveButton;
    private final Button deleteButton;
    private final Button cancelButton;
    private final HorizontalLayout buttonsHorizontalLayout;
    private final Binder<Customer> binder;
    private final ConfirmDialog confirmDialog;
    private CustomerEditViewHandler customerEditViewHandler;
    private final TextField name;
    private final DatePicker birthDate;
    private final Select<String> gender;

    @Autowired
    public CustomerEditView(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;

        this.saveButton = new Button("Save", VaadinIcon.CHECK.create());
        this.deleteButton = new Button("Delete", VaadinIcon.TRASH.create());
        this.cancelButton = new Button("Cancel");

        this.saveButton.getElement().getThemeList().add("primary");
        this.deleteButton.getElement().getThemeList().add("error");
        this.cancelButton.getElement().getThemeList().add("warning");

        this.saveButton.addClickListener(e -> save());
        this.deleteButton.addClickListener(e -> delete());
        this.cancelButton.addClickListener(e -> cancel());

        this.buttonsHorizontalLayout = new HorizontalLayout(saveButton, deleteButton, cancelButton);

        this.name = new TextField("Name: ");
        this.birthDate = new DatePicker("Birth Date: ", LocalDate.now());
        this.gender = new Select<>("Male", "Female");

        this.binder = new Binder<>(Customer.class);
        this.binder.bindInstanceFields(this);

        this.confirmDialog = new ConfirmDialog("Are you sure you want to delete the item?", e -> {
            this.customerRepository.delete(customer);
            this.customerEditViewHandler.onChange();
            this.customer = null;
        });

        this.addKeyPressListener(Key.ENTER, e -> save());

        this.add(name);
        this.add(birthDate);
        this.add(gender);
        this.add(buttonsHorizontalLayout);
        this.setVisible(false);
    }

    private void save() {
        this.customerRepository.save(this.customer);
        this.customerEditViewHandler.onChange();
        this.customer = null;
    }

    private void delete() {
        confirmDialog.open();
    }

    private void cancel() {
        this.customer = null;
        this.customerEditViewHandler.onChange();
    }

    public interface CustomerEditViewHandler {
        void onChange();
    }

    public void setCustomer(Customer customer) {
        if (customer != null) {
            if (customer.getId() != null) {
                this.customer = this.customerRepository.findById(customer.getId()).get();
            } else {
                this.customer = customer;
            }
            this.binder.setBean(this.customer);
            this.setVisible(true);
            this.name.focus();
        }else{
            this.customer = null;
        }
    }

    public void setCustomerEditViewHandler(CustomerEditViewHandler customerEditViewHandler) {
        this.customerEditViewHandler = customerEditViewHandler;
    }

}
