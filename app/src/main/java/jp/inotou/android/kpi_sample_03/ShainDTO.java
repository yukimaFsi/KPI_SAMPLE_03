package jp.inotou.android.kpi_sample_03;

import java.io.Serializable;

public class ShainDTO implements Serializable{

    private String num;
    private String name;

    public ShainDTO(){
        this.num="";
        this.name="";
    }
    public void setNum(String num){this.num=num;}
    public void setName(String name){this.name=name;}

    public String getNum(){return this.num;}
    public String getName(){return this.name;}
}
