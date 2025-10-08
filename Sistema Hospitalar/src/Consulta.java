
import java.time.LocalDateTime;

public class Consulta {

    private Paciente paciente;
    private Medico medico;
    private LocalDateTime dataHora;
    private String local;
    private String status;
    private String prescricao;
    private String diagnostico;

    public Paciente getPaciente() {
        return paciente;
    }

    public void setPaciente(Paciente paciente) {
        this.paciente = paciente;
    }

    public Medico getMedico() {
        return medico;
    }

    public void setMedico(Medico medico) {
        this.medico = medico;
    }

    public LocalDateTime getDataHora() {
        return dataHora;
    }

    public void setDataHora(LocalDateTime dataHora) {
        this.dataHora = dataHora;
    }

    public String getLocal() {
        return local;
    }

    public void setLocal(String local) {
        this.local = local;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getPrescricao() {
        return prescricao;
    }

    public void setPrescricao(String prescriacao) {
        this.prescricao = prescriacao;
    }

    public String getDiagnostico() {
        return diagnostico;
    }

    public void setDiagnostico(String diagnostico) {
        this.diagnostico = diagnostico;
    }

    public Consulta(){

    }

    public Consulta(Paciente paciente, Medico medico, LocalDateTime dataHora, String local, String status, String prescriacao,
            String diagnostico) {
        this.paciente = paciente;
        this.medico = medico;
        this.dataHora = dataHora;
        this.local = local;
        this.status = status;
        this.prescricao = prescriacao;
        this.diagnostico = diagnostico;
    }

// vê se tem consulta marcada, se tiver ele cancela
public void cancelarConsulta(){
    if (!"Agendada".equals(this.status)){
        System.out.println("Consulta não está marcada, então não pode ser cancelada" );
        return;
    } 
    this.status = "Cancelada";

    if (this.medico != null) {
        this.medico.removerHorario(this.dataHora);
    }
    System.out.println("Consulta cancelada");
}

// calcula o preço da consulta
public double calcularPreco(){
    if (this.medico == null ||this.paciente == null){
        return 0.0;
    }
    double custoBase = this.medico.getValor_Consulta();
    return this.paciente.valor_total_Consulta(custoBase);
} 

}