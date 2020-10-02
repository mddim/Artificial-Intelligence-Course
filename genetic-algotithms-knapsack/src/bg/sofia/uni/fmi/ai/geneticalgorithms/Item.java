package bg.sofia.uni.fmi.ai.geneticalgorithms;

public class Item {

    private int id;
    private String name;
    private int price;
    private int value;

    public Item(int id, String name, int price, int value) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.value = value;
    }

    public static Item createItem(String line) {
        String arguments[] = line.split(" ");
        int id = Integer.parseInt(arguments[0]);
        String name = "";
        int price = 0;
        int value = 0;
        if (arguments.length > 4) {
            name = arguments[1] + " " + arguments[2];
            price = Integer.parseInt(arguments[3]);
            value = Integer.parseInt(arguments[4]);
        } else {
            name = arguments[1];
            price = Integer.parseInt(arguments[2]);
            value = Integer.parseInt(arguments[3]);
        }

        return new Item(id, name, price, value);
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public int getPrice() {
        return price;
    }

    public int getValue() {
        return value;
    }
}
