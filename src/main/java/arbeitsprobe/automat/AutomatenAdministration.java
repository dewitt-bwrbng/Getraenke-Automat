package arbeitsprobe.automat;

import arbeitsprobe.artikel.ArtikelEinheit;
import arbeitsprobe.artikel.ArtikelManager;
import arbeitsprobe.exceptions.FachNichtZugewiesenException;
import arbeitsprobe.exceptions.MindestwertUnterschrittenException;
import arbeitsprobe.exceptions.UngueltigesFachException;
import arbeitsprobe.exceptions.UnterschiedlicheArtikelException;
import arbeitsprobe.geld.GeldSpeicher;
import arbeitsprobe.geld.Muenze;

public interface AutomatenAdministration {

    public void muenzfachFuellen(Muenze muenze, int anzahl) throws MindestwertUnterschrittenException;
    public int holeGesamtbetragInMuenzfach();
    public GeldSpeicher muenzfachLeeren() throws MindestwertUnterschrittenException;
    public void fachFuellen(int fachIndex, ArtikelEinheit... einheiten) throws UngueltigesFachException, FachNichtZugewiesenException, UnterschiedlicheArtikelException;
    public void faecherLeeren();
    public ArtikelManager holeArtikelManager();

}
