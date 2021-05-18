package de.jonas.textfilter.gui;

import de.jonas.textfilter.GUI;
import de.jonas.textfilter.WindowType;
import de.jonas.textfilter.system.FilterSystem;
import org.jetbrains.annotations.NotNull;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JTextField;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

/**
 * Das Haupt-Fenster der Anwendung, welches durch die Main-Methode direkt initialisiert und geöffnet wird. Das {@link
 * MainWindow} erbt von dem {@link GUI}.
 */
public final class MainWindow extends GUI implements ActionListener {

    //<editor-fold desc="CONSTANTS">
    //<editor-fold desc="data-button">
    /** Der Text des Buttons, womit man die zu filternde Text-Datei auswählen kann. */
    private static final String DATA_BUTTON_TEXT = "Datei wählen";
    /** Die X-Koordinate des Buttons, womit man die zu filternde Text-Datei auswählen kann. */
    private static final int DATA_BUTTON_X = 100;
    /** Die Y-Koordinate des Buttons, womit man die zu filternde Text-Datei auswählen kann. */
    private static final int DATA_BUTTON_Y = 30;
    /** Die Breite des Buttons, womit man die zu filternde Text-Datei auswählen kann. */
    private static final int DATA_BUTTON_WIDTH = 300;
    /** Die Höhe des Buttons, womit man die zu filternde Text-Datei auswählen kann. */
    private static final int DATA_BUTTON_HEIGHT = 30;
    //</editor-fold>

    //<editor-fold desc="field-label">
    /** Der Text des Schriftzugs, welcher über dem Text-Feld steht. */
    private static final String FIELD_LABEL_TEXT = "Filtern nach folgendem Text:";
    /** Die X-Koordinate des Schriftzugs, welcher über dem Text-Feld steht. */
    private static final int FIELD_LABEL_X = 50;
    /** Die Y-Koordinate des Schriftzugs, welcher über dem Text-Feld steht. */
    private static final int FIELD_LABEL_Y = 80;
    /** Die Breite des Schriftzugs, welcher über dem Text-Feld steht. */
    private static final int FIELD_LABEL_WIDTH = 400;
    /** Die Höhe des Schriftzugs, welcher über dem Text-Feld steht. */
    private static final int FIELD_LABEL_HEIGHT = 20;
    //</editor-fold>

    //<editor-fold desc="text-field">
    /** Die X-Koordinate des Text-Feldes, worin man den Text eingeben kann, nach dem gefiltert werden soll. */
    private static final int TEXT_FIELD_X = 50;
    /** Die Y-Koordinate des Text-Feldes, worin man den Text eingeben kann, nach dem gefiltert werden soll. */
    private static final int TEXT_FIELD_Y = 100;
    /** Die Breite des Text-Feldes, worin man den Text eingeben kann, nach dem gefiltert werden soll. */
    private static final int TEXT_FIELD_WIDTH = 400;
    /** Die Höhe des Text-Feldes, worin man den Text eingeben kann, nach dem gefiltert werden soll. */
    private static final int TEXT_FIELD_HEIGHT = 40;
    //</editor-fold>

    //<editor-fold desc="filter-button">
    /** Der Text des Buttons, womit man den Text filtern kann. */
    private static final String FILTER_BUTTON_TEXT = "Filtern";
    /** Die X-Koordinate des Buttons, womit man den Text filtern kann. */
    private static final int FILTER_BUTTON_X = 50;
    /** Die Y-Koordinate des Buttons, womit man den Text filtern kann. */
    private static final int FILTER_BUTTON_Y = 200;
    /** Die Breite des Buttons, womit man den Text filtern kann. */
    private static final int FILTER_BUTTON_WIDTH = 400;
    /** Die Höhe des Buttons, womit man den Text filtern kann. */
    private static final int FILTER_BUTTON_HEIGHT = 50;
    //</editor-fold>
    //</editor-fold>


    //<editor-fold desc="LOCAL FIELDS">
    /** Der {@link JButton}, womit man die zu filternde Text-Datei auswählen kann. */
    private final JButton data;
    /** Der {@link JButton}, womit man den Text filtern kann. */
    private final JButton filter;

    /** Das {@link JTextField}, worin man den Text eingeben kann, nach dem gefiltert werden soll. */
    private final JTextField field;
    //</editor-fold>


    //<editor-fold desc="CONSTRUCTORS">

    /**
     * Erzeugt eine neue und vollständig unabhängige Instanz des {@link MainWindow}. Beim Erzeugen, wird auch eine neue
     * Instanz des vererbten {@link GUI} erzeugt.
     */
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
        exit.addActionListener(actionEvent -> System.exit(0));

        super.add(this.data);
        super.add(label);
        super.getFrame().add(this.field);
        super.add(this.filter);
        super.add(exit);
    }
    //</editor-fold>


    //<editor-fold desc="implementation">
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
    //</editor-fold>

    //<editor-fold desc="draw">
    @Override
    protected void draw(@NotNull final Graphics graphics) {
        final File file = FilterSystem.SYSTEM.getFile();
        graphics.drawString("Datei: " + (file == null ? "-" : file.getName()), 100, 20);
    }
    //</editor-fold>
}
