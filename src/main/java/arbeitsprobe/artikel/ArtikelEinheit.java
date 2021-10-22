package arbeitsprobe.artikel;

import java.util.Objects;

public class ArtikelEinheit {

    private Artikel artikel;

    public ArtikelEinheit(Artikel artikel) {
        this.artikel = artikel;
    }

    public Artikel holeArtikel() {
        return artikel;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ArtikelEinheit einheit = (ArtikelEinheit) o;
        return Objects.equals(artikel, einheit.artikel);
    }

    @Override
    public int hashCode() {
        return Objects.hash(artikel);
    }
}
