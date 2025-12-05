package br.edu.ifspcjo.ads.cjoweb2.dto;

public class AnimalBySpecies {

	private String species; /
	private Long count;     
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
