

public class Rating implements Comparable<Rating>{
	
	private Movie movie;
	private User user;
	private int timeStamp;
	private double rating;
	
	public Rating(Movie movie, User user, double rating, int timeStamp){
		this.movie = movie;
		this.user = user;
		this.rating = rating;
		this.timeStamp = timeStamp;
	}
	
	public Movie getMovie() {
		return movie;
	}
	
	public int getTimeStamp() {
		return timeStamp;
	}
	
	public double getRating() {
		return rating;
	}
	
	public User getUser() {
		return user;
	}
	
	public String toString() {
		return "MOVIE: ("+movie+"), RATING: "+rating+", TIME: "+timeStamp;
	}

	@Override
	public int compareTo(Rating r) {
		if(rating>r.getRating()) {
			return 1;
		} else if(rating<r.getRating()) {
			return -1;
		} else {
			return 0;
		}
	}
}
