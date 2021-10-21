package arbeitsprobe.automat.geld;

import arbeitsprobe.automat.artikel.Artikel;
import arbeitsprobe.automat.exceptions.GeldEntnahmeNichtMoeglichException;
import arbeitsprobe.automat.exceptions.MindestwertUnterschrittenException;
import arbeitsprobe.automat.exceptions.MuenzeNichtVorhandenException;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class GeldSpeicher {

    private final Map<Muenze, Integer> muenzen;

    public GeldSpeicher() {
        muenzen = new HashMap<>();
    }

    public void hinzufuegen(Muenze muenze) {
        try {
            hinzufuegen(muenze, 1);
        } catch (MindestwertUnterschrittenException e) {
            throw new RuntimeException("Unerwarteter Fehler. Das hinzufuegen einer arbeitsprobe.automat.geld.Muenze sollte nicht fehlschlagen, da die Anzahl fix auf 1 gesetzt ist und der Mindestwert somit nicht untschritten.", e);
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

    public boolean istMuenzeVorhanden(Muenze muenze) {
        return muenzen.containsKey(muenze) && muenzen.get(muenze) > 0;
    }

    public boolean istEntnahmeMoeglich(int wert) {
        if(wert < 0) {
            return false;
        }

        if(holeGesamtbetrag() < wert) {
            return false;
        }

        // Da die Wertigkeit der kleinsten Muenze 10 Cent betraegt muss der zu entnehmende Wert durch 10 Teilbar sein
        return wert % 10 == 0;
    }

    private void entnehmeMuenze(Muenze muenze) throws MuenzeNichtVorhandenException {
        if(!istMuenzeVorhanden(muenze)) {
            throw new MuenzeNichtVorhandenException(muenze);
        }

        muenzen.put(muenze, muenzen.get(muenze) -1);
    }

    public GeldSpeicher entnehmeGeld(int wert) throws GeldEntnahmeNichtMoeglichException {
        if(!istEntnahmeMoeglich(wert)) {
            throw new GeldEntnahmeNichtMoeglichException(wert);
        }

        GeldSpeicher entnahme = new GeldSpeicher();

        // Damit der entsprechende Wert mit den vorhanden Muenzen entommen werden kann,
        // wird durch alle muenzen in absteigender Wertigkeit iteriert. Damit werden so wenig einzelne Muenzen wie moeglich entnommen
        Iterator<Muenze> iterator = muenzen.keySet().stream().sorted((muenze1, muenze2) -> Integer.compare(muenze1.holeWert(), muenze2.holeWert()) * -1).iterator();

        try {
            while(iterator.hasNext()) {
                Muenze muenze = iterator.next();

                while(wert > 0 && istMuenzeVorhanden(muenze) && muenze.holeWert() <= wert) {
                    entnehmeMuenze(muenze);
                    wert -= muenze.holeWert();
                    entnahme.hinzufuegen(muenze);
                }
            }
        } catch (MuenzeNichtVorhandenException e) {
            // Bei der Berechnung der Muenzen gab es einen Fehler.
            // Daher muessen die bereits entnommenen Muenzen wieder zurueck transferiert werden
            try {
                entnahme.uebertragen(this);
            } catch (MindestwertUnterschrittenException e1) {
                // Es gab beim Uebertrag einen Fehler.
                // In der Regel sollte dies nicht passieren, doch scheinbar war die Anzahl einer Muenze negativ.
                throw new GeldEntnahmeNichtMoeglichException(wert, e1);
            }
            throw new GeldEntnahmeNichtMoeglichException(wert, e);
        }

        return entnahme;
    }

    public void uebertragen(GeldSpeicher ziel) throws MindestwertUnterschrittenException {
        Iterator<Muenze> iterator = muenzen.keySet().iterator();
        while(iterator.hasNext()) {
            Muenze muenze = iterator.next();
            ziel.hinzufuegen(muenze, muenzen.get(muenze));
            iterator.remove();
        }
    }





}
