package cafe.logic;

public class Post {

	private final int id;

	private final String text;

	private final User user;

	private final Cafe cafe;

        public Post(int id,String text,User user,Cafe cafe){
            this.id = id;
            this.text = text;
            this.user = user;
            this.cafe = cafe;
        }

    /**
     * Return post ID.
     * @return the id
     */
    public int getId() {
        return id;
    }

    /**
     * Return post text.
     * @return the text
     */
    public String getText() {
        return text;
    }

    /**
     * Return user, who submitted the post.
     * @return the user
     */
    public User getUser() {
        return user;
    }

    /**
     * Return cafe that the post is about.
     * @return the cafe
     */
    public Cafe getCafe() {
        return cafe;
    }
    
    @Override
    public String toString(){
        return ">> " + user.getSurname() + " " + user.getName() + " <<\n" + text;
    }
}
