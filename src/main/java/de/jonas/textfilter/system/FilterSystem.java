package de.jonas.textfilter.system;

import lombok.Getter;
import lombok.SneakyThrows;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.font.PDFont;
import org.apache.pdfbox.pdmodel.font.PDType1Font;
import org.jetbrains.annotations.NotNull;

import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.filechooser.FileNameExtensionFilter;

import java.awt.Color;
import java.awt.Component;
import java.io.File;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

/**
 * In dem {@link FilterSystem} werden alle Aktionen ausgeführt, die zum Filtern gebraucht werden. Dies sind
 * hauptsächlich drei Aktionen: Einmal das Auswählen der zu filternden Datei, dann das hauptsächliche Filtern des Textes
 * und das Abspeichern der Ergebnisse in PDF-Format.
 */
public final class FilterSystem extends Component {

    //<editor-fold desc="CONSTANTS">
    /** Die Instanz-Variable, womit man auf diese Instanz zugreifen kann. */
    public static final FilterSystem SYSTEM = new FilterSystem();

    /** Der Abstand von der unteren Kante eines PDF-Dokumentes, von wo die erste Zeile beginnt. */
    private static final int SAVED_PDF_MARGIN_BOTTOM = 700;
    /** Der Abstand von der linken Kante eines PDF-Dokumentes, von wo eine Zeile beginnt. */
    private static final int SAVED_PDF_MARGIN_LEFT = 50;
    /** Die Schriftgröße der Überschrift. */
    private static final int HEADING_FONT_SIZE = 30;
    /** Die Schriftgröße des normalen Textes. */
    private static final int DEFAULT_FONT_SIZE = 15;
    //</editor-fold>


    //<editor-fold desc="LOCAL FIELDS">
    /** Alle Zeilen (ungefiltert) des zu filternden Dokuemntes. */
    private final List<String> textInLines = new ArrayList<>();
    /** Alle herausgefilterten Zeilen des zu filterndesn Dokumentes mit Zeilen-Angabe. */
    @Getter
    private final Map<Integer, String> filteredLines = new HashMap<>();

    /** Die zu filternde Datei. */
    @Getter
    private File file;
    //</editor-fold>

    //<editor-fold desc="initializing">

    /**
     * Öffnet dem Nutzer ein Fenster, in dem er die zu filternde Datei auswählen kann. Die ausgewählte Datei wird dann
     * als zu filternde Datei initialisiert.
     */
    public void initializeData() {
        // initialize text in lines
        final JFileChooser chooser = new JFileChooser("user.home");
        chooser.setDialogType(JFileChooser.OPEN_DIALOG);

        final FileNameExtensionFilter filter = new FileNameExtensionFilter(
            "Plaintext: txt",
            "txt"
        );

        chooser.removeChoosableFileFilter(chooser.getAcceptAllFileFilter());
        chooser.setFileFilter(filter);

        chooser.setDialogTitle("Öffnen...");
        chooser.setVisible(true);

        final int result = chooser.showOpenDialog(this);

        if (result == JFileChooser.OPEN_DIALOG) {
            this.file = chooser.getSelectedFile();
        }

        chooser.setVisible(false);
    }
    //</editor-fold>

    //<editor-fold desc="basic filtering">

    /**
     * Zufalls der Nutzer schon eine Datei ausgewählt hat, wird diese nach einem bestimmten Vorlage-Text gefiltert und
     * die gefilterten Ergebnisse werden in eine {@link Map} gespeichert. Wenn der Nutzer noch keine Darei ausgewählt
     * hat, wird eine Error-Nachricht ausgegeben.
     *
     * @param templateText Der Vorlage-Text, nach dem die Datei gefiltert wird.
     *
     * @return Wenn das Filtern erfolgreich war, {@code true}, ansonsten {@code false}.
     */
    @SneakyThrows
    public boolean filter(@NotNull final String templateText) {
        if (this.file == null) {
            JOptionPane.showMessageDialog(
                null,
                "Es wurde keine Datei ausgewählt!",
                "Error",
                JOptionPane.ERROR_MESSAGE
            );
            return false;
        }

        final Scanner scanner = new Scanner(this.file);

        while (scanner.hasNextLine()) {
            this.textInLines.add(scanner.nextLine());
        }

        int i = 0;

        for (@NotNull final String line : this.textInLines) {
            i++;
            if (!line.contains(templateText)) {
                continue;
            }

            this.filteredLines.put(i, line);
        }

        scanner.close();
        return true;
    }
    //</editor-fold>

    //<editor-fold desc="saving">

    /**
     * Speichert den gefilterten Text als PDF-Datei im Download-Verzeichnis ab.
     */
    @SneakyThrows
    public void writePDF() {
        final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH-mm");
        final LocalDateTime time = LocalDateTime.now();

        final String home = System.getProperty("user.home");
        final String name = "Gefilterter-Text " + formatter.format(time) + ".pdf";
        final File filteredText = new File(home + "/Downloads/" + name);

        final PDDocument document = new PDDocument();

        final PDPage page = new PDPage();

        document.addPage(page);

        PDPageContentStream contentStream = new PDPageContentStream(document, page);

        contentStream.beginText();

        final PDFont font = PDType1Font.TIMES_BOLD;

        contentStream.setFont(font, HEADING_FONT_SIZE);

        contentStream.newLineAtOffset(SAVED_PDF_MARGIN_LEFT, SAVED_PDF_MARGIN_BOTTOM);

        // draw heading
        contentStream.setNonStrokingColor(Color.DARK_GRAY);
        contentStream.showText("Gefilterter Text");

        contentStream.endText();

        int line = 1;
        boolean pageOne = true;

        for (@NotNull final Map.Entry<Integer, String> filteredLine : this.filteredLines.entrySet()) {
            line++;
            final int base = pageOne ? SAVED_PDF_MARGIN_BOTTOM : SAVED_PDF_MARGIN_BOTTOM + 100;
            final int marginTop = base - line * (DEFAULT_FONT_SIZE + 10);

            final int currentLine = filteredLine.getKey();
            final String lineText = filteredLine.getValue();

            contentStream.beginText();

            contentStream.setFont(font, DEFAULT_FONT_SIZE);

            contentStream.newLineAtOffset(SAVED_PDF_MARGIN_LEFT, marginTop);

            contentStream.setNonStrokingColor(Color.GRAY);
            contentStream.showText("Zeile ");
            contentStream.setNonStrokingColor(Color.RED);
            contentStream.showText(currentLine + "");
            contentStream.setNonStrokingColor(Color.GRAY);
            contentStream.showText(": ");
            contentStream.setNonStrokingColor(Color.BLACK);
            contentStream.showText(lineText);

            contentStream.endText();

            if (marginTop <= 25) {
                pageOne = false;
                contentStream.close();

                line = 1;

                final PDPage newPage = new PDPage();
                document.addPage(newPage);
                contentStream = new PDPageContentStream(document, newPage);
            }
        }

        contentStream.close();

        document.save(filteredText);
        document.close();
    }
    //</editor-fold>

}
