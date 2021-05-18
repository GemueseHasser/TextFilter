package de.jonas.textfilter;

import lombok.Getter;
import lombok.Setter;
import org.jetbrains.annotations.NotNull;

import javax.swing.JFrame;
import javax.swing.JLabel;

import java.awt.Color;
import java.awt.Component;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;

/**
 * Ein {@link GUI} ist ein leeres Fenster, welches als Vorlage für genauer definierte Fenster dient.
 */
public abstract class GUI {

    //<editor-fold desc="LOCAL FIELDS">
    /** Der Typ des Fensters. */
    @Getter
    private final WindowType type;
    /** Die Basis-Schriftart des Fensters. */
    @Getter
    private final Font font;
    /** Das {@link JFrame}, auf das das gesamte {@link GUI} aufbaut. */
    @Getter
    private final JFrame frame;
    /** Mithilfe dieses {@link Draw} wird alles auf das Fenster gezeichnet. */
    @Getter
    private final Draw draw;
    /** Die {@link Color Hintergrund-Farbe} des Fensters. */
    @Setter
    private Color background = Color.WHITE;
    //</editor-fold>


    //<editor-fold desc="CONSTRUCTORS">

    /**
     * Erzeugt mithilfe eines {@link WindowType} eine neue und vollständig unabhängige Instanz des {@link GUI}. Ein
     * {@link GUI} ist ein leeres Fenster, welches als Vorlage für genauer definierte Fenster dient.
     *
     * @param type Der {@link WindowType Typ} des {@link GUI}.
     */
    public GUI(@NotNull final WindowType type) {
        this.type = type;
        this.font = new Font("Arial", Font.BOLD, 15);
        this.frame = new JFrame(this.type.getTitle());
        this.frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.frame.setBounds(0, 0, this.type.getWidth(), this.type.getHeight());
        this.frame.setLocationRelativeTo(null);
        this.frame.setLayout(null);
        this.frame.setResizable(false);

        this.draw = new Draw();
        this.draw.setBounds(0, 0, this.frame.getWidth(), this.frame.getHeight());
        this.draw.setVisible(true);
    }
    //</editor-fold>


    /**
     * Öffnet das {@link GUI}.
     */
    public void open() {
        this.frame.setVisible(true);

        this.draw.setBackground(this.background);

        this.frame.add(draw);
    }

    /**
     * Schließt das {@link GUI}.
     */
    public void close() {
        this.frame.dispose();
    }

    /**
     * Fügt einen bestimmten {@link Component Komponenten} zu dem {@link GUI} hinzu.
     *
     * @param component Der {@link Component Komponent}, der zu dem {@link GUI} hinzugefügt wird.
     */
    public void add(@NotNull final Component component) {
        component.setFocusable(false);
        this.frame.add(component);
    }

    /**
     * Diese Methode wird konstant wiederholt aufgerufen, um etwas auf das {@link GUI} zu zeichnen.
     *
     * @param graphics Die {@link Graphics}, mit der gezeichnet wird.
     */
    protected abstract void draw(@NotNull final Graphics graphics);

    //<editor-fold desc="Draw">

    /**
     * Mithilfe dieses {@link Draw} wird alles auf das Fenster gezeichnet.
     */
    private final class Draw extends JLabel {

        //<editor-fold desc="LOCAL FIELDS">
        /** Die Hintergrund-Farbe des Fensters. */
        @Setter
        private Color background;
        //</editor-fold>

        //<editor-fold desc="paint">
        @Override
        protected void paintComponent(@NotNull final Graphics g) {
            super.paintComponent(g);

            final Graphics2D g2d = (Graphics2D) g;

            g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

            g.setColor(this.background);
            g.fillRect(0, 0, this.getWidth(), this.getHeight());

            g.setColor(Color.BLACK);

            GUI.this.draw(g);

            g.dispose();

            super.repaint();
        }
        //</editor-fold>
    }
    //</editor-fold>

}
