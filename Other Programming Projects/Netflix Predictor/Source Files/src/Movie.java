import java.util.ArrayList;

public class Movie implements Comparable<Movie> {

	private int id;
	private double avgRating;
	private String title, year, imdbID, tmdbID;
	private String[] genres;
	private ArrayList<Tag> tags;
	private ArrayList<Rating> ratings;

	public Movie(int id) {
		this.id = id;
	}

	public Movie(int id, String title, String year, String[] genres) {
		this.id = id;
		this.title = title;
		this.year = year;
		this.genres = genres;
		tags = new ArrayList<Tag>();
		ratings = new ArrayList<Rating>();
		imdbID = "";
		tmdbID = "";
		avgRating = -1;
	}

	public void setup() {
		if (ratings.size() <= 0) {
			avgRating = 3;
			return;
		}
		avgRating = 0;
		for (Rating r : ratings) {
			avgRating += r.getRating();
		}
		avgRating /= ratings.size();
	}

	public double getAvgRating() {
		return avgRating;
	}

	public int getYear() {
		try {
		return Integer.parseInt(year);
		} catch(NumberFormatException e) {
			try {
				return Integer.parseInt(year.substring(0, year.indexOf('-')))+Integer.parseInt(year.substring(year.indexOf('-')+1))/2;
			} catch (NumberFormatException n) {
				return Integer.parseInt(year.substring(0, year.indexOf('-')));
			}catch (StringIndexOutOfBoundsException s) {
				return 0;
			} 
		}
	}

	public void setLinks(String imdb, String tmdb) {
		imdbID = imdb;
		tmdbID = tmdb;
	}

	public int getID() {
		return id;
	}

	public String getTitle() {
		return title;
	}

	public String getIMDB() {
		return imdbID;
	}

	public String getTMDB() {
		return tmdbID;
	}

	public String toString() {
		// return name;
		String returnString = "ID: " + id + " NAME: " + title + " YEAR: " + year + " GENRES:";
		for (String s : genres) {
			returnString += s + ", ";
		}
		returnString = returnString.substring(0, returnString.length() - 2) + " ";
		returnString += "IMDBID: " + imdbID + " TMDBID: " + tmdbID;
		return returnString;

	}

	public ArrayList<Tag> getTags() {
		return tags;
	}

	public void addTag(Tag t) {
		tags.add(t);
	}

	public ArrayList<Rating> getRatings() {
		return ratings;
	}

	public void addRating(Rating r) {
		ratings.add(r);
	}

	public String[] getGenres() {
		return genres;
	}

	@Override
	public int compareTo(Movie m) {
		// if (id > m.getID())
		// return 1;
		// else if (id < m.getID())
		// return -1;
		// else
		// return 0;
		return id - m.getID();
	}

}
