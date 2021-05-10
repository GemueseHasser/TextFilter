package de.jonas.textfilter.gui;

import de.jonas.textfilter.GUI;
import de.jonas.textfilter.WindowType;
import de.jonas.textfilter.system.FilterSystem;
import org.jetbrains.annotations.NotNull;

import javax.swing.JButton;
import javax.swing.JTextField;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public final class MainWindow extends GUI implements ActionListener {

    private static final String DATA_BUTTON_TEXT = "Datei";
    private static final int DATA_BUTTON_X = 30;
    private static final int DATA_BUTTON_Y = 50;
    private static final int DATA_BUTTON_WIDTH = 150;
    private static final int DATA_BUTTON_HEIGHT = 30;

    private static final int TEXT_FIELD_X = 250;
    private static final int TEXT_FIELD_Y = 50;
    private static final int TEXT_FIELD_WIDTH = 200;
    private static final int TEXT_FIELD_HEIGHT = 30;

    private static final String FILTER_BUTTON_TEXT = "Filtern";
    private static final int FILTER_BUTTON_X = 50;
    private static final int FILTER_BUTTON_Y = 200;
    private static final int FILTER_BUTTON_WIDTH = 400;
    private static final int FILTER_BUTTON_HEIGHT = 50;


    private final JButton data;
    private final JButton filter;

    private final JTextField field;

    public MainWindow() {
        super(WindowType.MAIN_WINDOW);

        this.data = new JButton(DATA_BUTTON_TEXT);
        this.data.setBounds(DATA_BUTTON_X, DATA_BUTTON_Y, DATA_BUTTON_WIDTH, DATA_BUTTON_HEIGHT);
        this.data.addActionListener(this);

        this.field = new JTextField();
        this.field.setBounds(TEXT_FIELD_X, TEXT_FIELD_Y, TEXT_FIELD_WIDTH, TEXT_FIELD_HEIGHT);

        this.filter = new JButton(FILTER_BUTTON_TEXT);
        this.filter.setBounds(FILTER_BUTTON_X, FILTER_BUTTON_Y, FILTER_BUTTON_WIDTH, FILTER_BUTTON_HEIGHT);
        this.filter.addActionListener(this);

        super.add(this.data);
        super.add(this.field);
        super.add(this.filter);
    }

    @Override
    public void actionPerformed(@NotNull final ActionEvent e) {
        if (e.getSource().equals(this.data)) {
            // get data
        }

        if (e.getSource().equals(this.filter)) {
            // filter data from text
            final String templateText = this.field.getText();
            FilterSystem.SYSTEM.filter(templateText);

            // close current window
            super.close();

            // open result window
            final ResultWindow resultWindow = new ResultWindow();
            resultWindow.open();
        }
    }
}
