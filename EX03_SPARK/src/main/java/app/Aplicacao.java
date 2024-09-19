package app;

import static spark.Spark.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import service.ProdutoService;
import model.Produto;

public class Aplicacao {
    
    private static ProdutoService produtoService = new ProdutoService();
    
    public static void main(String[] args) {
        port(6789);

        post("/produto", (request, response) -> {
            Produto produto = new Produto();
            produto.setDescricao(request.queryParams("descricao"));
            produto.setPreco(Float.parseFloat(request.queryParams("preco")));
            produto.setQuant(Integer.parseInt(request.queryParams("quantidade")));
            produto.setDataFabricacao(LocalDateTime.parse(request.queryParams("dataFabricacao")));
            produto.setDataValidade(LocalDate.parse(request.queryParams("dataValidade")));
            produtoService.adicionarProduto(produto);
            return "Produto adicionado com sucesso";
        });

        get("/produto/:id", (request, response) -> {
            int id = Integer.parseInt(request.params(":id"));
            Produto produto = produtoService.obterProduto(id);
            if (produto != null) {
                return produto.toString();
            } else {
                response.status(404);
                return "Produto não encontrado";
            }
        });

        get("/produto/update/:id", (request, response) -> {
            int id = Integer.parseInt(request.params(":id"));
            Produto produto = produtoService.obterProduto(id);
            if (produto != null) {
                produto.setDescricao(request.queryParams("descricao"));
                produto.setPreco(Float.parseFloat(request.queryParams("preco")));
                produto.setQuant(Integer.parseInt(request.queryParams("quantidade")));
                produto.setDataFabricacao(LocalDateTime.parse(request.queryParams("dataFabricacao")));
                produto.setDataValidade(LocalDate.parse(request.queryParams("dataValidade")));
                produtoService.atualizarProduto(produto);
                return "Produto atualizado com sucesso";
            } else {
                response.status(404);
                return "Produto não encontrado";
            }
        });

        get("/produto/delete/:id", (request, response) -> {
            int id = Integer.parseInt(request.params(":id"));
            Produto produto = produtoService.obterProduto(id);
            if (produto != null) {
                produtoService.removerProduto(produto);
                return "Produto removido com sucesso";
            } else {
                response.status(404);
                return "Produto não encontrado";
            }
        });

        get("/produto", (request, response) -> {
            List<Produto> produtos = produtoService.listarProdutos();
            if (produtos != null && !produtos.isEmpty()) {
                StringBuilder sb = new StringBuilder();
                for (Produto produto : produtos) {
                    sb.append(produto.toString()).append("<br>");
                }
                return sb.toString();
            } else {
                return "Nenhum produto encontrado";
            }
        });
    }
}