package com.entreprise.Gestionentreprise.Entities;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
public class Fournisseur {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	private String societe;
	private String pays;
	private String ville;
	private String codePostale;
	private String adresse;
	private String phone;
	private String email;
	private String website;
	private String description;
	private String compteBancaire;

	@Column(columnDefinition = "LONGTEXT")
	private String image64;

	@OneToMany(mappedBy = "fournisseur")
	@JsonIgnore
	private List<CommandeFournisseur> commandes;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getSociete() {
		return societe;
	}

	public void setSociete(String societe) {
		this.societe = societe;
	}

	public String getPays() {
		return pays;
	}

	public void setPays(String pays) {
		this.pays = pays;
	}

	public String getVille() {
		return ville;
	}

	public void setVille(String ville) {
		this.ville = ville;
	}

	public String getCodePostale() {
		return codePostale;
	}

	public void setCodePostale(String codePostale) {
		this.codePostale = codePostale;
	}

	public String getAdresse() {
		return adresse;
	}

	public void setAdresse(String adresse) {
		this.adresse = adresse;
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

	public String getWebsite() {
		return website;
	}

	public void setWebsite(String website) {
		this.website = website;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getCompteBancaire() {
		return compteBancaire;
	}

	public void setCompteBancaire(String compteBancaire) {
		this.compteBancaire = compteBancaire;
	}

	public String getImage64() {
		return image64;
	}

	public void setImage64(String image64) {
		this.image64 = image64;
	}

	public List<CommandeFournisseur> getCommandes() {
		return commandes;
	}

	public void setCommandes(List<CommandeFournisseur> commandes) {
		this.commandes = commandes;
	}

	public Fournisseur(String societe, String pays, String ville, String codePostale, String adresse, String phone,
			String email, String website, String description, String compteBancaire, String image64) {
		super();
		this.societe = societe;
		this.pays = pays;
		this.ville = ville;
		this.codePostale = codePostale;
		this.adresse = adresse;
		this.phone = phone;
		this.email = email;
		this.website = website;
		this.description = description;
		this.compteBancaire = compteBancaire;
		this.image64 = image64;
	}

	public Fournisseur() {
	}

}
