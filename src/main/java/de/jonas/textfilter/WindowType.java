package de.jonas.textfilter;

import lombok.Getter;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Range;

public enum WindowType {

    MAIN_WINDOW(
        500,
        300,
        "Text-Filter"
    ),
    RESULT_WINDOW(
        500,
        500,
        "Gefiltert!"
    );

    @Getter
    private final int width;
    @Getter
    private final int height;
    @Getter
    private final String title;


    WindowType(
        @Range(from = 0, to = Integer.MAX_VALUE) final int width,
        @Range(from = 0, to = Integer.MAX_VALUE) final int height,
        @NotNull final String title
    ) {
        this.width = width;
        this.height = height;
        this.title = title;
    }

}
