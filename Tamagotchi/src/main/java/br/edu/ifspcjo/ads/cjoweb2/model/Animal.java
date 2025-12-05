package br.edu.ifspcjo.ads.cjoweb2.model;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.Objects;

public class Animal implements Serializable {
    private static final long serialVersionUID = 1L;
    
    private Long id;
    private String name;
    private String species;
    private LocalDate dateOfBirth;
    private String description;
    private String status; 
    private Long userId;
    private Integer adopterId; // NOVO CAMPO

    // Getters e Setters normais...
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    public String getSpecies() { return species; }
    public void setSpecies(String species) { this.species = species; }
    public LocalDate getDateOfBirth() { return dateOfBirth; }
    public void setDateOfBirth(LocalDate dateOfBirth) { this.dateOfBirth = dateOfBirth; }
    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }
    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }
    public Long getUserId() { return userId; }
    public void setUserId(Long userId) { this.userId = userId; }
    
    // Getter e Setter do AdopterId
    public Integer getAdopterId() { return adopterId; }
    public void setAdopterId(Integer adopterId) { this.adopterId = adopterId; }

    @Override
    public int hashCode() { return Objects.hash(id); }
    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        Animal other = (Animal) obj;
        return Objects.equals(id, other.id);
    }
}