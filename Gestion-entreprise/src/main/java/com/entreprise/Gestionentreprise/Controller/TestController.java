package com.entreprise.Gestionentreprise.Controller;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.RestController;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/test")
public class TestController {
	
	@GetMapping("/all")
	public String allAccess() {
		return "Public Content.";
	}
	
	@GetMapping("/public")
	@PreAuthorize("hasAuthority('public_user') or hasAuthority('Responsable_ventes') or hasAuthority('Responsable_achats') or hasAuthority('admin')")
	public String publicAccess() {
		return "public user.";
	}
	
	@GetMapping("/ventes")
	@PreAuthorize("hasAuthority('Responsable_ventes')")
	public String ventesAccess() {
		return "les ventes.";
	}

	@GetMapping("/achats")
	@PreAuthorize("hasAuthority('Responsable_achats')")
	public String achatsAccess() {
		return "les achats.";
	}

	@GetMapping("/admin")
	@PreAuthorize("hasAuthority('admin')")
	public String adminAccess() {
		return "Admin Board.";
	}
	
	

}
