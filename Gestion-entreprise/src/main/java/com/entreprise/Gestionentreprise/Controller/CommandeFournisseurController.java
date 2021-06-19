package com.entreprise.Gestionentreprise.Controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.entreprise.Gestionentreprise.Entities.CommandeFournisseur;
import com.entreprise.Gestionentreprise.Services.CommandeFournisseurService;

@CrossOrigin(origins = "http://localhost:8082")
@RestController
@RequestMapping(path="/api/commandesFournisseur")
public class CommandeFournisseurController {
	
	@Autowired
	private CommandeFournisseurService commandeFournisseurService;
	
	@GetMapping  
	public ResponseEntity<List<CommandeFournisseur>> getAllCommandes(){
		return commandeFournisseurService.getAllCommandes();
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<CommandeFournisseur> getCommandeById(@PathVariable("id") Long id) {
		return commandeFournisseurService.getCommandeById(id);
	}

	@PostMapping("/{idFournisseur}")
	public ResponseEntity<CommandeFournisseur> createCommande(@RequestBody CommandeFournisseur commande, @PathVariable("idFournisseur") Long idFournisseur){
		return commandeFournisseurService.createCommande(commande, idFournisseur);
	}
	
	
	@PutMapping("/{id}")
	public ResponseEntity<CommandeFournisseur> updateCommande(@PathVariable("id") Long id, @RequestBody CommandeFournisseur commande) {
		return commandeFournisseurService.updateCommande(id, commande);
	}
	
	
	@DeleteMapping("/{id}")
	public ResponseEntity<HttpStatus> deleteCommande(@PathVariable("id") Long id) {
		return commandeFournisseurService.deleteCommande(id);
	}

	@DeleteMapping
	public ResponseEntity<HttpStatus> deleteAllCommandes() {
		return commandeFournisseurService.deleteAllCommandes();
	}

}
