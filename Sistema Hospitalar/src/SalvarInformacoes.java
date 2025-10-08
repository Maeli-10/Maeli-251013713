import java.io.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Objects;

public class SalvarInformacoes {
    private static final String PLANOS_CSV = "planos.csv";
    private static final String MEDICOS_CSV = "medicos.csv";
    private static final String PACIENTES_CSV = "pacientes.csv";
    private static final String CONSULTAS_CSV = "consultas.csv";

    private static final DateTimeFormatter FORMATADOR = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");

    public static void salvarDados(List<PlanoDeSaude> planos, List<Medico> medicos, List<Paciente> pacientes, List<Consulta> consultas) {
        try {
            salvarPlanos(planos);
            salvarMedicos(medicos);
            salvarPacientes(pacientes);
            salvarConsultas(consultas);
            System.out.println("Dados salvos com sucesso!");
        } catch (IOException e) {
            System.err.println("Erro ao salvar os dados: " + e.getMessage());
        }
    }

    private static void salvarPlanos(List<PlanoDeSaude> planos) throws IOException {
        try (PrintWriter writer = new PrintWriter(new FileWriter(PLANOS_CSV))) {
            for (PlanoDeSaude p : planos) {
                writer.println(p.getNome() + "," + p.getDescricao() + "," + p.getDesconto() + "," + p.pode_ter_internacao());
            }
        }
    }

    private static void salvarMedicos(List<Medico> medicos) throws IOException {
        try (PrintWriter writer = new PrintWriter(new FileWriter(MEDICOS_CSV))) {
            for (Medico m : medicos) {
                writer.println(m.getNome() + "," + m.getCrm() + "," + m.getEspecialidade() + "," + m.getValor_Consulta());
            }
        }
    }

    private static void salvarPacientes(List<Paciente> pacientes) throws IOException {
        try (PrintWriter writer = new PrintWriter(new FileWriter(PACIENTES_CSV))) {
            for (Paciente p : pacientes) {
                writer.println(p.toCsvString());
            }
        }
    }

    private static void salvarConsultas(List<Consulta> consultas) throws IOException {
        try (PrintWriter writer = new PrintWriter(new FileWriter(CONSULTAS_CSV))) {
            for (Consulta c : consultas) {
                String linha = String.join(";",
                        c.getPaciente().getCpf(),
                        c.getMedico().getCrm(),
                        c.getDataHora().format(FORMATADOR),
                        Objects.toString(c.getLocal(), ""),
                        Objects.toString(c.getStatus(), ""),
                        Objects.toString(c.getDiagnostico(), ""),
                        Objects.toString(c.getPrescricao(), "")
                );
                writer.println(linha);
            }
        }
    }

    public static void carregarDados(List<PlanoDeSaude> planos, List<Medico> medicos, List<Paciente> pacientes, List<Consulta> consultas) {
        carregarPlanos(planos);
        carregarMedicos(medicos);
        carregarPacientes(pacientes, planos);
        carregarConsultas(consultas, pacientes, medicos);
        System.out.println("Dados carregados do sistema.");
    }

    private static void carregarPlanos(List<PlanoDeSaude> planos) {
        File arquivo = new File(PLANOS_CSV);
        if (!arquivo.exists()) return;

        try (BufferedReader reader = new BufferedReader(new FileReader(arquivo))) {
            String linha;
            while ((linha = reader.readLine()) != null) {
                String[] dados = linha.split(",");
                planos.add(new PlanoDeSaude(dados[0], Double.parseDouble(dados[2]), dados[1], Boolean.parseBoolean(dados[3])));
            }
        } catch (IOException e) {
            System.err.println("Erro ao carregar planos: " + e.getMessage());
        }
    }

    private static void carregarMedicos(List<Medico> medicos) {
        File arquivo = new File(MEDICOS_CSV);
        if (!arquivo.exists()) return;

        try (BufferedReader reader = new BufferedReader(new FileReader(arquivo))) {
            String linha;
            while ((linha = reader.readLine()) != null) {
                String[] dados = linha.split(",");
                medicos.add(new Medico(dados[0], dados[1], dados[2], Double.parseDouble(dados[3]), null));
            }
        } catch (IOException e) {
            System.err.println("Erro ao carregar médicos: " + e.getMessage());
        }
    }

    private static void carregarPacientes(List<Paciente> pacientes, List<PlanoDeSaude> planos) {
        File arquivo = new File(PACIENTES_CSV);
        if (!arquivo.exists()) return;

        try (BufferedReader reader = new BufferedReader(new FileReader(arquivo))) {
            String linha;
            while ((linha = reader.readLine()) != null) {
                String[] dados = linha.split(",");
                String nome = dados[0];
                String cpf = dados[1];
                int idade = Integer.parseInt(dados[2]);
                String tipo = dados[3];
                String detalhe = dados[4];

                if ("ESPECIAL".equalsIgnoreCase(tipo)) {
                    PlanoDeSaude plano = buscarPlanoPorNome(detalhe, planos);
                    pacientes.add(new PacienteEspecial(nome, idade, cpf, plano));
                } else {
                    pacientes.add(new PacienteComum(detalhe, nome, idade, cpf));
                }
            }
        } catch (IOException e) {
            System.err.println("Erro ao carregar pacientes: " + e.getMessage());
        }
    }

    private static void carregarConsultas(List<Consulta> consultas, List<Paciente> pacientes, List<Medico> medicos) {
        File arquivo = new File(CONSULTAS_CSV);
        if (!arquivo.exists()) return;

        try (BufferedReader reader = new BufferedReader(new FileReader(arquivo))) {
            String linha;
            while ((linha = reader.readLine()) != null) {
                String[] dados = linha.split(";", -1);
                Paciente p = buscarPacientePorCpf(dados[0], pacientes);
                Medico m = buscarMedicoPorCrm(dados[1], medicos);
                LocalDateTime data = LocalDateTime.parse(dados[2], FORMATADOR);

                if (p != null && m != null) {
                    Consulta c = new Consulta(p, m, data, dados[3], dados[4], dados[6], dados[5]);
                    consultas.add(c);
                    
                    p.adicionar_Consulta(c);
                    if (!"Cancelada".equalsIgnoreCase(c.getStatus())) {
                        m.adicionarHorario(data);
                    }
                }
            }
        } catch (Exception e) {
            System.err.println("Erro ao carregar consultas: " + e.getMessage());
        }
    }

    private static PlanoDeSaude buscarPlanoPorNome(String nome, List<PlanoDeSaude> planos) {
        return planos.stream()
                .filter(p -> p.getNome().equalsIgnoreCase(nome))
                .findFirst()
                .orElse(null);
    }

    private static Paciente buscarPacientePorCpf(String cpf, List<Paciente> pacientes) {
        return pacientes.stream()
                .filter(p -> p.getCpf().equals(cpf))
                .findFirst()
                .orElse(null);
    }

    private static Medico buscarMedicoPorCrm(String crm, List<Medico> medicos) {
        return medicos.stream()
                .filter(m -> m.getCrm().equals(crm))
                .findFirst()
                .orElse(null);
    }
}

