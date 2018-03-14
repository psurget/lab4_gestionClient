/**
 * Client.java
 * Description:
 *
 * Créé le Mar 12, 2018
 * Par Pascal Surget
 */
package ca.qc.cgodin.modele;

import java.sql.SQLException;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.HashMap;

public class Client {
    private String nom;
    private String prenom;
    private String telephone;
    private String adresse;
    private String courriel;
    private String codeClient;
    
    
    public Client (String nom, String prenom, String telephone,String adresse, String courriel) throws SQLException {
	this.setNom(nom);
	this.setPrenom(prenom);
	this.setTelephone(telephone);
	this.setAdresse(adresse);
	this.setCourriel(courriel);
	genCode();
    }
    
    public Client(String codeClient) throws SQLException, ClientException{
	String requete= "select * from client where codeClient=" + sQuotes(codeClient);
	ResultSet rSet = DBManager.executeQuery(requete);
	boolean trouve=false;
	while(rSet.next()) {
	    this.codeClient = rSet.getString("codeClient");
	    nom = rSet.getString("nom");
	    prenom = rSet.getString("prenom");
	    adresse = rSet.getString("adresse");
	    telephone = rSet.getString("telephone");
	    courriel = rSet.getString("courriel");
	    trouve=true;
	}
	if(!trouve){
	    throw new ClientException("Ce client n'existe pas.");
	}	
    }
    
    private void genCode() throws SQLException {
	String req="select * from nombreClient";
	int no=0;
	ResultSet res = DBManager.executeQuery(req);
	while(res.next()){
	    no=res.getInt("nombre");
	}
	no++;
	req= "update nombreClient set nombre=" + no;
	DBManager.executeQuery(req);
	
	if(no<10) {
	    this.codeClient = "CL-00"+no;
	}else if(no<100){
	    this.codeClient = "CL-0"+ no;
	}else {
	    this.codeClient = "CL-"+ no;
	}
    }
    
    public void enregistrer() throws SQLException{
	String push = codeClient + "Ω" + nom + "Ω" + prenom + "Ω" + telephone + "Ω" + adresse + "Ω" + courriel;
	String req= "insert into client values (" + sQuotes(push) + ")";
	DBManager.executeQuery(req);
    }
    
    public void modifier() throws SQLException{
	String push = "nom=" + nom + "Ω" + "prenom=" + prenom + "Ω" + "telephone=" + telephone + "Ω" + "adresse=" + adresse + "Ω" + "courriel=" + courriel + "where codeClient=" + codeClient;
	String req= "update client set " + sQuotes(push);
	DBManager.executeQuery(req);
    }
    
    public void supprimer() throws SQLException{
	String req= "delete from client where codeClient=" + sQuotes(this.codeClient);
	DBManager.executeQuery(req);
    }

    
    public static ArrayList<Client> recupListeClient() throws SQLException{
	ArrayList<Client> liste=new ArrayList<Client>();
	String req="select * from client";
	ResultSet res=DBManager.executeQuery(req);
	
	while(res.next()) {
	    String codeClient = res.getString("code");
	    String nom = res.getString("nom");
	    String prenom = res.getString("prenom");
	    String adresse = res.getString("adresse");
	    String telephone = res.getString("telephone");
	    String courriel = res.getString("courriel");
	    Client cl= new Client(nom, prenom, telephone, adresse, courriel);
	    liste.add(cl);
	}
	return liste;
    }
    
    public static ArrayList<Client> recupListeClient(HashMap<String,String> criteres) throws SQLException{
	ArrayList<Client> liste=new ArrayList<Client>();
	boolean crit2=false;
	String req="select * from client";
	if(!criteres.isEmpty()) {
	    req+=" where";
	    String nom=criteres.get("nom");
	    String prenom=criteres.get("prenom");
	    String telephone=criteres.get("telephone");
	    String courriel=criteres.get("courriel");
	    String codeClient=criteres.get("code");
	    
	    if(nom !=null) {
		req+= " nom=" + sQuotes(nom);
		crit2=true;
	    }	    
	    if(prenom !=null) {
		if(crit2)
		    req+=" and ";
		req+= " prenom=" + sQuotes(prenom);
		crit2=true;
	    }
	    if(telephone !=null) {
		req+= " telephone=" + sQuotes(telephone);
		crit2=true;
	    }
	    if(courriel !=null) {
		req+= " courriel=" + sQuotes(courriel);
		crit2=true;
	    }
	    if(codeClient !=null) {
		req+= " code=" + sQuotes(codeClient);
		crit2=true;
	    }
	}	
	ResultSet res=DBManager.executeQuery(req);
	
	while(res.next()) {
	    String codeClient = res.getString("code");
	    String nom = res.getString("nom");
	    String prenom = res.getString("prenom");
	    String adresse = res.getString("adresse");
	    String telephone = res.getString("telephone");
	    String courriel = res.getString("courriel");
	    Client cl= new Client(nom, prenom, telephone, adresse, courriel);
	    liste.add(cl);
	}
	return liste;
    }
    
    
    public String toString() {
	return codeClient + " " + nom + " " + prenom + " " + adresse + " " + telephone + " " + courriel;
    }
    
    public static String sQuotes(String text) {
	return "'" + text.replaceAll("Ω", "\', \'") + "'";
    } 
    
/* ======= GETTERS ========= */    
 
    public String getNom() {
	return nom;
    }
    public String getTelephone() {
	return telephone;
    }
    public String getPrenom() {
	return prenom;
    }
    public String getAdresse() {
	return adresse;
    }
    public String getCourriel() {
	return courriel;
    }
    public String getCode() {
	return codeClient;
    }
    
    
/* ======= SETTERS ========= */     

    public void setNom(String nom) {
	this.nom = nom;
    }

    public void setPrenom(String prenom) {
	this.prenom = prenom;
    }

    public void setTelephone(String telephone) {
	this.telephone = telephone;
    }


    public void setAdresse(String adresse) {
	this.adresse = adresse;
    }


    public void setCourriel(String courriel) {
	this.courriel = courriel;
    }
    

}
