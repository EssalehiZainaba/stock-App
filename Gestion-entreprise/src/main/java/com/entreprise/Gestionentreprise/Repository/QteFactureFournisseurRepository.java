package com.entreprise.Gestionentreprise.Repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.entreprise.Gestionentreprise.Entities.QteFactureFournisseur;
import com.entreprise.Gestionentreprise.Entities.QteFactureFournisseurKey;

public interface QteFactureFournisseurRepository
		extends JpaRepository<QteFactureFournisseur, QteFactureFournisseurKey> {

}
