package querydsl;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
public class MyTable {

    @Id
    @GeneratedValue
    private Integer id;

    private Integer col1;
    private Integer col2;

    public Integer getId() {
        return id;
    }

    public Integer getCol1() {
        return col1;
    }

    public void setCol1(final Integer col1) {
        this.col1 = col1;
    }

    public Integer getCol2() {
        return col2;
    }

    public void setCol2(final Integer high) {
        this.col2 = high;
    }
}
