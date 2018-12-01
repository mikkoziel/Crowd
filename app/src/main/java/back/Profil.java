package back;

public class Profil {
    public String name;
    private int ID;
    public int points;

    public Profil(int ID, String name, String password){
        this.ID = ID;
        this.name = name;
        this.points = 0;
    }

    public String getName() {
        return name;
    }

    public int getID() {
        return ID;
    }

    public int getPoints() {
        return points;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public void addPoints(int toAdd){
        this.points += toAdd;
    }

}
