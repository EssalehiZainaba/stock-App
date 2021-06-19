package com.entreprise.Gestionentreprise.Repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.entreprise.Gestionentreprise.Entities.Produit;

public interface ProduitRepository extends JpaRepository<Produit, Long>{
	
	List<Produit> findByNomContaining(String nom);

}
