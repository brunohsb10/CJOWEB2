package br.edu.ifspcjo.ads.cjoweb2.dao;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import javax.sql.DataSource;

import br.edu.ifspcjo.ads.cjoweb2.dto.AnimalByBirthDate;
import br.edu.ifspcjo.ads.cjoweb2.dto.AnimalBySpecies;
import br.edu.ifspcjo.ads.cjoweb2.filters.AnimalFilter;
import br.edu.ifspcjo.ads.cjoweb2.model.Animal;
import br.edu.ifspcjo.ads.cjoweb2.model.User;

public class AnimalDao {

    private DataSource dataSource;

    public AnimalDao(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    public Boolean save(Animal a) {
        String sql = "INSERT INTO animal (name, species, birth_date, description, status, user_id) VALUES (?,?,?,?,?,?)";
        try (Connection conn = dataSource.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            
            ps.setString(1, a.getName());
            ps.setString(2, a.getSpecies());
            ps.setDate(3, Date.valueOf(a.getDateOfBirth()));
            ps.setString(4, a.getDescription());
            ps.setString(5, a.getStatus());
            ps.setLong(6, a.getUserId());
            
            ps.executeUpdate();
            
            try (ResultSet rs = ps.getGeneratedKeys()) {
                if(rs.next()) {
                    a.setId(rs.getLong(1));
                }
            }
            return true;
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao salvar animal", e);
        }
    }

    public Boolean update(Animal a) {
        String sql = "UPDATE animal SET name=?, species=?, birth_date=?, description=?, status=?, user_id=? WHERE id=?";
        try (Connection conn = dataSource.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            
            ps.setString(1, a.getName());
            ps.setString(2, a.getSpecies());
            ps.setDate(3, Date.valueOf(a.getDateOfBirth()));
            ps.setString(4, a.getDescription());
            ps.setString(5, a.getStatus());
            ps.setLong(6, a.getUserId());
            ps.setLong(7, a.getId());
            
            int rowsAffected = ps.executeUpdate();
            return rowsAffected > 0;
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao atualizar animal", e);
        }
    }

    public void delete(Long id) {
        String sql = "DELETE FROM animal WHERE id = ?";
        try (Connection conn = dataSource.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setLong(1, id);
            ps.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao deletar animal", e);
        }
    }

    public Animal getAnimalById(Long id) {
        String sql = "SELECT id, name, species, birth_date, description, status, user_id FROM animal WHERE id = ?";
        Animal animal = null;

        try (Connection conn = dataSource.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            
            ps.setLong(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    animal = new Animal();
                    animal.setId(rs.getLong("id"));
                    animal.setName(rs.getString("name"));
                    animal.setSpecies(rs.getString("species"));
                    
                    Date sqlDate = rs.getDate("birth_date");
                    if (sqlDate != null) {
                        animal.setDateOfBirth(sqlDate.toLocalDate());
                    }
                    
                    animal.setDescription(rs.getString("description"));
                    animal.setStatus(rs.getString("status"));
                    animal.setUserId(rs.getLong("user_id"));
                }
            }
            return animal;
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao buscar animal por ID", e);
        }
    }

    // Método antigo mantido para compatibilidade, mas agora você pode usar o getAnimalsByFilter se quiser
    public List<Animal> getAnimalsByUser(User user) {
        String sql = "SELECT id, name, species, birth_date, description, status, user_id FROM animal WHERE user_id = ?";
        List<Object> params = new ArrayList<>();
        params.add(user.getId());
        
        // Reutiliza o método auxiliar para evitar repetição de código
        return getAnimalList(sql, params, user);
    }

    // --- NOVOS MÉTODOS DE FILTRO ---

