package br.edu.ifspcjo.ads.cjoweb2.dto;

public class AnimalBySpecies {

	private String species; // Era "type"
	private Long count;     // Era "Integer", mas bancos geralmente retornam COUNT como Long

	public AnimalBySpecies(String species, Long count) {
		this.species = species;
		this.count = count;
	}
	
	public AnimalBySpecies() {
	}

	public String getSpecies() {
		return species;
	}

	public void setSpecies(String species) {
		this.species = species;
	}

	public Long getCount() {
		return count;
	}

	public void setCount(Long count) {
		this.count = count;
	}
}