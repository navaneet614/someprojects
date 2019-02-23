import java.util.ArrayList;

public class MovieLensCSVTranslator {

	public Movie parseMovie(String line) {
		int id = Integer.parseInt(line.substring(0, line.indexOf(',')));
		// String[] genres =
		// line.substring(line.lastIndexOf(',')+1).split("[^a-zA-Z0-9']+");
		String[] genres = line.substring(line.lastIndexOf(',') + 1).split("\\|");
		String title = line.substring(line.indexOf(',') + 1, line.lastIndexOf(',')).replaceAll("\"\"", "~~~")
				.replaceAll("\"", "").replaceAll("~~~", "\"");
		String year = "No Year Specified";
		if (title.indexOf('(') != -1 && title.indexOf(')') != -1) {
			year = (title.substring(title.lastIndexOf('(') + 1, title.lastIndexOf(')')));
			title = title.substring(0, title.lastIndexOf('('));
		}
		return new Movie(id, title, year, genres);
	}
	
	private Movie getMovie(int movieID, ArrayList<Movie> movies) {
		for (Movie m : movies) {
			if (m.getID() == movieID) {
				return m;
			}
		}
		return null;
	}

	public void parseLinks(String line, ArrayList<Movie> movies) {
		int movieID = Integer.parseInt(line.substring(0, line.indexOf(',')));
		String imdbID = line.substring(line.indexOf(',') + 1, line.lastIndexOf(','));
		String tmdbID = line.substring(line.lastIndexOf(',') + 1);
		getMovie(movieID, movies).setLinks(imdbID, tmdbID);
	}
	
	private User getUser(int userID, ArrayList<User> users) {
		for (User u : users) {
			if (u.getID() == userID) {
				return u;
			}
		}
		return null;
	}

	public void parseRatings(String line, ArrayList<User> users, ArrayList<Movie> movies) {
		int userID = Integer.parseInt(line.substring(0, line.indexOf(',')));
		line = line.substring(line.indexOf(',') + 1);
		int movieID = Integer.parseInt(line.substring(0, line.indexOf(',')));
		line = line.substring(line.indexOf(',') + 1);
		double rating = Double.parseDouble(line.substring(0, line.indexOf(',')));
		int timeStamp = Integer.parseInt(line.substring(line.indexOf(',') + 1));
		User u = getUser(userID, users);
		if(u==null) {
			u = new User(userID);
			users.add(u);
		}
		Movie m = getMovie(movieID, movies);
		Rating r = new Rating(m, u, rating, timeStamp);
		u.addRating(r);
		m.addRating(r);
		u.addMovie(m);
	}

	public void parseTags(String line, ArrayList<User> users, ArrayList<Movie> movies) {
		int userID = Integer.parseInt(line.substring(0, line.indexOf(',')));
		line = line.substring(line.indexOf(',') + 1);
		int movieID = Integer.parseInt(line.substring(0, line.indexOf(',')));
		line = line.substring(line.indexOf(',') + 1);
		String tag = line.substring(0, line.lastIndexOf(',')).replaceAll("\"\"", "~~~").replaceAll("\"", "")
				.replaceAll("~~~", "\"");
		int timeStamp = Integer.parseInt(line.substring(line.lastIndexOf(',') + 1));
		Tag t = new Tag(tag, timeStamp);
		getMovie(movieID, movies).addTag(t);
		getUser(userID, users).addTag(t);
	}
}
