package cafe.logic;

import java.util.Date;

public class SpecialOffer {

	private int id;

	private Date startDate;

	private Date endDate;

	private String name;

	private String description;

        public SpecialOffer(int id,Date start, Date end, String name, String description){
            this.id = id;
            startDate = start;
            endDate = end;
            this.name = name;
            this.description = description;
        }
        
        public String getName(){
            return name;
        }
        
        @Override
        public String toString(){
            return name + ": " + startDate + "-" + endDate + "\n" + description;
        }
}
