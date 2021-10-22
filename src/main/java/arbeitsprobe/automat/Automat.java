package arbeitsprobe.automat;

import arbeitsprobe.artikel.ArtikelEinheit;
import arbeitsprobe.exceptions.*;
import arbeitsprobe.geld.Muenze;

public interface Automat {

    public void muenzeEinwerfen(Muenze... muenzen);
    public int holeGesamtBetragMuenzEinwurf();
    public KaufErgebnis abbrechen() throws MindestwertUnterschrittenException;
    public boolean istKaufbar(int fachIndex) throws UngueltigesFachException, FachNichtZugewiesenException;
    public KaufErgebnis kaufen(int fachIndex) throws KaufFehlgeschlagenException;

}
