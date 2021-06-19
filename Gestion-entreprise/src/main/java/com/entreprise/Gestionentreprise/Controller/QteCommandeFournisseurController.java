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

import com.entreprise.Gestionentreprise.Entities.QteCommandFournisseur;
import com.entreprise.Gestionentreprise.Entities.QteCommandFournisseurKey;
import com.entreprise.Gestionentreprise.Services.QteCommandeFournisseurService;

@CrossOrigin(origins = "http://localhost:8082")
@RestController
@RequestMapping(path = "/api/qteCommandesFournisseur")
public class QteCommandeFournisseurController {

	@Autowired
	private QteCommandeFournisseurService qteCommandeFournisseurService;

	@GetMapping
	public ResponseEntity<List<QteCommandFournisseur>> getQteAllCommandes() {
		return qteCommandeFournisseurService.getAllQteCommandes();
	}

	@GetMapping("/{idProduit}/{idCommande}")
	public ResponseEntity<QteCommandFournisseur> getQteCommandeById(@PathVariable("idCommande") Long idCommande,
			@PathVariable("idProduit") Long idProduit) {
		return qteCommandeFournisseurService.getQteCommandeById(idCommande, idProduit);
	}

	@PostMapping("/{idProduit}/{idCommande}/{qte}")
	public ResponseEntity<QteCommandFournisseur> createQteCommande(@PathVariable("idCommande") Long idCommande,
			@PathVariable("idProduit") Long idProduit, @PathVariable("qte") int qtecommandeFournisseur) {
		return qteCommandeFournisseurService.createQteCommande(idCommande, idProduit, qtecommandeFournisseur);
	}

	@GetMapping("/{idCommande}")
	public ResponseEntity<List<QteCommandFournisseur>> getQteCommandeByCommande(
			@PathVariable("idCommande") Long idCommande) {
		return qteCommandeFournisseurService.getQteCommandeByCommande(idCommande);
	}

	@PutMapping("/{id}")
	public ResponseEntity<QteCommandFournisseur> updateQteCommande(@PathVariable("id") QteCommandFournisseurKey id,
			@RequestBody QteCommandFournisseur qteCommande) {
		return qteCommandeFournisseurService.updateQteCommande(id, qteCommande);
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<HttpStatus> deleteQteCommande(@PathVariable("id") QteCommandFournisseurKey id) {
		return qteCommandeFournisseurService.deleteQteCommande(id);
	}

	@DeleteMapping
	public ResponseEntity<HttpStatus> deleteAllQteCommandes() {
		return qteCommandeFournisseurService.deleteAllQteCommandes();
	}

}
