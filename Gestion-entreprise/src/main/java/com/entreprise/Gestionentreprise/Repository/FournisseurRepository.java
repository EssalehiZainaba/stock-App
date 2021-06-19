package com.entreprise.Gestionentreprise.Repository;

import java.util.List;


import org.springframework.data.jpa.repository.JpaRepository;

import com.entreprise.Gestionentreprise.Entities.Fournisseur;

public interface FournisseurRepository extends JpaRepository<Fournisseur, Long> {
	List<Fournisseur> findBySocieteContaining(String societe);

}
