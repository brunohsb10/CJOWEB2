package br.edu.ifspcjo.ads.cjoweb2.filters; // Pacote ajustado para cjoweb2

import java.time.LocalDate;

import br.edu.ifspcjo.ads.cjoweb2.model.User;


public class AnimalFilter {

	private User user;
	
	
	private String species;
	
	
	private String status;
	
	private LocalDate initialDate; 
	
	private LocalDate finalDate;

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public String getSpecies() {
		return species;
	}

	public void setSpecies(String species) {
		this.species = species;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public LocalDate getInitialDate() {
		return initialDate;
	}

	public void setInitialDate(LocalDate initialDate) {
		this.initialDate = initialDate;
	}

	public LocalDate getFinalDate() {
		return finalDate;
	}

	public void setFinalDate(LocalDate finalDate) {
		this.finalDate = finalDate;
	}

}
