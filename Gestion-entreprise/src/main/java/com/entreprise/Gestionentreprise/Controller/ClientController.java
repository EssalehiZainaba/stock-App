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

import com.entreprise.Gestionentreprise.Entities.Client;
import com.entreprise.Gestionentreprise.Services.ClientService;

@CrossOrigin(origins = "http://localhost:8082")
@RestController
@RequestMapping(path = "/api/clients")
public class ClientController {

	@Autowired
	private ClientService clientService;

	@GetMapping
	public ResponseEntity<List<Client>> getAllClients(@RequestParam(required = false) String prenom) {
		return clientService.getAllClients(prenom);
	}

	@GetMapping("/{id}")
	public ResponseEntity<Client> getClientById(@PathVariable("id") Long id) {
		return clientService.getClientById(id);
	}

	@PostMapping
	public ResponseEntity<Client> createClient(@RequestBody Client client) {
		return clientService.createClient(client);
	}

	@PutMapping("/{id}")
	public ResponseEntity<Client> updateClient(@PathVariable("id") Long id, @RequestBody Client client) {
		return clientService.updateProduit(id, client);
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<HttpStatus> deleteClient(@PathVariable("id") Long id) {
		return clientService.deleteClient(id);
	}

	@DeleteMapping
	public ResponseEntity<HttpStatus> deleteAllClients() {
		return clientService.deleteAllClients();
	}

}
