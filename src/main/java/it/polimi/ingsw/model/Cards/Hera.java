package it.polimi.ingsw.model.Cards;

public class Hera extends Card{

    public Hera(String name, String description, boolean isPlayableIn3, CardType type, CardSubType subType) {
        super(name, description, isPlayableIn3, type, subType);
    }



    @Override
    public boolean isValidVictory() {
        return super.isValidVictory();
    }

}
