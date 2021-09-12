package com.example.petstore.pet;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.value.ValueChangeMode;
import com.vaadin.flow.router.Route;

@Route
public class PetMainView extends VerticalLayout {
    private final TextField textField;
    private final Grid<Pet> grid;
    private final Button newButton;
    private final PetRepository petRepository;
    private final PetEditView petEditView;

    public PetMainView(PetRepository petRepository, PetEditView petEditView) {
        this.petRepository = petRepository;
        this.petEditView = petEditView;

        this.textField = new TextField("Pet Name: ");
        this.textField.setValueChangeMode(ValueChangeMode.EAGER);
        this.textField.addValueChangeListener(e -> refreshGrid(e.getValue()));

        this.grid = new Grid<>(Pet.class);
        this.grid.setColumns("name", "birthDate");
        this.grid.asSingleSelect().addValueChangeListener(e -> petEditView.setPet(e.getValue()));
        this.refreshGrid(null);

        this.newButton = new Button("New Pet");
        this.newButton.addClickListener(e -> petEditView.setPet(new Pet()));

        this.petEditView.setPetEditViewHandler(() -> {
            this.refreshGrid(textField.getValue());
            this.petEditView.setVisible(false);
        });

        this.add(textField);
        this.add(grid);
        this.add(newButton);
        this.add(petEditView);
    }

    private void refreshGrid(String name) {
        grid.setItems(
                name != null && !"".equals(name.trim()) ?
                        petRepository.findByNameContainsIgnoreCase(name)
                        :
                        petRepository.findAll()
        );
    }

}
