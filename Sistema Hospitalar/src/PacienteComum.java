public class PacienteComum extends Paciente{
    private String forma_Pagamento;

    public String getForma_Pagamento() {
        return forma_Pagamento;
    }

    public void setForma_Pagamento(String forma_Pagamento) {
        this.forma_Pagamento = forma_Pagamento;
    }


    public PacienteComum(){

    }
    
    public PacienteComum(String forma_Pagamento, String nome, int idade, String cpf) {
        super(nome, idade, cpf);
        this.forma_Pagamento = forma_Pagamento;
    }

    @Override
    public double valor_total_Consulta(double custoBase){
        return custoBase;
    } 

    @Override
    public String toCsvString() {
       return getNome() + "," + getCpf() + "," + getIdade() + ",comum," + getForma_Pagamento();

    }
    
}
