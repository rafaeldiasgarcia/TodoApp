import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class ComponenteFactory {
    private final TemaManager tema;
    
    public ComponenteFactory(TemaManager tema) {
        this.tema = tema;
    }
    
    public JButton criarBotao(String texto, Color cor) {
        JButton btn = new JButton(texto);
        btn.setFont(new Font("Segoe UI", Font.BOLD, 13));
        btn.setForeground(Color.WHITE);
        btn.setBackground(cor);
        btn.setFocusPainted(false);
        btn.setBorderPainted(false);
        btn.setPreferredSize(new Dimension(180, 45));
        btn.setCursor(new Cursor(Cursor.HAND_CURSOR));
        
        btn.addMouseListener(new MouseAdapter() {
            public void mouseEntered(MouseEvent evt) {
                btn.setBackground(cor.darker());
            }
            public void mouseExited(MouseEvent evt) {
                btn.setBackground(cor);
            }
        });
        
        return btn;
    }
    
    public JButton criarBotaoPequeno(String texto, Color cor) {
        JButton btn = new JButton(texto);
        btn.setFont(new Font("Segoe UI", Font.BOLD, 12));
        btn.setForeground(Color.WHITE);
        btn.setBackground(cor);
        btn.setFocusPainted(false);
        btn.setBorderPainted(false);
        btn.setPreferredSize(new Dimension(150, 30));
        btn.setCursor(new Cursor(Cursor.HAND_CURSOR));
        
        btn.addMouseListener(new MouseAdapter() {
            public void mouseEntered(MouseEvent evt) {
                btn.setBackground(cor.darker());
            }
            public void mouseExited(MouseEvent evt) {
                btn.setBackground(cor);
            }
        });
        
        return btn;
    }
    
    public JLabel criarLabel(String texto, int tamanho, int estilo) {
        JLabel label = new JLabel(texto);
        label.setFont(new Font("Segoe UI", estilo, tamanho));
        label.setForeground(tema.getText());
        return label;
    }
    
    public JTextArea criarTextArea(String texto, int linhas, int colunas) {
        JTextArea textArea = new JTextArea(texto, linhas, colunas);
        textArea.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        textArea.setLineWrap(true);
        textArea.setWrapStyleWord(true);
        textArea.setBackground(tema.getCardBackground());
        textArea.setForeground(tema.getText());
        textArea.setCaretColor(tema.getText());
        textArea.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(tema.getBorder(), 2),
            BorderFactory.createEmptyBorder(5, 10, 5, 10)
        ));
        return textArea;
    }
    
    public void mostrarMensagem(Component parent, String mensagem, String titulo, Color cor) {
        JLabel label = new JLabel("<html><div style='padding:10px; color:" + 
            (tema.isEscuro() ? "white" : "black") + ";'>" + mensagem + "</div></html>");
        label.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        
        JOptionPane.showMessageDialog(
            parent,
            label,
            titulo,
            JOptionPane.PLAIN_MESSAGE
        );
    }
}

