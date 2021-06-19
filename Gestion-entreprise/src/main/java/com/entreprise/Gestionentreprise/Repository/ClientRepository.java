package com.entreprise.Gestionentreprise.Repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.entreprise.Gestionentreprise.Entities.Client;
import com.entreprise.Gestionentreprise.Entities.Produit;

public interface ClientRepository extends JpaRepository<Client, Long>{
	
	List<Client> findByNomContaining(String nom);
}
