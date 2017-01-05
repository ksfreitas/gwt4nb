package org.yournamehere.client;

import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.ClickListener;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.DockPanel;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.KeyboardListenerAdapter;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.MouseListener;
import com.google.gwt.user.client.ui.SourcesClickEvents;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Widget;

public class GWTFlickrEditableLabelPanel extends Composite {

    private TextBox newName;
    private Label originalName;
    private String originalText;
    private Button saveButton;
    private Button cancelButton;
    private Image image = new Image("images/sample.jpg");
    private Label orLabel = new Label("or");

    public GWTFlickrEditableLabelPanel() {

        originalName = new Label("MyImage");
        originalName.setStyleName("flickrPanel-label");

        originalName.addClickListener(new ClickListener() {

            public void onClick(Widget sender) {
                ShowText();
            }
        });

        originalName.addMouseListener(new MouseListener() {

            public void onMouseDown(Widget sender, int x, int y) {
            }

            public void onMouseEnter(Widget sender) {
                originalName.setStyleName("flickrPanel-label-hover");
            }

            public void onMouseLeave(Widget sender) {
                originalName.setStyleName("flickrPanel-label");
            }

            public void onMouseMove(Widget sender, int x, int y) {
            }

            public void onMouseUp(Widget sender, int x, int y) {
            }
        });

        newName = new TextBox();
        newName.setStyleName("flickrPanel-textBox");

        newName.addKeyboardListener(new KeyboardListenerAdapter() {

            public void onKeyPress(Widget sender, char keyCode, int modifiers) {
                switch (keyCode) {
                    case KeyboardListenerAdapter.KEY_ENTER:
                        saveChange();
                        break;
                    case KeyboardListenerAdapter.KEY_ESCAPE:
                        cancelChange();
                        break;
                }
            }
        });

        saveButton = new Button();
        saveButton.setStyleName("flickrPanel-buttons");
        saveButton.addStyleName("flickrPanel-save");
        saveButton.setText("SAVE");

        ((SourcesClickEvents) saveButton).addClickListener(new ClickListener() {

            public void onClick(Widget sender) {
                saveChange();
            }
        });

        cancelButton = new Button();
        cancelButton.setStyleName("flickrPanel-buttons");
        cancelButton.addStyleName("flickrPanel-cancel");
        cancelButton.setText("CANCEL");

        ((SourcesClickEvents) cancelButton).addClickListener(new ClickListener() {

            public void onClick(Widget sender) {
                cancelChange();
            }
        });

        originalName.setVisible(true);
        newName.setVisible(false);
        saveButton.setVisible(false);
        orLabel.setVisible(false);
        cancelButton.setVisible(false);

        HorizontalPanel buttonPanel = new HorizontalPanel();
        buttonPanel.setStyleName("flickrPanel-buttonPanel");
        buttonPanel.add(saveButton);
        buttonPanel.add(orLabel);
        buttonPanel.add(cancelButton);
        DockPanel workPane = new DockPanel();
        VerticalPanel workPanel = new VerticalPanel();

        workPanel.setStyleName("flickrPanel");
        workPanel.add(image);
        workPanel.add(originalName);
        workPanel.add(newName);
        workPanel.add(buttonPanel);
        workPane.add(workPanel, DockPanel.CENTER);
        workPane.setCellHeight(workPanel, "100%");
        workPane.setCellWidth(workPanel, "100%");

        initWidget(workPane);
    }

    private void ShowText() {
        originalText = originalName.getText();

        originalName.setVisible(false);
        saveButton.setVisible(true);
        orLabel.setVisible(true);
        cancelButton.setVisible(true);

        newName.setText(originalText);
        newName.setVisible(true);
        newName.setFocus(true);
        newName.setStyleName("flickrPanel-textBox-edit");
    }

    private void showLabel() {
        originalName.setVisible(true);
        saveButton.setVisible(false);
        orLabel.setVisible(false);
        cancelButton.setVisible(false);
        newName.setVisible(false);
    }

    private void saveChange() {
        originalName.setText(newName.getText());
        showLabel();

    // This is where you can call an RPC service to update
    // a db or call some other service to propagate
    // the change. In this example we just change the
    // text of the label.
    }

    public void cancelChange() {
        originalName.setText(originalText);
        showLabel();
    }

    public void onShow() {
    }
}
