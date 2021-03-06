package com.entreprise.Gestionentreprise.security.services;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.entreprise.Gestionentreprise.Entities.Utilisateur;
import com.entreprise.Gestionentreprise.Repository.UtilisateurRepository;

@Service
public class UtilisateurDetailsServiceImpl implements UserDetailsService{

	@Autowired
	UtilisateurRepository utilisateurRepository;

	@Override
	@Transactional
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		Utilisateur user = utilisateurRepository.findByUsername(username)
				.orElseThrow(() -> new UsernameNotFoundException("User Not Found with username: " + username));

		return UtilisateurDetailsImpl.build(user);
	}
}
