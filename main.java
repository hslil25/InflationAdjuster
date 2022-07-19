package inflationAdjuster;

import java.util.ArrayList;
import java.util.Scanner;

public class main {
    public static int currency;
    public static char cur;
    public static Scanner in;
    public static ArrayList<PriceIndex> cpiData = new ArrayList<>();
    public static ArrayList<AmountData> data = new ArrayList<>();

    public static void selectCur() throws IllegalArgumentException {
        System.out.println("1-USD\n2-TRY\n");
        currency = in.nextInt();
        in.nextLine();
        if (!(currency == 1 || currency == 2)) {
            throw new IllegalArgumentException();
        }
    }

    private static double yearToCPI(int year) {
        for (PriceIndex cpi : cpiData) {
            if (cpi.getYear() == year) {
                return cpi.getPI();
            }
        }
        return 100;
    }

    public static void calculate() {
        System.out.println("Select a year to calculate: ");
        int baseYear = yearFinder(in.nextInt());
        double cpi = yearToCPI(baseYear);
        double amount = 0;
        for (int i = 0; i < data.size(); i++) {
            amount = data.get(i).amount / yearToCPI(data.get(i).year);

        }
        if ((currency == 2 && baseYear > 2004)) {
            System.out.println(String.format("It is equal to %.2f %c in the year %d", amount * cpi/1000000, cur, baseYear));
        } else
        System.out.println(String.format("It is equal to %.2f %c in the year %d", amount * cpi, cur, baseYear));
    }

    public static void addCPIData() {
        double cpi;
        int year;
        System.out.println("Enter the CPI");
        amount = in.nextDouble();
        System.out.println("Enter the year");
        year = in.nextInt();
        if (yearFinder(year) == year) {
            System.out.println("Invalid input!");
            return;
        }
        cpiData.add(new PriceIndex(year, cpi));
    }

    public static void addData() {
        double amount;
        int year;
        System.out.println("Enter the amount");
        amount = in.nextDouble();
        System.out.println("Enter the year");
        year = yearFinder(in.nextInt());
        if (currency == 2 && year > 2004) {
            data.add(new AmountData(amount * 1000000, year));
        } else
            data.add(new AmountData(amount, year));
    }

    public static void deleteData() {
        double amount;
        int year;
        System.out.println("Enter the amount");
        amount = in.nextDouble();
        System.out.println("Enter the year");
        year = yearFinder(in.nextInt());
        if (currency == 2 && year > 2004) {
            data.add(new AmountData(-amount * 1000000, year));
        } else
            data.add(new AmountData(-amount, year));
    }

    public static int yearFinder(int input) {
        int best = 0;
        int dif = Integer.MAX_VALUE;
        for (int i = 0; i < cpiData.size(); i++) {
            if (Math.abs(cpiData.get(i).getYear() - input) < dif) {
                dif = Math.abs(cpiData.get(i).getYear() - input);
                best = cpiData.get(i).getYear();
            }
        }
        return best;

    }

    public static void initialize() {
        if (currency == 1) {
            cpiData.add(new PriceIndex(1920, 20));
            cpiData.add(new PriceIndex(1930, 16.7));
            cpiData.add(new PriceIndex(1940, 14));
            cpiData.add(new PriceIndex(1950, 24.1));
            cpiData.add(new PriceIndex(1960, 29.6));
            cpiData.add(new PriceIndex(1970, 38.8));
            cpiData.add(new PriceIndex(1980, 82.4));
            cpiData.add(new PriceIndex(1990, 130.7));
            cpiData.add(new PriceIndex(2000, 172.2));
            cpiData.add(new PriceIndex(2010, 218.1));
            cpiData.add(new PriceIndex(2020, 258.8));
            cpiData.add(new PriceIndex(2022, 292.6));
        }

        if (currency == 2) {
            cpiData.add(new PriceIndex(1950, 32));
            cpiData.add(new PriceIndex(1960, 93));
            cpiData.add(new PriceIndex(1970, 145));
            cpiData.add(new PriceIndex(1980, 2582));
            cpiData.add(new PriceIndex(1990, 82666));
            cpiData.add(new PriceIndex(2000, 15478184));
            cpiData.add(new PriceIndex(2010, 74982332));
            cpiData.add(new PriceIndex(2020, 197298743));
            cpiData.add(new PriceIndex(2022, 41417319511.0));
        }
    }

    public static void showHistory(char currency) {
        for (AmountData operation : data) {
            System.out.println(operation.amount + " " + currency + " from " + operation.year + '.');
        }
    }

    public static void main(String[] args) {
        in = new Scanner(System.in);
        System.out.println(
                "\nWelcome to inflation adjuster!\nIndex for year 1950 is 24.1 in US and 32 in Turkish inflation.");
        System.out.println("-------------------------------------------------------------------------");
        try {
            selectCur();
        } catch (IllegalArgumentException e) {
            System.out.println("\nInvalid choice!\nSetting USD by default.\n");
            currency = 1;
        }
        int choice;
        if (currency == 1) {
            cur = '$';
        } else
            cur = '₺';
        initialize();
        while (true) {
            System.out.println("\nCurrency: " + cur);
            System.out.println(
                    "Select your operation!\n1-Add \n2-Delete\n3-History\n4-Add CPI Data\n5-Calculate\n6-Exit");
            System.out.print("Choice: ");
            choice = in.nextInt();
            System.out.println();
            switch (choice) {
                case 1:
                    addData();
                    break;
                case 2:
                    deleteData();
                    break;
                case 3:
                    showHistory(cur);
                    break;
                case 4:
                    addCPIData();
                    break;
                case 5:
                    calculate();
                    break;
                case 6:
                    System.out.println("\nGoodbye!");
                    return;
            }
        }

    }
}
