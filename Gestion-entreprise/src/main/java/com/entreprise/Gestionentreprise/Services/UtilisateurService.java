package com.entreprise.Gestionentreprise.Services;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.entreprise.Gestionentreprise.Entities.Client;
import com.entreprise.Gestionentreprise.Entities.Utilisateur;
import com.entreprise.Gestionentreprise.Repository.UtilisateurRepository;

@Service
public class UtilisateurService {
	
	@Autowired
	UtilisateurRepository utilisateurRepository;
	
	public ResponseEntity<List<Utilisateur>> getAllUtilisateurs(String username) {
		
		try {
			List<Utilisateur> utilisateurs = new ArrayList<Utilisateur>();

			if (username == null)
				utilisateurRepository.findAll().forEach(utilisateurs::add);
			else
				utilisateurRepository.findByUsernameContaining(username).forEach(utilisateurs::add);

			if (utilisateurs.isEmpty()) {
				return new ResponseEntity<>(HttpStatus.NO_CONTENT);
			}

			return new ResponseEntity<>(utilisateurs, HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

}
