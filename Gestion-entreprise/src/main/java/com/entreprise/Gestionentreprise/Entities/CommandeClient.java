package com.entreprise.Gestionentreprise.Entities;

import java.time.LocalDate;
import java.util.List;

import javax.persistence.*;

@Entity
public class CommandeClient {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	
	private LocalDate date;
	
	@OneToMany(mappedBy = "commande")
	private List<FactureClient> factures;
	
	@ManyToOne
    @JoinColumn(name = "id_client", referencedColumnName = "id")
    private Client client;
	
	@OneToMany(mappedBy = "commandeClient")
    List<QteCommandClient> qtecommandeClient;

}
