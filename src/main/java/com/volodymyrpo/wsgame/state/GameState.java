package com.volodymyrpo.wsgame.state;

import com.volodymyrpo.wsgame.entity.Player;
import lombok.Data;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

@Data
public class GameState {

    private List<Player> players = new CopyOnWriteArrayList<>();

}
