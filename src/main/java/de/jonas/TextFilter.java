package de.jonas;

import de.jonas.textfilter.gui.MainWindow;

/**
 * Die Haupt- und Main-Klasse der Anwendung. Von hier aus wird die Anwendung initialisiert und gestartet.
 */
public final class TextFilter {

    //<editor-fold desc="main">

    /**
     * Die Main-Methode der Anwendung, die von der Java-Laufzeitumgebung als erstes aufgerufen wird bzw. beim Starten
     * der Anwendung aufgerufen wird.
     *
     * @param args Die Argumente, mit der die Anwendung gestartet wird bzw. die der Anwendung beim Starten mitgegeben
     *             werden.
     */
    public static void main(String[] args) {
        // open main window
        final MainWindow mainWindow = new MainWindow();
        mainWindow.open();
    }
    //</editor-fold>

}
