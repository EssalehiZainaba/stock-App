package com.entreprise.Gestionentreprise.Services;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.entreprise.Gestionentreprise.Entities.CommandeFournisseur;
import com.entreprise.Gestionentreprise.Entities.Produit;
import com.entreprise.Gestionentreprise.Entities.QteCommandFournisseur;
import com.entreprise.Gestionentreprise.Entities.QteCommandFournisseurKey;
import com.entreprise.Gestionentreprise.Repository.CommandeFournisseurRepository;
import com.entreprise.Gestionentreprise.Repository.ProduitRepository;
import com.entreprise.Gestionentreprise.Repository.QteCommandeFournisseurRepository;

@Service
public class QteCommandeFournisseurService {

	@Autowired
	QteCommandeFournisseurRepository qteCommandeFournisseurRepository;
	@Autowired
	CommandeFournisseurRepository commandeFournisseurRepository;
	@Autowired
	ProduitRepository produitRepository;

	public ResponseEntity<List<QteCommandFournisseur>> getAllQteCommandes() {

		try {
			List<QteCommandFournisseur> qteCommandes = new ArrayList<QteCommandFournisseur>();

			qteCommandeFournisseurRepository.findAll().forEach(qteCommandes::add);

			if (qteCommandes.isEmpty()) {
				return new ResponseEntity<>(HttpStatus.NO_CONTENT);
			}

			return new ResponseEntity<>(qteCommandes, HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	public ResponseEntity<QteCommandFournisseur> createQteCommande(Long idCommande, Long idProduit,
			int qtecommandeFournisseur) {

		Optional<CommandeFournisseur> commande = commandeFournisseurRepository.findById(idCommande);
		Optional<Produit> produit = produitRepository.findById(idProduit);
		if (commande.isPresent() && produit.isPresent()) {

			QteCommandFournisseurKey idQteCommande = new QteCommandFournisseurKey(idProduit, idCommande);
			QteCommandFournisseur _qteCommande = qteCommandeFournisseurRepository.save(
					new QteCommandFournisseur(idQteCommande, commande.get(), produit.get(), qtecommandeFournisseur));
			return new ResponseEntity<>(_qteCommande, HttpStatus.CREATED);
		} else {
			return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
		}

	}

	public ResponseEntity<QteCommandFournisseur> getQteCommandeById(Long idCommande, Long idProduit) {

		QteCommandFournisseurKey idQteCommande = new QteCommandFournisseurKey(idProduit, idCommande);

		Optional<QteCommandFournisseur> qteCommande = qteCommandeFournisseurRepository.findById(idQteCommande);

		if (qteCommande.isPresent()) {
			return new ResponseEntity<>(qteCommande.get(), HttpStatus.OK);
		} else {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
	}

	public ResponseEntity<List<QteCommandFournisseur>> getQteCommandeByCommande(Long idCommande) {

		Optional<CommandeFournisseur> commande = commandeFournisseurRepository.findById(idCommande);

		List<QteCommandFournisseur> qteCommande = new ArrayList<QteCommandFournisseur>();

		// Optional<QteCommandFournisseur> qteCommande =
		// qteCommandeFournisseurRepository.findById(idQteCommande);
		try {
			if (commande.isPresent()) {
				qteCommandeFournisseurRepository.findByCommandeFournisseur(commande).forEach(qteCommande::add);
			} else {
				return new ResponseEntity<>(HttpStatus.NO_CONTENT);
			}
			System.out.println("---------------taille--------------" + qteCommande.size());
			return new ResponseEntity<>(qteCommande, HttpStatus.OK);

		} catch (Exception e) {
			return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
		}

	}

	public ResponseEntity<QteCommandFournisseur> updateQteCommande(QteCommandFournisseurKey id,
			QteCommandFournisseur qteCommande) {
		Optional<QteCommandFournisseur> qteCommandeData = qteCommandeFournisseurRepository.findById(id);

		if (qteCommandeData.isPresent()) {
			QteCommandFournisseur _commande = qteCommandeData.get();

			_commande.setCommandeFournisseur(_commande.getCommandeFournisseur());
			_commande.setProduit(_commande.getProduit());

			return new ResponseEntity<>(qteCommandeFournisseurRepository.save(_commande), HttpStatus.OK);

		} else {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
	}

	public ResponseEntity<HttpStatus> deleteQteCommande(QteCommandFournisseurKey id) {

		try {
			qteCommandeFournisseurRepository.deleteById(id);

			return new ResponseEntity<>(HttpStatus.NO_CONTENT);
		} catch (Exception e) {
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	public ResponseEntity<HttpStatus> deleteAllQteCommandes() {
		try {
			qteCommandeFournisseurRepository.deleteAll();
			return new ResponseEntity<>(HttpStatus.NO_CONTENT);
		} catch (Exception e) {
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
}
