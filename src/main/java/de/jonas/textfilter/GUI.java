package de.jonas.textfilter;

import lombok.Getter;
import org.jetbrains.annotations.NotNull;

import javax.swing.JFrame;

import java.awt.Component;

public class GUI {

    @Getter
    private final WindowType type;
    private final JFrame frame;

    public GUI(@NotNull final WindowType type) {
        this.type = type;
        this.frame = new JFrame(this.type.getTitle());
        this.frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.frame.setBounds(0, 0, this.type.getWidth(), this.type.getHeight());
        this.frame.setLocationRelativeTo(null);
        this.frame.setLayout(null);
        this.frame.setResizable(false);
    }

    public void open() {
        this.frame.setVisible(true);
    }

    public void close() {
        this.frame.dispose();
    }

    public void add(@NotNull final Component component) {
        this.frame.add(component);
    }

}
