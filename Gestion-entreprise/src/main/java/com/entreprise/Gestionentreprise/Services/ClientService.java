package com.entreprise.Gestionentreprise.Services;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.entreprise.Gestionentreprise.Entities.Client;
import com.entreprise.Gestionentreprise.Repository.ClientRepository;

@Service
public class ClientService {

	@Autowired
	ClientRepository clientRepository;

	public ResponseEntity<List<Client>> getAllClients(String prenom) {

		try {
			List<Client> clients = new ArrayList<Client>();

			if (prenom == null)
				clientRepository.findAll().forEach(clients::add);
			else
				clientRepository.findByNomContaining(prenom).forEach(clients::add);

			if (clients.isEmpty()) {
				return new ResponseEntity<>(HttpStatus.NO_CONTENT);
			}

			return new ResponseEntity<>(clients, HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	public ResponseEntity<Client> createClient(Client client) {
		try {

			Client _client = clientRepository.save(new Client(client.getNom(), client.getPrenom(), client.getVille(),
					client.getPays(), client.getPhone(), client.getEmail(), client.getDescription(), client.getRue(),
					client.getImage64()));

			return new ResponseEntity<>(_client, HttpStatus.CREATED);
		} catch (Exception e) {
			return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	public ResponseEntity<Client> getClientById(Long id) {
		Optional<Client> client = clientRepository.findById(id);

		if (client.isPresent()) {
			return new ResponseEntity<>(client.get(), HttpStatus.OK);
		} else {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
	}

	public ResponseEntity<Client> updateProduit(long id, Client client) {
		Optional<Client> clientData = clientRepository.findById(id);

		if (clientData.isPresent()) {
			Client _client = clientData.get();

			_client.setNom(client.getNom());
			_client.setPrenom(client.getPrenom());
			_client.setVille(client.getVille());
			_client.setPays(client.getPays());
			_client.setPhone(client.getPhone());
			_client.setRue(client.getRue());
			_client.setDescription(client.getDescription());
			_client.setImage64(client.getImage64());

			return new ResponseEntity<>(clientRepository.save(_client), HttpStatus.OK);

		} else {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
	}

	public ResponseEntity<HttpStatus> deleteClient(Long id) {

		Optional<Client> client = clientRepository.findById(id);

		try {
			clientRepository.deleteById(id);

			return new ResponseEntity<>(HttpStatus.NO_CONTENT);
		} catch (Exception e) {
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	public ResponseEntity<HttpStatus> deleteAllClients() {
		try {
			clientRepository.deleteAll();
			return new ResponseEntity<>(HttpStatus.NO_CONTENT);
		} catch (Exception e) {
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

}
