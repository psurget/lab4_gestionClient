/**
 * DBManager.java
 * Description:
 *
 * Créé le Mar 12, 2018
 * Par Pascal Surget
 */
package ca.qc.cgodin.modele;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class DBManager {
    private static Connection con;
    private static Statement stm;
    private static String serverName;
    private static String database;
    private static String user;
    private static String password;
    private static int port;
    
    public static void createConnection(String serverName, String database, String user, String password, int port) throws ClassNotFoundException, SQLException {
	DBManager.serverName = serverName;
	DBManager.database = database;
	DBManager.user=user;
	DBManager.password=password;
	DBManager.port=port;
	Class.forName("com.mysql.jdbc.Driver");
	String connectionUrl = "jdbc:mysql://" + serverName + ":" + port + "/" + database + "?user=" + user + "&password="+ password;
	con= DriverManager.getConnection(connectionUrl); 
    }
    
    public static int executeUpdate(String requete) throws SQLException{
	Statement stm= con.createStatement();
	int nbLignes= stm.executeUpdate(requete);
	stm.close();
	return nbLignes;
    }
    
    public static ResultSet executeQuery(String requete) throws SQLException{
	Statement stm= con.createStatement();
	ResultSet res = stm.executeQuery(requete);
	return res;
    }
    
    public static void fermerConnection() throws SQLException{
	con.close();
    }
    
}


