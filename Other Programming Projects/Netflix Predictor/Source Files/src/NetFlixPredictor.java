import java.util.ArrayList;
import java.util.Collections;

public class NetFlixPredictor {

	// Add fields to represent your database.

	private ArrayList<Movie> movies;
	private ArrayList<User> users;

	/**
	 * 
	 * Use the file names to read all data into some local structures.
	 * 
	 * @param movieFilePath
	 *            The full path to the movies database.
	 * @param ratingFilePath
	 *            The full path to the ratings database.
	 * @param tagFilePath
	 *            The full path to the tags database.
	 * @param linkFilePath
	 *            The full path to the links database.
	 */
	public NetFlixPredictor(String movieFilePath, String ratingFilePath, String tagFilePath, String linkFilePath) {
		ArrayList<String> movieStrings = FileIO.readFile(movieFilePath);
		movies = new ArrayList<Movie>();
		MovieLensCSVTranslator translator = new MovieLensCSVTranslator();
		for (int i = 1; i < movieStrings.size(); i++) {
			movies.add(translator.parseMovie(movieStrings.get(i)));
		}

		ArrayList<String> links = FileIO.readFile(linkFilePath);
		for (int i = 1; i < movies.size(); i++) {
			translator.parseLinks(links.get(i), movies);
		}

		ArrayList<String> ratings = FileIO.readFile(ratingFilePath);
		users = new ArrayList<User>();
		for (int i = 1; i < ratings.size(); i++) {
			translator.parseRatings(ratings.get(i), users, movies);
		}

		ArrayList<String> tags = FileIO.readFile(tagFilePath);
		for (int i = 1; i < tags.size(); i++) {
			translator.parseTags(tags.get(i), users, movies);
		}

		Collections.sort(movies);
		Collections.sort(users);

		for (int i = 0; i < users.size(); i++) {
			users.get(i).setup();
			// System.out.println("user# "+i+" done.");
		}

		for (Movie m : movies) {
			m.setup();
		}
	}

	/**
	 * If userNumber has rated movieNumber, return the rating. Otherwise, return -1.
	 * 
	 * @param userNumber
	 *            The ID of the user.
	 * @param movieNumber
	 *            The ID of the movie.
	 * @return The rating that userNumber gave movieNumber, or -1 if the user does
	 *         not exist in the database, the movie does not exist, or the movie has
	 *         not been rated by this user.
	 */
	public double getRating(int userID, int movieID) {
		int p = Collections.binarySearch(users, new User(userID));
		if (p < 0) {
			return -1;
		}
		User u = users.get(p);

		for (Rating r : u.getRatings()) {
			if (r.getMovie().getID() == movieID) {
				return r.getRating();
			}
		}
		// for (Movie m : movies) {
		// if (m.getID() == movieID) {
		// for (Rating r : m.getRatings()) {
		// if (r.getUser().getID() == userID) {
		// return r.getRating();
		// }
		// }
		// return -1;
		// }
		// }
		return -1;
	}

	/**
	 * If userNumber has rated movieNumber, return the rating. Otherwise, use other
	 * available data to guess what this user would rate the movie.
	 * 
	 * @param userNumber
	 *            The ID of the user.
	 * @param movieNumber
	 *            The ID of the movie.
	 * @return The rating that userNumber gave movieNumber, or the best guess if the
	 *         movie has not been rated by this user.
	 * @pre A user with id userID and a movie with id movieID exist in the database.
	 */
	public double guessRating(int userID, int movieID) {
		double rating = getRating(userID, movieID);
		if (rating == -1) {
			Movie movie = movies.get(Collections.binarySearch(movies, new Movie(movieID)));
			User user = users.get(Collections.binarySearch(users, new User(userID)));
			double tempRating = 0;
			int count = 0;
			for (String s : movie.getGenres()) {
				if (user.getAvgGenreRatings().containsKey(s)) {
					tempRating += user.getAvgGenreRatings().get(s);
					count++;
				}
			}
			double avgGenreRating = -1;
			if (count != 0) {
				avgGenreRating = tempRating / count;
			}

			tempRating = 0;
			count = 0;
			for (User u : users) {
				if (u.getMovies().contains(movie) && u.isSimilarTo(user, movie.getGenres())) {
					tempRating += u.getRating(movie).getRating();
					count++;
				}
			}
			double avgSimRating = -1;
			if (count != 0) {
				avgSimRating = tempRating / count;
			}

			if (avgGenreRating == -1 && avgSimRating == -1) {
				rating = user.getAvgRating() * .5 + movie.getAvgRating() * .5;
			} else if (avgSimRating == -1) {
				rating = movie.getAvgRating() * .2 + user.getAvgRating() * .0 + avgGenreRating * .8;
			} else if (avgGenreRating == -1) {
				rating = movie.getAvgRating() * .4 + user.getAvgRating() * .3 + avgSimRating * .3;
			} else {
				rating = movie.getAvgRating() * .3 + user.getAvgRating() * .2 + avgSimRating * .2 + avgGenreRating * .3;
			}

			boolean favGenre = false, hateGenre = false;
			for (String s : movie.getGenres()) {
				if (s.equals(user.getFavGenre())) {
					favGenre = true;
					break;
				} else if (s.equals(user.getHateGenre())) {
					hateGenre = true;
					break;
				}
			}
			if (favGenre) {
				// rating += 0.15;
				rating = rating * .9 + user.getAvgHighLowRating()[0] * .1;
			}
			if (hateGenre) {
				// rating -= 0.13;
				rating = rating * .9 + user.getAvgHighLowRating()[1] * .1;
			}
			if (movie.getYear() != 0 && Math.abs(movie.getYear() - user.getAvgYear()) < 5) {
				rating += .05;
				// rating = rating*.95+user.getAvgHighLowRating()[0]*.05;
			}

		}
		// System.out.println(rating);
		return rating;
	}

	/**
	 * Recommend a movie that you think this user would enjoy (but they have not
	 * currently rated it).
	 * 
	 * @param userNumber
	 *            The ID of the user.
	 * @return The ID of a movie that data suggests this user would rate highly (but
	 *         they haven't rated it currently).
	 * @pre A user with id userID exists in the database.
	 */
	public int recommendMovie(int userID) {
		User user = users.get(Collections.binarySearch(users, new User(userID)));
		double target = 4.9;
		while (true) {
			for (Movie m : movies) {
				if (!user.getMovies().contains(m) && guessRating(userID, m.getID()) > target) {
//					System.out.println(guessRating(userID, m.getID()));
					return m.getID();
				}
			}
			target = -.1;
		}
	}

	public ArrayList<Movie> getMovies() {
		return movies;
	}

	public ArrayList<User> getUsers() {
		return users;
	}

}
