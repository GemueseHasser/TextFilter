package de.jonas.textfilter.system;

import lombok.Getter;
import lombok.SneakyThrows;
import org.jetbrains.annotations.NotNull;

import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.filechooser.FileNameExtensionFilter;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

public final class FilterSystem extends JFrame {

    public static final FilterSystem SYSTEM = new FilterSystem();


    private final List<String> textInLines = new ArrayList<String>();
    @Getter
    private final Map<Integer, String> filteredLines = new HashMap<Integer, String>();

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
    public boolean filter(@NotNull final String templateText) {
        final File file;
        try {
            file = new File(this.pfad);
        } catch (@NotNull final NullPointerException ignored) {
            JOptionPane.showMessageDialog(
                null,
                "Es wurde keine Datei ausgewählt!",
                "Error",
                JOptionPane.ERROR_MESSAGE
            );
            return false;
        }

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

            filteredLines.put(i, line);
        }

        scanner.close();
        return true;
    }

}
