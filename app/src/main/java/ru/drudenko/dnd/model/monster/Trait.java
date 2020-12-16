package ru.drudenko.dnd.model.monster;

import java.io.Serializable;
import java.util.List;

public class Trait implements Serializable {

    private String name;
    private List<String> text;
    private List<String> attack;

    public Trait() {
    }

    public Trait(String name, List<String> text, List<String> attack) {
        this.name = name;
        this.text = text;
        this.attack = attack;
    }

    public String getName() {
        return name;
    }

    public List<String> getText() {
        return text;
    }

    public List<String> getAttack() {
        return attack;
    }

    @Override
    public String toString() {
        StringBuilder r = new StringBuilder();
        if (name != null) {
            r.append("<font color=#B31000>").append(name).append("</font><br>");
        }

        if (text != null) {
            for (String a : text) {
                r.append(a).append("<br>");
            }
        }

        if (attack != null) {
            for (String a : attack) {
                r.append(a).append("<br>");
            }
        }

        r.append("<br>");

        return r.toString();
    }
}
