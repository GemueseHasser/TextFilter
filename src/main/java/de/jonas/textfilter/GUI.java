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

public class GUI {

    @Getter
    private final WindowType type;
    @Getter
    private final Font font;
    @Getter
    private final JFrame frame;
    @Getter
    private final Draw draw;

    @Setter
    private Color background = Color.WHITE;

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

    public void open() {
        this.frame.setVisible(true);

        this.draw.setBackground(this.background);

        this.frame.add(draw);
    }

    public void close() {
        this.frame.dispose();
    }

    public void add(@NotNull final Component component) {
        component.setFocusable(false);
        this.frame.add(component);
    }

    protected void draw(@NotNull final Graphics graphics) {}

    private final class Draw extends JLabel {

        @Setter
        private Color background;

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
    }

}
