package com.entreprise.Gestionentreprise.Entities;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;

@Entity
public class Client {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	private String nom;
	private String prenom;
	private String ville;
	private String pays;
	private String phone;
	private String email;
	private String description;
	private String rue;
	
	
	@Column(columnDefinition="LONGTEXT")
	private String image64;
		
	
	@OneToMany(mappedBy = "client")
	private List<CommandeClient> commandes;


	public Long getId() {
		return id;
	}


	public void setId(Long id) {
		this.id = id;
	}


	public String getNom() {
		return nom;
	}


	public void setNom(String nom) {
		this.nom = nom;
	}


	public String getPrenom() {
		return prenom;
	}


	public void setPrenom(String prenom) {
		this.prenom = prenom;
	}


	public String getVille() {
		return ville;
	}


	public String getPays() {
		return pays;
	}


	public void setPays(String pays) {
		this.pays = pays;
	}


	public String getPhone() {
		return phone;
	}


	public void setPhone(String phone) {
		this.phone = phone;
	}


	public String getEmail() {
		return email;
	}


	public void setEmail(String email) {
		this.email = email;
	}


	public String getRue() {
		return rue;
	}


	public void setRue(String rue) {
		this.rue = rue;
	}


	public String getDescription() {
		return description;
	}


	public void setDescription(String description) {
		this.description = description;
	}


	public void setVille(String ville) {
		this.ville = ville;
	}


	public List<CommandeClient> getCommandes() {
		return commandes;
	}


	public void setCommandes(List<CommandeClient> commandes) {
		this.commandes = commandes;
	}


	public String getImage64() {
		return image64;
	}


	public void setImage64(String image64) {
		this.image64 = image64;
	}

	
	public Client(String nom, String prenom, String ville, String pays, String phone, String email, String description, String rue,
			String image64) {
		super();
		this.nom = nom;
		this.rue = rue;
		this.prenom = prenom;
		this.ville = ville;
		this.pays = pays;
		this.phone = phone;
		this.email = email;
		this.description = description;
		this.image64 = image64;
	}


	public Client() {}
	
	
}
