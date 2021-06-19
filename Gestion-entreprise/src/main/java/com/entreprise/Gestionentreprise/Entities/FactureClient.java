package com.entreprise.Gestionentreprise.Entities;

import java.util.List;

import javax.persistence.*;

@Entity
public class FactureClient {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	
	@ManyToOne
    @JoinColumn(name = "id_commande", referencedColumnName = "id")
    private CommandeClient commande;
	
	@OneToMany(mappedBy = "factureClient")
	List<QteFactureClient> qteFactureClient;

}
