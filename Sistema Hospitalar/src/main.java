import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Main{ 
  
    private static List<Paciente> pacientes = new ArrayList<>();
    private static List<Medico> medicos = new ArrayList<>();
    private static List<PlanoDeSaude> planos = new ArrayList<>();
    private static List<Consulta> consultas = new ArrayList<>();
    private static List<Internacao> internacao = new ArrayList<>();

    private static Scanner sc = new Scanner(System.in);
    private static DateTimeFormatter formatadorData = DateTimeFormatter.ofPattern("dd/MM/yyyy 'às' HH:mm");

    public static void main(String[] args) {
        SalvarInformacoes.carregarDados(planos, medicos, pacientes, consultas);

        boolean executando = true;
        
// menu principal (primeiro menu)
        while (executando) {
            System.out.println("\n===== SISTEMA HOSPITALAR  =====");
            System.out.println("1) Cadastrar...");
            System.out.println("2) Gerenciar...");
            System.out.println("3) Visualizar...");
            System.out.println("0) Salvar e Sair");

            int opcao = lerInt("Escolha uma ação: ");

            switch (opcao) {
                case 1:
                    menuCadastrar();
                    break;
                case 2:
                    menuGerenciar();
                    break;
                case 3:
                    menuVisualizar();
                    break;
                case 0:
                    executando = false;
                    break;
                default:
                    System.out.println("Opção inválida.");
            }
        }

        SalvarInformacoes.salvarDados(planos, medicos, pacientes, consultas);
        System.out.println("Sistema finalizado. Dados salvos.");
        sc.close();
    }
}    
    
    
