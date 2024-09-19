package service;

import dao.ProdutoDAO;
import model.Produto;

import java.sql.SQLException;
import java.util.List;

public class ProdutoService {
    private ProdutoDAO produtoDAO;

    public ProdutoService() {
        try {
            produtoDAO = new ProdutoDAO();
        } catch (SQLException e) {
            e.printStackTrace();
            // Lidar com exceção de forma adequada, talvez fechar o aplicativo ou tentar novamente
        }
    }

    public void adicionarProduto(Produto produto) {
        try {
            produtoDAO.add(produto);
        } catch (SQLException e) {
            e.printStackTrace();
            // Lidar com exceção de forma adequada
        }
    }

    public Produto obterProduto(int id) {
        try {
            return produtoDAO.get(id);
        } catch (SQLException e) {
            e.printStackTrace();
            // Lidar com exceção de forma adequada
        }
        return null;
    }

    public void atualizarProduto(Produto produto) {
        try {
            produtoDAO.update(produto);
        } catch (SQLException e) {
            e.printStackTrace();
            // Lidar com exceção de forma adequada
        }
    }

    public void removerProduto(Produto produto) {
        try {
            produtoDAO.remove(produto);
        } catch (SQLException e) {
            e.printStackTrace();
            // Lidar com exceção de forma adequada
        }
    }

    public List<Produto> listarProdutos() {
        try {
            return produtoDAO.getAll();
        } catch (SQLException e) {
            e.printStackTrace();
            // Lidar com exceção de forma adequada
        }
        return null;
    }
}