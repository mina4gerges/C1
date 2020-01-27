
import java.io.Serializable;

public class Hello implements Serializable {

    private String valeur;
    private Petstore petstore;

    // Constructeur
    public Hello(String valeur) {
        this.valeur = valeur;
    }

    public void setPetstore(Petstore petstore) {
        this.petstore = petstore;
    }

    // Cette mehode retourne le contenu de la classe Hello
    // concatene avec le contenu de la classe Petstore
    public String toString() {
        return valeur + petstore;
    }
}
