import java.util.LinkedList;
import java.util.List;

public class Vlacek {

    private Vagonek lokomotiva = new Vagonek(VagonekType.LOKOMOTIVA);
    private Vagonek posledni = new Vagonek(VagonekType.POSTOVNI);
    private int delka = 2;

    public Vlacek() {
        lokomotiva.setNasledujici(posledni);
        lokomotiva.setUmisteni(1);
        posledni.setPredchozi(lokomotiva);
        posledni.setUmisteni(2);
    }

    /**
     * Přidávejte vagonky do vlaku
     * Podmínka je že vagonek první třídy musí být vždy řazen za předchozí vagonek toho typu, pokud žádný takový není je řazen rovnou za lokomotivu
     * vagonek 2 třídy musí být vždy řazen až za poslední vagonek třídy první
     * Poštovní vagonek musí být vždy poslední vagonek lokomotivy
     * Při vkládání vagonku nezapomeňte vagonku přiřadit danou pozici ve vlaku
     * !!!!!!! POZOR !!!!!! pokud přidáváte vagonek jinak než na konec vlaku musíte všem následujícím vagonkům zvýšit jejich umístění - doporučuji si pro tento účel vytvořit privátní metodu
     *
     * @param type
     */


    public void pridatVagonek(VagonekType type) {
        Vagonek newvagonek = new Vagonek(type);

        switch (type) {
            case PRVNI_TRIDA:
                newvagonek.setPredchozi(lokomotiva);
                newvagonek.setNasledujici(lokomotiva.getNasledujici());
                lokomotiva.getNasledujici().setPredchozi(newvagonek);
                lokomotiva.setNasledujici(newvagonek);
                nastavumistení();

                delka++;
                break;
            case DRUHA_TRIDA:
                newvagonek.setNasledujici(posledni);
                newvagonek.setPredchozi(posledni.getPredchozi());
                posledni.getPredchozi().setNasledujici(newvagonek);
                posledni.setPredchozi(newvagonek);
                nastavumistení();
                delka++;


                break;
            case JIDELNI:

                pridatJidelniVagonek();
                nastavumistení();

                delka++;


                break;
            case POSTOVNI:

                break;
        }

    }


    public Vagonek getVagonekByIndex(int index) {
        int i = 1;
        Vagonek atIndex = lokomotiva;
        while (i < index) {
            atIndex = atIndex.getNasledujici();
            i++;
        }
        return atIndex;
    }


    /**
     * Touto metodou si můžete vrátit poslední vagonek daného typu
     * Pokud tedy budu chtít vrátit vagonek typu lokomotiva dostanu hned první vagonek
     *
     * @param type
     * @return
     */
    public Vagonek getLastVagonekByType(VagonekType type) {
        Vagonek last = new Vagonek(type);


        return last;
    }

    /**
     * Tato funkce přidá jídelní vagonek za poslední vagonek první třídy, pokud jídelní vagonek za vagonkem první třídy již existuje
     * tak se další vagonek přidá nejblíže středu vagonků druhé třídy
     * tzn: pokud budu mít č osobních vagonků tak zařadím jídelní vagonek za 2 osobní vagónek
     * pokud budu mít osobních vagonků 5 zařadím jídelní vagonek za 3 osobní vagonek
     */
    public void pridatJidelniVagonek() {
        Vagonek jidelni = new Vagonek(VagonekType.JIDELNI);

        for(int i = 0; i < delka; i++){
            if(getVagonekByIndex(i).getType() == VagonekType.JIDELNI){
                int x = getDelkaByType(VagonekType.DRUHA_TRIDA);
                x = x/2;
                Vagonek pomocny = posledni;
                for(int j = 0; j < x ; j++){
                   pomocny = pomocny.getPredchozi();
                }
                pomocny.getPredchozi().setNasledujici(jidelni);
                jidelni.setNasledujici(pomocny);
                jidelni.setPredchozi(pomocny.getPredchozi());
                pomocny.setPredchozi(jidelni);
                nastavumistení();
                delka++;
                return;
            }

        }







        int i = 2;
        while (getVagonekByIndex(i).getType() == VagonekType.PRVNI_TRIDA) {
            i++;

        }
        Vagonek AtIndexi = getVagonekByIndex(i);
        AtIndexi.getPredchozi().setNasledujici(jidelni);
        jidelni.setNasledujici(AtIndexi);
        jidelni.setPredchozi(AtIndexi.getPredchozi());
        AtIndexi.setPredchozi(jidelni);





    }

