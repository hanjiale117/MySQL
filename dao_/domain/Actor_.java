package domain;

import java.util.Date;

public class Actor_ {
    private Integer id;
    private String name;
    private String sex;
    private Date borndate;
    private String phone;

    public Actor_() {
    }

    public Actor_(Integer id, String name, String sex, Date borndate, String phone) {
        this.id = id;
        this.name = name;
        this.sex = sex;
        this.borndate = borndate;
        this.phone = phone;
    }

    public Integer getId() {
        return this.id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSex() {
        return this.sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public Date getBorndate() {
        return this.borndate;
    }

    public void setBorndate(Date borndate) {
        this.borndate = borndate;
    }

    public String getPhone() {
        return this.phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String toString() {
        return "\nActor{id=" + this.id + ", name='" + this.name + '\'' + ", sex='" + this.sex + '\'' + ", borndate=" + this.borndate + ", phone='" + this.phone + '\'' + '}';
    }
}