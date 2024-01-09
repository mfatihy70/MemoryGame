import java.util.ArrayList;
import java.util.Collections;

public class Cards {
    private final ArrayList<Card> gameCards = new ArrayList<>();

    public Cards(Integer countOfPairs){
        ArrayList<Card> baseCards = new ArrayList<>();
        baseCards.add(new Card("icons/ghost.png"));
        baseCards.add(new Card("icons/lightbulb-on.png"));
        baseCards.add(new Card("icons/train.png"));
        baseCards.add(new Card("icons/tshirt-v.png"));
        baseCards.add(new Card("icons/lighthouse.png"));
        baseCards.add(new Card("icons/ice-cream.png"));
        baseCards.add(new Card("icons/television-classic.png"));
        baseCards.add(new Card("icons/home-city.png"));
        baseCards.add(new Card("icons/dice-multiple.png"));
        baseCards.add(new Card("icons/weather-lightning-rainy.png"));

        // Adding wanted count of pairs to gameCards
        for (int i = 0; i < countOfPairs; i++) {
            Card card = baseCards.get(i);
            gameCards.add(card);
            gameCards.add(new Card(card.getIconPath())); // Add a duplicate card
        }

        Collections.shuffle(gameCards);
    }

    public ArrayList<Card> getCards() {
        return gameCards;
    }
}
