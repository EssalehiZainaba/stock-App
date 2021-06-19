package com.entreprise.Gestionentreprise.Services;


import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;


import com.entreprise.Gestionentreprise.Entities.Produit;
import com.entreprise.Gestionentreprise.Repository.ProduitRepository;


@Service
public class ProduitService {
	
	@Autowired
	ProduitRepository produitRepository;

	public ResponseEntity<List<Produit>> getAllPoduits(String nom) {
		
		try {
			List<Produit> produits = new ArrayList<Produit>();

			if (nom == null)
				produitRepository.findAll().forEach(produits::add);
			else
				produitRepository.findByNomContaining(nom).forEach(produits::add);

			if (produits.isEmpty()) {
				return new ResponseEntity<>(HttpStatus.NO_CONTENT);
			}

			return new ResponseEntity<>(produits, HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	public ResponseEntity<Produit> createProduit(Produit produit) {
		try {
			
			Produit _produit = produitRepository.save(new Produit(produit.getNom(), produit.getDescription(), produit.getCodeBarre(), produit.getPrix(), produit.getTypeArticle(), produit.getImage64()));		 
	        
			return new ResponseEntity<>(_produit, HttpStatus.CREATED);
		} catch (Exception e) {
			return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	public ResponseEntity<Produit> getProduitById(Long id) {
		Optional<Produit> produit = produitRepository.findById(id);

		if (produit.isPresent()) {
			return new ResponseEntity<>(produit.get(), HttpStatus.OK);
		} else {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
	}


	public ResponseEntity<Produit> updateProduit(long id, Produit produit) {
		Optional<Produit> produitData = produitRepository.findById(id);

		if (produitData.isPresent()) {
			Produit _produit = produitData.get();
			_produit.setNom(produit.getNom());
			_produit.setDescription(produit.getDescription());
			_produit.setCodeBarre(produit.getCodeBarre());
			_produit.setPrix(produit.getPrix());
			_produit.setTypeArticle(produit.getTypeArticle());
			_produit.setImage64(produit.getImage64());
			
			return new ResponseEntity<>(produitRepository.save(_produit), HttpStatus.OK);
			
		} else {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
	}

	public ResponseEntity<HttpStatus> deleteProduit(Long id) {
	
		try {
			produitRepository.deleteById(id);

			return new ResponseEntity<>(HttpStatus.NO_CONTENT);
		} catch (Exception e) {
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	public ResponseEntity<HttpStatus> deleteAllProduits() {
		try {
			produitRepository.deleteAll();
			return new ResponseEntity<>(HttpStatus.NO_CONTENT);
		} catch (Exception e) {
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	

}
