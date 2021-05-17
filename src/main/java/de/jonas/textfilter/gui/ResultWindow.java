package de.jonas.textfilter.gui;

import de.jonas.textfilter.GUI;
import de.jonas.textfilter.WindowType;
import de.jonas.textfilter.system.FilterSystem;
import org.jetbrains.annotations.NotNull;

import javax.swing.JScrollPane;
import javax.swing.JTextPane;
import javax.swing.text.AttributeSet;
import javax.swing.text.SimpleAttributeSet;
import javax.swing.text.StyleConstants;
import javax.swing.text.StyleContext;

import java.awt.Color;
import java.awt.Font;
import java.util.Map;

public final class ResultWindow extends GUI {

    private final JTextPane pane;

    public ResultWindow() {
        super(WindowType.RESULT_WINDOW);

        final WindowType type = super.getType();

        this.pane = new JTextPane();
        this.pane.setFont(new Font("Arial", Font.BOLD, 15));

        final JScrollPane scrollPane = new JScrollPane(this.pane);

        final int width = type.getWidth();
        final int height = type.getHeight();

        scrollPane.setBounds(0, 0, width - 10, height - 35);

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

        super.add(scrollPane);
    }

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
}
