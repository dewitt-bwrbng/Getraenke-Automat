package arbeitsprobe.automat;

import arbeitsprobe.automat.exceptions.FachNichtLeerException;
import arbeitsprobe.automat.exceptions.KeineArtikelEinheitenImFachException;
import arbeitsprobe.automat.exceptions.UnterschiedlicheArtikelException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class ArtikelFachTest {

    private static Artikel artikel;
    private ArtikelFach fach;

    @BeforeAll
    static void setUpClass() {
        artikel = new Artikel("Apfelsaft", 120);
    }

    @BeforeEach
    void setUp() {
        fach = new ArtikelFach(artikel);
    }

    @Test
    void testFuellen() throws UnterschiedlicheArtikelException {

        // Keine Artikel im Fach
        Assertions.assertFalse(fach.istArtikelEinheitVorhanden());

        // 10 Einheiten erzeugen und Fach Fuellen
        fach.fuellen(generiereArtikelEinheiten(artikel, 10));
        Assertions.assertTrue(fach.istArtikelEinheitVorhanden());

        // Falschen Artikel ins Fach legen
        Assertions.assertThrows(UnterschiedlicheArtikelException.class, () -> fach.fuellen(new ArtikelEinheit(new Artikel("FalscherArtikel", 0))));
    }

    private ArtikelEinheit[] generiereArtikelEinheiten(Artikel artikel, int anzahl) {
        ArtikelEinheit[] ergebnis = new ArtikelEinheit[anzahl];
        for(int i = 0; i < anzahl; i++) {
            ergebnis[i] = new ArtikelEinheit(artikel);
        }
        return ergebnis;
    }

    @Test
    void testEntnehmen() throws UnterschiedlicheArtikelException, KeineArtikelEinheitenImFachException {
        // Eine Einheit erzeugen, ins Fach legen und wieder entnehmen
        fach.fuellen(new ArtikelEinheit(artikel));
        ArtikelEinheit entnommeneEinheit = fach.entnehmen();
        Assertions.assertNotNull(entnommeneEinheit);
        Assertions.assertEquals(artikel, entnommeneEinheit.holeArtikel());

        // Pruefen, dass keine Artikel mehr im Fach sind
        Assertions.assertFalse(fach.istArtikelEinheitVorhanden());
        Assertions.assertThrows(KeineArtikelEinheitenImFachException.class, () -> fach.entnehmen());
    }

    @Test
    void testLeeren() throws UnterschiedlicheArtikelException {
        fach.fuellen(generiereArtikelEinheiten(artikel, 2));
        Assertions.assertTrue(fach.istArtikelEinheitVorhanden());
        fach.leeren();
        Assertions.assertFalse(fach.istArtikelEinheitVorhanden());
    }

    @Test
    void testArtikelSetzen() throws FachNichtLeerException, UnterschiedlicheArtikelException {
        Artikel testArtikel = new Artikel("Test Artikel", 10);
        fach.setzeArtikel(testArtikel);
        Assertions.assertEquals(testArtikel, fach.holeArtikel());

        fach.fuellen(new ArtikelEinheit(testArtikel));
        Assertions.assertThrows(FachNichtLeerException.class, () -> fach.setzeArtikel(artikel));

        fach.leeren();
        fach.setzeArtikel(artikel);
        Assertions.assertEquals(artikel, fach.holeArtikel());
    }

}
