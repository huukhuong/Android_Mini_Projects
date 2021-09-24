package cf.huukhuongit.covidtracker;

public class DataCovid {

    private int total;
    private int active;
    private int recover;
    private int death;

    public DataCovid() {
    }

    public DataCovid(int total, int active, int recover, int death) {
        this.total = total;
        this.active = active;
        this.recover = recover;
        this.death = death;
    }

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    public int getActive() {
        return active;
    }

    public void setActive(int active) {
        this.active = active;
    }

    public int getRecover() {
        return recover;
    }

    public void setRecover(int recover) {
        this.recover = recover;
    }

    public int getDeath() {
        return death;
    }

    public void setDeath(int death) {
        this.death = death;
    }

    @Override
    public String toString() {
        return "DataCovid{" +
                "total=" + total +
                ", active=" + active +
                ", recover=" + recover +
                ", death=" + death +
                '}';
    }
}
