import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;

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

//menu para gerar os cadastros
    private static void menuCadastrar() {
        boolean voltando = false;
        while (!voltando) {
            System.out.println("\n--- O que você deseja cadastrar? ---");
            System.out.println("1) Novo Paciente");
            System.out.println("2) Novo Médico");
            System.out.println("3) Novo Plano de Saúde");
            System.out.println("4) Cadastrar Nova Consulta");
            System.out.println("5) Iniciar Nova Internação");
            System.out.println("0) Voltar");

            int opcao = lerInt("Escolha uma opção: ");
            switch (opcao) {
                case 1: cadastrarPaciente(); break;
                case 2: cadastrarMedico(); break;
                case 3: cadastrarPlano(); break;
                case 4: agendarConsulta(); break;
                case 5: iniciarInternacao(); break;
                case 0: voltando = true; break;
                default: System.out.println("Opção inválida.");
            }
        }
    }

    private static void cadastrarPaciente() {
        System.out.println("\n--- Cadastro de Paciente ---");
        String nome = lerString("Nome: ");
        String cpf = lerString("CPF: ");
        int idade = lerInt("Idade: ");
        
        String resposta = lerString("Possui plano de saúde? (S/N): ");
        if (resposta.equalsIgnoreCase("S")) {
            PlanoDeSaude plano = selecionarItem(planos, "Selecione o Plano de Saúde:");
            if (plano == null) return;
            pacientes.add(new PacienteEspecial(nome, idade, cpf, plano));
        } else {
            pacientes.add(new PacienteComum(null, nome, idade, cpf));
        }
        System.out.println("Paciente cadastrado com sucesso!");
    }

    private static void cadastrarMedico() {
        System.out.println("\n--- Cadastro de Médico ---");
        String nome = lerString("Nome: ");
        String crm = lerString("CRM: ");
        String especialidade = lerString("Especialidade: ");
        double valorConsulta = lerDouble("Valor da consulta: R$");
        
        medicos.add(new Medico(nome, crm, especialidade, valorConsulta, null));
        System.out.println("Médico cadastrado com sucesso!");
    }

    private static void cadastrarPlano() {
        System.out.println("\n--- Cadastro de Plano de Saúde ---");
        String nome = lerString("Nome do plano: ");
        String descricao = lerString("Descrição: ");
        double desconto = lerDouble("Desconto (ex: 0.1 para 10%): ");
        boolean cobreInternacao = lerString("Cobre internação especial? (S/N): ").equalsIgnoreCase("S");
        
        planos.add(new PlanoDeSaude(nome, desconto, descricao, cobreInternacao));
        System.out.println("Plano de saúde cadastrado com sucesso!");
    }

    private static void agendarConsulta() {
        System.out.println("\n--- Cadastro de Nova Consulta ---");
        Paciente paciente = selecionarItem(pacientes, "Selecione o Paciente:");
        if (paciente == null) return;
        
        Medico medico = selecionarItem(medicos, "Selecione o Médico:");
        if (medico == null) return;

        System.out.println("Digite a data e hora da consulta.");
        LocalDateTime dataHora = lerDataHora();

        if (!medico.disponilidade(dataHora)) {
            System.out.println("erro: Horário indisponível para este médico.");
            return;
        }

        String local = lerString("Local da consulta: ");
        Consulta novaConsulta = new Consulta(paciente, medico, dataHora, local, "Agendada", "", "");
        consultas.add(novaConsulta);
        medico.adicionarHorario(dataHora);
        
        System.out.println("Consulta cadastrada (agendada) com sucesso!");
    }

