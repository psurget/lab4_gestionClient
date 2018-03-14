/**
 * TestClient.java
 * Description:
 *
 * Créé le Mar 14, 2018
 * Par Pascal Surget
 */
package ca.qc.cgodin.modele;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;

public class TestClient {

    public static void main(String[] args) throws ClassNotFoundException, SQLException {
	try {
	   
	    DBManager.createConnection("localhost", "gestionClient", "aec", "p@ssword2017", 3306);
	    // test du constructeur
	    Client cl1=new Client("Tremblay", "Jeannine", "514-000-0001", "185 boul. Gouin", "v@v.com");
	    // test du generateur de code
	    System.out.println(cl1.getCode());
	    //Vérification dans la table nombre++	    
	    //Enregistrer le client
	    cl1.enregistrer();
	    //vérifier l'enregistrement
	    cl1.setAdresse("532 boul. Maisonneuve");
	    cl1.setTelephone("514-245-0001");
	    cl1.modifier();
	  //vérifier les modifs
	    
	    Client cl2=new Client("CL-002");
	    // test du constructeur à partir du code client
	    System.out.println(cl2);
	    //Chargement du client créé
	    // Si on essaie de charger un client inexistant, un ClientException va être retourner
	    ArrayList<Client> liste= Client.recupListeClient();
	    System.out.println("Affichage de la liste des clients");
	    for(Client cl:liste) {
		System.out.println(cl);
	    }
	    
	    //Recherche de tous les clients avec pour nom Tremblay et prenom Antoine
	    HashMap<String, String> criteres = new HashMap<String, String>();
	    criteres.put("nom", "Tremblay");
	    liste= Client.recupListeClient(criteres);
	    System.out.println("Affichage des clients nommés Tremblay, Antoine:");
	    for(Client cl:liste) {
		System.out.println(cl);
	    }
	    
	    //Supprimer le client cl1
	    cl1.supprimer();
	    //Vérifier la suppression
	}catch(ClassNotFoundException ex) {
	    System.out.println(ex.getMessage());	    
	}catch(SQLException ex) {
	    System.out.println(ex.getMessage());	
	}catch(ClientException ex) {
	    System.out.println(ex.getMessage());	
	}
    }

}
