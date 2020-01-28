
import java.sql.*;

public class HelloPetstore {
    public static String dbUrl = "jdbc:mysql://localhost:3306/petstoreDB?useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC";
    public static String dbUser = "root";
    public static String dbPassword = "";

    public static void main(String[] args) {
        // Charge la classe du driver JDBC
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

        if (args[0].equals("ecrire")) {
            ecrire();
        } else {
            lire();
        }
    }

    private static void ecrire() {
        Connection connection = null;
        Statement statement = null;

        try {
            // Recupere une connexion a la base de donnees
            connection = DriverManager.getConnection(dbUrl, dbUser, dbPassword);
            statement = connection.createStatement();

            // Insert une ligne en base
            statement.executeUpdate("INSERT INTO HELLO_PETSTORE (cle, valeur) VALUES ('Hello', 'PetStore!')");

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            // Ferme la connexion
            try {
                if (statement != null) statement.close();
                if (connection != null) connection.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    private static void lire() {
        Connection connection = null;
        Statement statement = null;
        ResultSet resultSet = null;

        try {
            // Recupere une connection a la base de donnees
            connection = DriverManager.getConnection(dbUrl, dbUser, dbPassword);
            statement = connection.createStatement();

            // Recupere une ligne de la base
            resultSet = statement.executeQuery("SELECT valeur FROM HELLO_PETSTORE WHERE cle = 'Hello' ");
            if (!resultSet.next())
                System.out.println("Non trouve !!!");

            // Affiche le resultat
            System.out.println("Hello " + resultSet.getString(1));

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            // Ferme la connexion
            try {
                if (resultSet != null) resultSet.close();
                if (statement != null) statement.close();
                if (connection != null) connection.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
}
