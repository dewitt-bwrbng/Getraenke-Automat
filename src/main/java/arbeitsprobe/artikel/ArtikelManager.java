package arbeitsprobe.artikel;

import arbeitsprobe.exceptions.*;

public class ArtikelManager {

    private final ArtikelFach[] artikelFaecher;

    public ArtikelManager(int anzahlFaecher) {
        artikelFaecher = new ArtikelFach[anzahlFaecher];
    }

    public Artikel erzeugeArtikel(String name, int preis) throws UngueltigerPreisException {
        if(name == null) {
            throw new NullPointerException("Name ist nicht gesetzt.");
        }

        // Pruefen, ob ein gültiger Preis uebergeben wurde
        if(preis <= 0 || preis % 10 != 0) {
            throw new UngueltigerPreisException(preis);
        }

        return new Artikel(name, preis);
    }

    private boolean existiertFach(int fachIndex) {
        return fachIndex >= 0 && fachIndex < artikelFaecher.length;
    }

    public void setzeArtikelInFach(int fachIndex, Artikel artikel) throws UngueltigesFachException, FachNichtLeerException {
        if(!existiertFach(fachIndex)) {
            throw new UngueltigesFachException(fachIndex);
        }

        if(artikel == null) {
            throw new NullPointerException("Artikel ist nicht gesetzt.");
        }

        ArtikelFach artikelFach = artikelFaecher[fachIndex];
        if(artikelFach == null) {
            artikelFach = new ArtikelFach(artikel);
            artikelFaecher[fachIndex] = artikelFach;
        }

        artikelFach.setzeArtikel(artikel);
    }

    public Artikel holeArtikelInFach(int fachIndex) throws UngueltigesFachException, FachNichtZugewiesenException {
        ArtikelFach fach = holeArtikelFach(fachIndex);
        Artikel artikel = fach.holeArtikel();
        if(artikel == null) {
            throw new FachNichtZugewiesenException(fachIndex);
        }

        return artikel;
    }

    public ArtikelEinheit entnehmeArtikelEinheitAusFach(int fachIndex) throws UngueltigesFachException, FachNichtZugewiesenException, KeineArtikelEinheitenImFachException {
        return holeArtikelFach(fachIndex).entnehmen();
    }

    public boolean hatArtikelEinheitImFach(int fachIndex) throws UngueltigesFachException, FachNichtZugewiesenException {
        ArtikelFach fach = holeArtikelFach(fachIndex);
        return fach.istArtikelEinheitVorhanden();
    }

    private ArtikelFach holeArtikelFach(int fachIndex) throws UngueltigesFachException, FachNichtZugewiesenException {
        if(!existiertFach(fachIndex)) {
            throw new UngueltigesFachException(fachIndex);
        }

        ArtikelFach artikelFach = artikelFaecher[fachIndex];
        if(artikelFach == null) {
            throw new FachNichtZugewiesenException(fachIndex);
        }

        return artikelFach;
    }

    public void alleFaecherLeeren() {
        for (ArtikelFach artikelFach : artikelFaecher) {

            // Nicht zugewiesene Faecher werden uebersprungen.
            if(artikelFach == null) {
                continue;
            }

            artikelFach.leeren();

            try {
                artikelFach.setzeArtikel(null);
            } catch (FachNichtLeerException e) {
                // An dieser Stelle fangen wir FachNichtLeerException ganz bewusst und erzeugen eine RuntimeException, denn
                // scheinbar ist ein Fehler in der leeren() Implmenentierung oder es handelt sich um einen Fehler in der Laufzeit.
                throw new RuntimeException("Es konnten nicht alle Faecher geleert werden.", e);
            }

        }
    }

    public void auffuellen(int fachIndex, ArtikelEinheit... artikelEinheiten) throws UngueltigesFachException, FachNichtZugewiesenException, UnterschiedlicheArtikelException {
        if(artikelEinheiten == null) {
            throw new NullPointerException("artikelEinheiten ist nicht gesetzt.");
        }
        ArtikelFach artikelFach = holeArtikelFach(fachIndex);
        artikelFach.fuellen(artikelEinheiten);
    }



}
