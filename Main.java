import java.sql.SQLException;
import java.util.List;

public class Main {

    public static void main(String[] args) {
        try {
            // Criação das tabelas
            criarTabelas();

            // Operações CRUD para o Gerenciamento de Obras
            System.out.println("### GERENCIAMENTO DE OBRAS ###");
            gerenciamentoObrasCRUD();

            // Operações CRUD para o Gerenciamento de Livros e Autores
            System.out.println("\n### GERENCIAMENTO DE LIVROS E AUTORES ###");
            gerenciamentoLivrosAutoresCRUD();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private static void criarTabelas() throws SQLException {
        ConexaoBD conexao = ConexaoBD.getInstance();
        try (var stmt = conexao.getConnection().createStatement()) {
            // Tabela Projeto
            stmt.execute("CREATE TABLE IF NOT EXISTS Projeto (" +
                    "ID_Projeto INT PRIMARY KEY AUTO_INCREMENT, " +
                    "Nome_Projeto VARCHAR(255), " +
                    "Local VARCHAR(255), " +
                    "Data_Inicio DATE, " +
                    "Data_Termino DATE)");

            // Tabela Engenheiro
            stmt.execute("CREATE TABLE IF NOT EXISTS Engenheiro (" +
                    "ID_Engenheiro INT PRIMARY KEY AUTO_INCREMENT, " +
                    "Nome_Engenheiro VARCHAR(255), " +
                    "Especialidade VARCHAR(255))");

            // Tabela Operario
            stmt.execute("CREATE TABLE IF NOT EXISTS Operario (" +
                    "ID_Operario INT PRIMARY KEY AUTO_INCREMENT, " +
                    "Nome_Operario VARCHAR(255), " +
                    "Funcao VARCHAR(255))");

            // Tabela Equipamento
            stmt.execute("CREATE TABLE IF NOT EXISTS Equipamento (" +
                    "ID_Equipamento INT PRIMARY KEY AUTO_INCREMENT, " +
                    "Nome_Equipamento VARCHAR(255), " +
                    "Tipo VARCHAR(255))");

            // Tabela Material
            stmt.execute("CREATE TABLE IF NOT EXISTS Material (" +
                    "ID_Material INT PRIMARY KEY AUTO_INCREMENT, " +
                    "Nome_Material VARCHAR(255), " +
                    "Quantidade INT)");

            // Tabela Autor
            stmt.execute("CREATE TABLE IF NOT EXISTS Autor (" +
                    "ID_Autor INT PRIMARY KEY AUTO_INCREMENT, " +
                    "Nome_Autor VARCHAR(255), " +
                    "Nacionalidade VARCHAR(255))");

            // Tabela Livro
            stmt.execute("CREATE TABLE IF NOT EXISTS Livro (" +
                    "ID_Livro INT PRIMARY KEY AUTO_INCREMENT, " +
                    "Titulo VARCHAR(255), " +
                    "Genero VARCHAR(255), " +
                    "Ano_Publicacao INT, " +
                    "ID_Autor INT, " +
                    "FOREIGN KEY (ID_Autor) REFERENCES Autor(ID_Autor))");
        }
    }

    private static void gerenciamentoObrasCRUD() throws SQLException {
        // Instanciação dos DAOs
        ProjetoDAO projetoDAO = new ProjetoDAO();
        EngenheiroDAO engenheiroDAO = new EngenheiroDAO();
        OperarioDAO operarioDAO = new OperarioDAO();
        EquipamentoDAO equipamentoDAO = new EquipamentoDAO();
        MaterialDAO materialDAO = new MaterialDAO();

        // Inserir Projeto
        Projeto projeto = new Projeto(0, "Construção de Ponte", "Cidade A", "2023-05-01", "2024-05-01");
        projetoDAO.inserirProjeto(projeto);
        System.out.println("Projeto inserido: " + projeto.getNomeProjeto());

        // Inserir Engenheiro
        Engenheiro engenheiro = new Engenheiro(0, "João Silva", "Civil");
        engenheiroDAO.inserirEngenheiro(engenheiro);
        System.out.println("Engenheiro inserido: " + engenheiro.getNomeEngenheiro());

        // Inserir Operario
        Operario operario = new Operario(0, "Carlos Souza", "Pedreiro");
        operarioDAO.inserirOperario(operario);
        System.out.println("Operario inserido: " + operario.getNomeOperario());

        // Inserir Equipamento
        Equipamento equipamento = new Equipamento(0, "Guindaste", "Pesado");
        equipamentoDAO.inserirEquipamento(equipamento);
        System.out.println("Equipamento inserido: " + equipamento.getNomeEquipamento());

        // Inserir Material
        Material material = new Material(0, "Cimento", 500);
        materialDAO.inserirMaterial(material);
        System.out.println("Material inserido: " + material.getNomeMaterial());

        // Listar Projetos
        List<Projeto> projetos = projetoDAO.listarProjetos();
        System.out.println("\nProjetos:");
        for (Projeto p : projetos) {
            System.out.println(p.getNomeProjeto());
        }

        // Atualizar Projeto
        projeto.setNomeProjeto("Construção de Viaduto");
        projetoDAO.atualizarProjeto(projeto);
        System.out.println("\nProjeto atualizado: " + projeto.getNomeProjeto());

        // Excluir Operario
        operarioDAO.excluirOperario(operario.getIdOperario());
        System.out.println("\nOperario excluído: " + operario.getNomeOperario());
    }

    private static void gerenciamentoLivrosAutoresCRUD() throws SQLException {
        // Instanciação dos DAOs
        AutorDAO autorDAO = new AutorDAO();
        LivroDAO livroDAO = new LivroDAO();

        // Inserir Autor
        Autor autor = new Autor(0, "George Orwell", "Britânico");
        autorDAO.inserirAutor(autor);
        System.out.println("Autor inserido: " + autor.getNome());

        // Inserir Livro
        Livro livro = new Livro(0, "1984", 1949, autor.getIdAutor());
        livroDAO.inserirLivro(livro);
        System.out.println("Livro inserido: " + livro.getTitulo());

        // Listar Autores
        List<Autor> autores = autorDAO.listarAutores();
        System.out.println("\nAutores:");
        for (Autor a : autores) {
            System.out.println(a.getNome());
        }

        // Atualizar Livro
        livro.setTitulo("A Revolução dos Bichos");
        livroDAO.atualizarLivro(livro);
        System.out.println("\nLivro atualizado: " + livro.getTitulo());

        // Excluir Livro
        livroDAO.excluirLivro(livro.getIdLivro());
        System.out.println("\nLivro excluído: " + livro.getTitulo());
    }
}
