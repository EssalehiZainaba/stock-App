package com.entreprise.Gestionentreprise.Entities;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
public class Produit {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	private String nom;
	private String description;
	private String codeBarre;
	private Double prix;

	@Enumerated(EnumType.STRING)
	private TypeArticle typeArticle;

	@Column(columnDefinition = "LONGTEXT")
	private String image64;

	@OneToMany(mappedBy = "produit")
	@JsonIgnore
	List<QteCommandClient> qteCommandeClient;

	@OneToMany(mappedBy = "produit")
	@JsonIgnore
	List<QteFactureClient> qteFactureClient;

	@OneToMany(mappedBy = "produit")
	@JsonIgnore
	List<QteCommandFournisseur> qteCommandeFournisseur;

	@OneToMany(mappedBy = "produit")
	@JsonIgnore
	List<QteFactureFournisseur> qteFactureFournisseur;

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

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getCodeBarre() {
		return codeBarre;
	}

	public void setCodeBarre(String codeBarre) {
		this.codeBarre = codeBarre;
	}

	public Double getPrix() {
		return prix;
	}

	public String getImage64() {
		return image64;
	}

	public void setImage64(String image64) {
		this.image64 = image64;
	}

	public void setPrix(Double prix) {
		this.prix = prix;
	}

	public TypeArticle getTypeArticle() {
		return typeArticle;
	}

	public void setTypeArticle(TypeArticle typeArticle) {
		this.typeArticle = typeArticle;
	}

	public List<QteCommandClient> getQteCommandeClient() {
		return qteCommandeClient;
	}

	public void setQteCommandeClient(List<QteCommandClient> qteCommandeClient) {
		this.qteCommandeClient = qteCommandeClient;
	}

	public List<QteFactureClient> getQteFactureClient() {
		return qteFactureClient;
	}

	public void setQteFactureClient(List<QteFactureClient> qteFactureClient) {
		this.qteFactureClient = qteFactureClient;
	}

	public List<QteCommandFournisseur> getQteCommandeFournisseur() {
		return qteCommandeFournisseur;
	}

	public void setQteCommandeFournisseur(List<QteCommandFournisseur> qteCommandeFournisseur) {
		this.qteCommandeFournisseur = qteCommandeFournisseur;
	}

	public List<QteFactureFournisseur> getQteFactureFournisseur() {
		return qteFactureFournisseur;
	}

	public void setQteFactureFournisseur(List<QteFactureFournisseur> qteFactureFournisseur) {
		this.qteFactureFournisseur = qteFactureFournisseur;
	}

	public Produit() {
		super();
	}

	public Produit(String nom, String description, String codeBarre, Double prix, TypeArticle typeArticle,
			String image64) {
		super();
		this.nom = nom;
		this.description = description;
		this.codeBarre = codeBarre;
		this.prix = prix;
		this.typeArticle = typeArticle;
		this.image64 = image64;
	}

}
