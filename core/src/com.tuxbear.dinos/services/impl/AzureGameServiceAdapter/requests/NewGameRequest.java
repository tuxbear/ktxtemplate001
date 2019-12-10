package com.tuxbear.dinos.services.impl.AzureGameServiceAdapter.requests;

import java.util.*;

/**
 * Created with IntelliJ IDEA. User: tuxbear Date: 07/01/14 Time: 11:23 To change this template use File | Settings | File
 * Templates.
 */
public class NewGameRequest {

    private String username;
    private List<String> selectedFriends;
    private String board;
    private int rounds;
    private String difficulty;

    public NewGameRequest(String username, String board, List<String> players, int rounds, String difficulty) {
        this.username = username;
        this.board = board;
        this.selectedFriends = players;
        this.rounds = rounds;
        this.difficulty = difficulty;
    }

    public int getRounds() {
        return rounds;
    }

    public void setRounds(int rounds) {
        this.rounds = rounds;
    }

    public List<String> getSelectedFriends() {
        return selectedFriends;
    }

    public void setSelectedFriends(List<String> selectedFriends) {
        this.selectedFriends = selectedFriends;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }


    public String getBoard() {
        return board;
    }

    public void setBoard(String board) {
        this.board = board;
    }

    public String getDifficulty() {
        return difficulty;
    }

    public void setDifficulty(String difficulty) {
        this.difficulty = difficulty;
    }
}
