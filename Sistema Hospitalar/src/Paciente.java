import java.util.List;

public class Paciente {
    private String nome;
    private int idade;
    private String cpf;
    private List<Consulta> historico_Consulta;
    private List<Internacao> historico_Internacoes;

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public int getIdade() {
        return idade;
    }

    public void setIdade(int idade) {
        this.idade = idade;
    }

    public String getCpf() {
        return cpf;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
    }

    public List<Consulta> getHistorico_Consulta() {
        return historico_Consulta;
    }

    public void setHistorico_Consulta(List<Consulta> historico_Consulta) {
        this.historico_Consulta = historico_Consulta;
    }

    public List<Internacao> getHistorico_Internacoes() {
        return historico_Internacoes;
    }

    public void setHistorico_Internacoes(List<Internacao> historico_Internacoes) {
        this.historico_Internacoes = historico_Internacoes;
    }

    public Paciente(){

    }   

    public Paciente(String nome, int idade, String cpf, List<Consulta> historico_Consulta,
            List<Internacao> historico_Internacoes) {
        this.nome = nome;
        this.idade = idade;
        this.cpf = cpf;
        this.historico_Consulta = historico_Consulta;
        this.historico_Internacoes = historico_Internacoes;
    }

    public double valor_total_Consulta(double custoBase){
        return custoBase;
    }

    //adiciona a consulta ao historico de consultas do paciente
    public void adicionar_Consulta(Consulta consulta){
        if (consulta != null){
            this.historico_Consulta.add(consulta);
            System.out.println("Consulta adicionada ao histórico do paciente" + this.getNome());
        }
        }
      

}    
