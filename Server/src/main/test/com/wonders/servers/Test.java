package com.wonders.servers;

import com.wonders.model.Player;
import com.wonders.servers.game.Match;
import org.junit.jupiter.api.DisplayName;

public class Test {

    @DisplayName("AGE 2 Card testing..")
    @org.junit.jupiter.api.Test
    public void card_test(){
        Room room = new Room();
        room.add_player(new Player("Player1"));
        room.add_player(new Player("Player2"));
        room.add_player(new Player("Player3"));
        room.add_player(new Player("Player4"));
        Match match = new Match(room, "0001");
        match.state = Match.State.AGE_II_CARD;

    }
}
