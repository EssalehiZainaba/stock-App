package com.entreprise.Gestionentreprise.Entities;

import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.MapsId;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
public class QteCommandFournisseur {

	@EmbeddedId
	private QteCommandFournisseurKey id;

	@ManyToOne
	@MapsId("id")
	@JoinColumn(name = "id_commandeFournisseur")
	@JsonIgnore
	private CommandeFournisseur commandeFournisseur;

	@ManyToOne
	@MapsId("id")
	@JoinColumn(name = "id_produit")
	private Produit produit;

	private int qtecommandeFournisseur;

	public QteCommandFournisseurKey getId() {
		return id;
	}

	public void setId(QteCommandFournisseurKey id) {
		this.id = id;
	}

	public CommandeFournisseur getCommandeFournisseur() {
		return commandeFournisseur;
	}

	public void setCommandeFournisseur(CommandeFournisseur commandeFournisseur) {
		this.commandeFournisseur = commandeFournisseur;
	}

	public Produit getProduit() {
		return produit;
	}

	public void setProduit(Produit produit) {
		this.produit = produit;
	}

	public QteCommandFournisseur(QteCommandFournisseurKey id, CommandeFournisseur commandeFournisseur, Produit produit,
			int qtecommandeFournisseur) {
		super();
		this.id = id;
		this.commandeFournisseur = commandeFournisseur;
		this.produit = produit;
		this.qtecommandeFournisseur = qtecommandeFournisseur;
	}

	public QteCommandFournisseur() {
	}

	public int getQtecommandeFournisseur() {
		return qtecommandeFournisseur;
	}

	public void setQtecommandeFournisseur(int qtecommandeFournisseur) {
		this.qtecommandeFournisseur = qtecommandeFournisseur;
	}

}
