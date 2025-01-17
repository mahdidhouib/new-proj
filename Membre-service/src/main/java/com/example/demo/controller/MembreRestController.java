package com.example.demo.controller;

import java.util.List;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.entity.EnseignantChercheur;
import com.example.demo.entity.Etudiant;
import com.example.demo.entity.Membre;
import com.example.demo.service.IMembreEventService;
import com.example.demo.service.IMembreOutilService;
import com.example.demo.service.IMembreService;

import lombok.AllArgsConstructor;

@RestController
@AllArgsConstructor
public class MembreRestController {
	IMembreService membreService;
	IMembreOutilService membreOutilService;
	IMembreEventService membreEventService;

	@RequestMapping(value = "/membres", method = RequestMethod.GET)
	public List<Membre> findMembres() {
		return membreService.findAll();
	}

	@GetMapping(value = "/membres/{id}")
	public Membre findOneMemberById(@PathVariable Long id) {
		return membreService.findMembre(id);
	}

	@GetMapping(value = "/membres/search/cin")
	public Membre findOneMemberByCin(@RequestParam String cin) {
		return membreService.findByCin(cin);
	}

	@GetMapping(value = "/membres/search/email")
	public Membre findOneMemberByEmail(@RequestParam String email) {
		return membreService.findByEmail(email);
	}

	@PostMapping(value = "/membres/enseignant")
	public Membre addMembre(@RequestBody EnseignantChercheur m) {
		return membreService.addMembre(m);
	}

	@PostMapping(value = "/membres/etudiant")
	public Membre addMembre(@RequestBody Etudiant e) {
		return membreService.addMembre(e);
	}

	@DeleteMapping(value = "/membres/{id}")
	public void deleteMembre(@PathVariable Long id) {
		membreService.deleteMembre(id);
	}

	@PutMapping(value = "/membres/etudiant/{id}")
	public Membre updatemembre(@PathVariable Long id, @RequestBody Etudiant p) {
		p.setId(id);
		return membreService.updateMembre(p);
	}

	@PutMapping(value = "/membres/enseignant/{id}")
	public Membre updateMembre(@PathVariable Long id, @RequestBody EnseignantChercheur p) {
		p.setId(id);
		return membreService.updateMembre(p);
	}
	
	@GetMapping(value = "/membres/full")
	public List<Membre> findAllFullMembers() {
		List<Membre> mbrs = membreService.findAll();
		for(Membre mbr : mbrs) {
			mbr.setOutils(membreOutilService.findAllOutilparauteur(mbr.getId()));
			mbr.setPubs(membreService.findAllPublicationparauteur(mbr.getId()));
			mbr.setEvents(membreEventService.findAllEventparauteur(mbr.getId()));
		}
		return mbrs;
	}
	
	@GetMapping(value = "/membres/{id}/full")
	public Membre findAFullMember(@PathVariable(name = "id") Long id) {
		Membre mbr = membreService.findMembre(id);
		mbr.setOutils(membreOutilService.findAllOutilparauteur(id));
		mbr.setPubs(membreService.findAllPublicationparauteur(id));
		mbr.setEvents(membreEventService.findAllEventparauteur(id));
		return mbr;
	}
	
}
