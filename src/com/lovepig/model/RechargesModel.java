package com.lovepig.model;

public class RechargesModel {
    private String time;
    private String channel;
    private String chargeMoney;
    private String surplus;

    private String name;
    private String behavior;

    private int count;

    /*
     * “name”: “青年周刊”, “behavior”:”购买/赠送/订阅”， “price”: “25”, “time”: “2010-12-12
     * 17:45”
     */
    /*
     * "recharges:[{ “time”: “2010-12-12 17:45” “channel”: “”, “money”:”10”，
     * “price”: “200”, }]
     */

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getBehavior() {
        return behavior;
    }

    public void setBehavior(String behavior) {
        this.behavior = behavior;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getChannel() {
        return channel;
    }

    public void setChannel(String channel) {
        this.channel = channel;
    }

    public String getChargeMoney() {
        return chargeMoney;
    }

    public void setChargeMoney(String chargeMoney) {
        this.chargeMoney = chargeMoney;
    }

    public String getSurplus() {
        return surplus;
    }

    public void setSurplus(String surplus) {
        this.surplus = surplus;
    }

}
