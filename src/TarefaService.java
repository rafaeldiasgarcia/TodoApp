import java.io.*;
import java.nio.file.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.stream.Collectors;

public class TarefaService {
    private static final Path PATH = Paths.get("tarefas.dat");
    private final List<Tarefa> tarefas = new ArrayList<>();

    public enum FiltroTarefa {
        TODAS, PENDENTES, CONCLUIDAS
    }

    public TarefaService() {
        carregar();
    }

    public void adicionar(String desc, String observacao, Tarefa.Prioridade prioridade, String categoria, LocalDate dataVencimento) {
        tarefas.add(new Tarefa(desc, false, observacao, prioridade, categoria, dataVencimento));
        salvar();
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

    public void editar(int index, String novaDescricao, String novaObservacao, Tarefa.Prioridade novaPrioridade, String novaCategoria, LocalDate novaDataVencimento) {
        if (index >= 0 && index < tarefas.size()) {
            Tarefa tarefaAtual = tarefas.get(index);
            Tarefa tarefaEditada = new Tarefa(
                novaDescricao, 
                tarefaAtual.concluida(),
                novaObservacao,
                novaPrioridade,
                novaCategoria,
                novaDataVencimento
            );
            tarefas.set(index, tarefaEditada);
            salvar();
        }
    }

    public void editarObservacao(int index, String novaObservacao) {
        if (index >= 0 && index < tarefas.size()) {
            Tarefa tarefaAtual = tarefas.get(index);
            Tarefa tarefaEditada = new Tarefa(
                tarefaAtual.descricao(),
                tarefaAtual.concluida(),
                novaObservacao,
                tarefaAtual.prioridade(),
                tarefaAtual.categoria(),
                tarefaAtual.dataVencimento()
            );
            tarefas.set(index, tarefaEditada);
            salvar();
        }
    }

    public void editar(int index, String novaDescricao) {
        if (index >= 0 && index < tarefas.size()) {
            Tarefa tarefaAtual = tarefas.get(index);
            Tarefa tarefaEditada = new Tarefa(
                novaDescricao,
                tarefaAtual.concluida(),
                tarefaAtual.observacao(),
                tarefaAtual.prioridade(),
                tarefaAtual.categoria(),
                tarefaAtual.dataVencimento()
            );
            tarefas.set(index, tarefaEditada);
            salvar();
        }
    }

    public void alternarConclusao(int index) {
        if (index >= 0 && index < tarefas.size()) {
            Tarefa tarefaAtual = tarefas.get(index);
            Tarefa tarefaEditada = new Tarefa(
                tarefaAtual.descricao(),
                !tarefaAtual.concluida(),
                tarefaAtual.observacao(),
                tarefaAtual.prioridade(),
                tarefaAtual.categoria(),
                tarefaAtual.dataVencimento()
            );
            tarefas.set(index, tarefaEditada);
            salvar();
        }
    }

    public List<Tarefa> getTarefas() {
        return Collections.unmodifiableList(tarefas);
    }

    public List<Tarefa> getTarefasFiltradas(FiltroTarefa filtro) {
        return switch (filtro) {
            case PENDENTES -> tarefas.stream()
                .filter(t -> !t.concluida())
                .collect(Collectors.toList());
            case CONCLUIDAS -> tarefas.stream()
                .filter(Tarefa::concluida)
                .collect(Collectors.toList());
            default -> getTarefas();
        };
    }

    public Set<String> getCategorias() {
        Set<String> categorias = new TreeSet<>();
        categorias.add("Geral");
        categorias.addAll(tarefas.stream()
            .map(Tarefa::categoria)
            .filter(c -> c != null && !c.isEmpty())
            .collect(Collectors.toSet()));
        return categorias;
    }

    public void exportarParaCSV(Path destino) throws IOException {
        StringBuilder csv = new StringBuilder();
        csv.append("Descrição;Status;Observação;Prioridade;Categoria;Data Vencimento\n");
        
        for (Tarefa t : tarefas) {
            csv.append(escaparCSV(t.descricao())).append(";");
            csv.append(t.concluida() ? "Concluída" : "Pendente").append(";");
            csv.append(escaparCSV(t.observacao())).append(";");
            csv.append(t.prioridade()).append(";");
            csv.append(escaparCSV(t.categoria())).append(";");
            csv.append(t.dataVencimento() != null ? 
                t.dataVencimento().format(DateTimeFormatter.ofPattern("dd/MM/yyyy")) : "").append("\n");
        }
        
        Files.writeString(destino, csv.toString());
    }

    public void exportarParaJSON(Path destino) throws IOException {
        StringBuilder json = new StringBuilder();
        json.append("{\n  \"tarefas\": [\n");
        
        for (int i = 0; i < tarefas.size(); i++) {
            Tarefa t = tarefas.get(i);
            json.append("    {\n");
            json.append("      \"descricao\": \"").append(escaparJSON(t.descricao())).append("\",\n");
            json.append("      \"concluida\": ").append(t.concluida()).append(",\n");
            json.append("      \"observacao\": \"").append(escaparJSON(t.observacao())).append("\",\n");
            json.append("      \"prioridade\": \"").append(t.prioridade()).append("\",\n");
            json.append("      \"categoria\": \"").append(escaparJSON(t.categoria())).append("\",\n");
            json.append("      \"dataVencimento\": ");
            if (t.dataVencimento() != null) {
                json.append("\"").append(t.dataVencimento()).append("\"");
            } else {
                json.append("null");
            }
            json.append("\n    }");
            if (i < tarefas.size() - 1) {
                json.append(",");
            }
            json.append("\n");
        }
        
        json.append("  ]\n}");
        Files.writeString(destino, json.toString());
    }

    private String escaparCSV(String valor) {
        if (valor == null) return "";
        return "\"" + valor.replace("\"", "\"\"") + "\"";
    }

    private String escaparJSON(String valor) {
        if (valor == null) return "";
        return valor.replace("\\", "\\\\")
                   .replace("\"", "\\\"")
                   .replace("\n", "\\n")
                   .replace("\r", "\\r")
                   .replace("\t", "\\t");
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

