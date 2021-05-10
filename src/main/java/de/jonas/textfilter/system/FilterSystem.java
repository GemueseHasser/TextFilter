package de.jonas.textfilter.system;

import lombok.Getter;
import org.jetbrains.annotations.NotNull;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public final class FilterSystem {

    public static final FilterSystem SYSTEM = new FilterSystem();


    private final List<String> textInLines = new ArrayList<String>();
    @Getter
    private final List<String> filteredLines = new ArrayList<String>();

    public void initializeData(@NotNull final File file) {
        // initialize text in lines
    }

    public void filter(@NotNull final String templateText) {
        int i = 1;

        for (@NotNull final String line : this.textInLines) {
            if (!line.contains(templateText)) {
                continue;
            }

            filteredLines.add(i + ": " + line);
            i++;
        }
    }

}
