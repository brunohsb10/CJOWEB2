package br.edu.ifspcjo.ads.cjoweb2.formularioaluno.model;

public class Person {

	private String name;
	private String email;
	public String getName() {
		return name;
}
	
	public Person(String name, String email) {
		super();
		this.name = name;
		
		this.email = email;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public void setName(String name) {
		this.name = name;
	}

	@Override
	public String toString() {
		return "Person [name=" + name + ", email=" + email + "]";
	}
	}


	