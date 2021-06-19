package com.entreprise.Gestionentreprise.Services;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.entreprise.Gestionentreprise.Entities.CommandeFournisseur;
import com.entreprise.Gestionentreprise.Entities.Fournisseur;
import com.entreprise.Gestionentreprise.Entities.Produit;
import com.entreprise.Gestionentreprise.Repository.CommandeFournisseurRepository;
import com.entreprise.Gestionentreprise.Repository.FournisseurRepository;

@Service
public class CommandeFournisseurService {
	
	@Autowired
	CommandeFournisseurRepository commandeFournisseurRepository;
	
	@Autowired
	FournisseurRepository fournisseurRepository;
	
	public ResponseEntity<List<CommandeFournisseur>> getAllCommandes() {
			
			try {
				List<CommandeFournisseur> commandes = new ArrayList<CommandeFournisseur>();
				
				commandeFournisseurRepository.findAll().forEach(commandes::add);

				if (commandes.isEmpty()) {
					return new ResponseEntity<>(HttpStatus.NO_CONTENT);
				}
	
				return new ResponseEntity<>(commandes, HttpStatus.OK);
			} catch (Exception e) {
				return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
			}
		}
	
	public ResponseEntity<CommandeFournisseur> createCommande(CommandeFournisseur commande, Long idFournisseur) {
		try {
			Optional<Fournisseur> fournisseur = fournisseurRepository.findById(idFournisseur);
			
			if(fournisseur.isPresent())
			{
				CommandeFournisseur _commande = commandeFournisseurRepository.save(new CommandeFournisseur(commande.getDateEcheance(), commande.getDateReception(), fournisseur.get())); 
				return new ResponseEntity<>(_commande, HttpStatus.CREATED);
			}
				        
			
		} catch (Exception e) {
			return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		
		return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
	}

	public ResponseEntity<CommandeFournisseur> getCommandeById(Long id) {
		Optional<CommandeFournisseur> commande = commandeFournisseurRepository.findById(id);

		if (commande.isPresent()) {
			return new ResponseEntity<>(commande.get(), HttpStatus.OK);
		} else {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
	}


	public ResponseEntity<CommandeFournisseur> updateCommande(long id, CommandeFournisseur commande) {
		Optional<CommandeFournisseur> commandeData = commandeFournisseurRepository.findById(id);
		//Optional<Fournisseur> fournisseur = fournisseurRepository.findById(idFournisseur);

		if (commandeData.isPresent()) {
			CommandeFournisseur _commande = commandeData.get();
			_commande.setDateEcheance(_commande.getDateEcheance());
			_commande.setDateReception(_commande.getDateReception());
			_commande.setFournisseur(_commande.getFournisseur());
			
			return new ResponseEntity<>(commandeFournisseurRepository.save(_commande), HttpStatus.OK);
			
		} else {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
	}

	public ResponseEntity<HttpStatus> deleteCommande(Long id) {
		
		try {
			commandeFournisseurRepository.deleteById(id);

			return new ResponseEntity<>(HttpStatus.NO_CONTENT);
		} catch (Exception e) {
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	public ResponseEntity<HttpStatus> deleteAllCommandes() {
		try {
			commandeFournisseurRepository.deleteAll();
			return new ResponseEntity<>(HttpStatus.NO_CONTENT);
		} catch (Exception e) {
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

}
