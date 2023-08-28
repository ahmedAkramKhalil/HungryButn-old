package com.wiz.hungrybutn.chef;

import com.wiz.hungrybutn.R;

public class ComponentAnimation {
    String json;
    int type;
    boolean played = false;
    Component component;
    int category;


    public ComponentAnimation(Component component, int category) {
        this.component = component;
        this.category = category;
        this.json = getAnimation(component);


    }

    public int getCategory() {
        return category;
    }

    public void setCategory(int category) {
        this.category = category;
    }

    public Component getComponent() {
        return component;
    }

    public void setComponent(Component component) {
        this.component = component;
    }

    public String getJson() {
        return json;
    }

    public void setJson(String json) {
        this.json = json;
    }


    public boolean isPlayed() {
        return played;
    }

    public void setPlayed(boolean played) {
        this.played = played;
    }

    String getAnimation(Component component) {
        switch (component.getName_en()) {
            case "brioche bun":
                return "bun2.json";
            case "brioche bun red":
                return "bunr2.json";
            case "brioche bun yellow":
                return "buny2.json";
            case "brioche bun black":
                return "bunblack2.json";
            case "Toast":
                return "p24.json";
            case "deep fried mac&cheese":
                return "p2.json";
            case "Meat 140g":
                return "p21.json";
            case "Meat 170g":
                return "p21.json";
            case "(KFC) Deep Fried Chicken":
                return "p10.json";
            case "Caramelized Onion":
                return "onion.json";
            case "Grilled Pineapple":
                return "p12.json";
            case "Egg":
                return "egg.json";
            case "Mac&Cheese":
                return "p29.json";
            case "Deep Fried Mac&Cheese":
                return "p2.json";
            case "Deep Fried Cheese":
                return "p2.json";
            case "Cheese Sticks":
                return "p11.json";
            case "Onion Rings":
                return "p25.json";
            case "Mushrooms":
                return "p16.json";
            case "Pickles":
                return "torshi.json";
            case "Lettuce":
                return "kas.json";
            case "Chopped Onion":
                return "basal.json";
            case "Tomato":
                return "tomato.json";
            case "Jalapeños":
                return "jallopino.json";
            case "Sauce1":
                return "green.json";
            case "Sauce2":
                return "yallow.json";
            case "Sauce3":
                return "yallow.json";
            case "Sauce4":
                return "white.json";
            case "Sauce5":
                return "red.json";
            case "Chips":
                return "p28.json";
            case "Doritos":
                return "p22.json";
            default:
                return "tomato.json";
        }
    }
}
