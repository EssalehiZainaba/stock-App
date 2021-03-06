package com.entreprise.Gestionentreprise.Services;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.entreprise.Gestionentreprise.Entities.ERole;
import com.entreprise.Gestionentreprise.Entities.Role;
import com.entreprise.Gestionentreprise.Entities.Utilisateur;
import com.entreprise.Gestionentreprise.Playload.request.LoginRequest;
import com.entreprise.Gestionentreprise.Playload.request.SignupRequest;
import com.entreprise.Gestionentreprise.Playload.response.JwtResponse;
import com.entreprise.Gestionentreprise.Playload.response.MessageResponse;
import com.entreprise.Gestionentreprise.Repository.RoleRepository;
import com.entreprise.Gestionentreprise.Repository.UtilisateurRepository;
import com.entreprise.Gestionentreprise.security.jwt.JwtUtils;
import com.entreprise.Gestionentreprise.security.services.UtilisateurDetailsImpl;

@Service
public class AuthService {

	@Autowired
	AuthenticationManager authenticationManager;

	@Autowired
	UtilisateurRepository userRepository;

	@Autowired
	RoleRepository roleRepository;

	@Autowired
	PasswordEncoder encoder;

	@Autowired
	JwtUtils jwtUtils;

	@Autowired
	UtilisateurRepository utilisateurRepository;

	public ResponseEntity<?> authenticateUser(LoginRequest loginRequest) {

		Authentication authentication = authenticationManager.authenticate(
				new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword()));

		SecurityContextHolder.getContext().setAuthentication(authentication);
		String jwt = jwtUtils.generateJwtToken(authentication);

		Optional<Utilisateur> utilisateur = utilisateurRepository.findByUsername(loginRequest.getUsername());

		if (utilisateur.isPresent()) {
			Utilisateur _utilisateur = utilisateur.get();
			_utilisateur.setToken(jwt);
			utilisateurRepository.save(_utilisateur);
		}

		UtilisateurDetailsImpl userDetails = (UtilisateurDetailsImpl) authentication.getPrincipal();
		List<String> roles = userDetails.getAuthorities().stream().map(item -> item.getAuthority())
				.collect(Collectors.toList());

		return ResponseEntity.ok(
				new JwtResponse(jwt, userDetails.getId(), userDetails.getUsername(), userDetails.getEmail(), roles));
	}

	public ResponseEntity<?> registerUser(SignupRequest signUpRequest) {
		if (userRepository.existsByUsername(signUpRequest.getUsername())) {
			return ResponseEntity.badRequest().body(new MessageResponse("Error: Username is already taken!"));
		}

		if (userRepository.existsByEmail(signUpRequest.getEmail())) {
			return ResponseEntity.badRequest().body(new MessageResponse("Error: Email is already in use!"));
		}

		// Create new user's account
		Utilisateur user = new Utilisateur(signUpRequest.getUsername(), signUpRequest.getEmail(),
				encoder.encode(signUpRequest.getPassword()));

		Set<String> strRoles = signUpRequest.getRole();
		Set<Role> roles = new HashSet<>();

		if (strRoles == null) {
			Role publicRole = roleRepository.findByRole(ERole.public_user)
					.orElseThrow(() -> new RuntimeException("Error: Role is not found."));
			roles.add(publicRole);

		} else {
			strRoles.forEach(role -> {

				switch (role) {

				case "admin":
					Role adminRole = roleRepository.findByRole(ERole.admin)
							.orElseThrow(() -> new RuntimeException("Error: Role is not found."));
					roles.add(adminRole);

					break;
				case "Responsable_achats":
					Role achatsRole = roleRepository.findByRole(ERole.Responsable_achats)
							.orElseThrow(() -> new RuntimeException("Error: Role is not found."));
					roles.add(achatsRole);

					break;
				case "Responsable_ventes":
					Role ventsRole = roleRepository.findByRole(ERole.Responsable_ventes)
							.orElseThrow(() -> new RuntimeException("Error: Role is not found."));
					roles.add(ventsRole);

					break;
				default:
					Role publicRole = roleRepository.findByRole(ERole.public_user)
							.orElseThrow(() -> new RuntimeException("Error: Role is not found."));
					roles.add(publicRole);
				}
			});
		}

		user.setRoles(roles);
		userRepository.save(user);

		return ResponseEntity.ok(new MessageResponse("User registered successfully!"));
	}
}
