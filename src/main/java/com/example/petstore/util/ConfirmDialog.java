package com.example.petstore.util;

import com.vaadin.flow.component.ComponentEventListener;
import com.vaadin.flow.component.Text;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;

public final class ConfirmDialog extends Dialog {

    private Text text;
    private final Button okButton;
    private final Button cancelButton;
    private final HorizontalLayout horizontalLayout;
    private Boolean ok;

    public ConfirmDialog(String message, ComponentEventListener okListener){
        this.text = new Text(message);
        this.okButton = new Button("Ok");
        this.cancelButton = new Button("Cancel");

        this.okButton.getElement().getThemeList().add("primary");
        this.cancelButton.getElement().getThemeList().add("error");

        this.okButton.addClickListener(okListener);
        this.okButton.addClickListener(e -> this.close());
        this.cancelButton.addClickListener(e -> this.close());

        this.horizontalLayout = new HorizontalLayout(okButton, cancelButton);

        this.add(text);
        this.add(horizontalLayout);
    }

}
