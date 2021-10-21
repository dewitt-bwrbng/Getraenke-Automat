package arbeitsprobe.automat.exceptions;

import arbeitsprobe.automat.artikel.Artikel;

public class UnterschiedlicheArtikelException extends Exception {

    public UnterschiedlicheArtikelException(Artikel erwartet, Artikel uebergeben) {
        super("Der uebergebene Artikel " + uebergeben.holeName() + " passt nicht zum erwarteten Artikel " + erwartet.holeName());
    }
}
