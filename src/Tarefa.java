import java.io.Serializable;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public record Tarefa(
    String descricao, 
    boolean concluida,
    String observacao,
    Prioridade prioridade,
    String categoria,
    LocalDate dataVencimento
) implements Serializable {
    
    // Enum para prioridades
    public enum Prioridade {
        BAIXA("Baixa"),
        MEDIA("Media"),
        ALTA("Alta");
        
        private final String texto;
        
        Prioridade(String texto) {
            this.texto = texto;
        }
        
        @Override
        public String toString() {
            return texto;
        }
    }
    
    // Construtor para compatibilidade com tarefas antigas (sem observação)
    public Tarefa(String descricao, boolean concluida) {
        this(descricao, concluida, "", Prioridade.MEDIA, "Geral", null);
    }
    
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(concluida ? "[X] " : "[ ] ");
        sb.append(descricao);
        
        if (categoria != null && !categoria.isEmpty() && !categoria.equals("Geral")) {
            sb.append(" [").append(categoria).append("]");
        }
        
        if (dataVencimento != null) {
            sb.append(" (").append(dataVencimento.format(DateTimeFormatter.ofPattern("dd/MM/yyyy"))).append(")");
        }
        
        return sb.toString();
    }
    
    public String toDetailedString() {
        StringBuilder sb = new StringBuilder();
        sb.append("\n================================\n");
        sb.append("DETALHES DA TAREFA\n");
        sb.append("================================\n\n");
        sb.append("Descricao: ").append(descricao).append("\n");
        sb.append("Status: ").append(concluida ? "Concluida" : "Pendente").append("\n");
        sb.append("Prioridade: ").append(prioridade).append("\n");
        sb.append("Categoria: ").append(categoria).append("\n");
        
        if (dataVencimento != null) {
            sb.append("Vencimento: ").append(dataVencimento.format(DateTimeFormatter.ofPattern("dd/MM/yyyy"))).append("\n");
        } else {
            sb.append("Vencimento: Sem data definida\n");
        }
        
        if (observacao != null && !observacao.isEmpty()) {
            sb.append("\nObservacao:\n");
            sb.append(observacao).append("\n");
        }
        
        sb.append("\n================================\n");
        return sb.toString();
    }
}