    public void nastavumistení() {
        Vagonek newVagonek = lokomotiva.getNasledujici();
        for (int i = 0; i < delka; i++) {
            newVagonek.setUmisteni(newVagonek.getPredchozi().getUmisteni() + 1);
            newVagonek = newVagonek.getNasledujici();


        }
    }

    /**
     * Funkce vrátí počet vagonků daného typu
     * Dobré využití se najde v metodě @method(addJidelniVagonek)
     *
     * @param type
     * @return
     */
    public int getDelkaByType(VagonekType type) {
        Vagonek temp = lokomotiva;
        int delkabytype = 0;


        switch (type) {
            case LOKOMOTIVA:
                for (int i = 0; i < delka; i++) {
                    if (temp.getType() == VagonekType.LOKOMOTIVA) {
                        delkabytype++;

                    }
                    temp = temp.getNasledujici();
                }
                break;



                    case PRVNI_TRIDA:
                        for (int i = 0; i < delka; i++) {
                            if (temp.getType() == VagonekType.PRVNI_TRIDA) {
                                delkabytype++;


                            }
                            temp = temp.getNasledujici();
                }

                break;
            case DRUHA_TRIDA:
                for (int i = 0; i < delka; i++) {
                    if (temp.getType() == VagonekType.DRUHA_TRIDA) {
                        delkabytype++;

                    }
                    temp = temp.getNasledujici();
                }


                break;
        }
        return delkabytype;
    }









    /**
     * Hledejte jidelni vagonky
     * @return
     */
    public List<Vagonek> getJidelniVozy() {
        List<Vagonek> jidelniVozy = new LinkedList<>();
        int pomocny = 1;
        for(int i = 0 ; i < delka; i++ ){
            if (getVagonekByIndex(pomocny).getType() == VagonekType.JIDELNI){
                jidelniVozy.add(getVagonekByIndex(pomocny));
            }
            pomocny++;
        }

        return jidelniVozy;
    }

    /**
     * Odebere poslední vagonek daného typu
     * !!!! POZOR !!!!! pokud odebíráme z prostředku vlaku musíme zbývající vagonky projít a snížit jejich umístění ve vlaku
     * @param type
     */
    public void odebratPosledniVagonekByType(VagonekType type) {
        Vagonek jidelni = new Vagonek(VagonekType.JIDELNI);
        switch (type ){
            case JIDELNI:
                Vagonek x = posledni;
                int i = posledni.getUmisteni();
                while(getVagonekByIndex(i).getType() != VagonekType.JIDELNI ){
                    i--;
                }
                if(i < 0){
                    break;
                }
                if(getVagonekByIndex(i).getType() == VagonekType.JIDELNI ){
//                    Vagonek AtIndexi = getVagonekByIndex(i);
//                    AtIndexi.getPredchozi().setNasledujici(jidelni);
//                    jidelni.setNasledujici(AtIndexi);
//                    jidelni.setPredchozi(AtIndexi.getPredchozi());
//                    AtIndexi.setPredchozi(jidelni);


                    Vagonek Atindex = getVagonekByIndex(i);
                    jidelni.getNasledujici().setPredchozi(Atindex);
                    Atindex.setPredchozi(jidelni);
                    Atindex.setNasledujici(jidelni.getNasledujici());
                    jidelni.setNasledujici(Atindex);



                    nastavumistení();
                      delka--;
                }
                break;
        }


    }

    public int getDelka() {

        return delka;
    }

}
