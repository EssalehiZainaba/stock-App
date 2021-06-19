package com.entreprise.Gestionentreprise.Entities;

import javax.persistence.Column;
import javax.persistence.Embeddable;

@SuppressWarnings("serial")
@Embeddable
public class QteCommandFournisseurKey implements java.io.Serializable {
	
	@Column(name = "id_produit")
    private Long idProduit;

    @Column(name = "id_commandeFournisseur")
    private Long idCommandeFournisseur;

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((idCommandeFournisseur == null) ? 0 : idCommandeFournisseur.hashCode());
		result = prime * result + ((idProduit == null) ? 0 : idProduit.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		QteCommandFournisseurKey other = (QteCommandFournisseurKey) obj;
		if (idCommandeFournisseur == null) {
			if (other.idCommandeFournisseur != null)
				return false;
		} else if (!idCommandeFournisseur.equals(other.idCommandeFournisseur))
			return false;
		if (idProduit == null) {
			if (other.idProduit != null)
				return false;
		} else if (!idProduit.equals(other.idProduit))
			return false;
		return true;
	}

	public Long getIdProduit() {
		return idProduit;
	}

	public void setIdProduit(Long idProduit) {
		this.idProduit = idProduit;
	}

	public Long getIdFournisseur() {
		return idCommandeFournisseur;
	}

	public void setIdFournisseur(Long idFournisseur) {
		this.idCommandeFournisseur = idFournisseur;
	}

	public Long getIdCommandeFournisseur() {
		return idCommandeFournisseur;
	}

	public void setIdCommandeFournisseur(Long idCommandeFournisseur) {
		this.idCommandeFournisseur = idCommandeFournisseur;
	}

	public QteCommandFournisseurKey(Long idProduit, Long idCommandeFournisseur) {
		super();
		this.idProduit = idProduit;
		this.idCommandeFournisseur = idCommandeFournisseur;
	}
    
	public QteCommandFournisseurKey() {}

}
