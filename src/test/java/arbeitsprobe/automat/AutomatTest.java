package arbeitsprobe.automat;

import arbeitsprobe.artikel.Artikel;
import arbeitsprobe.artikel.ArtikelEinheit;
import arbeitsprobe.artikel.ArtikelManager;
import arbeitsprobe.exceptions.*;
import arbeitsprobe.geld.GeldSpeicher;
import arbeitsprobe.geld.Muenze;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class AutomatTest {

    private Automat getraenkeAutomat;
    private Artikel artikel;

    @BeforeEach
    void setUp() throws UngueltigerPreisException, UngueltigesFachException, FachNichtLeerException {
        getraenkeAutomat = new GetraenkeAutomatImpl("TestAutomat", 3);
        ArtikelManager manager = ((AutomatenAdministration)getraenkeAutomat).holeArtikelManager();
        artikel = manager.erzeugeArtikel("Apfelsaft", 120);
        manager.setzeArtikelInFach(0, artikel);
    }


    @Test
    void testMuenzeEinwerfen() {
        Assertions.assertEquals(0, getraenkeAutomat.holeGesamtBetragMuenzEinwurf());
        getraenkeAutomat.muenzeEinwerfen(Muenze.Euro1);
        Assertions.assertEquals(100, getraenkeAutomat.holeGesamtBetragMuenzEinwurf());

        getraenkeAutomat.muenzeEinwerfen(Muenze.Euro2, Muenze.Cent50, Muenze.Cent20, Muenze.Cent10);
        Assertions.assertEquals(380, getraenkeAutomat.holeGesamtBetragMuenzEinwurf());
    }

    @Test
    void testAbbrechen() throws MindestwertUnterschrittenException {
        getraenkeAutomat.muenzeEinwerfen(Muenze.Euro1, Muenze.Cent20);
        KaufErgebnis ergebnis = getraenkeAutomat.abbrechen();
        Assertions.assertNull(ergebnis.holeArtikelEinheit());
        Assertions.assertEquals(120, ergebnis.holeWechselgeld().holeGesamtbetrag());
        Assertions.assertEquals(0, getraenkeAutomat.holeGesamtBetragMuenzEinwurf());
    }

    @Test
    void testIstKaufbar() throws UngueltigesFachException, FachNichtZugewiesenException, UnterschiedlicheArtikelException {
        setUpArtikel();
        getraenkeAutomat.muenzeEinwerfen(Muenze.Euro1);
        Assertions.assertFalse(getraenkeAutomat.istKaufbar(0));
        getraenkeAutomat.muenzeEinwerfen(Muenze.Cent20);
        Assertions.assertTrue(getraenkeAutomat.istKaufbar(0));

        Assertions.assertThrows(FachNichtZugewiesenException.class, () -> getraenkeAutomat.istKaufbar(1));
        Assertions.assertThrows(UngueltigesFachException.class, () -> getraenkeAutomat.istKaufbar(-1));
        Assertions.assertThrows(UngueltigesFachException.class, () -> getraenkeAutomat.istKaufbar(3));
     }

    @Test
    void testKaufen() throws UngueltigesFachException, FachNichtZugewiesenException, UnterschiedlicheArtikelException, KaufFehlgeschlagenException {
        setUpArtikel();

        int betragInMuenzfachVorKauf = ((AutomatenAdministration)getraenkeAutomat).holeGesamtbetragInMuenzfach();

        getraenkeAutomat.muenzeEinwerfen(Muenze.Euro1, Muenze.Cent20);
        KaufErgebnis ergebnis = getraenkeAutomat.kaufen(0);
        Assertions.assertNotNull(ergebnis.holeArtikelEinheit());
        Assertions.assertEquals(0, ergebnis.holeWechselgeld().holeGesamtbetrag());
        Assertions.assertEquals(betragInMuenzfachVorKauf + 120, ((AutomatenAdministration)getraenkeAutomat).holeGesamtbetragInMuenzfach());

        // Kein weiterer Artikel
        getraenkeAutomat.muenzeEinwerfen(Muenze.Euro1, Muenze.Cent20);
        Assertions.assertThrows(KaufFehlgeschlagenException.class, () -> getraenkeAutomat.kaufen(0));

        // Weiteren Artikel hinzufuegen
        setUpArtikel();
        getraenkeAutomat.muenzeEinwerfen(Muenze.Euro2);
        Assertions.assertThrows(KaufFehlgeschlagenException.class, () -> getraenkeAutomat.kaufen(0));

        Assertions.assertThrows(KaufFehlgeschlagenException.class, () -> getraenkeAutomat.kaufen(1));
        Assertions.assertThrows(KaufFehlgeschlagenException.class, () -> getraenkeAutomat.kaufen(-1));

    }

    private void setUpArtikel() throws UngueltigesFachException, FachNichtZugewiesenException, UnterschiedlicheArtikelException {
        AutomatenAdministration automat = (AutomatenAdministration)getraenkeAutomat;
        ArtikelEinheit einheit = new ArtikelEinheit(artikel);
        automat.fachFuellen(0, einheit);
    }


}
