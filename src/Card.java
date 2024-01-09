import javax.swing.*;

public class Card {
    private final JButton card = new JButton();
    private boolean isFlipped = false;
    private final String iconPath;

    public Card(String iconPath) {
        this.iconPath = iconPath;
        card.addActionListener(e -> flip());
    }

    public JButton getButton() {
        return this.card;
    }

    public String getIconPath() {
        return this.iconPath;
    }

    public boolean isFlipped() {
        return this.isFlipped;
    }

    public void flip() {
        if (!this.isFlipped) {
            card.setIcon(new ImageIcon(iconPath));
            this.isFlipped = true;
        }
    }

    public void unflip() {
        if (this.isFlipped) {
            card.setIcon(null);
            this.isFlipped = false;
        }
    }

    public boolean doesMatch(Card other) {
        return this.iconPath.equals(other.iconPath);
    }

}
