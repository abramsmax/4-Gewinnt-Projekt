public class ZugNichtMoeglichExeption extends Exception{
    private int spalte;

    public ZugNichtMoeglichExeption(int spalte)
    {
        super();
        this.spalte = spalte;
    }

    public int getSpalte()
    {
        return spalte;
    }
}
