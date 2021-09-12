package com.example.petstore.pet;

import com.example.petstore.util.ConfirmDialog;
import com.vaadin.flow.component.Key;
import com.vaadin.flow.component.KeyNotifier;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.datepicker.DatePicker;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.binder.Binder;
import com.vaadin.flow.spring.annotation.SpringComponent;
import com.vaadin.flow.spring.annotation.UIScope;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.LocalDate;

@UIScope
@SpringComponent
public class PetEditView extends VerticalLayout implements KeyNotifier {
    private final PetRepository petRepository;
    private Pet pet;
    private final Button saveButton;
    private final Button deleteButton;
    private final Button cancelButton;
    private final HorizontalLayout buttonsHorizontalLayout;
    private final Binder<Pet> binder;
    private final TextField name;
    private final DatePicker birthDate;
    private final ConfirmDialog confirmDialog;
    private PetEditViewHandler petEditViewHandler;

    @Autowired
    public PetEditView(PetRepository petRepository) {
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

        this.buttonsHorizontalLayout = new HorizontalLayout(saveButton, deleteButton, cancelButton);

        this.name = new TextField("Name: ");
        this.birthDate = new DatePicker("Birth Date: ");

        this.binder = new Binder<>(Pet.class);
        this.binder.bindInstanceFields(this);

        this.addKeyPressListener(Key.ENTER, e -> save());
        this.confirmDialog = new ConfirmDialog("Are you sure you want to delete the item?", e -> {
            this.petRepository.delete(pet);
            this.petEditViewHandler.onChange();
            this.pet = null;
        });

        this.add(name);
        this.add(birthDate);
        this.add(buttonsHorizontalLayout);
        this.setVisible(false);
    }

    public void setPet(Pet pet) {
        if (pet != null) {
            if (pet.getId() != null) {
                this.pet = petRepository.findById(pet.getId()).get();
            } else {
                this.pet = pet;
            }

            this.binder.setBean(this.pet);
            this.setVisible(true);
            this.name.focus();
        }else{
            this.pet = null;
        }
    }

    public void setPetEditViewHandler(PetEditViewHandler petEditViewHandler) {
        this.petEditViewHandler = petEditViewHandler;
    }

    private void save() {
        this.petRepository.save(pet);
        this.petEditViewHandler.onChange();
        this.pet = null;
    }

    private void delete() {
        confirmDialog.open();
    }

    private void cancel() {
        this.pet = null;
        this.petEditViewHandler.onChange();
    }

    public interface PetEditViewHandler {
        void onChange();
    }

}
