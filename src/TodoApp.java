import javax.swing.*;
import javax.swing.border.*;
import java.awt.*;
import java.awt.event.*;
import java.time.LocalDate;
import java.util.List;
import java.io.File;

public class TodoApp extends JFrame {
    private final TarefaService service = new TarefaService();
    private final TemaManager tema = new TemaManager();
    private final ComponenteFactory factory = new ComponenteFactory(tema);
    
    private DefaultListModel<String> listModel;
    private JList<String> taskList;
    private TarefaService.FiltroTarefa filtroAtual = TarefaService.FiltroTarefa.TODAS;

    public static void main(String[] args) {
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception ignored) {}
        
        SwingUtilities.invokeLater(() -> new TodoApp().setVisible(true));
    }

    public TodoApp() {
        configurarJanela();
        inicializarComponentes();
        configurarAtalhosTeclado();
        atualizarLista();
    }

    private void configurarJanela() {
        setTitle("Task Manager Pro - Java 21");
        setSize(700, 850);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setResizable(true);
        setMinimumSize(new Dimension(600, 700));
        getContentPane().setBackground(tema.getBackground());
    }

    private void inicializarComponentes() {
        setLayout(new BorderLayout(0, 0));

        add(criarHeader(), BorderLayout.NORTH);
        add(criarPainelLista(), BorderLayout.CENTER);
        add(criarPainelBotoes(), BorderLayout.SOUTH);
    }

    private void configurarAtalhosTeclado() {
        // Ctrl+N - Nova tarefa
        KeyStroke ctrlN = KeyStroke.getKeyStroke(KeyEvent.VK_N, InputEvent.CTRL_DOWN_MASK);
        getRootPane().registerKeyboardAction(e -> adicionar(), ctrlN, JComponent.WHEN_IN_FOCUSED_WINDOW);
        
        // Ctrl+E - Editar tarefa
        KeyStroke ctrlE = KeyStroke.getKeyStroke(KeyEvent.VK_E, InputEvent.CTRL_DOWN_MASK);
        getRootPane().registerKeyboardAction(e -> editar(), ctrlE, JComponent.WHEN_IN_FOCUSED_WINDOW);
        
        // Delete - Remover tarefa
        KeyStroke delete = KeyStroke.getKeyStroke(KeyEvent.VK_DELETE, 0);
        getRootPane().registerKeyboardAction(e -> remover(), delete, JComponent.WHEN_IN_FOCUSED_WINDOW);
        
        // Ctrl+R - Atualizar
        KeyStroke ctrlR = KeyStroke.getKeyStroke(KeyEvent.VK_R, InputEvent.CTRL_DOWN_MASK);
        getRootPane().registerKeyboardAction(e -> atualizarLista(), ctrlR, JComponent.WHEN_IN_FOCUSED_WINDOW);
        
        // Espaço - Marcar/desmarcar como concluída
        KeyStroke space = KeyStroke.getKeyStroke(KeyEvent.VK_SPACE, 0);
        getRootPane().registerKeyboardAction(e -> marcarConcluida(), space, JComponent.WHEN_IN_FOCUSED_WINDOW);
        
        // Enter - Ver detalhes
        KeyStroke enter = KeyStroke.getKeyStroke(KeyEvent.VK_ENTER, 0);
        getRootPane().registerKeyboardAction(e -> verDetalhes(), enter, JComponent.WHEN_IN_FOCUSED_WINDOW);
    }

    private JPanel criarHeader() {
        JPanel header = new JPanel();
        header.setBackground(tema.getPrimary());
        header.setPreferredSize(new Dimension(700, 100));
        header.setLayout(new BorderLayout());

        // Título
        JPanel tituloPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        tituloPanel.setBackground(tema.getPrimary());
        JLabel titulo = new JLabel("TASK MANAGER PRO");
        titulo.setFont(new Font("Segoe UI", Font.BOLD, 28));
        titulo.setForeground(Color.WHITE);
        tituloPanel.add(titulo);
        
        // Painel de filtros
        JPanel filtrosPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 5));
        filtrosPanel.setBackground(tema.getPrimary());
        
        JLabel labelFiltro = new JLabel("Filtro:");
        labelFiltro.setForeground(Color.WHITE);
        labelFiltro.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        
        JComboBox<String> comboFiltro = new JComboBox<>(new String[]{"Todas", "Pendentes", "Concluidas"});
        comboFiltro.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        comboFiltro.setPreferredSize(new Dimension(150, 30));
        comboFiltro.addActionListener(e -> {
            int index = comboFiltro.getSelectedIndex();
            filtroAtual = switch (index) {
                case 1 -> TarefaService.FiltroTarefa.PENDENTES;
                case 2 -> TarefaService.FiltroTarefa.CONCLUIDAS;
                default -> TarefaService.FiltroTarefa.TODAS;
            };
            atualizarLista();
        });
        
        filtrosPanel.add(labelFiltro);
        filtrosPanel.add(comboFiltro);
        
        header.add(tituloPanel, BorderLayout.CENTER);
        header.add(filtrosPanel, BorderLayout.SOUTH);
        
        return header;
    }

    private JScrollPane criarPainelLista() {
        listModel = new DefaultListModel<>();
        taskList = new JList<>(listModel);
        
        taskList.setFont(new Font("Segoe UI", Font.PLAIN, 15));
        taskList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        taskList.setBackground(tema.getCardBackground());
        taskList.setForeground(tema.getText());
        taskList.setFixedCellHeight(45);
        taskList.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        
        // Duplo clique para ver detalhes
        taskList.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent evt) {
                if (evt.getClickCount() == 2) {
                    verDetalhes();
                }
            }
        });
        
        JScrollPane scrollPane = new JScrollPane(taskList);
        scrollPane.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createEmptyBorder(20, 20, 20, 20),
            BorderFactory.createLineBorder(tema.getBorder(), 2, true)
        ));
        scrollPane.getViewport().setBackground(tema.getCardBackground());
        
        return scrollPane;
    }

    private JPanel criarPainelBotoes() {
        JPanel painelPrincipal = new JPanel(new BorderLayout());
        painelPrincipal.setBackground(tema.getBackground());
        
        // Painel de botões principais
        JPanel painelBotoes = new JPanel();
        painelBotoes.setBackground(tema.getBackground());
        painelBotoes.setLayout(new GridLayout(2, 3, 15, 15));
        painelBotoes.setBorder(BorderFactory.createEmptyBorder(10, 40, 10, 40));

        JButton btnAdicionar = factory.criarBotao("+ Adicionar (Ctrl+N)", tema.getSuccess());
        JButton btnEditar = factory.criarBotao("Editar (Ctrl+E)", tema.getWarning());
        JButton btnRemover = factory.criarBotao("Remover (Del)", tema.getDanger());
        JButton btnConcluir = factory.criarBotao("Concluir (Space)", tema.getPrimary());
        JButton btnDetalhes = factory.criarBotao("Detalhes (Enter)", tema.getSecondary());
        JButton btnAtualizar = factory.criarBotao("Atualizar (Ctrl+R)", tema.getInfo());

        btnAdicionar.addActionListener(e -> adicionar());
        btnEditar.addActionListener(e -> editar());
        btnRemover.addActionListener(e -> remover());
        btnConcluir.addActionListener(e -> marcarConcluida());
        btnDetalhes.addActionListener(e -> verDetalhes());
        btnAtualizar.addActionListener(e -> atualizarLista());

        painelBotoes.add(btnAdicionar);
        painelBotoes.add(btnEditar);
        painelBotoes.add(btnRemover);
        painelBotoes.add(btnConcluir);
        painelBotoes.add(btnDetalhes);
        painelBotoes.add(btnAtualizar);
        
        // Painel de exportação
        JPanel painelExportar = new JPanel(new FlowLayout(FlowLayout.CENTER, 15, 5));
        painelExportar.setBackground(tema.getBackground());
        
        JButton btnExportarCSV = factory.criarBotaoPequeno("Exportar CSV", new Color(34, 197, 94));
        JButton btnExportarJSON = factory.criarBotaoPequeno("Exportar JSON", new Color(59, 130, 246));
        
        btnExportarCSV.addActionListener(e -> exportar("CSV"));
        btnExportarJSON.addActionListener(e -> exportar("JSON"));
        
        painelExportar.add(btnExportarCSV);
        painelExportar.add(btnExportarJSON);

        painelPrincipal.add(painelBotoes, BorderLayout.CENTER);
        painelPrincipal.add(painelExportar, BorderLayout.SOUTH);
        painelPrincipal.setBorder(BorderFactory.createEmptyBorder(10, 0, 20, 0));
        
        return painelPrincipal;
    }

    private void atualizarLista() {
        listModel.clear();
        List<Tarefa> tarefas = service.getTarefasFiltradas(filtroAtual);
        
        if (tarefas.isEmpty()) {
            String mensagem = switch (filtroAtual) {
                case PENDENTES -> "   Nenhuma tarefa pendente!";
                case CONCLUIDAS -> "   Nenhuma tarefa concluida ainda.";
                default -> "   Nenhuma tarefa cadastrada. Adicione uma nova!";
            };
            listModel.addElement(mensagem);
        } else {
            List<Tarefa> todasTarefas = service.getTarefas();
            for (Tarefa t : tarefas) {
                int indiceOriginal = todasTarefas.indexOf(t);
                listModel.addElement("   " + (indiceOriginal + 1) + ".  " + t.toString() + "  " + t.prioridade());
            }
        }
    }

    private void adicionar() {
        DialogoTarefa dialogo = new DialogoTarefa(tema, service.getCategorias(), null);
        
        int result = JOptionPane.showConfirmDialog(
            this, 
            dialogo.getPanel(), 
            "Nova Tarefa",
            JOptionPane.OK_CANCEL_OPTION,
            JOptionPane.PLAIN_MESSAGE
        );
        
        if (result == JOptionPane.OK_OPTION) {
            String desc = dialogo.getDescricao();
            if (!desc.isBlank()) {
                try {
                    LocalDate dataVencimento = dialogo.getDataVencimento();
                    service.adicionar(desc, dialogo.getObservacao(), dialogo.getPrioridade(), 
                                    dialogo.getCategoria(), dataVencimento);
                    atualizarLista();
                    factory.mostrarMensagem(this, "Tarefa adicionada com sucesso!", "Sucesso", tema.getSuccess());
                } catch (Exception ex) {
                    factory.mostrarMensagem(this, "Data invalida! Use o formato dd/mm/aaaa", "Erro", tema.getDanger());
                }
            }
        }
    }

    private void editar() {
        List<Tarefa> tarefas = service.getTarefas();
        
        if (tarefas.isEmpty()) {
            factory.mostrarMensagem(this, "Nao ha tarefas para editar.", "Aviso", tema.getPrimary());
            return;
        }
        
        int selectedIndex = taskList.getSelectedIndex();
        
        if (selectedIndex == -1) {
            factory.mostrarMensagem(this, "Selecione uma tarefa na lista para editar.", "Aviso", tema.getPrimary());
            return;
        }
        
        // Obter o índice real da tarefa
        String item = listModel.getElementAt(selectedIndex);
        int indiceReal = Integer.parseInt(item.trim().split("\\.")[0].trim()) - 1;
        
        Tarefa tarefaAtual = tarefas.get(indiceReal);
        DialogoTarefa dialogo = new DialogoTarefa(tema, service.getCategorias(), tarefaAtual);
        
        int result = JOptionPane.showConfirmDialog(
            this, 
            dialogo.getPanel(), 
            "Editar Tarefa",
            JOptionPane.OK_CANCEL_OPTION,
            JOptionPane.PLAIN_MESSAGE
        );
        
        if (result == JOptionPane.OK_OPTION) {
            String novaDesc = dialogo.getDescricao();
            if (!novaDesc.isBlank()) {
                try {
                    LocalDate novaDataVencimento = dialogo.getDataVencimento();
                    service.editar(indiceReal, novaDesc, dialogo.getObservacao(), 
                                 dialogo.getPrioridade(), dialogo.getCategoria(), novaDataVencimento);
                    atualizarLista();
                    factory.mostrarMensagem(this, "Tarefa editada com sucesso!", "Sucesso", tema.getWarning());
                } catch (Exception ex) {
                    factory.mostrarMensagem(this, "Data invalida! Use o formato dd/mm/aaaa", "Erro", tema.getDanger());
                }
            }
        }
    }

    private void remover() {
        List<Tarefa> tarefas = service.getTarefas();
        
        if (tarefas.isEmpty()) {
            factory.mostrarMensagem(this, "Nao ha tarefas para remover.", "Aviso", tema.getPrimary());
            return;
        }
        
        int selectedIndex = taskList.getSelectedIndex();
        
        if (selectedIndex == -1) {
            factory.mostrarMensagem(this, "Selecione uma tarefa na lista para remover.", "Aviso", tema.getPrimary());
            return;
        }
        
        // Obter o índice real da tarefa
        String item = listModel.getElementAt(selectedIndex);
        int indiceReal = Integer.parseInt(item.trim().split("\\.")[0].trim()) - 1;
        
        int confirm = JOptionPane.showConfirmDialog(
            this,
            "Tem certeza que deseja remover esta tarefa?\n\n" + tarefas.get(indiceReal).toString(),
            "Confirmar Remocao",
            JOptionPane.YES_NO_OPTION,
            JOptionPane.QUESTION_MESSAGE
        );
        
        if (confirm == JOptionPane.YES_OPTION) {
            service.remover(indiceReal);
            atualizarLista();
            factory.mostrarMensagem(this, "Tarefa removida com sucesso!", "Sucesso", tema.getDanger());
        }
    }

    private void marcarConcluida() {
        List<Tarefa> tarefas = service.getTarefas();
        
        if (tarefas.isEmpty()) {
            factory.mostrarMensagem(this, "Nao ha tarefas para marcar.", "Aviso", tema.getPrimary());
            return;
        }
        
        int selectedIndex = taskList.getSelectedIndex();
        
        if (selectedIndex == -1) {
            factory.mostrarMensagem(this, "Selecione uma tarefa na lista.", "Aviso", tema.getPrimary());
            return;
        }
        
        // Obter o índice real da tarefa
        String item = listModel.getElementAt(selectedIndex);
        int indiceReal = Integer.parseInt(item.trim().split("\\.")[0].trim()) - 1;
        
        service.alternarConclusao(indiceReal);
        atualizarLista();
        
        Tarefa tarefa = service.getTarefas().get(indiceReal);
        if (tarefa.concluida()) {
            factory.mostrarMensagem(this, "Tarefa marcada como concluida!", "Sucesso", tema.getSuccess());
        } else {
            factory.mostrarMensagem(this, "Tarefa marcada como pendente!", "Sucesso", tema.getPrimary());
        }
    }

    private void verDetalhes() {
        List<Tarefa> tarefas = service.getTarefas();
        
        if (tarefas.isEmpty()) {
            factory.mostrarMensagem(this, "Nao ha tarefas para visualizar.", "Aviso", tema.getPrimary());
            return;
        }
        
        int selectedIndex = taskList.getSelectedIndex();
        
        if (selectedIndex == -1) {
            factory.mostrarMensagem(this, "Selecione uma tarefa na lista para ver detalhes.", "Aviso", tema.getPrimary());
            return;
        }
        
        // Obter o índice real da tarefa
        String item = listModel.getElementAt(selectedIndex);
        int indiceReal = Integer.parseInt(item.trim().split("\\.")[0].trim()) - 1;
        
        Tarefa tarefa = tarefas.get(indiceReal);
        
        // Criar painel de detalhes
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        panel.setBackground(tema.getCardBackground());
        
        JTextArea textArea = new JTextArea(tarefa.toDetailedString());
        textArea.setEditable(false);
        textArea.setFont(new Font("Consolas", Font.PLAIN, 13));
        textArea.setBackground(tema.getCardBackground());
        textArea.setForeground(tema.getText());
        textArea.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        
        panel.add(new JScrollPane(textArea), BorderLayout.CENTER);
        
        String[] opcoes = {"Editar Observacao", "Fechar"};
        int result = JOptionPane.showOptionDialog(
            this,
            panel,
            "Detalhes da Tarefa",
            JOptionPane.DEFAULT_OPTION,
            JOptionPane.PLAIN_MESSAGE,
            null,
            opcoes,
            opcoes[1]
        );
        
        if (result == 0) {
            editarObservacao(indiceReal, tarefa);
        }
    }

    private void editarObservacao(int indiceReal, Tarefa tarefa) {
        JPanel panel = new JPanel(new BorderLayout(10, 10));
        panel.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));
        panel.setBackground(tema.getCardBackground());
        
        JLabel label = factory.criarLabel("Edite a observacao da tarefa:", 14, Font.BOLD);
        
        JTextArea textArea = factory.criarTextArea(tarefa.observacao(), 5, 30);
        JScrollPane scrollPane = new JScrollPane(textArea);
        
        panel.add(label, BorderLayout.NORTH);
        panel.add(scrollPane, BorderLayout.CENTER);
        
        int result = JOptionPane.showConfirmDialog(
            this,
            panel,
            "Editar Observacao",
            JOptionPane.OK_CANCEL_OPTION,
            JOptionPane.PLAIN_MESSAGE
        );
        
        if (result == JOptionPane.OK_OPTION) {
            String novaObs = textArea.getText().trim();
            service.editarObservacao(indiceReal, novaObs);
            atualizarLista();
            factory.mostrarMensagem(this, "Observacao editada com sucesso!", "Sucesso", tema.getPrimary());
        }
    }

    private void exportar(String formato) {
        List<Tarefa> tarefas = service.getTarefas();
        
        if (tarefas.isEmpty()) {
            factory.mostrarMensagem(this, "Nao ha tarefas para exportar.", "Aviso", tema.getPrimary());
            return;
        }
        
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setDialogTitle("Salvar arquivo " + formato);
        
        String extensao = formato.toLowerCase();
        fileChooser.setSelectedFile(new File("tarefas." + extensao));
        
        int result = fileChooser.showSaveDialog(this);
        
        if (result == JFileChooser.APPROVE_OPTION) {
            File arquivo = fileChooser.getSelectedFile();
            try {
                if (formato.equals("CSV")) {
                    service.exportarParaCSV(arquivo.toPath());
                } else {
                    service.exportarParaJSON(arquivo.toPath());
                }
                factory.mostrarMensagem(this, "Tarefas exportadas com sucesso para:\n" + arquivo.getAbsolutePath(), 
                               "Sucesso", tema.getSuccess());
            } catch (Exception ex) {
                factory.mostrarMensagem(this, "Erro ao exportar: " + ex.getMessage(), "Erro", tema.getDanger());
            }
        }
    }

}
