import java.io.*;
import java.util.*;

public class BranchBound {
    private int poids_max = 0;
    private ArrayList<Integer> poids = new ArrayList<>();
    private ArrayList<Integer> valeur = new ArrayList<>();

    public BranchBound(String path) {
        File file = new File(path);
        try {
            Scanner scanner = new Scanner(file);
            poids_max = scanner.nextInt();
            while (scanner.hasNext()) {
                poids.add(scanner.nextInt());
                valeur.add(scanner.nextInt());
            }
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    public static void main(String[] args) throws FileNotFoundException {
        BranchBound sac0 = new BranchBound("C:\\Users\\zanet\\IdeaProjects\\ARO-TP2\\sacs\\sac0");
        sac0.objetsPris();
    }

    public ArrayList<Integer> ratioOrdonne() {
        HashMap<Integer, Double> ratioListe = new HashMap<>();
        ArrayList<Integer> objetsOrdonne = new ArrayList<>();
        for (int i = 0; i < poids.size(); i++) {
            double ratio = valeur.get(i).doubleValue() / poids.get(i).doubleValue();
            ratioListe.put(i, ratio);
            objetsOrdonne.add(i);
        }

        Collections.sort(objetsOrdonne, new Comparator<Integer>() {
            @Override
            public int compare(Integer o1, Integer o2) {
                return ratioListe.get(o2).compareTo(ratioListe.get(o1));
            }
        });

        return objetsOrdonne;
    }

    public void objetsPris() {
        ArrayList<Integer> objetsOrdonne = this.ratioOrdonne();
        int poidsObjets = 0;
        int valeurObjets = 0;
        int[] choix = new int[poids.size()];
        Stack<Integer> pile = new Stack<>();
        int objetCourant = 0;

        while (objetCourant < objetsOrdonne.size() || !pile.isEmpty()) {
            if (objetCourant < objetsOrdonne.size()) {
                int objetIndex = objetsOrdonne.get(objetCourant);

                if (poidsObjets + poids.get(objetIndex) <= poids_max) {
                    // On prend
                    choix[objetIndex] = 1;
                    poidsObjets += poids.get(objetIndex);
                    valeurObjets += valeur.get(objetIndex);
                } else {
                    // On prend pas
                    choix[objetIndex] = 0;
                }

                objetCourant++;
            } else {
                // Remonte dans la pile
                if (!pile.isEmpty()) {
                    objetCourant = pile.pop();
                    if (objetCourant < objetsOrdonne.size()) {
                        int objetIndex = objetsOrdonne.get(objetCourant);
                        poidsObjets -= poids.get(objetIndex);
                        valeurObjets -= valeur.get(objetIndex);
                    }
                    objetCourant++; // Passez à l'objet suivant
                }
            }
        }

        System.out.println("Val total: " + valeurObjets);
        System.out.println("Objets pris : ");
        for (int i = 0; i < choix.length; i++) {
            if (choix[i] == 1) {
                System.out.println("Obj " + i + ": Poids=" + poids.get(i) + ", Valeur=" + valeur.get(i));
            }
        }
    }

}
