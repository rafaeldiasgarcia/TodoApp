import java.io.*;
import java.nio.file.*;
import java.util.*;

public class TarefaService {
    private static final Path PATH = Paths.get("tarefas.dat");
    private final List<Tarefa> tarefas = new ArrayList<>();

    public TarefaService() {
        carregar();
    }

    public void adicionar(String desc) {
        tarefas.add(new Tarefa(desc, false));
        salvar();
    }

    public void remover(int index) {
        if (index >= 0 && index < tarefas.size()) {
            tarefas.remove(index);
            salvar();
        }
    }

    public void editar(int index, String novaDescricao) {
        if (index >= 0 && index < tarefas.size()) {
            Tarefa tarefaAtual = tarefas.get(index);
            Tarefa tarefaEditada = new Tarefa(novaDescricao, tarefaAtual.concluida());
            tarefas.set(index, tarefaEditada);
            salvar();
        }
    }

    public List<Tarefa> getTarefas() {
        return Collections.unmodifiableList(tarefas);
    }

    private void salvar() {
        try (ObjectOutputStream oos = new ObjectOutputStream(Files.newOutputStream(PATH))) {
            oos.writeObject(tarefas);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @SuppressWarnings("unchecked")
    private void carregar() {
        if (Files.exists(PATH)) {
            try (ObjectInputStream ois = new ObjectInputStream(Files.newInputStream(PATH))) {
                tarefas.addAll((List<Tarefa>) ois.readObject());
            } catch (Exception e) {
                System.err.println("Erro ao carregar dados.");
            }
        }
    }
}

