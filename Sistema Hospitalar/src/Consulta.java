import java.sql.Date;

public class Consulta {

    private Paciente paciente;
    private Medico medico;
    private Date dataHora;
    private String local;
    private String status;
    private String prescriacao;
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

    public Date getDataHora() {
        return dataHora;
    }

    public void setDataHora(Date dataHora) {
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

    public String getPrescriacao() {
        return prescriacao;
    }

    public void setPrescriacao(String prescriacao) {
        this.prescriacao = prescriacao;
    }

    public String getDiagnostico() {
        return diagnostico;
    }

    public void setDiagnostico(String diagnostico) {
        this.diagnostico = diagnostico;
    }

    public Consulta(){

    }

    public Consulta(Paciente paciente, Medico medico, Date dataHora, String local, String status, String prescriacao,
            String diagnostico) {
        this.paciente = paciente;
        this.medico = medico;
        this.dataHora = dataHora;
        this.local = local;
        this.status = status;
        this.prescriacao = prescriacao;
        this.diagnostico = diagnostico;
    }

    

}