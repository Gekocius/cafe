package cafe.logic;

public class Coffee {

	private int id;

	private double price;

	private String name;

        public Coffee(int id, double price, String name){
            this.id = id;
            this.price = price;
            this.name = name;
        }
        
        public String getName(){
            return name;
        }
        
        @Override
        public String toString(){
            return name + ": " + price;
        }
}
