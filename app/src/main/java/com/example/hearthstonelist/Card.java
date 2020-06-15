package com.example.hearthstonelist;

import android.graphics.drawable.Drawable;

public class Card {
    private String name;
    private String cardText;
    private int attack;
    private int health;
    private String cardID;


    public Card(String name, String cardText, int attack, int health, String cardID) {
        this.name = name;
        this.cardText = cardText;
        this.attack = attack;
        this.health = health;
        this.cardID = cardID;

    }

    public String getCardID() {
        return cardID;
    }

    public String getName() {
        return name;
    }

    public String getCardText() {
        return cardText;
    }

    public int getAttack() {
        return attack;
    }

    public int getHealth() {
        return health;
    }




}
