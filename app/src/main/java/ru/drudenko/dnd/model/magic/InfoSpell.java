package ru.drudenko.dnd.model.magic;

import java.io.Serializable;

public class InfoSpell implements Serializable {
    private String name;
    private String school;
    private String level;
    private String castingTime;
    private String range;
    private String components;
    private String duration;
    private String text;
    private String source;

    public InfoSpell() {
    }

    public InfoSpell(String name, String school, String level, String castingTime, String range, String components, String duration, String text, String source) {
        this.name = name;
        this.school = school;
        this.level = level;
        this.castingTime = castingTime;
        this.range = range;
        this.components = components;
        this.duration = duration;
        this.text = text;
        this.source = source;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSchool() {
        return school;
    }

    public void setSchool(String school) {
        this.school = school;
    }

    public String getLevel() {
        return level;
    }

    public void setLevel(String level) {
        this.level = level;
    }

    public String getCastingTime() {
        return castingTime;
    }

    public void setCastingTime(String castingTime) {
        this.castingTime = castingTime;
    }

    public String getRange() {
        return range;
    }

    public void setRange(String range) {
        this.range = range;
    }

    public String getComponents() {
        return components;
    }

    public void setComponents(String components) {
        this.components = components;
    }

    public String getDuration() {
        return duration;
    }

    public void setDuration(String duration) {
        this.duration = duration;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }
    @Override
    public String toString() {
        return
                "<font color=#B31000>Школа:</font> " + school + "<br>" +
                        "<font color=#B31000>Уровень:</font> " + level + "<br>" +
                        "<font color=#B31000>Время накладывания:</font> " + castingTime + "<br>" +
                        "<font color=#B31000>Дистанция:</font> " + range + "<br>" +
                        "<font color=#B31000>Компоненты:</font> " + components + "<br>" +
                        "<font color=#B31000>Длительность:</font> " + duration + "<br>" +
                        "<font color=#B31000>Описание:</font><br>" + text;
    }
}

