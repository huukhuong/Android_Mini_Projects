package huukhuongtran.gameduoihinhbatchu.DTO;

public class CauHoi {

    private String tenMan;
    private String dapAn;
    private String chiTietDapAn;
    private String anh;

    public CauHoi() {
    }

    public CauHoi(String tenMan, String dapAn, String chiTietDapAn, String anh) {
        this.tenMan = tenMan;
        this.dapAn = dapAn;
        this.chiTietDapAn = chiTietDapAn;
        this.anh = anh;
    }

    public String getTenMan() {
        return tenMan;
    }

    public void setTenMan(String tenMan) {
        this.tenMan = tenMan;
    }

    public String getDapAn() {
        return dapAn;
    }

    public void setDapAn(String dapAn) {
        this.dapAn = dapAn;
    }

    public String getChiTietDapAn() {
        return chiTietDapAn;
    }

    public void setChiTietDapAn(String chiTietDapAn) {
        this.chiTietDapAn = chiTietDapAn;
    }

    public String getAnh() {
        return anh;
    }

    public void setAnh(String anh) {
        this.anh = anh;
    }

    @Override
    public String toString() {
        return "CauHoi{" +
                "tenMan='" + tenMan + '\'' +
                ", dapAn='" + dapAn + '\'' +
                ", chiTietDapAn='" + chiTietDapAn + '\'' +
                ", anh='" + anh + '\'' +
                '}';
    }
}
