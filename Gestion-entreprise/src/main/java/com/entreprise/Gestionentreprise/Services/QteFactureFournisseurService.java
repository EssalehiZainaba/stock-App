package com.entreprise.Gestionentreprise.Services;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.entreprise.Gestionentreprise.Entities.FactureFournisseur;
import com.entreprise.Gestionentreprise.Entities.Produit;
import com.entreprise.Gestionentreprise.Entities.QteFactureFournisseur;
import com.entreprise.Gestionentreprise.Entities.QteFactureFournisseurKey;
import com.entreprise.Gestionentreprise.Repository.FactureFournisseurRepository;
import com.entreprise.Gestionentreprise.Repository.ProduitRepository;
import com.entreprise.Gestionentreprise.Repository.QteFactureFournisseurRepository;

@Service
public class QteFactureFournisseurService {

	@Autowired
	QteFactureFournisseurRepository qteFactureFournisseurRepository;
	@Autowired
	FactureFournisseurRepository factureFournisseurRepository;
	@Autowired
	ProduitRepository produitRepository;

	public ResponseEntity<List<QteFactureFournisseur>> getAllQteFactureFournisseurs() {

		try {
			List<QteFactureFournisseur> qteFactureFournisseurs = new ArrayList<QteFactureFournisseur>();

			qteFactureFournisseurRepository.findAll().forEach(qteFactureFournisseurs::add);

			if (qteFactureFournisseurs.isEmpty()) {
				return new ResponseEntity<>(HttpStatus.NO_CONTENT);
			}

			return new ResponseEntity<>(qteFactureFournisseurs, HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	public ResponseEntity<QteFactureFournisseur> createQteFactureFournisseur(Long idFacture, Long idProduit,
			QteFactureFournisseur qteFactureFournisseur) {

		Optional<FactureFournisseur> facture = factureFournisseurRepository.findById(idFacture);
		Optional<Produit> produit = produitRepository.findById(idProduit);

		if (facture.isPresent() && produit.isPresent()) {

			QteFactureFournisseurKey idQteFacture = new QteFactureFournisseurKey(idProduit, idFacture);
			QteFactureFournisseur _qteFacture = qteFactureFournisseurRepository
					.save(new QteFactureFournisseur(idQteFacture, facture.get(), produit.get(),
							qteFactureFournisseur.getQteRecu(), qteFactureFournisseur.getQteManquante()));

			return new ResponseEntity<>(_qteFacture, HttpStatus.CREATED);

		} else {
			return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	public ResponseEntity<QteFactureFournisseur> getQteFactureFournisseurById(Long idFacture, Long idProduit) {

		QteFactureFournisseurKey idQteFacture = new QteFactureFournisseurKey(idProduit, idFacture);

		Optional<QteFactureFournisseur> qteCommande = qteFactureFournisseurRepository.findById(idQteFacture);

		if (qteCommande.isPresent()) {
			return new ResponseEntity<>(qteCommande.get(), HttpStatus.OK);
		} else {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}

	}

	public ResponseEntity<HttpStatus> deleteQteFactureFournisseur(Long idFacture, Long idProduit) {
		try {
			QteFactureFournisseurKey idQteFacture = new QteFactureFournisseurKey(idProduit, idFacture);
			qteFactureFournisseurRepository.deleteById(idQteFacture);

			return new ResponseEntity<>(HttpStatus.NO_CONTENT);
		} catch (Exception e) {
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	public ResponseEntity<HttpStatus> deleteAllQteFacturesFournisseurs() {
		try {
			qteFactureFournisseurRepository.deleteAll();
			return new ResponseEntity<>(HttpStatus.NO_CONTENT);
		} catch (Exception e) {
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
}
