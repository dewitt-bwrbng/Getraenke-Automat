package arbeitsprobe.exceptions;

import arbeitsprobe.artikel.Artikel;

public class KeineArtikelEinheitenImFachException extends Exception {
    public KeineArtikelEinheitenImFachException(Artikel artikel) {
        super("Es sind keine Artikel Einheiten des Artikels " + artikel.holeName() + " in diesem Fach vorhanden.");
    }
}
