public class PlanoDeSaude {
    private String nome;
    private double desconto;
    private String descricao;
    public String getNome() {
        return nome;
    }
    public void setNome(String nome) {
        this.nome = nome;
    }
    public double getDesconto() {
        return desconto;
    }
    public void setDesconto(double desconto) {
        this.desconto = desconto;
    }
    public String getDescricao() {
        return descricao;
    }
    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public PlanoDeSaude(){
        
    }
    public PlanoDeSaude(String nome, double desconto, String descricao) {
        this.nome = nome;
        this.desconto = desconto;
        this.descricao = descricao;
    }
    
}
