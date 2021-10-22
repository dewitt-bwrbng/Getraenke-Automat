package arbeitsprobe.automat;

import arbeitsprobe.artikel.ArtikelEinheit;
import arbeitsprobe.geld.GeldSpeicher;

public class KaufErgebnis {

    private ArtikelEinheit artikelEinheit;
    private GeldSpeicher wechselgeld;

    public KaufErgebnis(ArtikelEinheit artikelEinheit, GeldSpeicher wechselgeld) {
        this.artikelEinheit = artikelEinheit;
        this.wechselgeld = wechselgeld;
    }

    public ArtikelEinheit holeArtikelEinheit() {
        return artikelEinheit;
    }

    public GeldSpeicher holeWechselgeld() {
        return wechselgeld;
    }
}
