package com.entreprise.Gestionentreprise.Entities;

import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.MapsId;

@Entity
public class QteFactureFournisseur {

	@EmbeddedId
	private QteFactureFournisseurKey id;

	@ManyToOne
	@MapsId("id")
	@JoinColumn(name = "id_facture")
	private FactureFournisseur factureFournisseur;

	@ManyToOne
	@MapsId("id")
	@JoinColumn(name = "id_produit")
	private Produit produit;

	private int qteRecu;

	private int qteManquante;

	public QteFactureFournisseurKey getId() {
		return id;
	}

	public void setId(QteFactureFournisseurKey id) {
		this.id = id;
	}

	public FactureFournisseur getFactureFournisseur() {
		return factureFournisseur;
	}

	public void setFactureFournisseur(FactureFournisseur factureFournisseur) {
		this.factureFournisseur = factureFournisseur;
	}

	public Produit getProduit() {
		return produit;
	}

	public void setProduit(Produit produit) {
		this.produit = produit;
	}

	public int getQteRecu() {
		return qteRecu;
	}

	public void setQteRecu(int qteRecu) {
		this.qteRecu = qteRecu;
	}

	public int getQteManquante() {
		return qteManquante;
	}

	public void setQteManquante(int qteManquante) {
		this.qteManquante = qteManquante;
	}

	public QteFactureFournisseur(QteFactureFournisseurKey id, FactureFournisseur factureFournisseur, Produit produit,
			int qteRecu, int qteManquante) {
		super();
		this.id = id;
		this.factureFournisseur = factureFournisseur;
		this.produit = produit;
		this.qteRecu = qteRecu;
		this.qteManquante = qteManquante;
	}

	public QteFactureFournisseur() {
	}

}
