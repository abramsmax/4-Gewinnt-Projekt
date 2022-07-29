public class Spielfeld {
    private int [][] felder;
    private int zeilen;
    private int spalten;
    private int sieger = 0;

    /**
     * @param zeilen
     * @param spalten
     * Erzeugt ein Spielfeld in der angegebenen Größe.
     */
    public Spielfeld(int zeilen, int spalten) {
        felder = new int[zeilen][spalten];
        this.zeilen = zeilen;
        this.spalten = spalten;
    }

    /**
     * @param spalte
     * @param spieler
     * @throws ZugNichtMoeglichExeption
     * @return zeile
     * Versucht für den angegebenen Spieler einen "Stein" in das Spielfeld zu werfen und gibt die Zeile zurück, in der der Stein gelandet ist.
     * Ist die Spalte bereits voll, wird ein Fehler zurückgegeben.
     */
    public int setSpielzug(int spalte, int spieler) throws ZugNichtMoeglichExeption
    {
        boolean zuggespechiert = false;
        int zeile =0;
        //sucht das unterste freie Feld in der Spalte
        while(zeile<zeilen)
        {
            if(felder[zeile][spalte]==0)
            {
                felder[zeile][spalte]=spieler;
                zuggespechiert=true;
                break;
            }
            zeile++;
        }
        if(!zuggespechiert)
        {
            throw new ZugNichtMoeglichExeption(spalte);
        }
        this.ermittleSieger(spalte,zeile,spieler);
        // Rückgabe der Zeile, in die der Stein eingeworfen wurde
        return zeile;
    }

    /**
     * @return isUnentschieden
     * Gibt true zurück, wenn es keinen Sieger gibt und kein Zug mehr möglich ist
     */
    public boolean isUnentschieden() {
        boolean isVoll = true;
        for(int s =0; s<spalten; s++)
        {
            for(int z =0; z<zeilen; z++ )
            {
                if(felder[z][s]==0)
                {
                    isVoll = false;
                    break;
                }
            }
        }
        return sieger == 0 && isVoll;
    }

    /**
     * @return Sieger
     * Gibt den Sieger des Spiels zurück. Existiert noch kein Sieger, wird 0 zurückgeben.
     */
    public int getSieger()
    {
        return sieger;
    }

    /*
    Überprüft ob der Spieler durch seinen wurf in die zeile und spale gewonnen hat.
     */
    private void ermittleSieger(int spalte, int zeile, int spieler)
    {
        if(this.isSiegerHorizontal(zeile,spieler) ||
            this.isSiegerVertikal(spalte,spieler) ||
            this.isSiegerDiagonal(spalte,zeile,spieler))
        {
            sieger = spieler;
        }

    }
    /*
  Überprüft ob der Spieler in einer der beiden diagonalen Linien um den angegebenen Punkt gewonnen hat
   */
    private boolean isSiegerDiagonal(int spalte, int zeile, int spieler) {
        int zaehler =0;
        //Aufsteigende Diagonale
        int startZ = zeile;
        int startS = spalte;
        while(startS>0 && startZ>0)
        {
            startS--;
            startZ--;
        }
        while(startS<spalten && startZ<zeilen)
        {
            if(felder[startZ][startS]==spieler)
            {
                zaehler++;
            }
            else
            {
                zaehler =0;
            }
            if(zaehler==4)
            {
                return true;
            }
            startS++;
            startZ++;
        }
        //Absteigende Diagonale
        startZ = zeile;
        startS = spalte;
        while(startS>0 && startZ<zeilen-1)
        {
            startS--;
            startZ++;
        }
        zaehler = 0;
        while(startS<spalten && startZ>=0)
        {
            if(felder[startZ][startS]==spieler)
            {
                zaehler++;
            }
            else
            {
                zaehler =0;
            }
            if(zaehler==4)
            {
                return true;
            }
            startS++;
            startZ--;
        }
        return false;
    }
    /*
      Überprüft ob der Spieler in der Vertikalen Linie gewonnen hat
       */
    private boolean isSiegerVertikal(int spalte, int spieler) {
        int zaehler =0;
        for(int z = 0; z<zeilen; z++)
        {
            if(felder[z][spalte]==spieler)
            {
                zaehler++;
            }
            else
            {
                zaehler =0;
            }
            if(zaehler==4)
            {
                return true;
            }
        }
        return false;
    }

    /*
    Überprüft ob der Spieler in der Horizontalen Linie gewonnen hat
     */
    private boolean isSiegerHorizontal(int zeile, int spieler) {
        int zaehler =0;
        for(int s = 0; s<spalten; s++)
        {
            if(felder[zeile][s]==spieler)
            {
                zaehler++;
            }
            else
            {
                zaehler =0;
            }
            if(zaehler==4)
            {
                return true;
            }
        }
        return false;
    }
}
