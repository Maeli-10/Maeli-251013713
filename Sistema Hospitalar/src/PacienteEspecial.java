public class PacienteEspecial extends Paciente {
    private PlanoDeSaude planoSaude;

    public PlanoDeSaude getPlanoSaude() {
        return planoSaude;
    }

    public void setPlanoSaude(PlanoDeSaude planoSaude) {
        this.planoSaude = planoSaude;
    }

    public PacienteEspecial() {

    }

    public PacienteEspecial(String nome, int idade, String cpf, PlanoDeSaude planoSaude) {
        super(nome, idade, cpf);
        this.planoSaude = planoSaude;
    }

    @Override
    public double valor_total_Consulta(double custoBase) {
        if (this.planoSaude != null) {
            return this.planoSaude.aplicarDesconto(custoBase, this);
        }
        return custoBase;
    }

    @Override
    public boolean temPlano(String nomePlano) {
        if (this.planoSaude != null && this.planoSaude.getNome().equalsIgnoreCase(nomePlano)) {
            return true;
        }
        return false;
    }

    @Override
    public String toCsvString() {
    String nomePlano = (this.getPlanoSaude() != null) ? this.getPlanoSaude().getNome() : "N/A";
    return getNome() + "," + getCpf() + "," + getIdade() + ",ESPECIAL," + nomePlano;
}

}
