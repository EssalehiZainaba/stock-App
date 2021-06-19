package com.entreprise.Gestionentreprise.Entities;

import javax.persistence.*;

@SuppressWarnings("serial")
@Embeddable
public class QteCommandClientKey  implements java.io.Serializable {
	
	@Column(name = "id_produit")
    private Long idProduit;

    @Column(name = "id_commandeClient")
    private Long idCommandeClient;

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((idCommandeClient == null) ? 0 : idCommandeClient.hashCode());
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
		QteCommandClientKey other = (QteCommandClientKey) obj;
		if (idCommandeClient == null) {
			if (other.idCommandeClient != null)
				return false;
		} else if (!idCommandeClient.equals(other.idCommandeClient))
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

	public Long getIdClient() {
		return idCommandeClient;
	}

	public void setIdClient(Long idClient) {
		this.idCommandeClient = idClient;
	}
    
    
    

}
