package by.trapecia.model;

/**
 * Created by Denis on 15.12.2015.
 */
public class Rent {
    public int clientId;
    public int rentId;
    public int climbingShoes;
    public int harness;
    public int magnesia;
    public int carabine;
    public int griGri;

    public int getRentId() {
        return rentId;
    }

    public void setRentId(int rentId) {
        this.rentId = rentId;
    }

    public int getGriGri() {
        return griGri;
    }

    public void setGriGri(int griGri) {
        this.griGri = griGri;
    }

    public int getClientId() {
        return clientId;
    }

    public void setClientId(int clientId) {
        this.clientId = clientId;
    }

    public int getClimbingShoes() {
        return climbingShoes;
    }

    public void setClimbingShoes(int climbingShoes) {
        this.climbingShoes = climbingShoes;
    }

    public int getHarness() {
        return harness;
    }

    public void setHarness(int harness) {
        this.harness = harness;
    }

    public int getMagnesia() {
        return magnesia;
    }

    public void setMagnesia(int magnesia) {
        this.magnesia = magnesia;
    }

    public int getCarabine() {
        return carabine;
    }

    public void setCarabine(int carabine) {
        this.carabine = carabine;
    }
}
