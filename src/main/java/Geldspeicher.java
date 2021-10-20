import exceptions.MindestwertUnterschrittenException;

import java.util.HashMap;
import java.util.Map;

public class Geldspeicher {

    private Map<Muenze, Integer> muenzen;

    public Geldspeicher() {
        muenzen = new HashMap<>();
    }

    public void hinzufuegen(Muenze muenze) {
        try {
            hinzufuegen(muenze, 1);
        } catch (MindestwertUnterschrittenException e) {
            throw new RuntimeException("Unerwarteter Fehler. Das hinzufuegen einer Muenze sollte nicht fehlschlagen, da die Anzahl fix auf 1 gesetzt ist und der Mindestwert somit nicht untschritten.", e);
        }
    }

    public void hinzufuegen(Muenze muenze, int anzahl) throws MindestwertUnterschrittenException {
        if(anzahl < 1) {
            throw new MindestwertUnterschrittenException(anzahl, 1);
        }

        int vorherigeAnzahl = 0;
        if(muenzen.containsKey(muenze)) {
            vorherigeAnzahl = muenzen.get(muenze);
        }

        int neueAnzahl = vorherigeAnzahl + anzahl;
        muenzen.put(muenze, neueAnzahl);
    }

    public int holeBetragFuerMuenze(Muenze muenze) {
        int betrag = 0;

        if(muenzen.containsKey(muenze)) {
            betrag = muenzen.get(muenze) * muenze.holeWert();
        }

        return betrag;
    }

    public int holeGesamtbetrag() {
        return muenzen.keySet().stream().mapToInt(this::holeBetragFuerMuenze).sum();
    }

    public boolean muenzeVorhanden(Muenze muenze) {
        return false;
    }

    public Geldspeicher entnehmeGeld(int wert) {
        return null;
    }



}
