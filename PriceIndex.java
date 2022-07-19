package inflationAdjuster;

public class PriceIndex {
    private int year;
    private double priceIndex;

    public int getYear() {return year;}
    public double getPI() {return priceIndex;}
    
    PriceIndex(int year, double pindex) {
        this.year = year;
        this.priceIndex = pindex;
    }
}
