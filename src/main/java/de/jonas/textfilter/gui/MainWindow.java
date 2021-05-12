package de.jonas.textfilter.gui;

import de.jonas.textfilter.GUI;
import de.jonas.textfilter.WindowType;
import de.jonas.textfilter.system.FilterSystem;
import org.jetbrains.annotations.NotNull;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JTextField;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public final class MainWindow extends GUI implements ActionListener {

    //<editor-fold desc="CONSTANTS">
    //<editor-fold desc="data-button">
    private static final String DATA_BUTTON_TEXT = "Datei wählen";
    private static final int DATA_BUTTON_X = 100;
    private static final int DATA_BUTTON_Y = 30;
    private static final int DATA_BUTTON_WIDTH = 300;
    private static final int DATA_BUTTON_HEIGHT = 30;
    //</editor-fold>

    private static final String FIELD_LABEL_TEXT = "Filtern nach folgendem Text:";
    private static final int FIELD_LABEL_X = 50;
    private static final int FIELD_LABEL_Y = 80;
    private static final int FIELD_LABEL_WIDTH = 400;
    private static final int FIELD_LABEL_HEIGHT = 20;

    //<editor-fold desc="text-field">
    private static final int TEXT_FIELD_X = 50;
    private static final int TEXT_FIELD_Y = 100;
    private static final int TEXT_FIELD_WIDTH = 400;
    private static final int TEXT_FIELD_HEIGHT = 40;
    //</editor-fold>

    //<editor-fold desc="filter-button">
    private static final String FILTER_BUTTON_TEXT = "Filtern";
    private static final int FILTER_BUTTON_X = 50;
    private static final int FILTER_BUTTON_Y = 200;
    private static final int FILTER_BUTTON_WIDTH = 400;
    private static final int FILTER_BUTTON_HEIGHT = 50;
    //</editor-fold>
    //</editor-fold>


    //<editor-fold desc="LOCAL FIELDS">
    private final JButton data;
    private final JButton filter;

    private final JTextField field;
    //</editor-fold>


    public MainWindow() {
        super(WindowType.MAIN_WINDOW);
        super.getFrame().setUndecorated(true);

        super.setBackground(Color.LIGHT_GRAY);

        this.data = new JButton(DATA_BUTTON_TEXT);
        this.data.setBounds(DATA_BUTTON_X, DATA_BUTTON_Y, DATA_BUTTON_WIDTH, DATA_BUTTON_HEIGHT);
        this.data.addActionListener(this);
        this.data.setFont(super.getFont());

        final JLabel label = new JLabel(FIELD_LABEL_TEXT);
        label.setBounds(FIELD_LABEL_X, FIELD_LABEL_Y, FIELD_LABEL_WIDTH, FIELD_LABEL_HEIGHT);
        label.setFont(super.getFont());

        this.field = new JTextField();
        this.field.setBounds(TEXT_FIELD_X, TEXT_FIELD_Y, TEXT_FIELD_WIDTH, TEXT_FIELD_HEIGHT);
        this.field.setFont(super.getFont());

        this.filter = new JButton(FILTER_BUTTON_TEXT);
        this.filter.setBounds(FILTER_BUTTON_X, FILTER_BUTTON_Y, FILTER_BUTTON_WIDTH, FILTER_BUTTON_HEIGHT);
        this.filter.addActionListener(this);
        this.filter.setFont(super.getFont());

        final JButton exit = new JButton("x");
        exit.setFont(super.getFont());
        exit.setOpaque(true);
        exit.setBackground(Color.RED);
        exit.setForeground(Color.WHITE);
        exit.setBounds(super.getFrame().getWidth() - 45, 0, 45, 45);
        exit.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(final ActionEvent actionEvent) {
                System.exit(0);
            }
        });

        super.add(this.data);
        super.add(label);
        super.getFrame().add(this.field);
        super.add(this.filter);
        super.add(exit);
    }

    @Override
    public void actionPerformed(@NotNull final ActionEvent e) {
        if (e.getSource().equals(this.data)) {
            // get data
            FilterSystem.SYSTEM.initializeData();
        }

        if (e.getSource().equals(this.filter)) {
            // filter data from text
            final String templateText = this.field.getText();
            if (!FilterSystem.SYSTEM.filter(templateText)) {
                return;
            }

            // close current window
            super.close();

            // open result window
            final ResultWindow resultWindow = new ResultWindow();
            resultWindow.open();
        }
    }
}
