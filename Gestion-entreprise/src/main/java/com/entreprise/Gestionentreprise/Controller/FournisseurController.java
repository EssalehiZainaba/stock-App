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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.entreprise.Gestionentreprise.Entities.Fournisseur;
import com.entreprise.Gestionentreprise.Services.FournisseurService;

@CrossOrigin(origins = "http://localhost:8082")
@RestController
@RequestMapping(path="/api/fournisseurs")
public class FournisseurController {
	
	@Autowired
	private FournisseurService fournisseurService;
	
	@GetMapping  
	public ResponseEntity<List<Fournisseur>> getAllFournisseurs(@RequestParam(required = false) String societe){
		return fournisseurService.getAllFournisseurs(societe);
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<Fournisseur> getFournisseurById(@PathVariable("id") Long id) {
		return fournisseurService.getFournisseurById(id);
	}

	@PostMapping
	public ResponseEntity<Fournisseur> createFournisseur(@RequestBody Fournisseur fournisseur){
		return fournisseurService.createFournisseur(fournisseur);
	}
	
	
	@PutMapping("/{id}")
	public ResponseEntity<Fournisseur> updateFournisseur(@PathVariable("id") Long id, @RequestBody Fournisseur fournisseur) {
		return fournisseurService.updateFournisseur(id, fournisseur);
	}
	
	
	@DeleteMapping("/{id}")
	public ResponseEntity<HttpStatus> deleteFournisseur(@PathVariable("id") Long id) {
		return fournisseurService.deleteFournisseur(id);
	}

	@DeleteMapping
	public ResponseEntity<HttpStatus> deleteAllFournisseurs() {
		return fournisseurService.deleteAllFournisseurs();
	}

}
