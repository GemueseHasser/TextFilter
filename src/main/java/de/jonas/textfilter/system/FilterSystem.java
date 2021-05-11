package de.jonas.textfilter.system;

import lombok.Getter;
import lombok.SneakyThrows;
import org.jetbrains.annotations.NotNull;

import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.filechooser.FileNameExtensionFilter;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public final class FilterSystem extends JFrame {

    public static final FilterSystem SYSTEM = new FilterSystem();


    private final List<String> textInLines = new ArrayList<String>();
    @Getter
    private final List<String> filteredLines = new ArrayList<String>();

    private String pfad;

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
            this.pfad = chooser.getSelectedFile().toString();
        }

        chooser.setVisible(false);
    }

    @SneakyThrows
    public void filter(@NotNull final String templateText) {
        final File file = new File(this.pfad);

        final Scanner scanner = new Scanner(file);

        while (scanner.hasNextLine()) {
            this.textInLines.add(scanner.nextLine());
        }

        int i = 0;

        for (@NotNull final String line : this.textInLines) {
            i++;
            if (!line.contains(templateText)) {
                continue;
            }

            filteredLines.add(i + ": " + line);
        }
    }

}
