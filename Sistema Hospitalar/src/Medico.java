import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

public class Medico {
    private String nome;
    private String crm;
    private String especialidade;
    private double valor_Consulta;
    private List<Date> agendadeHorarios;


    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getCrm() {
        return crm;
    }

    public void setCrm(String crm) {
        this.crm = crm;
    }

    public String getEspecialidade() {
        return especialidade;
    }

    public void setEspecialidade(String especialidade) {
        this.especialidade = especialidade;
    }

    public double getValor_Consulta() {
        return valor_Consulta;
    }

    public void setValor_Consulta(double valor_Consulta) {
        this.valor_Consulta = valor_Consulta;
    }

    public List<Date> getAgendadeHorarios() {
        return agendadeHorarios;
    }

    public void setAgendadeHorarios(List<Date> agendadeHorarios) {
        this.agendadeHorarios = agendadeHorarios;
    }

    public Medico(){

    }

    public Medico(String nome, String crm, String especialidade, double valor_Consulta, List<Date> agendadeHorarios) {
        this.nome = nome;
        this.crm = crm;
        this.especialidade = especialidade;
        this.valor_Consulta = valor_Consulta;
        this.agendadeHorarios = new ArrayList<>();
    }

}
