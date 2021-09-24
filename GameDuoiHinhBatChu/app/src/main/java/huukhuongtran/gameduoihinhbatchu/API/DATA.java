package huukhuongtran.gameduoihinhbatchu.API;

import java.util.ArrayList;

import huukhuongtran.gameduoihinhbatchu.DTO.CauHoi;

public class DATA {

    private static DATA data;

    static {
        data = new DATA();
    }

    public static DATA getData() {
        return data;
    }

    public ArrayList<CauHoi> arrCauHoi = new ArrayList<>();

}
