package com.entreprise.Gestionentreprise.Entities;

import java.time.LocalDate;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
public class FactureFournisseur {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;

	@ManyToOne
	@JoinColumn(name = "id_commande", referencedColumnName = "id")
	private CommandeFournisseur commande;

	@OneToMany(mappedBy = "factureFournisseur")
	@JsonIgnore
	List<QteFactureFournisseur> qteFactureFournisseur;

	private LocalDate dateFacture;
	private Double prixTotal;

	public LocalDate getDateFacture() {
		return dateFacture;
	}

	public void setDateFacture(LocalDate dateFacture) {
		this.dateFacture = dateFacture;
	}

	public Double getPrixTotal() {
		return prixTotal;
	}

	public void setPrixTotal(Double prixTotal) {
		this.prixTotal = prixTotal;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public CommandeFournisseur getCommande() {
		return commande;
	}

	public void setCommande(CommandeFournisseur commande) {
		this.commande = commande;
	}

	public List<QteFactureFournisseur> getQteFactureFournisseur() {
		return qteFactureFournisseur;
	}

	public void setQteFactureFournisseur(List<QteFactureFournisseur> qteFactureFournisseur) {
		this.qteFactureFournisseur = qteFactureFournisseur;
	}

	public FactureFournisseur() {
		super();
	}

	public FactureFournisseur(CommandeFournisseur commande, LocalDate dateFacture, Double prixTotal) {
		super();
		this.commande = commande;
		this.dateFacture = dateFacture;
		this.prixTotal = prixTotal;
	}

}
