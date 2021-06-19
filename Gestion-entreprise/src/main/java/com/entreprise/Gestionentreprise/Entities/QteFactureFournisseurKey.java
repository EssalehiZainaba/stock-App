package com.entreprise.Gestionentreprise.Entities;

import javax.persistence.Column;
import javax.persistence.Embeddable;

@SuppressWarnings("serial")
@Embeddable
public class QteFactureFournisseurKey implements java.io.Serializable {

	@Column(name = "id_produit")
	private Long idProduit;

	@Column(name = "id_facture")
	private Long idFacture;

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((idFacture == null) ? 0 : idFacture.hashCode());
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
		QteFactureFournisseurKey other = (QteFactureFournisseurKey) obj;
		if (idFacture == null) {
			if (other.idFacture != null)
				return false;
		} else if (!idFacture.equals(other.idFacture))
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

	public Long getIdFacture() {
		return idFacture;
	}

	public void setIdFacture(Long idFacture) {
		this.idFacture = idFacture;
	}

	public QteFactureFournisseurKey(Long idProduit, Long idFacture) {
		super();
		this.idProduit = idProduit;
		this.idFacture = idFacture;
	}

	public QteFactureFournisseurKey() {
		super();
	}

}
