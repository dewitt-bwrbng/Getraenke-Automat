package arbeitsprobe.artikel;

import arbeitsprobe.exceptions.FachNichtLeerException;
import arbeitsprobe.exceptions.KeineArtikelEinheitenImFachException;
import arbeitsprobe.exceptions.UnterschiedlicheArtikelException;

import java.util.*;
import java.util.stream.Collectors;

public class ArtikelFach {

    private Artikel artikel;
    private Queue<ArtikelEinheit> artikelEinheiten;

    public ArtikelFach(Artikel artikel) {
        this.artikel = artikel;
        artikelEinheiten = new LinkedList<>();
    }

    public Artikel holeArtikel() {
        return artikel;
    }

    public void fuellen(ArtikelEinheit... einheiten) throws UnterschiedlicheArtikelException {
        // Pruefen, ob Artikeleinheiten mit einem unpassenden Artikel uebergeben wurden
        List<ArtikelEinheit> ungueltigeArtikelEinheiten = Arrays.stream(einheiten).filter(e -> !e.holeArtikel().equals(artikel)).collect(Collectors.toList());
        if(ungueltigeArtikelEinheiten.size() != 0) {
            throw new UnterschiedlicheArtikelException(artikel, ungueltigeArtikelEinheiten.get(0).holeArtikel());
        }

        artikelEinheiten.addAll(Arrays.asList(einheiten));
    }

    public ArtikelEinheit entnehmen() throws KeineArtikelEinheitenImFachException {
        if(artikelEinheiten.size() == 0) {
            throw new KeineArtikelEinheitenImFachException(artikel);
        }

        return artikelEinheiten.poll();
    }

    public void leeren() {
        artikelEinheiten.clear();
    }

    public boolean istArtikelEinheitVorhanden() {
        return !artikelEinheiten.isEmpty();
    }

    public void setzeArtikel(Artikel artikel) throws FachNichtLeerException {
        if(this.artikel.equals(artikel)) {
            // Nichts zu tun, da sich der Artikel nicht geaendert hat
            return;
        }

        if(istArtikelEinheitVorhanden()) {
            throw new FachNichtLeerException();
        }

        this.artikel = artikel;
    }


}
