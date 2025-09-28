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

    public Relatorios(){

    }
    
    public Relatorios(List<Paciente> pacienteCdastrado, List<Consulta> consulta, List<Internacao> internacao) {
        this.pacienteCdastrado = pacienteCdastrado;
        this.consulta = consulta;
        Internacao = internacao;
    }
    
}
