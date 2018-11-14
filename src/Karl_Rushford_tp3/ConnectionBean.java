package Karl_Rushford_tp3;



import java.sql.DriverManager;
import java.sql.Connection;
import java.sql.SQLException;

/************************
cours: IFT1176
TP3
Karl Rushford



 **************************/

/* Classe que va obtenir la connection avec la base de données
 *
 * Le login et le mot de passe seront récupérer de la classe UserData
 * que vous devez modifier avec les informations sur votre compte
 * mysql.
 */

public class ConnectionBean {
   //remplacez reid_ift1176 par le nom de votre base de données que vous avez
   //préalablement créée dans mysql.
   private String url="jdbc:mysql://mysql.iro.umontreal.ca/rushfork_tp3";

   private String username = UserData.login;
   private String password = UserData.passwd;
   private String driver = "com.mysql.jdbc.Driver";

   public ConnectionBean() {

   }

   public void setDriver()
      throws ClassNotFoundException    {
      Class.forName(driver);
   }

   public void setUrl(String aUrl) {
      url = aUrl;
   }

   public void setUsername(String aUsername) {
      username = aUsername;
   }

    public void setPassword(String aPassword) {
      password = aPassword;
   }

    /* Obtention de connection avec une base de donnees MySql */
   public Connection getConnection()  throws SQLException  {
         return DriverManager.getConnection(url, username , password);
   }

}


