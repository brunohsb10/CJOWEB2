package br.edu.ifspcjo.ads.cjoweb2.filters; // Pacote ajustado para cjoweb2

import java.time.LocalDate;

import br.edu.ifspcjo.ads.cjoweb2.model.User;

// Alterado de ActivityFilter para AnimalFilter
public class AnimalFilter {

	private User user;
	
	// Substituímos ActivityType por String, pois sua espécie é uma String (Gato, Cachorro...)
	private String species;
	
	// Adicionei status, pois é comum filtrar por "DISPONIVEL" ou "ADOTADO"
	private String status;
	
	private LocalDate initialDate; // Para filtrar intervalo de data de nascimento
	
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