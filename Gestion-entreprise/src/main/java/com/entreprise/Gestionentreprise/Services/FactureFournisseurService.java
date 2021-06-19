package com.entreprise.Gestionentreprise.Services;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.entreprise.Gestionentreprise.Entities.CommandeFournisseur;
import com.entreprise.Gestionentreprise.Entities.FactureFournisseur;
import com.entreprise.Gestionentreprise.Repository.CommandeFournisseurRepository;
import com.entreprise.Gestionentreprise.Repository.FactureFournisseurRepository;

@Service
public class FactureFournisseurService {

	@Autowired
	FactureFournisseurRepository factureFournisseurRepository;
	@Autowired
	CommandeFournisseurRepository commandeFournisseurRepository;

	public ResponseEntity<List<FactureFournisseur>> getAllFactureFournisseurs() {

		try {
			List<FactureFournisseur> factureFournisseurs = new ArrayList<FactureFournisseur>();

			factureFournisseurRepository.findAll().forEach(factureFournisseurs::add);

			if (factureFournisseurs.isEmpty()) {
				return new ResponseEntity<>(HttpStatus.NO_CONTENT);
			}

			return new ResponseEntity<>(factureFournisseurs, HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	public ResponseEntity<FactureFournisseur> createFactureFournisseur(Long idCommande,
			FactureFournisseur factureFournisseur) {
		try {

			Optional<CommandeFournisseur> commande = commandeFournisseurRepository.findById(idCommande);
			FactureFournisseur _FactureFournisseur = factureFournisseurRepository.save(new FactureFournisseur(
					commande.get(), factureFournisseur.getDateFacture(), factureFournisseur.getPrixTotal()));

			return new ResponseEntity<>(_FactureFournisseur, HttpStatus.CREATED);
		} catch (Exception e) {
			return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	public ResponseEntity<FactureFournisseur> getFactureFournisseurById(Long id) {
		Optional<FactureFournisseur> FactureFournisseur = factureFournisseurRepository.findById(id);

		if (FactureFournisseur.isPresent()) {
			return new ResponseEntity<>(FactureFournisseur.get(), HttpStatus.OK);
		} else {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
	}

	public ResponseEntity<HttpStatus> deleteFactureFournisseur(Long id) {
		try {
			factureFournisseurRepository.deleteById(id);

			return new ResponseEntity<>(HttpStatus.NO_CONTENT);
		} catch (Exception e) {
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	public ResponseEntity<HttpStatus> deleteAllFactureFournisseurs() {
		try {
			factureFournisseurRepository.deleteAll();
			return new ResponseEntity<>(HttpStatus.NO_CONTENT);
		} catch (Exception e) {
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
}
