import java.awt.Color;

public class TemaManager {
    // Cores - Tema Claro (unico)
    private static final Color PRIMARY = new Color(99, 102, 241);
    private static final Color SECONDARY = new Color(236, 72, 153);
    private static final Color BG = new Color(249, 250, 251);
    private static final Color CARD_BG = Color.WHITE;
    private static final Color TEXT = new Color(31, 41, 55);
    private static final Color SUCCESS = new Color(16, 185, 129);
    private static final Color DANGER = new Color(239, 68, 68);
    private static final Color WARNING = new Color(245, 158, 11);
    private static final Color INFO = new Color(59, 130, 246);
    private static final Color BORDER = new Color(229, 231, 235);
    
    public boolean isEscuro() {
        return false;
    }
    
    public Color getPrimary() {
        return PRIMARY;
    }
    
    public Color getSecondary() {
        return SECONDARY;
    }
    
    public Color getBackground() {
        return BG;
    }
    
    public Color getCardBackground() {
        return CARD_BG;
    }
    
    public Color getText() {
        return TEXT;
    }
    
    public Color getSuccess() {
        return SUCCESS;
    }
    
    public Color getDanger() {
        return DANGER;
    }
    
    public Color getWarning() {
        return WARNING;
    }
    
    public Color getInfo() {
        return INFO;
    }
    
    public Color getBorder() {
        return BORDER;
    }
}

