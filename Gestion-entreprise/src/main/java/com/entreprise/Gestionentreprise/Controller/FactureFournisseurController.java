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
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.entreprise.Gestionentreprise.Entities.FactureFournisseur;
import com.entreprise.Gestionentreprise.Services.FactureFournisseurService;

@CrossOrigin(origins = "http://localhost:8082")
@RestController
@RequestMapping(path = "/api/factures")
public class FactureFournisseurController {

	@Autowired
	private FactureFournisseurService factureFournisseurService;

	@GetMapping
	public ResponseEntity<List<FactureFournisseur>> getAllFactureFournisseurs() {
		return factureFournisseurService.getAllFactureFournisseurs();
	}

	@GetMapping("/{id}")
	public ResponseEntity<FactureFournisseur> getFactureFournisseurById(@PathVariable("id") Long id) {
		return factureFournisseurService.getFactureFournisseurById(id);
	}

	@PostMapping("/{idCommande}")
	public ResponseEntity<FactureFournisseur> createFactureFournisseur(@PathVariable("idCommande") Long idCommande,
			@RequestBody FactureFournisseur factureFournisseur) {
		return factureFournisseurService.createFactureFournisseur(idCommande, factureFournisseur);
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<HttpStatus> deleteFactureFournisseur(@PathVariable("id") Long id) {
		return factureFournisseurService.deleteFactureFournisseur(id);
	}

	@DeleteMapping
	public ResponseEntity<HttpStatus> deleteAllFactureFournisseurs() {
		return factureFournisseurService.deleteAllFactureFournisseurs();
	}
}
