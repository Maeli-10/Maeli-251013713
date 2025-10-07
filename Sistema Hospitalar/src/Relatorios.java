import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class Relatorios {
    private List<Paciente> pacienteCdastrado;
    private List<Consulta> consulta;
    private List<Internacao> internacao;
    private List<Medico> medicos_Cadastrados;

    public List<Paciente> getPacienteCdastrado() {
        return pacienteCdastrado;
    }

    public void setPacienteCdastrado(List<Paciente> pacienteCdastrado) {
        this.pacienteCdastrado = pacienteCdastrado;
    }

    public List<Consulta> getConsulta() {
        return consulta;
    }

    public void setConsulta(List<Consulta> consulta) {
        this.consulta = consulta;
    }

    public List<Internacao> getInternacao() {
        return internacao;
    }

    public void setInternacao(List<Internacao> internacao) {
        this.internacao = internacao;
    }

    public List<Medico> getMedico_Cadastrados() {
        return medicos_Cadastrados;
    }

    public void setMedico_Cadastrados(List<Medico> medicos_Cadastrados) {
        this.medicos_Cadastrados = medicos_Cadastrados;
    }

    public Relatorios() {

    }

    public Relatorios(List<Paciente> pacienteCdastrado, List<Consulta> consulta, List<Internacao> internacao) {
        this.pacienteCdastrado = pacienteCdastrado;
        this.consulta = consulta;
        this.internacao = internacao;
        this.medicos_Cadastrados = new ArrayList<>();
    }

    // mostra os pacientes cadastrados
    public void gerarRelatorioPaciente() {
        System.out.println("     RELATÓRIO DE PACIENTES CADASTRADOS     ");
        if (pacienteCdastrado == null || pacienteCdastrado.isEmpty()) {
            System.out.println("nenhum paciente cadastrado no sistema");
            return;
        }
        for (Paciente p : pacienteCdastrado) {
            System.out.printf("Nome: %s | CPF: %s | Idade: %d\n",
                    p.getNome(), p.getCpf(), p.getIdade());
        }
    }

    // mostra as futuras consultas
    public void gerarRelatorioConsultasFuturas() {
        System.out.println("\n     RELATÓRIO DE CONSULTAS FUTURAS     ");

        if (consulta == null || consulta.isEmpty()) {
            System.out.println("Nenhuma consulta agendada.");
            return;
        }
        DateTimeFormatter formatador = DateTimeFormatter.ofPattern("dd/MM/yyyy 'às' HH:mm");

        LocalDateTime agora = LocalDateTime.now();
        boolean encontrouConsultaFutura = false;

        for (Consulta c : consulta) {
            if (c.getDataHora().isAfter(agora)) {
                System.out.printf("Data: %s | Paciente: %s | Médico: %s\n",
                        c.getDataHora().format(formatador),
                        c.getPaciente().getNome(),
                        c.getMedico().getNome());
                encontrouConsultaFutura = true;
            }
        }

        if (!encontrouConsultaFutura) {
            System.out.println("Não há consultas futuras agendadas.");
        }
    }

    // mostra os medicos cadastrados e seuas consultas realizadas
    public void gerarRelatorioMedicosCadastrados() {
        System.out.println("\n--- RELATÓRIO DE MÉDICOS CADASTRADOS ---");

        if (medicos_Cadastrados == null || medicos_Cadastrados.isEmpty()) {
            System.out.println("Nenhum médico cadastrado no sistema.");
            return;
        }

        DateTimeFormatter formatador = DateTimeFormatter.ofPattern("dd/MM/yyyy 'às' HH:mm");

        for (Medico medico : medicos_Cadastrados) {
            System.out.printf("\nNome: Dr(a). %s | CRM: %s | Especialidade: %s\n",
                    medico.getNome(), medico.getCrm(), medico.getEspecialidade());

            List<Consulta> consultasRealizadasDoMedico = new ArrayList<>();

            if (this.consulta != null) {
            
                consultasRealizadasDoMedico = this.consulta.stream()
                        .filter(c -> c.getMedico().getCrm().equals(medico.getCrm())
                                && c.getDataHora().isBefore(LocalDateTime.now()))
                        .collect(Collectors.toList());
            }

            // Mostra a contagem de consultas realizadas
            System.out.printf("Número de Consultas Realizadas: %d\n", consultasRealizadasDoMedico.size());
            System.out.println("Histórico de Consultas Realizadas:");

            if (consultasRealizadasDoMedico.isEmpty()) {
                System.out.println("  - Nenhuma consulta realizada encontrada no histórico.");
            } else {
                for (Consulta consultaRealizada : consultasRealizadasDoMedico) {
                    System.out.printf("  - Data: %s | Paciente: %s\n",
                            consultaRealizada.getDataHora().format(formatador),
                            consultaRealizada.getPaciente().getNome());
                }
            }
        }
    }

}
