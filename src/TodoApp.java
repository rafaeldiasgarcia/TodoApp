import javax.swing.*;
import javax.swing.border.*;
import java.awt.*;
import java.util.List;

public class TodoApp extends JFrame {
    private final TarefaService service = new TarefaService();
    private DefaultListModel<String> listModel;
    private JList<String> taskList;
    
    private static final Color PRIMARY_COLOR = new Color(99, 102, 241);
    private static final Color SECONDARY_COLOR = new Color(236, 72, 153);
    private static final Color BG_COLOR = new Color(249, 250, 251);
    private static final Color CARD_BG = Color.WHITE;
    private static final Color TEXT_COLOR = new Color(31, 41, 55);
    private static final Color SUCCESS_COLOR = new Color(16, 185, 129);
    private static final Color DANGER_COLOR = new Color(239, 68, 68);

    public static void main(String[] args) {
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception ignored) {}
        
        SwingUtilities.invokeLater(() -> new TodoApp().setVisible(true));
    }

    public TodoApp() {
        configurarJanela();
        inicializarComponentes();
        atualizarLista();
    }

    private void configurarJanela() {
        setTitle("Task Manager Pro - Java 21");
        setSize(600, 750);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setResizable(false);
        getContentPane().setBackground(BG_COLOR);
    }

    private void inicializarComponentes() {
        setLayout(new BorderLayout(0, 0));

        add(criarHeader(), BorderLayout.NORTH);
        add(criarPainelLista(), BorderLayout.CENTER);
        add(criarPainelBotoes(), BorderLayout.SOUTH);
    }

    private JPanel criarHeader() {
        JPanel header = new JPanel();
        header.setBackground(PRIMARY_COLOR);
        header.setPreferredSize(new Dimension(600, 80));
        header.setLayout(new FlowLayout(FlowLayout.CENTER, 10, 20));

        JLabel titulo = new JLabel("TASK MANAGER PRO");
        titulo.setFont(new Font("Segoe UI", Font.BOLD, 28));
        titulo.setForeground(Color.WHITE);
        
        header.add(titulo);
        return header;
    }

    private JScrollPane criarPainelLista() {
        listModel = new DefaultListModel<>();
        taskList = new JList<>(listModel);
        
        taskList.setFont(new Font("Segoe UI", Font.PLAIN, 16));
        taskList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        taskList.setBackground(CARD_BG);
        taskList.setFixedCellHeight(50);
        taskList.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        
        JScrollPane scrollPane = new JScrollPane(taskList);
        scrollPane.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createEmptyBorder(20, 20, 20, 20),
            BorderFactory.createLineBorder(new Color(229, 231, 235), 2, true)
        ));
        scrollPane.getViewport().setBackground(CARD_BG);
        
        return scrollPane;
    }

    private JPanel criarPainelBotoes() {
        JPanel painel = new JPanel();
        painel.setBackground(BG_COLOR);
        painel.setLayout(new GridLayout(2, 2, 15, 15));
        painel.setBorder(BorderFactory.createEmptyBorder(20, 40, 30, 40));

        JButton btnAdicionar = criarBotao("+ Adicionar", SUCCESS_COLOR);
        JButton btnEditar = criarBotao("Editar", new Color(245, 158, 11));
        JButton btnRemover = criarBotao("X Remover", DANGER_COLOR);
        JButton btnAtualizar = criarBotao("Atualizar", PRIMARY_COLOR);

        btnAdicionar.addActionListener(e -> adicionar());
        btnEditar.addActionListener(e -> editar());
        btnRemover.addActionListener(e -> remover());
        btnAtualizar.addActionListener(e -> atualizarLista());

        painel.add(btnAdicionar);
        painel.add(btnEditar);
        painel.add(btnRemover);
        painel.add(btnAtualizar);

        return painel;
    }

    private JButton criarBotao(String texto, Color cor) {
        JButton btn = new JButton(texto);
        btn.setFont(new Font("Segoe UI", Font.BOLD, 14));
        btn.setForeground(Color.WHITE);
        btn.setBackground(cor);
        btn.setFocusPainted(false);
        btn.setBorderPainted(false);
        btn.setPreferredSize(new Dimension(200, 50));
        btn.setCursor(new Cursor(Cursor.HAND_CURSOR));
        
        btn.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                btn.setBackground(cor.darker());
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                btn.setBackground(cor);
            }
        });
        
        return btn;
    }

    private void atualizarLista() {
        listModel.clear();
        List<Tarefa> tarefas = service.getTarefas();
        
        if (tarefas.isEmpty()) {
            listModel.addElement("   Nenhuma tarefa cadastrada. Adicione uma nova!");
        } else {
            for (int i = 0; i < tarefas.size(); i++) {
                listModel.addElement("   " + (i + 1) + ".  " + tarefas.get(i).toString());
            }
        }
    }

    private void adicionar() {
        JPanel panel = new JPanel(new BorderLayout(10, 10));
        panel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        
        JLabel label = new JLabel("Digite a descrição da tarefa:");
        label.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        
        JTextField textField = new JTextField(20);
        textField.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        textField.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(209, 213, 219), 2),
            BorderFactory.createEmptyBorder(5, 10, 5, 10)
        ));
        
        panel.add(label, BorderLayout.NORTH);
        panel.add(textField, BorderLayout.CENTER);
        
        int result = JOptionPane.showConfirmDialog(
            this, 
            panel, 
            "Nova Tarefa",
            JOptionPane.OK_CANCEL_OPTION,
            JOptionPane.PLAIN_MESSAGE
        );
        
        if (result == JOptionPane.OK_OPTION) {
            String desc = textField.getText().trim();
            if (!desc.isBlank()) {
                service.adicionar(desc);
                atualizarLista();
                mostrarMensagem("Tarefa adicionada com sucesso!", "Sucesso", SUCCESS_COLOR);
            }
        }
    }

    private void editar() {
        List<Tarefa> tarefas = service.getTarefas();
        
        if (tarefas.isEmpty()) {
            mostrarMensagem("Nao ha tarefas para editar.", "Aviso", PRIMARY_COLOR);
            return;
        }
        
        int selectedIndex = taskList.getSelectedIndex();
        
        if (selectedIndex == -1) {
            mostrarMensagem("Selecione uma tarefa na lista para editar.", "Aviso", PRIMARY_COLOR);
            return;
        }
        
        Tarefa tarefaAtual = tarefas.get(selectedIndex);
        
        JPanel panel = new JPanel(new BorderLayout(10, 10));
        panel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        
        JLabel label = new JLabel("Edite a descricao da tarefa:");
        label.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        
        JTextField textField = new JTextField(tarefaAtual.descricao());
        textField.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        textField.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(209, 213, 219), 2),
            BorderFactory.createEmptyBorder(5, 10, 5, 10)
        ));
        
        panel.add(label, BorderLayout.NORTH);
        panel.add(textField, BorderLayout.CENTER);
        
        int result = JOptionPane.showConfirmDialog(
            this, 
            panel, 
            "Editar Tarefa",
            JOptionPane.OK_CANCEL_OPTION,
            JOptionPane.PLAIN_MESSAGE
        );
        
        if (result == JOptionPane.OK_OPTION) {
            String novaDesc = textField.getText().trim();
            if (!novaDesc.isBlank()) {
                service.editar(selectedIndex, novaDesc);
                atualizarLista();
                mostrarMensagem("Tarefa editada com sucesso!", "Sucesso", new Color(245, 158, 11));
            }
        }
    }

    private void remover() {
        List<Tarefa> tarefas = service.getTarefas();
        
        if (tarefas.isEmpty()) {
            mostrarMensagem("Nao ha tarefas para remover.", "Aviso", PRIMARY_COLOR);
            return;
        }
        
        int selectedIndex = taskList.getSelectedIndex();
        
        if (selectedIndex == -1) {
            mostrarMensagem("Selecione uma tarefa na lista para remover.", "Aviso", PRIMARY_COLOR);
            return;
        }
        
        int confirm = JOptionPane.showConfirmDialog(
            this,
            "Tem certeza que deseja remover esta tarefa?\n\n" + tarefas.get(selectedIndex).toString(),
            "Confirmar Remoção",
            JOptionPane.YES_NO_OPTION,
            JOptionPane.QUESTION_MESSAGE
        );
        
        if (confirm == JOptionPane.YES_OPTION) {
            service.remover(selectedIndex);
            atualizarLista();
            mostrarMensagem("Tarefa removida com sucesso!", "Sucesso", DANGER_COLOR);
        }
    }

    private void mostrarMensagem(String mensagem, String titulo, Color cor) {
        JLabel label = new JLabel("<html><div style='padding:10px;'>" + mensagem + "</div></html>");
        label.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        label.setForeground(TEXT_COLOR);
        
        JOptionPane.showMessageDialog(
            this,
            label,
            titulo,
            JOptionPane.PLAIN_MESSAGE
        );
    }
}
