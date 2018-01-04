package cafe.logic;

public class Coffee {

	private final int id;

	private double price;

	private String name;

        public Coffee(int id, double price, String name){
            this.id = id;
            this.price = price;
            this.name = name;
        }
        
        public int getID(){
            return id;
        }
        
        public String getName(){
            return name;
        }
        
        @Override
        public String toString(){
            return name + ": " + price;
        }
}
