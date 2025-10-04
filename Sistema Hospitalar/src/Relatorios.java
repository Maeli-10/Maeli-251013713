import java.time.LocalDateTime;
import java.util.List;

public class Relatorios {
    private List<Paciente> pacienteCdastrado;
    private List<Consulta> consulta;
    private List<Internacao> Internacao;

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
        return Internacao;
    }

    public void setInternacao(List<Internacao> internacao) {
        Internacao = internacao;
    }

    public Relatorios() {

    }

    public Relatorios(List<Paciente> pacienteCdastrado, List<Consulta> consulta, List<Internacao> internacao) {
        this.pacienteCdastrado = pacienteCdastrado;
        this.consulta = consulta;
        Internacao = internacao;
    }

    public void gerarRelatorioPaciente(){
        System.out.println("     RELATÓRIO DE PACIENTES CADASTRADOS     ");
        if (pacienteCdastrado == null || pacienteCdastrado.isEmpty()){ 
            System.out.println("nenhum paciente cadastrado no sistema");
        return;    
        }
        for (Paciente p : pacienteCdastrado) {
            System.out.printf("Nome: %s | CPF: %s | Idade: %d\n",
                    p.getNome(), p.getCpf(), p.getIdade());
        }
    }

    public void gerarRelatorioConsultasFuturas() {
        System.out.println("\n     RELATÓRIO DE CONSULTAS FUTURAS     ");

        if (consulta == null || consulta.isEmpty()) {
            System.out.println("Nenhuma consulta agendada.");
            return;
        }

        LocalDateTime agora = LocalDateTime.now();
        boolean encontrouConsultaFutura = false;

        for (Consulta c : consulta) {
            if (c.getDataHora().isAfter(agora)) {
                System.out.printf("Data: %s | Paciente: %s | Médico: %s\n",
                        c.getDataHora(), c.getPaciente().getNome(), c.getMedico().getNome());
                encontrouConsultaFutura = true;
            }
        }

        if (!encontrouConsultaFutura) {
            System.out.println("Não há consultas futuras agendadas.");
        }
    }
}
    


