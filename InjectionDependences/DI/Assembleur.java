 


/**
 * Décrivez votre classe Assembleur ici.
 *
 * @author (votre nom)
 * @version (un numéro de version ou une date)
 */
public class Assembleur
{
   

    /**
     * Constructeur d'objets de classe Assembleur
     */
    public Assembleur()
    {
       //
    }

    public static void assembler() {
        A a = new A(new B());
    }
    
    public static A liee() throws Exception{
    Properties props = new Properties();
// chargement de la table des propriétés par une lecture du fichier
    props.load(new FileInputStream(new File("assembleur.props")));
// chargement de la classe, à partir de la clef
    Class<?> c = 
       Class.forName(props.getProperty("implementation_i_class"));

// création d’une instance, appel du constructeur par défaut
    I i = (I)c.newInstance();

// injection de cette instance, à la création de a
    A a = new A(i);
  }

}
