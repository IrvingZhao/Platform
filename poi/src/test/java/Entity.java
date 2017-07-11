import cn.irving.zhao.util.poi.annotation.Cell;
import cn.irving.zhao.util.poi.annotation.MergedRegion;
import cn.irving.zhao.util.poi.annotation.Repeatable;
import cn.irving.zhao.util.poi.annotation.Sheet;
import cn.irving.zhao.util.poi.enums.Direction;

import java.util.List;

/**
 *
 */
@Sheet(name = "sheetName")
public class Entity {

    @Cell(row = 1, col = 1)
    private String s1;

    @Cell(row = 2, col = 2)
    @Repeatable
    private List<String> listStr;

    @Cell(row = 0, col = 0)
    @Repeatable(direction = Direction.VERTICALLY)
    private List<String> vListStr;

    @MergedRegion(start = @Cell(row = 3, col = 3), end = @Cell(row = 5, col = 5))
    private String region;

    @MergedRegion(start = @Cell(row = 6, col = 1), end = @Cell(row = 6, col = 2))
    @Repeatable
    private List<String> mergeds;

    private Entity entity;

    public String getS1() {
        return s1;
    }

    public void setS1(String s1) {
        this.s1 = s1;
    }

    public List<String> getListStr() {
        return listStr;
    }

    public void setListStr(List<String> listStr) {
        this.listStr = listStr;
    }

    public String getRegion() {
        return region;
    }

    public void setRegion(String region) {
        this.region = region;
    }

    public List<String> getMergeds() {
        return mergeds;
    }

    public void setMergeds(List<String> mergeds) {
        this.mergeds = mergeds;
    }

    public List<String> getvListStr() {
        return vListStr;
    }

    public void setvListStr(List<String> vListStr) {
        this.vListStr = vListStr;
    }
}
