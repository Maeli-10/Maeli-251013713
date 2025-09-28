import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

public class Internacao {
    private Paciente paciente;
    private Medico medResponsavel;
    private Date data_Entrada;
    private Date data_Saida;
    private int quarto;
    private double valor_internacao;
    private String status;
    private static List<Internacao> internacaoAtiva = new ArrayList<>();

    public Paciente getPaciente() {
        return paciente;
    }

    public void setPaciente(Paciente paciente) {
        this.paciente = paciente;
    }

    public Medico getMedResponsavel() {
        return medResponsavel;
    }

    public void setMedResponsavel(Medico medResponsavel) {
        this.medResponsavel = medResponsavel;
    }

    public Date getData_Entrada() {
        return data_Entrada;
    }

    public void setData_Entrada(Date data_Entrada) {
        this.data_Entrada = data_Entrada;
    }

    public Date getData_Saida() {
        return data_Saida;
    }

    public void setData_Saida(Date data_Saida) {
        this.data_Saida = data_Saida;
    }

    public int getQuarto() {
        return quarto;
    }

    public void setQuarto(int quarto) {
        this.quarto = quarto;
    }

    public double getValor_internacao() {
        return valor_internacao;
    }

    public void setValor_internacao(double valor_internacao) {
        this.valor_internacao = valor_internacao;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public static List<Internacao> getInternacaoAtiva() {
        return internacaoAtiva;
    }

    public static void setInternacaoAtiva(List<Internacao> internacaoAtiva) {
        Internacao.internacaoAtiva = internacaoAtiva;
    }

    public Internacao(){

    }

    public Internacao(Paciente paciente, Medico medResponsavel, Date data_Entrada, Date data_Saida, int quarto,
            double valor_internacao, String status) {
        this.paciente = paciente;
        this.medResponsavel = medResponsavel;
        this.data_Entrada = new Date();
        this.data_Saida = null;
        this.quarto = quarto;
        this.valor_internacao = valor_internacao;
        this.status = "Internado";
        internacaoAtiva.add(this);
    }

}
