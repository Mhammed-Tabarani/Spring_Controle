package com.controle.bibliotheque.repos;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.controle.bibliotheque.entities.Livre;

@Repository
public interface LivreRepo extends JpaRepository<Livre, Long> {

	List<Livre> findByOrderByTitreAsc();

}
