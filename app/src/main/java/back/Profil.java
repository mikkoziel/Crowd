package back;

public class Profil {
    public String name;
    private String password;
    public int points;

    public Profil(String name, String password){
        this.name = name;
        this.password = password;
        this.points = 0;
    }

    public String getName() {
        return name;
    }

    public String getPassword() {
        return password;
    }

    public int getPoints() {
        return points;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void addPoints(int toAdd){
        this.points += toAdd;
    }

}
