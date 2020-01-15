package dep2;


/**
 * Décrivez votre classe A ici.
 *
 * @author (votre nom)
 * @version (un numéro de version ou une date)
 */
public class A
{
    // variables d'instance - remplacez l'exemple qui suit par le vôtre
    private I i;

    /**
     * Constructeur d'objets de classe A
     */
    public A()
    {
        // initialisation des variables d'instance
        i = new B();
    }

    /**
     * Un exemple de méthode - remplacez ce commentaire par le vôtre
     *
     * @param  y   le paramètre de la méthode
     * @return     la somme de x et de y
     */
    public int sampleMethod(int y)
    {
        return i.exempleDeMethode(y);
    }
}
