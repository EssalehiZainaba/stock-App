package com.entreprise.Gestionentreprise.Repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.entreprise.Gestionentreprise.Entities.ERole;
import com.entreprise.Gestionentreprise.Entities.Role;

@Repository
public interface RoleRepository extends JpaRepository<Role, Long> {

	Optional<Role> findByRole(ERole role);
}
