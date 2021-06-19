package com.entreprise.Gestionentreprise.Entities;

import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.MapsId;

@Entity
public class QteCommandClient {

	@EmbeddedId
	QteCommandClientKey id;

	@ManyToOne
	@MapsId("id")
	@JoinColumn(name = "id_commandeClient")
	CommandeClient commandeClient;

	@ManyToOne
	@MapsId("id")
	@JoinColumn(name = "id_produit")
	Produit produit;

	private int qtecommandeClient;

	public QteCommandClientKey getId() {
		return id;
	}

	public void setId(QteCommandClientKey id) {
		this.id = id;
	}

	public CommandeClient getCommandeClient() {
		return commandeClient;
	}

	public void setCommandeClient(CommandeClient commandeClient) {
		this.commandeClient = commandeClient;
	}

	public Produit getProduit() {
		return produit;
	}

	public void setProduit(Produit produit) {
		this.produit = produit;
	}

	public int getQtecommandeClient() {
		return qtecommandeClient;
	}

	public void setQtecommandeClient(int qtecommandeClient) {
		this.qtecommandeClient = qtecommandeClient;
	}

}
