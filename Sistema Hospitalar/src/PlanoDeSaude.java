public class PlanoDeSaude {
    private String nome;
    private double desconto;
    private String descricao;
    private boolean internacaoEspecial;

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

    public PlanoDeSaude() {

    }

    public PlanoDeSaude(String nome, double desconto, String descricao, boolean internacaoEspecial) {
        this.nome = nome;
        this.desconto = desconto;
        this.descricao = descricao;
        this.internacaoEspecial = internacaoEspecial;
    }

    public double aplicarDesconto(double custo_original, Paciente paciente) {
        double desconto_ = this.desconto;
        if (paciente.getIdade() >= 60) {
            desconto_ += 0.10;
        }
        if (desconto > 1.0) {
            desconto_ = 1.0;
        }
        double valor_do_Desconto = custo_original * desconto_;
        double custoFinal = custo_original - valor_do_Desconto;
        return custoFinal;
    }

    public boolean pode_ter_internacao(){ 
        return this.internacaoEspecial;
    }
}