//menu de gerenciamento
    private static void menuGerenciar() {
        boolean voltando = false;
        while (!voltando) {
            System.out.println("\n--- O que você deseja gerenciar? ---");
            System.out.println("1) Finalizar Consulta");
            System.out.println("2) Cancelar Consulta");
            System.out.println("3) Finalizar Internação");
            System.out.println("4) Cancelar Internação");
            System.out.println("0) Voltar");

            int opcao = lerInt("Escolha uma opção: ");
            switch (opcao) {
                case 1: finalizarConsulta(); break;
                case 2: cancelarConsulta(); break;
                case 3: finalizarInternacao(); break;
                case 4: cancelarInternacao(); break;
                case 0: voltando = true; break;
                default: System.out.println("Opção inválida.");
            }
        }
    }
    private static void finalizarConsulta() {
        System.out.println("\n--- Finalizar Consulta ---");
        List<Consulta> agendadas = consultas.stream()
            .filter(c -> "Agendada".equalsIgnoreCase(c.getStatus()))
            .collect(Collectors.toList());

        Consulta consulta = selecionarItem(agendadas, "Selecione a consulta para finalizar:");
        if (consulta == null) return;

        String diagnostico = lerString("Diagnóstico: ");
        String prescricao = lerString("Prescrição: ");
        
        consulta.setDiagnostico(diagnostico);
        consulta.setPrescricao(prescricao);
        consulta.setStatus("Finalizada");
        
        System.out.println("Consulta finalizada com sucesso!");
    }

    private static void cancelarConsulta() {
        System.out.println("\n--- Cancelar Consulta ---");
        List<Consulta> agendadas = consultas.stream()
            .filter(c -> "Agendada".equalsIgnoreCase(c.getStatus()))
            .collect(Collectors.toList());
            
        Consulta consulta = selecionarItem(agendadas, "Selecione a consulta para cancelar:");
        if (consulta == null) return;
        
        consulta.cancelarConsulta();
    }

    private static void finalizarInternacao() {
        System.out.println("\n--- Finalizar Internação ---");
        List<Internacao> ativas = internacao.stream()
            .filter(i -> "Internado".equalsIgnoreCase(i.getStatus()))
            .collect(Collectors.toList());

        Internacao internacaoParaFinalizar = selecionarItem(ativas, "Selecione a internação para dar alta:");
        if (internacaoParaFinalizar == null) return;
        
        internacaoParaFinalizar.fim_internacao();
    }

    private static void cancelarInternacao() {
        System.out.println("\n--- Cancelar Internação ---");
        List<Internacao> ativas = internacao.stream()
            .filter(i -> "Internado".equalsIgnoreCase(i.getStatus()))
            .collect(Collectors.toList());

        Internacao internacaoParaCancelar = selecionarItem(ativas, "Selecione a internação para cancelar:");
        if (internacaoParaCancelar == null) return;

        internacaoParaCancelar.cancelar_internacao();
    }

 //menu de vizualização   
    private static void menuVisualizar() {
    boolean voltando = false;
    while (!voltando) {
        System.out.println("\n--- O que você deseja ver? ---");
        System.out.println("1) Listar todos os Pacientes");
        System.out.println("2) Listar todos os Médicos");
        System.out.println("3) Gerar Relatórios Completos");
        System.out.println("0) Voltar");
        
        int opcao = lerInt("Escolha uma opção: ");
        switch (opcao) {
            case 1:
                System.out.println("\n--- Lista de Pacientes Cadastrados ---");
                if (pacientes.isEmpty()) {
                    System.out.println("Nenhum paciente cadastrado.");
                } else {
                    pacientes.forEach(p -> System.out.println("- " + p.getNome() + " (CPF: " + p.getCpf() + ")"));
                }
                lerString("Pressione Enter para continuar...");
                break;
            case 2:
                System.out.println("\n--- Lista de Médicos Cadastrados ---");
                if (medicos.isEmpty()) {
                    System.out.println("Nenhum médico cadastrado.");
                } else {
                    medicos.forEach(m -> System.out.println("- Dr(a). " + m.getNome() + " (CRM: " + m.getCrm() + ", " + m.getEspecialidade() + ")"));
                }
                lerString("Pressione Enter para continuar...");
                break;
            case 3:
                gerarRelatoriosCompletos();
                break;
            case 0:
                voltando = true;
                break;
            default:
                System.out.println("Opção inválida.");
        }
    }
}


