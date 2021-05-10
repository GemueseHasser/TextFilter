package de.jonas.textfilter.gui;

import de.jonas.textfilter.GUI;
import de.jonas.textfilter.WindowType;
import de.jonas.textfilter.system.FilterSystem;
import org.jetbrains.annotations.NotNull;

import java.awt.TextArea;

public final class ResultWindow extends GUI {

    public ResultWindow() {
        super(WindowType.RESULT_WINDOW);

        final TextArea area = new TextArea();

        for (@NotNull final String line : FilterSystem.SYSTEM.getFilteredLines()) {
            area.append(line + "\n \n");
        }

        area.setEditable(false);
    }

}
