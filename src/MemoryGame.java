import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class MemoryGame extends JFrame {
    private final JPanel main = new JPanel();
    private final JPanel textPanel = new JPanel();
    private final JPanel cardPanel = new JPanel();

    PlayerSelection ps = new PlayerSelection(main);

    public final ArrayList<Player> players = ps.playerSelection();
    private final ArrayList<Card> cards = new Cards(ps.getCountOfPairs()).getCards();
    private final ArrayList<Card> flippedCards = new ArrayList<>();
    private final JLabel currentPlayerLabel = new JLabel();
    private int currentPlayerIndex = 0;

    public MemoryGame() {
        main.setLayout(new GridLayout(2, 1));
        main.add(textPanel);
        main.add(cardPanel);
        setContentPane(main);
        setTitle("Memory Game");
        setSize(500, 500);
        setVisible(true);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
    }

    // Just for removing repetitive code
    private void initLabel(JLabel label, boolean isHeader){
        if (isHeader) {label.setFont(new Font("Arial", Font.BOLD, 20));}
        label.setHorizontalAlignment(JLabel.CENTER);
        textPanel.add(label);
    }

    private void initTextPanel() {
        textPanel.setLayout(new GridLayout(players.size() + 2, 2));
        initLabel(new JLabel("Player names"), true);
        initLabel(new JLabel("Scores"), true);
        for (Player player : players) {
            initLabel(new JLabel(player.name), false);
            initLabel(new JLabel(String.valueOf(player.score)), false);
            System.out.println(player.name);
        }
        initLabel(currentPlayerLabel, false);

        try {currentPlayerLabel.setText("Current player is " + players.get(0).name);}
        catch (IndexOutOfBoundsException e) {
            // Optional error message when closing the dialog
            /*JOptionPane.showMessageDialog(this,
                    "No players were selected, quitting the game",
                    "Error", JOptionPane.ERROR_MESSAGE);*/
            System.exit(0);
        }
    }

    private void initCardPanel() {
        // Calculate the optimal number of rows for the GridLayout according to the number of cards
        int rows = cards.size() > 4 ? (int) Math.ceil(((double) cards.size() /2) / 2.5) : 2;
        cardPanel.setLayout(new GridLayout(rows, 5));

        //cardPanel.setLayout(new GridLayout(4, 5));
        for (Card gc : cards) {
            cardPanel.add(gc.getButton());
            handleCardClick(gc);
        }
    }

    private void updateScores(Player player) {
        JLabel scoreLabel = (JLabel) textPanel.getComponent(players.indexOf(player) * 2 + 3);
        scoreLabel.setText(Integer.toString(player.score));
        main.revalidate();
    }

    private void checkWinner() {
        // Check if all cards are flipped
        boolean allCardsFlipped = true;
        for (Card card : cards) {
            if (!card.isFlipped()) {
                allCardsFlipped = false;
                break;
            }
        }
        // Break out of the method if not all cards are flipped
        if (!allCardsFlipped) {
            return;
        }

        // Find the player with the highest score
        Player winner = players.get(0);
        for (int i = 1; i < players.size(); i++) {
            if (players.get(i).score > winner.score) {
                winner = players.get(i);
            }
        }

        JOptionPane.showMessageDialog(this,
                winner.name+" has won in "+ winner.tries + " tries!");
        System.exit(0);
    }

    private void handleCardClick(Card card) {
        card.getButton().addActionListener(e -> {
            Player currentPlayer = players.get(currentPlayerIndex);

            //Flip the card only if it's not already flipped and there are less than 2 flipped cards
            if (!card.isFlipped() && flippedCards.size() < 2) {
                card.flip();
                flippedCards.add(card);

                //Two cards are flipped
                if (flippedCards.size() == 2) {
                    Card firstCard = flippedCards.get(0);
                    Card secondCard = flippedCards.get(1);

                    //The two cards match, increase the current player's score and clear the flipped cards
                    if (firstCard.doesMatch(secondCard)) {
                        currentPlayer.score++;
                        updateScores(currentPlayer);
                        flippedCards.clear();
                    }

                    //The cards don't match, unflip them for 1 second and change the current player
                    else {
                        Timer timer = new Timer(1000, e1 -> {
                            firstCard.unflip();
                            secondCard.unflip();
                            flippedCards.clear();
                            currentPlayerIndex = (currentPlayerIndex + 1) % players.size();
                            currentPlayerLabel.setText("Current player is " + players.get(currentPlayerIndex).name);
                        });
                        timer.setRepeats(false);
                        timer.start();
                    }
                    currentPlayer.tries++;
                }
            }
            checkWinner();
        });
    }


    public void start() {
        initTextPanel();
        initCardPanel();
        main.revalidate();
    }
}