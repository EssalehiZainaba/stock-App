package com.entreprise.Gestionentreprise.Controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.entreprise.Gestionentreprise.Entities.Client;
import com.entreprise.Gestionentreprise.Entities.Utilisateur;
import com.entreprise.Gestionentreprise.Services.ClientService;
import com.entreprise.Gestionentreprise.Services.UtilisateurService;

@CrossOrigin(origins = "http://localhost:8082")
@RestController
@RequestMapping(path="/api/utilisateurs")
public class UtilisateurController {

	@Autowired
	private UtilisateurService utilisateurService;
	
	@GetMapping  
	public ResponseEntity<List<Utilisateur>> getAllUtilisateurs(@RequestParam(required = false) String username){
		return utilisateurService.getAllUtilisateurs(username);
	}
	
	
	
	
	
	
	
}
