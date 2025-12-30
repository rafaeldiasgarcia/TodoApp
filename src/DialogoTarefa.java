import javax.swing.*;
import java.awt.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Set;

public class DialogoTarefa {
    private final TemaManager tema;
    private final JTextField txtDescricao;
    private final JTextField txtObservacao;
    private final JComboBox<Tarefa.Prioridade> comboPrioridade;
    private final JComboBox<String> comboCategoria;
    private final JTextField txtData;
    private final JPanel panel;
    
    public DialogoTarefa(TemaManager tema, Set<String> categorias, Tarefa tarefaExistente) {
        this.tema = tema;
        
        panel = new JPanel(new GridLayout(6, 2, 10, 10));
        panel.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));
        panel.setBackground(tema.getCardBackground());
        
        // Descrição
        JLabel lblDescricao = criarLabel("Descricao:");
        txtDescricao = criarTextField();
        if (tarefaExistente != null) {
            txtDescricao.setText(tarefaExistente.descricao());
        }
        panel.add(lblDescricao);
        panel.add(txtDescricao);
        
        // Observação
        JLabel lblObservacao = criarLabel("Observacao:");
        txtObservacao = criarTextField();
        if (tarefaExistente != null) {
            txtObservacao.setText(tarefaExistente.observacao());
        }
        panel.add(lblObservacao);
        panel.add(txtObservacao);
        
        // Prioridade
        JLabel lblPrioridade = criarLabel("Prioridade:");
        comboPrioridade = new JComboBox<>(Tarefa.Prioridade.values());
        estilizarCombo(comboPrioridade);
        if (tarefaExistente != null) {
            comboPrioridade.setSelectedItem(tarefaExistente.prioridade());
        } else {
            comboPrioridade.setSelectedItem(Tarefa.Prioridade.MEDIA);
        }
        panel.add(lblPrioridade);
        panel.add(comboPrioridade);
        
        // Categoria
        JLabel lblCategoria = criarLabel("Categoria:");
        comboCategoria = new JComboBox<>();
        for (String cat : categorias) {
            comboCategoria.addItem(cat);
        }
        comboCategoria.setEditable(true);
        estilizarCombo(comboCategoria);
        if (tarefaExistente != null) {
            comboCategoria.setSelectedItem(tarefaExistente.categoria());
        }
        panel.add(lblCategoria);
        panel.add(comboCategoria);
        
        // Data de vencimento
        JLabel lblData = criarLabel("Vencimento (dd/mm/aaaa):");
        txtData = criarTextField();
        if (tarefaExistente != null && tarefaExistente.dataVencimento() != null) {
            txtData.setText(tarefaExistente.dataVencimento().format(DateTimeFormatter.ofPattern("dd/MM/yyyy")));
        }
        panel.add(lblData);
        panel.add(txtData);
        
        // Nota explicativa
        panel.add(new JLabel(""));
        JLabel lblNota = criarLabel("<html><i>Deixe a data em branco se nao houver vencimento</i></html>");
        lblNota.setFont(new Font("Segoe UI", Font.ITALIC, 11));
        panel.add(lblNota);
    }
    
    private JLabel criarLabel(String texto) {
        JLabel label = new JLabel(texto);
        label.setForeground(tema.getText());
        label.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        return label;
    }
    
    private JTextField criarTextField() {
        JTextField field = new JTextField(20);
        field.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        field.setBackground(tema.getCardBackground());
        field.setForeground(tema.getText());
        field.setCaretColor(tema.getText());
        field.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(tema.getBorder(), 2),
            BorderFactory.createEmptyBorder(5, 10, 5, 10)
        ));
        return field;
    }
    
    private void estilizarCombo(JComboBox<?> combo) {
        combo.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        combo.setBackground(tema.getCardBackground());
        combo.setForeground(tema.getText());
    }
    
    public JPanel getPanel() {
        return panel;
    }
    
    public String getDescricao() {
        return txtDescricao.getText().trim();
    }
    
    public String getObservacao() {
        return txtObservacao.getText().trim();
    }
    
    public Tarefa.Prioridade getPrioridade() {
        return (Tarefa.Prioridade) comboPrioridade.getSelectedItem();
    }
    
    public String getCategoria() {
        return (String) comboCategoria.getSelectedItem();
    }
    
    public LocalDate getDataVencimento() throws Exception {
        String dataStr = txtData.getText().trim();
        if (dataStr.isEmpty()) {
            return null;
        }
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        return LocalDate.parse(dataStr, formatter);
    }
}

