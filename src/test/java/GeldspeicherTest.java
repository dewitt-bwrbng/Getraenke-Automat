import exceptions.MindestwertUnterschrittenException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertThrows;

public class GeldspeicherTest {

    private Geldspeicher geldspeicher;
    private int initialerGesamtwert;

    @BeforeEach
    public void setUp() throws MindestwertUnterschrittenException {
        // initialen gesamtwert zuruecksetzen
        initialerGesamtwert = 0;

        // Geldspeicher erzeugen und mit je zwei Muenzen befuellen
        geldspeicher = new Geldspeicher();
        for(Muenze muenze : Muenze.values()) {
            geldspeicher.hinzufuegen(muenze, 2);
            initialerGesamtwert += muenze.holeWert() * 2;
        }
    }

    @Test
    void hinzufuegen() throws MindestwertUnterschrittenException {
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

}
