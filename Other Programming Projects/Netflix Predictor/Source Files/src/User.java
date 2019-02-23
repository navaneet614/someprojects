import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;

public class User implements Comparable<User> {

	private int id;
	private double avgRating;
	private double avgMovieYear;
	private ArrayList<Rating> ratings;
	private ArrayList<Tag> tags;
	private ArrayList<Movie> movies;
	private ArrayList<User> similarUsers;
	private HashMap<String, Double> avgGenreRatings;
	private String favGenre, hateGenre;

	public User(int userID) {
		this.id = userID;
		avgRating = -1;
		ratings = new ArrayList<Rating>();
		tags = new ArrayList<Tag>();
		movies = new ArrayList<Movie>();
		similarUsers = new ArrayList<User>();
		favGenre = "";
		hateGenre = "";
		avgGenreRatings = new HashMap<String, Double>();
	}

	public void setup() {
		doAvgGenreRatings();
		doGenres();
		
		int count = 0;
		for(Rating r:ratings) {
			if(r.getRating()-avgRating>.5||r.getRating()>getAvgHighLowRating()[0]) {
				avgMovieYear+=r.getMovie().getYear();
				if(r.getMovie().getYear()!=0) {
					count++;
				}
			}
		}
		if(count!=0) {
			avgMovieYear/=count;
		} else {
			avgMovieYear = 0;
		}
	}

	private void doGenres() {
		double tempFav = 0, tempHate = 5;
		for (String s : avgGenreRatings.keySet()) {
			if (avgGenreRatings.get(s) > tempFav) {
				tempFav = avgGenreRatings.get(s);
				favGenre = s;
			} else if (avgGenreRatings.get(s) < tempHate) {
				tempHate = avgGenreRatings.get(s);
				hateGenre = s;
			}
		}
	}
	
	public double getAvgYear() {
		return avgMovieYear;
	}

	public String getFavGenre() {
		return favGenre;
	}

	public String getHateGenre() {
		return hateGenre;
	}

	private void doAvgGenreRatings() {
		HashMap<String, Double[]> temp = new HashMap<String, Double[]>();
		int count = 0;
		for (Rating r : ratings) {
			avgRating += r.getRating();
			count++;
			for (String s : r.getMovie().getGenres()) {
				if (temp.containsKey(s)) {
					temp.put(s, new Double[] { temp.get(s)[0] + r.getRating(), temp.get(s)[1] + 1 });
				} else {
					temp.put(s, new Double[] { r.getRating(), 1.0 });
				}
			}
		}
		for (String s : temp.keySet()) {
			avgGenreRatings.put(s, temp.get(s)[0] / temp.get(s)[1]);
		}
		avgRating /= count;
	}

	public double getAvgRating() {
		return avgRating;
	}

	public HashMap<String, Double> getAvgGenreRatings() {
		return avgGenreRatings;
	}

	public int getID() {
		return id;
	}

	public void addTag(Tag t) {
		tags.add(t);
	}

	public ArrayList<Tag> getTags() {
		return tags;
	}

	public void addRating(Rating r) {
		ratings.add(r);
	}

	public ArrayList<Rating> getRatings() {
		return ratings;
	}

	public Rating getRating(Movie m) {
		for (Rating r : ratings) {
			if (r.getMovie().equals(m)) {
				return r;
			}
		}
		return null;
	}

	public String toString() {
		return "USERID: " + id + ", NUM RATINGS: " + ratings.size();
	}

	public boolean isSimilarTo(User u, String[] genres) {
		if (u.getFavGenre().equals(favGenre) || u.getHateGenre().equals(hateGenre)||Math.abs(u.getAvgYear()-avgMovieYear)<4||Math.abs(u.getAvgRating()-avgRating)<.5) {
			for (String s : genres) {
				if(avgGenreRatings.containsKey(s)&&u.getAvgGenreRatings().containsKey(s)) {
					if(Math.abs(avgGenreRatings.get(s)-u.getAvgGenreRatings().get(s))>.5) {
						return false;
					}
				}
			}
			return true;
		}
		return false;
	}

	public ArrayList<User> getSimilarUsers() {
		return similarUsers;
	}

	public void addMovie(Movie m) {
		movies.add(m);
	}

	public ArrayList<Movie> getMovies() {
		return movies;
	}

	@Override
	public int compareTo(User u) {
		return id - u.getID();
	}
	
	public double[] getAvgHighLowRating() {
		ArrayList<Rating> sorted = new ArrayList<Rating>(ratings); 
		Collections.sort(sorted);
		double avgHighRating = sorted.get(sorted.size()*3/4).getRating();
		double avgLowRating = sorted.get(sorted.size()*1/4).getRating();
		return new double[] {avgHighRating, avgLowRating};
	}
}
