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

import com.entreprise.Gestionentreprise.Entities.Produit;
import com.entreprise.Gestionentreprise.Services.ProduitService;

@CrossOrigin(origins = "http://localhost:8082")
@RestController
@RequestMapping(path = "/api")
public class ProduitController {

	@Autowired
	private ProduitService produitService;

	@GetMapping("/produits")
	public ResponseEntity<List<Produit>> getAllProduits(@RequestParam(required = false) String nom) {
		return produitService.getAllPoduits(nom);
	}

	@GetMapping("produits/{id}")
	public ResponseEntity<Produit> getProduitById(@PathVariable("id") Long id) {
		return produitService.getProduitById(id);
	}

	@PostMapping(value = "/produits")
	public ResponseEntity<Produit> createProduit(@RequestBody Produit produit) {
		return produitService.createProduit(produit);
	}

	@PutMapping("produits/{id}")
	public ResponseEntity<Produit> updateProduit(@PathVariable("id") Long id, @RequestBody Produit produit) {
		return produitService.updateProduit(id, produit);
	}

	@DeleteMapping("produits/{id}")
	public ResponseEntity<HttpStatus> deleteProduit(@PathVariable("id") Long id) {
		return produitService.deleteProduit(id);
	}

	@DeleteMapping("/produits")
	public ResponseEntity<HttpStatus> deleteAllProduits() {
		return produitService.deleteAllProduits();
	}

}
