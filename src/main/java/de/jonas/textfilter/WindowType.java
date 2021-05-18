package de.jonas.textfilter;

import de.jonas.textfilter.gui.MainWindow;
import de.jonas.textfilter.gui.ResultWindow;
import lombok.Getter;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Range;

/**
 * Mithilfe eines {@link WindowType} lässt sich ein {@link GUI} erzeugen. Ein {@link WindowType} übergibt die Breite,
 * die Höhe und den Titel des {@link GUI}.
 */
public enum WindowType {

    /** Der {@link WindowType} für das {@link MainWindow}. */
    MAIN_WINDOW(
        500,
        300,
        "Text-Filter"
    ),
    /** Der {@link WindowType} für das {@link ResultWindow}. */
    RESULT_WINDOW(
        500,
        500,
        "Gefiltert!"
    );

    //<editor-fold desc="LOCAL FIELDS">
    /** Die Breite des {@link WindowType}. */
    @Getter
    private final int width;
    /** Die Höhe des {@link WindowType}. */
    @Getter
    private final int height;
    /** Der Titel des {@link WindowType}. */
    @Getter
    private final String title;
    //</editor-fold>


    //<editor-fold desc="CONSTRUCTORS">

    /**
     * Erzeugt mithilfe eine Breite, einer Höhe und eines Titels einen neuen und vollständig unabhängigen {@link
     * WindowType}. Mithilfe eines {@link WindowType} lässt sich ein {@link GUI} erzeugen. Ein {@link WindowType}
     * übergibt die Breite, die Höhe und den Titel des {@link GUI}.
     *
     * @param width  Die Breite des {@link WindowType}.
     * @param height Die Höhe des {@link WindowType}.
     * @param title  Der Titel des {@link WindowType}.
     */
    WindowType(
        @Range(from = 0, to = Integer.MAX_VALUE) final int width,
        @Range(from = 0, to = Integer.MAX_VALUE) final int height,
        @NotNull final String title
    ) {
        this.width = width;
        this.height = height;
        this.title = title;
    }
    //</editor-fold>

}
