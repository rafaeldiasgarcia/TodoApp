import java.io.Serializable;

public record Tarefa(String descricao, boolean concluida) implements Serializable {
    @Override
    public String toString() {
        return (concluida ? "[X] " : "[ ] ") + descricao;
    }
}