//metodos auxiliares
    private static int lerInt(String prompt) {
        while (true) {
            try {
                System.out.print(prompt);
                return sc.nextInt();
            } catch (Exception e) {
                System.out.println("Entrada inválida. Por favor, digite um número inteiro.");
            } finally {
                sc.nextLine();
            }
        }
    }

    private static double lerDouble(String prompt) {
        while (true) {
            try {
                System.out.print(prompt);
                return sc.nextDouble();
            } catch (Exception e) {
                System.out.println("Entrada inválida. Por favor, digite um número (use '.' para numeros decimais).");
            } finally {
                sc.nextLine();
            }
        }
    }

    private static String lerString(String prompt) {
        System.out.print(prompt);
        return sc.nextLine();
    }

   private static <T> T selecionarItem(List<T> lista, String titulo) {
    if (lista.isEmpty()) {
        System.out.println("Nenhum item para selecionar.");
        return null;
    }

    System.out.println("\n" + titulo);
    for (int i = 0; i < lista.size(); i++) {
        String itemDescricao = "";
        Object item = lista.get(i);
        if (item instanceof Paciente) itemDescricao = ((Paciente) item).getNome();
        else if (item instanceof Medico) itemDescricao = ((Medico) item).getNome() + " (CRM: " + ((Medico) item).getCrm() + ")";
        else if (item instanceof PlanoDeSaude) itemDescricao = ((PlanoDeSaude) item).getNome();
        else if (item instanceof Consulta) {
            Consulta c = (Consulta) item;
            itemDescricao = "Paciente: " + c.getPaciente().getNome() + " | Médico: " + c.getMedico().getNome() + " | Data: " + c.getDataHora().format(formatadorData);
        } else if (item instanceof Internacao) {
            Internacao intern = (Internacao) item;
            itemDescricao = "Paciente: " + intern.getPaciente().getNome() + " | Quarto: " + intern.getQuarto() + " | Médico: " + intern.getMedResponsavel().getNome();
        }
        
        System.out.printf("%d) %s\n", i + 1, itemDescricao);
    }

    int escolha = lerInt("Escolha uma opção (0 para cancelar): ");
    if (escolha > 0 && escolha <= lista.size()) {
        return lista.get(escolha - 1);
    }
    
    System.out.println("Seleção cancelada ou inválida.");
    return null;
}

    private static LocalDateTime lerDataHora() {
    while (true) {
        try {
            int ano = lerInt("Ano (ex: 2025): "); 
            int mes = lerInt("Mês (1-12): ");
            int dia = lerInt("Dia: ");
            int hora = lerInt("Hora (0-23): ");
            int minuto = lerInt("Minuto (0-59): ");
            return LocalDateTime.of(ano, mes, dia, hora, minuto);
        } catch (Exception e) {
            System.out.println("Data ou hora inválida. Tente novamente.");
        }
    }
}

    private static void iniciarInternacao() {
    System.out.println("\n--- Iniciar Nova Internação ---");
    
        Paciente paciente = selecionarItem(pacientes, "Selecione o Paciente para internação:");
        if (paciente == null) return; 

        Medico medico = selecionarItem(medicos, "Selecione o Médico responsável:");
        if (medico == null) return; 

        int quarto = lerInt("Número do quarto: ");
        double valorDiaria = lerDouble("Valor da diária da internação: R$");

        Internacao novaInternacao = new Internacao(paciente, medico, null, null, quarto, valorDiaria, null);
        
        internacao.add(novaInternacao);

        System.out.println("Internação do paciente " + paciente.getNome() + " iniciada com sucesso!");
    }
    
    private static void gerarRelatoriosCompletos() {
        Relatorios relatorios = new Relatorios();
 
        relatorios.setPacienteCdastrado(pacientes);
        relatorios.setMedico_Cadastrados(medicos);
        relatorios.setConsulta(consultas);
        
        System.out.println("\n--- Geração de Relatórios ---");
    
        relatorios.gerarRelatorioPaciente();
        relatorios.gerarRelatorioMedicosCadastrados();
        relatorios.gerarRelatorioConsultasFuturas();
        
        lerString("\nPressione Enter para voltar...");
    }

}    
        
    
