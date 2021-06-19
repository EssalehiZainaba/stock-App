package com.entreprise.Gestionentreprise.Services;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.entreprise.Gestionentreprise.Entities.Fournisseur;
import com.entreprise.Gestionentreprise.Repository.FournisseurRepository;

@Service
public class FournisseurService {
	
	@Autowired
	FournisseurRepository fournisseurRepository;
	
	public ResponseEntity<List<Fournisseur>> getAllFournisseurs(String societe) {
			
			try {
				List<Fournisseur> fournisseurs = new ArrayList<Fournisseur>();
	
				if (societe == null)
					fournisseurRepository.findAll().forEach(fournisseurs::add);
				else
					fournisseurRepository.findBySocieteContaining(societe).forEach(fournisseurs::add);
	
				if (fournisseurs.isEmpty()) {
					return new ResponseEntity<>(HttpStatus.NO_CONTENT);
				}
	
				return new ResponseEntity<>(fournisseurs, HttpStatus.OK);
			} catch (Exception e) {
				return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
			}
		}
	
	public ResponseEntity<Fournisseur> createFournisseur(Fournisseur fournisseur) {
		try {
			
			Fournisseur _fournisseur = fournisseurRepository.save(new Fournisseur(fournisseur.getSociete(), fournisseur.getPays(), fournisseur.getVille(), fournisseur.getCodePostale(), fournisseur.getAdresse(), fournisseur.getPhone(), fournisseur.getEmail(), fournisseur.getWebsite(), fournisseur.getDescription(), fournisseur.getCompteBancaire(), fournisseur.getImage64()));			 
	        
			return new ResponseEntity<>(_fournisseur, HttpStatus.CREATED);
		} catch (Exception e) {
			return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	public ResponseEntity<Fournisseur> getFournisseurById(Long id) {
		Optional<Fournisseur> fournisseur = fournisseurRepository.findById(id);

		if (fournisseur.isPresent()) {
			return new ResponseEntity<>(fournisseur.get(), HttpStatus.OK);
		} else {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
	}


	public ResponseEntity<Fournisseur> updateFournisseur(long id, Fournisseur fournisseur) {
		Optional<Fournisseur> fournisseurData = fournisseurRepository.findById(id);

		if (fournisseurData.isPresent()) {
			Fournisseur _fournisseur = fournisseurData.get();
			
			_fournisseur.setSociete(fournisseur.getSociete());
			_fournisseur.setPays(fournisseur.getPays());
			_fournisseur.setVille(fournisseur.getVille());
			_fournisseur.setCodePostale(fournisseur.getCodePostale());
			_fournisseur.setAdresse(fournisseur.getAdresse());
			_fournisseur.setPhone(fournisseur.getPhone());
			_fournisseur.setEmail(fournisseur.getEmail());
			_fournisseur.setWebsite(fournisseur.getWebsite());
			_fournisseur.setDescription(fournisseur.getDescription());
			_fournisseur.setCompteBancaire(fournisseur.getCompteBancaire());
			_fournisseur.setImage64(fournisseur.getImage64());
			
			return new ResponseEntity<>(fournisseurRepository.save(_fournisseur), HttpStatus.OK);
			
		} else {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
	}

	public ResponseEntity<HttpStatus> deleteFournisseur(Long id) {
				
		try {
			fournisseurRepository.deleteById(id);

			return new ResponseEntity<>(HttpStatus.NO_CONTENT);
		} catch (Exception e) {
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	public ResponseEntity<HttpStatus> deleteAllFournisseurs() {
		try {
			fournisseurRepository.deleteAll();
			return new ResponseEntity<>(HttpStatus.NO_CONTENT);
		} catch (Exception e) {
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
}
