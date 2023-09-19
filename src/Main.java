import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;

// Press Shift twice to open the Search Everywhere dialog and type `show whitespaces`,
// then press Enter. You can now see whitespace characters in your code.
public class Main {
    private int poids_max = 0;
    private ArrayList<Integer> poids = new ArrayList<>();
    private ArrayList<Integer> valeur = new ArrayList<>();

    public Main(String path){
        File file = new File(path);
        try {
            Scanner scanner = new Scanner(file);
            poids_max = scanner.nextInt();
            while(scanner.hasNext()){
                poids.add(scanner.nextInt());
                valeur.add(scanner.nextInt());
            }
        }
        catch(Exception e){
            System.out.println(e);
        }

    }
    public static void main(String[] args) throws FileNotFoundException {
        Main sac0 = new Main("/amuhome/p20007831/Bureau/ARO/ARO-TP2/sacs/sac0");
//        System.out.println(sac0.poids_max);
//        for(int i=0; i<sac0.poids.size(); i++){
//            System.out.println("P: " + sac0.poids.get(i) + " V: " + sac0.valeur.get(i));
//        }
        sac0.objetsPris();
    }

    public ArrayList<Integer> ratioOrdonne(){
        HashMap<Integer,Double> ratioListe = new HashMap<>();
        ArrayList<Integer> objetsOrdonne = new ArrayList<>();
        for(int i=0; i<poids.size(); i++){
            double ratio = valeur.get(i).doubleValue()/poids.get(i).doubleValue();
            ratioListe.put(i, ratio);
            objetsOrdonne.add(i);
        }

        Collections.sort(objetsOrdonne, new Comparator<Integer>() {
            @Override
            public int compare(Integer o1, Integer o2) {
                return ratioListe.get(o2).compareTo(ratioListe.get(o1));
            }
        });
//        for(int i=0; i<poids.size(); i++){
//            System.out.println("R: " + ratioListe.get(i) + " O: " + i);
//
//        }
//        for(int i=0; i<poids.size(); i++) {
//            System.out.print(objetsOrdonne.get(i) + " ");
//        }

        return objetsOrdonne;
    }

    public void objetsPris(){
        ArrayList<Integer> objetsOrdonne = this.ratioOrdonne();
        int poidsObjets = 0;
        int valeurObjets = 0;
        int valeurMax = 0;
        int[] choix = new int[poids.size()];
        int ptr = -1;

        while(true){
            ptr++;
            if(poidsObjets<1000){
                choix[ptr] = 1;
                poidsObjets += poids.get(objetsOrdonne.get(ptr));
                valeurObjets += valeur.get(objetsOrdonne.get(ptr));
            }
            else{
                ptr--; //revient à l'objet où on dépasse 1000 sans le prendre
                choix[ptr] = 0;
                poidsObjets -= poids.get(objetsOrdonne.get(ptr));
                valeurObjets -= valeur.get(objetsOrdonne.get(ptr));
                ptr++; //prends l'objet suivant
                choix[ptr] = 1;
                poidsObjets += poids.get(objetsOrdonne.get(ptr));
                valeurObjets += valeur.get(objetsOrdonne.get(ptr));
            }

            System.out.println("V:" + valeurObjets + " P:" + poidsObjets);
            for(int e : choix){
                System.out.print(e + " ");
            }
            System.out.println();

        }

//        for(int i=0; i<this.poids.size() && poidsObjets<1000; i++){
//
//        }
    }

}