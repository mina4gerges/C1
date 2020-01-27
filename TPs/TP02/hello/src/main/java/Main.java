
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.Date;

public class Main {

    // Point d'entree de l'application
    public static void main(String[] args) {
        if (args[0].equals("ecrire")) {
            ecrire();
        } else {
            lire();
        }
    }

    private static void ecrire() {
        // Cree une instance de chaque objet et les lie entre eux
        Hello hello = new Hello("Hello ");
        Petstore petstore = new Petstore("PetStore!", new Date());
        hello.setPetstore(petstore);

        try {
            final FileOutputStream fout = new FileOutputStream("hellopetstore.ser");
            final ObjectOutputStream out = new ObjectOutputStream(fout);

            // Serialise l'objet hello qui est lie a l'objet petstore
            out.writeObject(hello);

            out.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static void lire() {
        try {
            final FileInputStream fin = new FileInputStream("hellopetstore.ser");
            final ObjectInputStream in = new ObjectInputStream(fin);

            // Deserialise l'objet hello
            Hello hello = (Hello) in.readObject();

            in.close();

            // En affichant ses attributs, le contenu de l'objet
            // petstore est aussi affiche
            System.out.println(hello);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
