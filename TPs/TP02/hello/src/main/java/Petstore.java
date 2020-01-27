
import java.io.Serializable;
import java.util.Date;

public class Petstore implements Serializable {

    private String valeur;
    private Date date;

    // Constructeur
    public Petstore(String valeur, Date date) {
        this.valeur = valeur;
        this.date = date;
    }

    // Cette mehode retourne le contenu de la classe Petstore
    public String toString() {
        return valeur + "\n" + date;
    }
}
