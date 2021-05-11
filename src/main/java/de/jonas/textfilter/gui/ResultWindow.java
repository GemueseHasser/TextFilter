package de.jonas.textfilter.gui;

import de.jonas.textfilter.GUI;
import de.jonas.textfilter.WindowType;
import de.jonas.textfilter.system.FilterSystem;
import org.jetbrains.annotations.NotNull;

import java.awt.TextArea;
import java.util.List;

public final class ResultWindow extends GUI {

    public ResultWindow() {
        super(WindowType.RESULT_WINDOW);

        final WindowType type = super.getType();

        final TextArea area = new TextArea();

        final int width = type.getWidth();
        final int height = type.getHeight();

        area.setBounds(0, 0, width - 10, height - 35);

        final List<String> filtered = FilterSystem.SYSTEM.getFilteredLines();

        if (filtered.isEmpty()) {
            area.append("Es wurde keine Übereinstimmung gefunden.");
        }

        for (@NotNull final String line : filtered) {
            area.append(line + "\n \n");
        }

        area.setFocusable(false);
        area.setEditable(false);

        super.add(area);
    }

}
