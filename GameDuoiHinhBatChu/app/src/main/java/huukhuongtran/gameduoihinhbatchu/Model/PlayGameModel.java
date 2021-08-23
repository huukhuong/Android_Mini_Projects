package huukhuongtran.gameduoihinhbatchu.Model;

import java.util.ArrayList;

import huukhuongtran.gameduoihinhbatchu.API.DATA;
import huukhuongtran.gameduoihinhbatchu.DTO.CauHoi;
import huukhuongtran.gameduoihinhbatchu.DTO.NguoiDung;
import huukhuongtran.gameduoihinhbatchu.PlayGameActivity;

public class PlayGameModel {

    private PlayGameActivity playGameActivity;
    private ArrayList<CauHoi> arr;
    private int cauSo = -1;
    private NguoiDung nguoiDung;

    public PlayGameModel() {
    }

    public PlayGameModel(PlayGameActivity playGameActivity) {
        this.playGameActivity = playGameActivity;
        nguoiDung = new NguoiDung();
        layThongTin();
        cauSo = nguoiDung.getSoCau();
        taoData();
    }

    private void taoData() {
        arr = new ArrayList<>();
        arr.addAll(DATA.getData().arrCauHoi);
    }

    public CauHoi layCauHoi() {
        cauSo++;
        if (cauSo >= arr.size()) {
            cauSo = arr.size() - 1;
        }
        return arr.get(cauSo);
    }

    public void layThongTin() {
        nguoiDung.getThongTin(playGameActivity);
    }

    public void luuThongTin() {
        nguoiDung.saveThongTin(playGameActivity);
    }

    public NguoiDung getNguoiDung() {
        return this.nguoiDung;
    }

}
