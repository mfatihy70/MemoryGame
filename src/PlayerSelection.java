import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class PlayerSelection extends JFrame{
    private final JPanel playerSelection = new JPanel();
    private final JPanel main;
    private final ArrayList<JTextField> playerNames = new ArrayList<>();
    private final ArrayList<Player> players = new ArrayList<>();
    private final JButton addPlayer = new JButton("Add new player");
    private final JComboBox<Integer> countOfPairs = new JComboBox<>(new Integer[]{10,8,6,4,2});


    public PlayerSelection(JPanel main) {
        this.main = main;
        playerSelection.add(new JLabel("Count of pairs: "));
        playerSelection.add(countOfPairs);
        playerSelection.add(new JLabel());
        playerNames.add(new JTextField(8));
        setContentPane(playerSelection);
    }

    public ArrayList<Player> playerSelection() {
        addPlayer.addActionListener(e -> {
            if (playerNames.size() == 4) {
                addPlayer.setEnabled(false);
                JOptionPane.showMessageDialog(playerSelection, "Maximum number of playerNames is 4",
                        "Too many playerNames", JOptionPane.ERROR_MESSAGE);
            }
            else {
                JTextField playerName = new JTextField(8);
                playerNames.add(playerName);
                playerSelection.add(playerName);
                playerSelection.revalidate();
            }
        });

        playerSelection.add(addPlayer);
        playerSelection.setLayout(new GridLayout(9, 2));
        playerSelection.add(new JLabel("Player name/s: "));
        playerSelection.add(playerNames.get(0));

        int result = JOptionPane.showConfirmDialog(main, playerSelection,
                "Please enter the playerNames", JOptionPane.OK_CANCEL_OPTION);

        if (result == JOptionPane.OK_OPTION) {
            for (JTextField playerName : playerNames) {
                players.add(new Player(playerName.getText()));
            }
        }

        else if (result == JOptionPane.CANCEL_OPTION) {
            System.exit(0);
        }

        return players;
    }

    public int getCountOfPairs() {
        return (int) countOfPairs.getSelectedItem();
    }
}