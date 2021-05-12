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
    }

    public void open() {
        this.frame.setVisible(true);

        final Draw draw = new Draw(this.background);
        draw.setBounds(0, 0, this.frame.getWidth(), this.frame.getHeight());
        draw.setVisible(true);

        this.frame.add(draw);
    }

    public void close() {
        this.frame.dispose();
    }

    public void add(@NotNull final Component component) {
        component.setFocusable(false);
        this.frame.add(component);
    }

    private static final class Draw extends JLabel {

        private final Color background;

        public Draw(@NotNull final Color background) {
            this.background = background;
        }

        @Override
        protected void paintComponent(final Graphics g) {
            super.paintComponent(g);

            final Graphics2D g2d = (Graphics2D) g;

            g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

            g.setColor(this.background);
            g.fillRect(0, 0, this.getWidth(), this.getHeight());
        }
    }

}
