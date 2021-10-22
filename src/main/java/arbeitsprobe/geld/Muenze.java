package arbeitsprobe.geld;

public enum Muenze {
    Cent10(10),
    Cent20(20),
    Cent50(50),
    Euro1(100),
    Euro2(200);

    private int wert;

    Muenze(int wert) {
        this.wert = wert;
    }

    public int holeWert() {
        return wert;
    }
}
