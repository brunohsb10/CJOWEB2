package br.edu.ifspcjo.ads.cjoweb2.dao;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import javax.sql.DataSource;

import br.edu.ifspcjo.ads.cjoweb2.dto.AnimalByBirthDate;
import br.edu.ifspcjo.ads.cjoweb2.dto.AnimalBySpecies;
import br.edu.ifspcjo.ads.cjoweb2.filters.AnimalFilter;
import br.edu.ifspcjo.ads.cjoweb2.model.Animal;

public class AnimalDao {
    private DataSource dataSource;

    public AnimalDao(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    // --- CRUD BÁSICO ---
    public void save(Animal a) {
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
                if(rs.next()) a.setId(rs.getLong(1));
            }
        } catch (SQLException e) { throw new RuntimeException(e); }
    }

    public void update(Animal a) {
        String sql = "UPDATE animal SET name=?, species=?, birth_date=?, description=?, status=? WHERE id=?";
        try (Connection conn = dataSource.getConnection(); PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, a.getName());
            ps.setString(2, a.getSpecies());
            ps.setDate(3, Date.valueOf(a.getDateOfBirth()));
            ps.setString(4, a.getDescription());
            ps.setString(5, a.getStatus());
            ps.setLong(6, a.getId());
            ps.executeUpdate();
        } catch (SQLException e) { throw new RuntimeException(e); }
    }

    public void delete(Long id) {
        String sql = "DELETE FROM animal WHERE id = ?";
        try (Connection conn = dataSource.getConnection(); PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setLong(1, id);
            ps.executeUpdate();
        } catch (SQLException e) { throw new RuntimeException(e); }
    }

    public Animal getAnimalById(Long id) {
        String sql = "SELECT * FROM animal WHERE id = ?";
        try (Connection conn = dataSource.getConnection(); PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setLong(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) return mapResultSetToAnimal(rs);
            }
        } catch (SQLException e) { throw new RuntimeException(e); }
        return null;
    }

    public List<Animal> findAll() {
        String sql = "SELECT * FROM animal";
        List<Animal> list = new ArrayList<>();
        try (Connection conn = dataSource.getConnection(); PreparedStatement ps = conn.prepareStatement(sql); ResultSet rs = ps.executeQuery()) {
            while (rs.next()) list.add(mapResultSetToAnimal(rs));
        } catch (SQLException e) { throw new RuntimeException(e); }
        return list;
    }

    // --- ADOÇÃO ---
    public boolean completeAdoption(Long animalId, Integer adopterId) {
        String sql = "UPDATE animal SET status='Adopted', adopter_id=? WHERE id=?";
        try (Connection conn = dataSource.getConnection(); PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, adopterId);
            ps.setLong(2, animalId);
            return ps.executeUpdate() > 0;
        } catch (SQLException e) { throw new RuntimeException(e); }
    }

    // --- FILTROS (Consertando o erro do SearchServlet) ---
    public List<Animal> getAnimalsByFilter(AnimalFilter filter) {
        StringBuilder sql = new StringBuilder("SELECT * FROM animal WHERE 1=1");
        List<Object> params = new ArrayList<>();

        if (filter.getSpecies() != null && !filter.getSpecies().isEmpty()) {
            sql.append(" AND species = ?");
            params.add(filter.getSpecies());
        }
        if (filter.getStatus() != null && !filter.getStatus().isEmpty()) {
            sql.append(" AND status = ?");
            params.add(filter.getStatus());
        }
        if (filter.getInitialDate() != null) {
            sql.append(" AND birth_date >= ?");
            params.add(Date.valueOf(filter.getInitialDate()));
        }
        if (filter.getFinalDate() != null) {
            sql.append(" AND birth_date <= ?");
            params.add(Date.valueOf(filter.getFinalDate()));
        }

        List<Animal> list = new ArrayList<>();
        try (Connection conn = dataSource.getConnection(); PreparedStatement ps = conn.prepareStatement(sql.toString())) {
            for (int i = 0; i < params.size(); i++) {
                ps.setObject(i + 1, params.get(i));
            }
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) list.add(mapResultSetToAnimal(rs));
            }
        } catch (SQLException e) { throw new RuntimeException(e); }
        return list;
    }

    // --- ESTATÍSTICAS (Consertando o erro da Imagem) ---
    // Note que removi o parâmetro 'User', pois o Admin vê tudo
    
    public List<AnimalBySpecies> getAnimalsStatisticsBySpecies() {
        String sql = "SELECT species, COUNT(*) AS count FROM animal GROUP BY species";
        List<AnimalBySpecies> list = new ArrayList<>();
        try (Connection conn = dataSource.getConnection(); PreparedStatement ps = conn.prepareStatement(sql); ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                list.add(new AnimalBySpecies(rs.getString("species"), rs.getLong("count")));
            }
        } catch (SQLException e) { throw new RuntimeException(e); }
        return list;
    }

    public List<AnimalByBirthDate> getAnimalsStatisticsByBirthDate() {
        String sql = "SELECT birth_date, COUNT(*) AS count FROM animal GROUP BY birth_date";
        List<AnimalByBirthDate> list = new ArrayList<>();
        try (Connection conn = dataSource.getConnection(); PreparedStatement ps = conn.prepareStatement(sql); ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                Date sqlDate = rs.getDate("birth_date");
                if (sqlDate != null) {
                    list.add(new AnimalByBirthDate(sqlDate.toLocalDate(), rs.getLong("count")));
                }
            }
        } catch (SQLException e) { throw new RuntimeException(e); }
        return list;
    }
    
    // Método auxiliar para não repetir código
    private Animal mapResultSetToAnimal(ResultSet rs) throws SQLException {
        Animal a = new Animal();
        a.setId(rs.getLong("id"));
        a.setName(rs.getString("name"));
        a.setSpecies(rs.getString("species"));
        if(rs.getDate("birth_date") != null) a.setDateOfBirth(rs.getDate("birth_date").toLocalDate());
        a.setDescription(rs.getString("description"));
        a.setStatus(rs.getString("status"));
        a.setUserId(rs.getLong("user_id"));
        // Verifica se a coluna adopter_id existe e pega o valor
        try {
            int adId = rs.getInt("adopter_id");
            if (!rs.wasNull()) a.setAdopterId(adId);
        } catch (SQLException e) { /* coluna pode não existir em queries antigas, ignorar */ }
        return a;
    }
    
    // Método legado para manter compatibilidade, mas redireciona para findAll
    public List<Animal> getAnimalsByUser(br.edu.ifspcjo.ads.cjoweb2.model.User user) {
        return findAll();
    }
}