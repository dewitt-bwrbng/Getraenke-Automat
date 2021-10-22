package arbeitsprobe.automat;

import arbeitsprobe.artikel.Artikel;
import arbeitsprobe.artikel.ArtikelEinheit;
import arbeitsprobe.artikel.ArtikelManager;
import arbeitsprobe.exceptions.*;
import arbeitsprobe.geld.GeldSpeicher;
import arbeitsprobe.geld.Muenze;

public class GetraenkeAutomatImpl implements Automat, AutomatenAdministration {

    private final String name;
    private final GeldSpeicher muenzfach;
    private final GeldSpeicher muenzEinwurf;
    private final ArtikelManager artikelManager;

    public GetraenkeAutomatImpl(String name, int anzahlFaecher) {
        this.name = name;
        muenzfach = new GeldSpeicher();
        muenzEinwurf = new GeldSpeicher();
        artikelManager = new ArtikelManager(anzahlFaecher);
    }


    @Override
    public void muenzfachFuellen(Muenze muenze, int anzahl) throws MindestwertUnterschrittenException {
        muenzfach.hinzufuegen(muenze, anzahl);
    }

    @Override
    public int holeGesamtbetragInMuenzfach() {
        return muenzfach.holeGesamtbetrag();
    }

    @Override
    public GeldSpeicher muenzfachLeeren() throws MindestwertUnterschrittenException {
        GeldSpeicher entnommenesGeld = new GeldSpeicher();
        muenzfach.uebertragen(entnommenesGeld);
        return entnommenesGeld;
    }

    @Override
    public void fachFuellen(int fachIndex, ArtikelEinheit... einheiten) throws UngueltigesFachException, FachNichtZugewiesenException, UnterschiedlicheArtikelException {
        artikelManager.auffuellen(fachIndex, einheiten);
    }

    @Override
    public void faecherLeeren() {
        artikelManager.alleFaecherLeeren();
    }

    @Override
    public ArtikelManager holeArtikelManager() {
        return artikelManager;
    }

    @Override
    public void muenzeEinwerfen(Muenze... muenzen) {
        for (Muenze muenze : muenzen) {
            muenzEinwurf.hinzufuegen(muenze);
        }
    }

    @Override
    public int holeGesamtBetragMuenzEinwurf() {
        return muenzEinwurf.holeGesamtbetrag();
    }

    @Override
    public KaufErgebnis abbrechen() throws MindestwertUnterschrittenException {
        GeldSpeicher wechselgeld = new GeldSpeicher();
        muenzEinwurf.uebertragen(wechselgeld);
        return new KaufErgebnis(null, wechselgeld);
    }

    @Override
    public boolean istKaufbar(int fachIndex) throws UngueltigesFachException, FachNichtZugewiesenException {
        if(!artikelManager.hatArtikelEinheitImFach(fachIndex)) {
            return false;
        }

        Artikel artikel = artikelManager.holeArtikelInFach(fachIndex);
        if(holeGesamtBetragMuenzEinwurf() < artikel.holePreis()) {
            return false;
        }

        int erwartetesWechselgeld = holeGesamtBetragMuenzEinwurf() - artikel.holePreis();
        return muenzfach.istEntnahmeMoeglich(erwartetesWechselgeld);
    }

    @Override
    public KaufErgebnis kaufen(int fachIndex) throws KaufFehlgeschlagenException {
        try {
            ArtikelEinheit einheit = artikelManager.entnehmeArtikelEinheitAusFach(fachIndex);
            int wechselgeldBetrag = muenzEinwurf.holeGesamtbetrag() - einheit.holeArtikel().holePreis();
            GeldSpeicher wechselgeld = muenzfach.entnehmeGeld(wechselgeldBetrag);
            muenzEinwurf.uebertragen(muenzfach);
            return new KaufErgebnis(einheit, wechselgeld);
        } catch (MindestwertUnterschrittenException|UngueltigesFachException|FachNichtZugewiesenException|KeineArtikelEinheitenImFachException|GeldEntnahmeNichtMoeglichException e) {
            throw new KaufFehlgeschlagenException(fachIndex, e);
        }
    }
}
