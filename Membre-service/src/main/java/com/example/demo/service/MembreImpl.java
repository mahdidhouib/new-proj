package com.example.demo.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.example.demo.entity.EnseignantChercheur;
import com.example.demo.entity.Etudiant;
import com.example.demo.entity.Membre;
import com.example.demo.repository.EnseignantRepository;
import com.example.demo.repository.EtudiantRepository;
import com.example.demo.repository.MembreRepository;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class MembreImpl implements IMembreService {
	MembreRepository membreRepository;
	EtudiantRepository etudiantRepository;
	EnseignantRepository enseignantRepository;

	public Membre addMembre(Membre m) {
		membreRepository.save(m);
		return m;
	}

	public void deleteMembre(Long id) {
		membreRepository.deleteById(id);
	}

	public Membre updateMembre(Membre m) {
		return membreRepository.saveAndFlush(m);
	}

	public Membre findMembre(Long id) {
		Membre m = (Membre) membreRepository.findById(id).get();
		return m;
	}

	public List<Membre> findAll() {
		return membreRepository.findAll();
	}

	public Membre findByCin(String cin) {
		return membreRepository.findByCin(cin);
	}

	public Membre findByEmail(String email) {
		return membreRepository.findByEmail(email);
	}

	public List<Membre> findByNom(String nom) {
		return membreRepository.findByNom(nom);
	}

	public List<Etudiant> findByDiplome(String diplome) {
		return etudiantRepository.findByDiplome(diplome);
	}

	public List<EnseignantChercheur> findByGrade(String grade) {
		return enseignantRepository.findByGrade(grade);
	}

	public List<EnseignantChercheur> findByEtablissement(String etablissement) {
		return enseignantRepository.findByEtablissement(etablissement);
	}
	
	public String affecterEncadrant(Long idEtd, Long idEns) {
		Etudiant etd = (Etudiant)this.findMembre(idEtd);
		EnseignantChercheur encadrant = (EnseignantChercheur)this.findMembre(idEns);
		etd.setEncadrant(encadrant);
		this.updateMembre(etd);
		return "Encadrant "+encadrant.getPrenom()+" "+encadrant.getNom()+" affecté avec succés à l'étudiant "+etd.getPrenom()+" "+etd.getNom();
	}
	
	public List<Etudiant> afficherEtudiantsEncadres(Long idEns){
		EnseignantChercheur ens = enseignantRepository.findById(idEns).get();
		return etudiantRepository.findByEncadrant(ens);
	}
}
