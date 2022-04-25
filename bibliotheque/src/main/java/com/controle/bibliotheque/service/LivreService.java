package com.controle.bibliotheque.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.controle.bibliotheque.entities.Livre;
import com.controle.bibliotheque.repos.LivreRepo;

import java.util.Date;
import java.util.List;

@Service
public class LivreService {

	@Autowired
	LivreRepo livreRepository;

	public java.util.List<Livre> getAll_findbytitle() {
		return livreRepository.findByOrderByTitreAsc();
	}

	public Livre getLivre(long id) {
		
		Livre livre=livreRepository.findById(id).get();
		livre.setDate_derniere_consultation(new Date());
		livreRepository.save(livre);
		return livre;
		
	}

	public void deleteLivre(long id) {

		livreRepository.deleteById(id);
	}

	public void update(long id, Livre l) {
		Livre livre = livreRepository.findById(id).get();
		livre.setAuteur(l.getAuteur());
		livre.setDate_derniere_consultation(new Date());
		livre.setDate_sortie(l.getDate_sortie());
		livre.setDiponible(l.getDiponible());
		livre.setIsbn(l.getIsbn());
		livre.setMaison_idition(l.getMaison_idition());
		livre.setNombre_page(l.getNombre_page());
		livre.setTitre(l.getTitre());
		livreRepository.save(livre);
	}

	public Livre create(Livre livre) {

		//livre.setAuteur("Mhammed");
		return livreRepository.save(livre);

	}

}
