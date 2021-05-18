package de.jonas.textfilter.gui;

import de.jonas.textfilter.GUI;
import de.jonas.textfilter.WindowType;
import de.jonas.textfilter.system.FilterSystem;
import org.jetbrains.annotations.NotNull;

import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTextPane;
import javax.swing.text.AttributeSet;
import javax.swing.text.SimpleAttributeSet;
import javax.swing.text.StyleConstants;
import javax.swing.text.StyleContext;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.util.Map;

/**
 * Das {@link ResultWindow} wird geöffnet, sobald der Nutzer auf "filtern" klickt. In diesem {@link GUI} wird dann das
 * gefilterte Ergebnis dargestellt. Zudem hat man in diesem {@link GUI} die Möglichkeit, die gefilterten Ergebnisse zu
 * speichern.
 */
public final class ResultWindow extends GUI {

    //<editor-fold desc="CONSTANTS">
    /** Der Text des Buttons, womit man die gefilterten Ergebnisse speichern kann. */
    private static final String SAVE_BUTTON_TEXT = "Speichern";
    /** Die X-Koordinate des Buttons, womit man die gefilterten Ergebnisse speichern kann. */
    private static final int SAVE_BUTTON_X = 0;
    /** Die Y-Koordinate des Buttons, womit man die gefilterten Ergebnisse speichern kann. */
    private static final int SAVE_BUTTON_Y = 435;
    /** Die Breite des Buttons, womit man die gefilterten Ergebnisse speichern kann. */
    private static final int SAVE_BUTTON_WIDTH = 490;
    /** Die Höhe des Buttons, womit man die gefilterten Ergebnisse speichern kann. */
    private static final int SAVE_BUTTON_HEIGHT = 30;
    //</editor-fold>

    //<editor-fold desc="LOCAL FIELDS">
    /** Das {@link JTextPane}, worin die gefilterten Ergebnisse dargestellt werden. */
    private final JTextPane pane;
    //</editor-fold>

    //<editor-fold desc="CONSTRUCTORS">

    /**
     * Erzeugt eine neue und vollständig unabhängige Instanz des {@link ResultWindow}. Das {@link ResultWindow} wird
     * geöffnet, sobald der Nutzer auf "filtern" klickt. In diesem {@link GUI} wird dann das gefilterte Ergebnis
     * dargestellt. Zudem hat man in diesem {@link GUI} die Möglichkeit, die gefilterten Ergebnisse zu speichern.
     */
    public ResultWindow() {
        super(WindowType.RESULT_WINDOW);

        final WindowType type = super.getType();

        this.pane = new JTextPane();
        this.pane.setFont(new Font("Arial", Font.BOLD, 15));

        final JScrollPane scrollPane = new JScrollPane(this.pane);

        final int width = type.getWidth();
        final int height = type.getHeight();

        scrollPane.setBounds(0, 0, width - 10, height - SAVE_BUTTON_HEIGHT - 35);

        final Map<Integer, String> filtered = FilterSystem.SYSTEM.getFilteredLines();

        if (filtered.isEmpty()) {
            appendToPane("Es wurde keine Übereinstimmung gefunden.", Color.BLACK);
        }

        for (@NotNull final Map.Entry<Integer, String> line : filtered.entrySet()) {
            appendToPane("Zeile ", Color.GRAY);
            appendToPane(line.getKey() + "", Color.RED);
            appendToPane(": ", Color.GRAY);
            appendToPane(line.getValue() + "\n \n", Color.BLACK);
        }

        this.pane.setFocusable(false);
        this.pane.setEditable(false);

        final JButton save = new JButton(SAVE_BUTTON_TEXT);
        save.setBounds(SAVE_BUTTON_X, SAVE_BUTTON_Y, SAVE_BUTTON_WIDTH, SAVE_BUTTON_HEIGHT);
        save.setBorderPainted(false);
        save.setFocusable(false);
        save.setOpaque(true);
        save.setBackground(Color.GRAY);
        save.setForeground(Color.WHITE);
        save.setFont(super.getFont());
        save.addActionListener(actionEvent -> {
            FilterSystem.SYSTEM.writePDF();
            JOptionPane.showMessageDialog(
                null,
                "Die gefilterten Werte wurden im Download Verzeichnis gespeichert!",
                "Gespeichert!",
                JOptionPane.INFORMATION_MESSAGE
            );
        });

        super.add(save);
        super.add(scrollPane);
    }
    //</editor-fold>


    /**
     * Fügt einen bestimmten Text, mit einer bestimmten {@link Color Farbe} zu dem {@link JTextPane} dieser Instanz
     * hinzu.
     *
     * @param text  Der Text, der zu dem {@link JTextPane} hinzugefügt wird.
     * @param color Die {@link Color Farbe}, in der der Text dargestellt wird.
     */
    private void appendToPane(String text, Color color) {
        final StyleContext styleContext = StyleContext.getDefaultStyleContext();

        AttributeSet attributeSet = styleContext.addAttribute(
            SimpleAttributeSet.EMPTY,
            StyleConstants.Foreground,
            color
        );
        attributeSet = styleContext.addAttribute(
            attributeSet,
            StyleConstants.FontFamily,
            "Lucida Console"
        );
        attributeSet = styleContext.addAttribute(
            attributeSet,
            StyleConstants.Alignment,
            StyleConstants.ALIGN_JUSTIFIED
        );

        final int length = this.pane.getDocument().getLength();

        this.pane.setCaretPosition(length);
        this.pane.setCharacterAttributes(attributeSet, false);
        this.pane.replaceSelection(text);
    }

    @Override
    protected void draw(final @NotNull Graphics graphics) {
        // draw stuff on frame
    }
}
