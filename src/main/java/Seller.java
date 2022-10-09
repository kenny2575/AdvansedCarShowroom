public class Seller {
    private int availableCars;
    private int carsSold;

    void carBuild() {
        this.availableCars++;
        System.out.println("Производитель Toyota выпустил 1 авто");
    }

    void carSell() {
        if (this.availableCars > 0) {
            this.availableCars--;
            this.carsSold++;
        }
    }

    public int getCarsSold() {
        return carsSold;
    }

    public int getAvailableCars() {
        return availableCars;
    }
}
