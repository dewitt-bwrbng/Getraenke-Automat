import exceptions.MindestwertUnterschrittenException;

import java.util.HashMap;
import java.util.Map;

public class Geldspeicher {

    private Map<Muenze, Integer> muenzen;

    public Geldspeicher() {
        muenzen = new HashMap<>();
    }

    public void hinzufuegen(Muenze muenze) {

    }

    public void hinzufuegen(Muenze muenze, int anzahl) throws MindestwertUnterschrittenException {

    }

    public int holeGesamtwert() {
        return 0;
    }

    public boolean muenzeVorhanden(Muenze muenze) {
        return false;
    }

    public Geldspeicher entnehmeGeld(int wert) {
        return null;
    }



}
