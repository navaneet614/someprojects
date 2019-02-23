import java.util.ArrayList;
import java.util.Collections;

public class Tester {
	
	public static final String moviesFile = "ml-latest-small" + FileIO.fileSeparator + "movies.csv";
	public static final String linksFile = "ml-latest-small" + FileIO.fileSeparator + "links.csv";
	public static final String tagsFile = "ml-latest-small" + FileIO.fileSeparator + "tags.csv";
	public static final String baseFile = "ml-latest-small" + FileIO.fileSeparator + "ratings.csv";

	public static void main(String[] args) {
		NetFlixPredictor tester = new NetFlixPredictor(moviesFile, baseFile, tagsFile, linksFile);
		for(User u:tester.getUsers()) {
			int r = tester.recommendMovie(u.getID());
//			System.out.println(r);
			System.out.println("User #:"+u.getID()+"Rating: "+tester.guessRating(u.getID(), r)+" Name: "+tester.getMovies().get(Collections.binarySearch(tester.getMovies(), new Movie(r))).getTitle());
		}
	}
}
