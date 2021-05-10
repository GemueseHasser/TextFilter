package de.jonas.textfilter;

import org.jetbrains.annotations.NotNull;

import javax.swing.JFrame;

import java.awt.Component;

public class GUI {

    private final JFrame frame;

    public GUI(@NotNull final WindowType type) {
        this.frame = new JFrame(type.getTitle());
        this.frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.frame.setBounds(0, 0, type.getWidth(), type.getHeight());
        this.frame.setLocationRelativeTo(null);
        this.frame.setLayout(null);
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
