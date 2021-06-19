package com.entreprise.Gestionentreprise.Repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.entreprise.Gestionentreprise.Entities.CommandeFournisseur;
import com.entreprise.Gestionentreprise.Entities.QteCommandFournisseur;
import com.entreprise.Gestionentreprise.Entities.QteCommandFournisseurKey;

public interface QteCommandeFournisseurRepository
		extends JpaRepository<QteCommandFournisseur, QteCommandFournisseurKey> {
	List<QteCommandFournisseur> findByCommandeFournisseur(Optional<CommandeFournisseur> commande);
}