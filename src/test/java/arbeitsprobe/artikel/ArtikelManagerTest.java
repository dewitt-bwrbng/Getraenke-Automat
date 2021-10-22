package arbeitsprobe.artikel;

import arbeitsprobe.exceptions.*;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class ArtikelManagerTest {

    private ArtikelManager manager;

    private static Artikel artikel1;
    private static Artikel artikel2;

    @BeforeAll
    static void setUpClass() {
        // Fuer den Test werden Artikel manuell erzeugt
        artikel1 = new Artikel("Apfelsaft", 100);
        artikel2 = new Artikel("Orangensaft", 150);
    }

    @BeforeEach
    void setUp() throws UngueltigesFachException, FachNichtLeerException {
        manager = new ArtikelManager(3);
        manager.setzeArtikelInFach(0, artikel1);
        manager.setzeArtikelInFach(1, artikel2);
    }

    @Test
    void testErzeugeArtikel() throws UngueltigerPreisException {
        Assertions.assertThrows(NullPointerException.class, () -> manager.erzeugeArtikel(null, 0));
        Assertions.assertThrows(UngueltigerPreisException.class, () -> manager.erzeugeArtikel("Testartikel", -1));
        Artikel artikel = manager.erzeugeArtikel("Testartikel", 10);
        Assertions.assertNotNull(artikel);
    }

    @Test
    void testArtikelHinzufuegen() throws UngueltigerPreisException, UngueltigesFachException, FachNichtLeerException, FachNichtZugewiesenException, UnterschiedlicheArtikelException {
        Assertions.assertThrows(UngueltigesFachException.class, () -> manager.setzeArtikelInFach(-1, artikel1));
        Assertions.assertThrows(UngueltigesFachException.class, () -> manager.setzeArtikelInFach(3, artikel1));
        Assertions.assertThrows(NullPointerException.class, () -> manager.setzeArtikelInFach(2, null));
        manager.setzeArtikelInFach(2, manager.erzeugeArtikel("Testartikel", 10));

        Assertions.assertThrows(NullPointerException.class, () -> manager.auffuellen(0, (ArtikelEinheit) null));

        // Fach fuellen und versuchen Artikel zu setzen
        manager.auffuellen(0, new ArtikelEinheit(artikel1));
        Assertions.assertThrows(FachNichtLeerException.class, () -> manager.setzeArtikelInFach(0, artikel2));
    }

    @Test
    void testHoleArtikelInFach() throws UngueltigesFachException, FachNichtZugewiesenException {
        Assertions.assertEquals(artikel1, manager.holeArtikelInFach(0));
        Assertions.assertEquals(artikel2, manager.holeArtikelInFach(1));

        Assertions.assertThrows(UngueltigesFachException.class, () -> manager.holeArtikelInFach(-1));
        Assertions.assertThrows(UngueltigesFachException.class, () -> manager.holeArtikelInFach(3));
        Assertions.assertThrows(UngueltigesFachException.class, () -> manager.holeArtikelInFach(Integer.MIN_VALUE));
        Assertions.assertThrows(UngueltigesFachException.class, () -> manager.holeArtikelInFach(Integer.MAX_VALUE));

        Assertions.assertThrows(FachNichtZugewiesenException.class, () -> manager.holeArtikelInFach(2));
    }

    @Test
    void testHatArtikelEinheitenImFach() throws UngueltigesFachException, FachNichtZugewiesenException, UnterschiedlicheArtikelException {
        manager.auffuellen(0, new ArtikelEinheit(artikel1));
        Assertions.assertTrue(manager.hatArtikelEinheitImFach(0));
        Assertions.assertFalse(manager.hatArtikelEinheitImFach(1));

        Assertions.assertThrows(UngueltigesFachException.class, () -> manager.hatArtikelEinheitImFach(-1));
        Assertions.assertThrows(UngueltigesFachException.class, () -> manager.hatArtikelEinheitImFach(3));
        Assertions.assertThrows(UngueltigesFachException.class, () -> manager.hatArtikelEinheitImFach(Integer.MIN_VALUE));
        Assertions.assertThrows(UngueltigesFachException.class, () -> manager.hatArtikelEinheitImFach(Integer.MAX_VALUE));

        Assertions.assertThrows(FachNichtZugewiesenException.class, () -> manager.hatArtikelEinheitImFach(2));
    }

    @Test
    void testEntnehmeArtikelEinheit() throws UngueltigesFachException, FachNichtZugewiesenException, UnterschiedlicheArtikelException, KeineArtikelEinheitenImFachException {
        manager.auffuellen(0, new ArtikelEinheit(artikel1));
        ArtikelEinheit einheit = manager.entnehmeArtikelEinheitAusFach(0);
        Assertions.assertNotNull(einheit);

        Assertions.assertThrows(KeineArtikelEinheitenImFachException.class, () -> manager.entnehmeArtikelEinheitAusFach(0));
        Assertions.assertThrows(KeineArtikelEinheitenImFachException.class, () -> manager.entnehmeArtikelEinheitAusFach(1));

        Assertions.assertThrows(UngueltigesFachException.class, () -> manager.entnehmeArtikelEinheitAusFach(-1));
        Assertions.assertThrows(UngueltigesFachException.class, () -> manager.entnehmeArtikelEinheitAusFach(3));
        Assertions.assertThrows(UngueltigesFachException.class, () -> manager.entnehmeArtikelEinheitAusFach(Integer.MIN_VALUE));
        Assertions.assertThrows(UngueltigesFachException.class, () -> manager.entnehmeArtikelEinheitAusFach(Integer.MAX_VALUE));

        Assertions.assertThrows(FachNichtZugewiesenException.class, () -> manager.entnehmeArtikelEinheitAusFach(2));
    }

    @Test
    void testAlleFaecherLeeren() throws UngueltigesFachException, FachNichtZugewiesenException, UnterschiedlicheArtikelException {
        manager.auffuellen(0, new ArtikelEinheit(artikel1));
        manager.auffuellen(1, new ArtikelEinheit(artikel2));

        Assertions.assertTrue(manager.hatArtikelEinheitImFach(0));
        Assertions.assertTrue(manager.hatArtikelEinheitImFach(1));
        manager.alleFaecherLeeren();
        Assertions.assertFalse(manager.hatArtikelEinheitImFach(0));
        Assertions.assertFalse(manager.hatArtikelEinheitImFach(1));
    }

    @Test
    void testAuffuellen() throws UngueltigesFachException, FachNichtZugewiesenException, UnterschiedlicheArtikelException, KeineArtikelEinheitenImFachException {
        ArtikelEinheit einheit = new ArtikelEinheit(artikel1);
        Artikel testArtikel = new Artikel("TestArtikel", 10);

        Assertions.assertFalse(manager.hatArtikelEinheitImFach(0));
        manager.auffuellen(0, einheit);
        Assertions.assertTrue(manager.hatArtikelEinheitImFach(0));
        Assertions.assertFalse(manager.hatArtikelEinheitImFach(1));

        ArtikelEinheit entnommeneEinheit = manager.entnehmeArtikelEinheitAusFach(0);
        Assertions.assertEquals(einheit, entnommeneEinheit);

        Assertions.assertThrows(UngueltigesFachException.class, () -> manager.auffuellen(-1, einheit));
        Assertions.assertThrows(UngueltigesFachException.class, () -> manager.auffuellen(3, einheit));
        Assertions.assertThrows(UngueltigesFachException.class, () -> manager.auffuellen(Integer.MIN_VALUE, einheit));
        Assertions.assertThrows(UngueltigesFachException.class, () -> manager.auffuellen(Integer.MAX_VALUE, einheit));

        Assertions.assertThrows(FachNichtZugewiesenException.class, () -> manager.auffuellen(2, einheit));

        Assertions.assertThrows(UnterschiedlicheArtikelException.class, () -> manager.auffuellen(0, new ArtikelEinheit(testArtikel)));
    }


}
