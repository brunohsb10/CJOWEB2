package br.edu.ifspcjo.ads.cjoweb2.dto;

import java.time.LocalDate;

public class AnimalByBirthDate {

	private LocalDate date;
	private Long count; // Substitui "totalDistance" por "count" (contagem de animais)

	public AnimalByBirthDate(LocalDate date, Long count) {
		this.date = date;
		this.count = count;
	}

	public AnimalByBirthDate() {
	}

	public LocalDate getDate() {
		return date;
	}

	public void setDate(LocalDate date) {
		this.date = date;
	}

	public Long getCount() {
		return count;
	}

	public void setCount(Long count) {
		this.count = count;
	}
	
}