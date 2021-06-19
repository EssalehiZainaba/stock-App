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
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@Entity
@JsonIgnoreProperties(value = { "qtecommandeFournisseur" })
public class CommandeFournisseur {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	private LocalDate dateEcheance;
	private LocalDate dateReception;

	@OneToMany(mappedBy = "commande")
	@JsonIgnore
	private List<FactureFournisseur> factures;

	@ManyToOne
	@JoinColumn(name = "id_fournisseur", referencedColumnName = "id")
	private Fournisseur fournisseur;

	@OneToMany(mappedBy = "commandeFournisseur")
	List<QteCommandFournisseur> qtecommandeFournisseur;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public LocalDate getDateEcheance() {
		return dateEcheance;
	}

	public void setDateEcheance(LocalDate dateEcheance) {
		this.dateEcheance = dateEcheance;
	}

	public LocalDate getDateReception() {
		return dateReception;
	}

	public void setDateReception(LocalDate dateReception) {
		this.dateReception = dateReception;
	}

	public List<FactureFournisseur> getFactures() {
		return factures;
	}

	public void setFactures(List<FactureFournisseur> factures) {
		this.factures = factures;
	}

	public Fournisseur getFournisseur() {
		return fournisseur;
	}

	public void setFournisseur(Fournisseur fournisseur) {
		this.fournisseur = fournisseur;
	}

	public List<QteCommandFournisseur> getQtecommandeFournisseur() {
		return qtecommandeFournisseur;
	}

	public void setQtecommandeFournisseur(List<QteCommandFournisseur> qtecommandeFournisseur) {
		this.qtecommandeFournisseur = qtecommandeFournisseur;
	}

	public CommandeFournisseur(LocalDate dateEcheance, LocalDate dateReception, Fournisseur fournisseur) {
		super();
		this.dateEcheance = dateEcheance;
		this.dateReception = dateReception;
		this.fournisseur = fournisseur;
	}

	public CommandeFournisseur() {
	}

}
