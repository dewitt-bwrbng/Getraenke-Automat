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

public class AutomatenAdministrationTest {

    private AutomatenAdministration automat;
    private Artikel artikel;

    @BeforeEach
    void setUp() throws UngueltigerPreisException, UngueltigesFachException, FachNichtLeerException {
        automat = new GetraenkeAutomatImpl("TestAutomat", 3);
        ArtikelManager manager = automat.holeArtikelManager();
        artikel = manager.erzeugeArtikel("Apfelsaft", 120);
        manager.setzeArtikelInFach(0, artikel);
    }

    @Test
    void testMuenzfachFuellen() throws MindestwertUnterschrittenException {
        automat.muenzfachFuellen(Muenze.Euro1, 10);
        Assertions.assertEquals(Muenze.Euro1.holeWert() * 10, automat.holeGesamtbetragInMuenzfach());
        automat.muenzfachFuellen(Muenze.Cent10, 3);
        Assertions.assertEquals((Muenze.Euro1.holeWert() * 10) + Muenze.Cent10.holeWert() * 3, automat.holeGesamtbetragInMuenzfach());
    }

    @Test
    void testMuenzfachLeeren() throws MindestwertUnterschrittenException {
        automat.muenzfachFuellen(Muenze.Euro1, 10);
        Assertions.assertEquals(Muenze.Euro1.holeWert() * 10, automat.holeGesamtbetragInMuenzfach());

        GeldSpeicher entnommeneMuenzen = automat.muenzfachLeeren();
        Assertions.assertEquals(0, automat.holeGesamtbetragInMuenzfach());
        Assertions.assertEquals(Muenze.Euro1.holeWert() * 10, entnommeneMuenzen.holeGesamtbetrag());
    }

    @Test
    void testFachFuellen() throws UngueltigesFachException, FachNichtZugewiesenException, UnterschiedlicheArtikelException {
        ArtikelManager manager = automat.holeArtikelManager();
        ArtikelEinheit artikelEinheit = new ArtikelEinheit(artikel);

        Assertions.assertFalse(manager.hatArtikelEinheitImFach(0));
        automat.fachFuellen(0, artikelEinheit);
        Assertions.assertTrue(manager.hatArtikelEinheitImFach(0));

        Assertions.assertThrows(UngueltigesFachException.class, () -> automat.fachFuellen(-1, artikelEinheit));
        Assertions.assertThrows(UngueltigesFachException.class, () -> automat.fachFuellen(3, artikelEinheit));

        Assertions.assertThrows(FachNichtZugewiesenException.class, () -> automat.fachFuellen(1, artikelEinheit));
    }

    @Test
    void testFaecherLeeren() throws UngueltigesFachException, FachNichtZugewiesenException, UnterschiedlicheArtikelException, FachNichtLeerException {
        ArtikelManager manager = automat.holeArtikelManager();
        automat.fachFuellen(0, new ArtikelEinheit(artikel));
        Assertions.assertTrue(manager.hatArtikelEinheitImFach(0));

        manager.setzeArtikelInFach(1, artikel);
        automat.fachFuellen(1, new ArtikelEinheit(artikel));
        Assertions.assertTrue(manager.hatArtikelEinheitImFach(1));

        automat.faecherLeeren();
        Assertions.assertThrows(FachNichtZugewiesenException.class, () -> manager.holeArtikelInFach(0));
        Assertions.assertThrows(FachNichtZugewiesenException.class, () -> manager.holeArtikelInFach(1));
    }

}
