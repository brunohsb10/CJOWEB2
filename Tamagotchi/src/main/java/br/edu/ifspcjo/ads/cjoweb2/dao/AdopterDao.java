package br.edu.ifspcjo.ads.cjoweb2.dao;

import br.edu.ifspcjo.ads.cjoweb2.model.Adopter;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import javax.sql.DataSource;

public class AdopterDao {

    private DataSource dataSource;
    
    // Use o mesmo construtor do AnimalDao/UserDao
    public AdopterDao(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    // Método para salvar um novo Adotante
    public Boolean save(Adopter a) {
        String sql = "INSERT INTO adopter (name, email, phone, address) VALUES (?, ?, ?, ?)";
        try (Connection conn = dataSource.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            
            ps.setString(1, a.getName());
            ps.setString(2, a.getEmail());
            ps.setString(3, a.getPhone());
            ps.setString(4, a.getAddress());
            
            return ps.executeUpdate() > 0;
            
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    // Método para listar todos os Adotantes
    public List<Adopter> findAll() {
        List<Adopter> adopters = new ArrayList<>();
        String sql = "SELECT id, name, email, phone, address FROM adopter ORDER BY name";
        
        try (Connection conn = dataSource.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            
            while (rs.next()) {
                Adopter a = new Adopter();
                a.setId(rs.getInt("id"));
                a.setName(rs.getString("name"));
                a.setEmail(rs.getString("email"));
                a.setPhone(rs.getString("phone"));
                a.setAddress(rs.getString("address"));
                adopters.add(a);
            }
            
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return adopters;
    }
    
    // Método para buscar um Adotante por ID (útil para edição e para a baixa de adoção)
    public Adopter findById(Integer id) {
        String sql = "SELECT id, name, email, phone, address FROM adopter WHERE id = ?";
        try (Connection conn = dataSource.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            
            ps.setInt(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    Adopter a = new Adopter();
                    a.setId(rs.getInt("id"));
                    a.setName(rs.getString("name"));
                    a.setEmail(rs.getString("email"));
                    a.setPhone(rs.getString("phone"));
                    a.setAddress(rs.getString("address"));
                    return a;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
}