    public List<Animal> getAnimalsByFilter(AnimalFilter filter) {
        StringBuilder sql = new StringBuilder("SELECT id, name, species, birth_date, description, status, user_id FROM animal WHERE user_id=?");
        List<Object> params = new ArrayList<>();
        params.add(filter.getUser().getId());

        // Filtra por Espécie (ex: Gato, Cachorro)
        if (filter.getSpecies() != null && !filter.getSpecies().isEmpty()) {
            sql.append(" AND species = ?");
            params.add(filter.getSpecies());
        }
        
        // Filtra por Status (ex: DISPONIVEL, ADOTADO)
        if (filter.getStatus() != null && !filter.getStatus().isEmpty()) {
            sql.append(" AND status = ?");
            params.add(filter.getStatus());
        }

        // Filtra por Data Inicial
        if (filter.getInitialDate() != null) {
            sql.append(" AND birth_date >= ?");
            params.add(Date.valueOf(filter.getInitialDate()));
        }

        // Filtra por Data Final
        if (filter.getFinalDate() != null) {
            sql.append(" AND birth_date <= ?");
            params.add(Date.valueOf(filter.getFinalDate()));
        }

        return getAnimalList(sql.toString(), params, filter.getUser());
    }

    // --- NOVOS MÉTODOS DE ESTATÍSTICA ---

    public List<AnimalBySpecies> getAnimalsStatisticsBySpecies(User user) {
        String sql = "SELECT species, COUNT(*) AS count FROM animal WHERE user_id=? GROUP BY species";
        List<AnimalBySpecies> list = new ArrayList<>();
        try (Connection conn = dataSource.getConnection(); 
             PreparedStatement ps = conn.prepareStatement(sql)) {
            
            ps.setLong(1, user.getId());
            
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    AnimalBySpecies item = new AnimalBySpecies();
                    item.setSpecies(rs.getString("species")); // Coluna 1
                    item.setCount(rs.getLong("count"));       // Coluna 2
                    list.add(item);
                }
            }
            return list;
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao buscar estatísticas por espécie", e);
        }
    }

    public List<AnimalByBirthDate> getAnimalsStatisticsByBirthDate(User user) {
        String sql = "SELECT birth_date, COUNT(*) AS count FROM animal WHERE user_id=? GROUP BY birth_date";
        List<AnimalByBirthDate> list = new ArrayList<>();
        try (Connection conn = dataSource.getConnection(); 
             PreparedStatement ps = conn.prepareStatement(sql)) {
            
            ps.setLong(1, user.getId());
            
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    AnimalByBirthDate item = new AnimalByBirthDate();
                    
                    Date sqlDate = rs.getDate("birth_date"); // Coluna 1
                    if (sqlDate != null) {
                        item.setDate(sqlDate.toLocalDate());
                    }
                    
                    item.setCount(rs.getLong("count")); // Coluna 2
                    list.add(item);
                }
            }
            return list;
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao buscar estatísticas por data", e);
        }
    }

    
    // Método auxiliar privado para executar a query e montar a lista
    private List<Animal> getAnimalList(String sql, List<Object> params, User user) {
        List<Animal> list = new ArrayList<>();
        try (Connection conn = dataSource.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            
            for (int i = 0; i < params.size(); i++) {
                ps.setObject(i + 1, params.get(i));
            }
            
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    Animal a = new Animal();
                    a.setId(rs.getLong("id"));
                    a.setName(rs.getString("name"));
                    a.setSpecies(rs.getString("species"));
                    
                    Date sqlDate = rs.getDate("birth_date");
                    if (sqlDate != null) {
                        a.setDateOfBirth(sqlDate.toLocalDate());
                    }
                    
                    a.setDescription(rs.getString("description"));
                    a.setStatus(rs.getString("status"));
                    
                    // Define o user_id. Se o objeto User passado estiver completo, ok.
                    // Caso contrário, apenas setamos o ID que veio do banco.
                    a.setUserId(rs.getLong("user_id"));
                    
                    list.add(a);
                }
            }
            return list;
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao listar animais", e);
        }
    }
}