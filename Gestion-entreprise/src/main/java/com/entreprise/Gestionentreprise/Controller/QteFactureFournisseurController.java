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

import com.entreprise.Gestionentreprise.Entities.QteFactureFournisseur;
import com.entreprise.Gestionentreprise.Services.QteFactureFournisseurService;

@CrossOrigin(origins = "http://localhost:8082")
@RestController
@RequestMapping(path = "/api/qteFacturesFournisseurs")
public class QteFactureFournisseurController {

	@Autowired
	private QteFactureFournisseurService qteFactureFournisseurService;

	@GetMapping
	public ResponseEntity<List<QteFactureFournisseur>> getQteAllFactures() {
		return qteFactureFournisseurService.getAllQteFactureFournisseurs();
	}

	@GetMapping("/{idProduit}/{idFacture}")
	public ResponseEntity<QteFactureFournisseur> getQteFactureById(@PathVariable("idFacture") Long idFacture,
			@PathVariable("idProduit") Long idProduit) {
		return qteFactureFournisseurService.getQteFactureFournisseurById(idFacture, idProduit);
	}

	@PostMapping("/{idProduit}/{idFacture}")
	public ResponseEntity<QteFactureFournisseur> createQteFacture(@PathVariable("idFacture") Long idFacture,
			@PathVariable("idProduit") Long idProduit, @RequestBody QteFactureFournisseur qteFactureFournisseur) {
		return qteFactureFournisseurService.createQteFactureFournisseur(idFacture, idProduit, qteFactureFournisseur);
	}

	@DeleteMapping("/{idProduit}/{idFacture}")
	public ResponseEntity<HttpStatus> deleteQteFacture(@PathVariable("idFacture") Long idFacture,
			@PathVariable("idProduit") Long idProduit) {
		return qteFactureFournisseurService.deleteQteFactureFournisseur(idFacture, idProduit);
	}

	@DeleteMapping
	public ResponseEntity<HttpStatus> deleteAllQteFactures() {
		return qteFactureFournisseurService.deleteAllQteFacturesFournisseurs();
	}
}
