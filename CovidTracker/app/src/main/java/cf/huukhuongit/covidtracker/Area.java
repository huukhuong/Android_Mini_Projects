package cf.huukhuongit.covidtracker;

public class Area {

    private String name;
    private int total;
    private int today;
    private int death;

    public Area() {
    }

    public Area(String name, int total, int today, int death) {
        this.name = name;
        this.total = total;
        this.today = today;
        this.death = death;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    public int getToday() {
        return today;
    }

    public void setToday(int today) {
        this.today = today;
    }

    public int getDeath() {
        return death;
    }

    public void setDeath(int death) {
        this.death = death;
    }

    @Override
    public String toString() {
        return "Area{" +
                "name='" + name + '\'' +
                ", total=" + total +
                ", today=" + today +
                ", death=" + death +
                '}';
    }
}
