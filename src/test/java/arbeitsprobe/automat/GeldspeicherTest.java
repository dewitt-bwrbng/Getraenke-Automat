package arbeitsprobe.automat;

import arbeitsprobe.automat.exceptions.MindestwertUnterschrittenException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertThrows;

public class GeldspeicherTest {

    private GeldSpeicher geldspeicher;
    private int initialerGesamtwert;

    @BeforeEach
    public void setUp() throws MindestwertUnterschrittenException {
        // initialen gesamtwert zuruecksetzen
        initialerGesamtwert = 0;

        // Geldspeicher erzeugen und mit je zwei Muenzen befuellen
        geldspeicher = new GeldSpeicher();
        for(Muenze muenze : Muenze.values()) {
            geldspeicher.hinzufuegen(muenze, 2);
            initialerGesamtwert += muenze.holeWert() * 2;
        }
    }

    @Test
    void testHinzufuegen() throws MindestwertUnterschrittenException {
        int hinzugefuegterWert = 0;

        hinzugefuegterWert += Muenze.Euro1.holeWert();
        geldspeicher.hinzufuegen(Muenze.Euro1);
        Assertions.assertEquals(geldspeicher.holeGesamtbetrag(), initialerGesamtwert + hinzugefuegterWert);

        hinzugefuegterWert += Muenze.Euro1.holeWert() * 2;
        geldspeicher.hinzufuegen(Muenze.Euro1, 2);
        Assertions.assertEquals(geldspeicher.holeGesamtbetrag(), initialerGesamtwert + hinzugefuegterWert);

        assertThrows(MindestwertUnterschrittenException.class, () -> geldspeicher.hinzufuegen(Muenze.Euro1, -1));
        Assertions.assertEquals(geldspeicher.holeGesamtbetrag(), initialerGesamtwert + hinzugefuegterWert);
    }

    @Test
    void testHoleGesamtbetrag() {
        Assertions.assertEquals(geldspeicher.holeGesamtbetrag(), initialerGesamtwert);
    }

    @Test
    void testUebertragen() throws MindestwertUnterschrittenException {
        GeldSpeicher testSpeicher = new GeldSpeicher();
        testSpeicher.hinzufuegen(Muenze.Euro1);

        testSpeicher.uebertragen(geldspeicher);
        Assertions.assertEquals(geldspeicher.holeGesamtbetrag(), initialerGesamtwert + Muenze.Euro1.holeWert());
        Assertions.assertEquals(testSpeicher.holeGesamtbetrag(), 0);

        geldspeicher.uebertragen(testSpeicher);
        Assertions.assertEquals(geldspeicher.holeGesamtbetrag(), 0);
        Assertions.assertEquals(testSpeicher.holeGesamtbetrag(), initialerGesamtwert + Muenze.Euro1.holeWert());
    }



}
