package app;

import java.util.List;
import java.util.Scanner;

import dao.usuarioDao;
import model.usuario;

public class aplicacao {

	 private static usuarioDao dao;

	    public static void main(String[] args) {
	        dao = new usuarioDao();
	        
	        if (dao.conectar()) {
	            Scanner scanner = new Scanner(System.in);
	            int option = 0;

	            do {
	                System.out.println("\nMenu:");
	                System.out.println("1 - Listar");
	                System.out.println("2 - Inserir");
	                System.out.println("3 - Excluir");
	                System.out.println("4 - Atualizar");
	                System.out.println("5 - Sair");
	                System.out.print("Escolha uma opção: ");
	                option = scanner.nextInt();
	                scanner.nextLine(); 

	                switch (option) {
	                    case 1:
	                        listar();
	                        break;
	                    case 2:
	                        inserir(scanner);
	                        break;
	                    case 3:
	                        excluir(scanner);
	                        break;
	                    case 4:
	                        atualizar(scanner);
	                        break;
	                    case 5:
	                        System.out.println("Encerrando...");
	                        break;
	                    default:
	                        System.out.println("Opção inválida!");
	                }

	            } while (option != 5);

	            dao.close();
	            scanner.close();
	        } else {
	            System.err.println("Não foi possível conectar ao banco de dados!");
	        }
	    }

	    private static void listar() {
	        List<usuario> usuarios = dao.getOrderByCodigo();
	        System.out.println("\nLista de Usuários:");
	        for (usuario u : usuarios) {
	            System.out.println("Código: " + u.getCodigo() + ", Login: " + u.getLogin() + ", Sexo: " + u.getSexo());
	        }
	    }

	    private static void inserir(Scanner scanner) {
	        System.out.print("Digite o código: ");
	        int codigo = scanner.nextInt();
	        scanner.nextLine(); 

	        System.out.print("Digite o login: ");
	        String login = scanner.nextLine();

	        System.out.print("Digite a senha: ");
	        String senha = scanner.nextLine();

	        System.out.print("Digite o sexo (M/F): ");
	        char sexo = scanner.nextLine().charAt(0);

	        usuario novoUsuario = new usuario(codigo, login, senha, sexo);
	        if (dao.insert(novoUsuario)) {
	            System.out.println("Usuário inserido com sucesso!");
	        } else {
	            System.out.println("Falha ao inserir usuário.");
	        }
	    }

	    private static void excluir(Scanner scanner) {
	        System.out.print("Digite o código do usuário a ser excluído: ");
	        int codigo = scanner.nextInt();

	        if (dao.delete(codigo)) {
	            System.out.println("Usuário excluído com sucesso!");
	        } else {
	            System.out.println("Falha ao excluir usuário.");
	        }
	    }

	    private static void atualizar(Scanner scanner) {
	        System.out.print("Digite o código do usuário a ser atualizado: ");
	        int codigo = scanner.nextInt();
	        scanner.nextLine(); 
	        usuario u = dao.get(codigo);
	        if (u != null) {
	            System.out.print("Digite o novo login: ");
	            String login = scanner.nextLine();

	            System.out.print("Digite a nova senha: ");
	            String senha = scanner.nextLine();

	            System.out.print("Digite o novo sexo (M/F): ");
	            char sexo = scanner.nextLine().charAt(0);

	            u.setLogin(login);
	            u.setSenha(senha);
	            u.setSexo(sexo);

	            if (dao.update(u)) {
	                System.out.println("Usuário atualizado com sucesso!");
	            } else {
	                System.out.println("Falha ao atualizar usuário.");
	            }
	        } else {
	            System.out.println("Usuário não encontrado.");
	        }
	    }

}