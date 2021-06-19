package com.entreprise.Gestionentreprise.Entities;

import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.MapsId;

@Entity
public class QteFactureClient {
	
	@EmbeddedId
	QteFactureClientKey id;
	
	@ManyToOne
    @MapsId("id")
    @JoinColumn(name = "id_facture")
    FactureClient factureClient;

    @ManyToOne
    @MapsId("id")
    @JoinColumn(name = "id_produit")
    Produit produit;
    
    private int qteFactureClient;

}
