package dao;

import java.math.BigDecimal;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import model.Produto;

public class ProdutoDAO {
    private Connection conexao;

    // Construtor para estabelecer a conexão com o banco de dados
    public ProdutoDAO() throws SQLException {
        String url = "jdbc:postgresql://localhost:5432/produtos_db";
        String user = "postgres";
        String password = "73548993";
        conexao = DriverManager.getConnection(url, user, password);
        
        // Verificação da conexão
        if (conexao != null) {
            System.out.println("Conexão com o banco de dados estabelecida com sucesso!");
        } else {
            throw new SQLException("Falha ao conectar ao banco de dados.");
        }
    }


    // Método para adicionar um produto ao banco de dados
    public void add(Produto produto) throws SQLException {
        String sql = "INSERT INTO produtos (descricao, preco, quantidade, data_fabricacao, data_validade) VALUES (?, ?, ?, ?, ?)";
        try (PreparedStatement stmt = conexao.prepareStatement(sql)) {
            stmt.setString(1, produto.getDescricao());
            stmt.setBigDecimal(2, new BigDecimal(produto.getPreco()));
            stmt.setInt(3, produto.getQuant());
            stmt.setTimestamp(4, Timestamp.valueOf(produto.getDataFabricacao()));
            stmt.setDate(5, Date.valueOf(produto.getDataValidade()));
            stmt.executeUpdate();
        }
    }

    // Método para obter um produto pelo ID
    public Produto get(int id) throws SQLException {
        String sql = "SELECT * FROM produtos WHERE id = ?";
        Produto produto = null;
        try (PreparedStatement stmt = conexao.prepareStatement(sql)) {
            stmt.setInt(1, id);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    produto = new Produto(
                        rs.getInt("id"),
                        rs.getString("descricao"),
                        rs.getBigDecimal("preco").floatValue(),
                        rs.getInt("quantidade"),
                        rs.getTimestamp("data_fabricacao").toLocalDateTime(),
                        rs.getDate("data_validade").toLocalDate()
                    );
                }
            }
        }
        return produto;
    }

    // Método para atualizar um produto no banco de dados
    public void update(Produto produto) throws SQLException {
        String sql = "UPDATE produtos SET descricao = ?, preco = ?, quantidade = ?, data_fabricacao = ?, data_validade = ? WHERE id = ?";
        try (PreparedStatement stmt = conexao.prepareStatement(sql)) {
            stmt.setString(1, produto.getDescricao());
            stmt.setBigDecimal(2, new BigDecimal(produto.getPreco()));
            stmt.setInt(3, produto.getQuant());
            stmt.setTimestamp(4, Timestamp.valueOf(produto.getDataFabricacao()));
            stmt.setDate(5, Date.valueOf(produto.getDataValidade()));
            stmt.setInt(6, produto.getId());
            stmt.executeUpdate();
        }
    }

    // Método para remover um produto do banco de dados
    public void remove(Produto produto) throws SQLException {
        String sql = "DELETE FROM produtos WHERE id = ?";
        try (PreparedStatement stmt = conexao.prepareStatement(sql)) {
            stmt.setInt(1, produto.getId());
            stmt.executeUpdate();
        }
    }

    // Método para obter todos os produtos do banco de dados
    public List<Produto> getAll() throws SQLException {
        List<Produto> produtos = new ArrayList<>();
        String sql = "SELECT * FROM produtos";
        try (PreparedStatement stmt = conexao.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                Produto produto = new Produto(
                    rs.getInt("id"),
                    rs.getString("descricao"),
                    rs.getBigDecimal("preco").floatValue(),
                    rs.getInt("quantidade"),
                    rs.getTimestamp("data_fabricacao").toLocalDateTime(),
                    rs.getDate("data_validade").toLocalDate()
                );
                produtos.add(produto);
            }
        }
        return produtos;
    }
}