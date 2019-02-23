

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;
import java.util.Scanner;

import processing.core.PApplet;
import processing.core.PImage;

public class DrawingMovie {

	private Movie movie;
	private PImage coverArt;
	
	public DrawingMovie(Movie m) {
		this.movie = m;
		coverArt = null;
	}
	
	public void draw(PApplet drawer, float x, float y, float width, float height) {
		if (movie != null) {
			if (coverArt != null) {
				drawer.image(coverArt, x, y,width,height);
			}
			
			
			String title = movie.getTitle();
			drawer.text(title, x, y);
		
		}
		
		drawer.stroke(0);
		drawer.noFill();
		drawer.rect(x, y, width, height);
	}
	

	public void downloadArt(PApplet drawer) {
		
		Thread downloader = new Thread(new Runnable() {

			@Override
			public void run() {
				
				
				// Find the cover art using IMDB links
				String imdbLink = movie.getIMDB();
				String url = "http://www.imdb.com/title/tt"+imdbLink+"/";
				
				Scanner scan = null;
				try {
					String fileData = "";
					
					URL imdbURL = new URL(url);
					URLConnection urlconn = imdbURL.openConnection();
					InputStream is = urlconn.getInputStream();
					scan = new Scanner(is);
					
					while(scan.hasNextLine()) {
						String line = scan.nextLine();
						fileData+=line+FileIO.lineSeparator;
					}
					
//					System.out.println(fileData);
					//look for "title-overview-widget"
					fileData = fileData.substring(fileData.indexOf("title-overview-widget"));
					String location = fileData.substring(fileData.indexOf("<img"), fileData.indexOf(">", fileData.indexOf("<img")));
					String picURL = location.substring(location.indexOf("src=\"")+5, location.indexOf("\"", location.indexOf("src=\"")+5));
					coverArt = drawer.loadImage(picURL);
					
					
				} catch (IOException e) {
					e.printStackTrace();
				} finally {
					scan.close();
				}
				
				
				
				
				
			}
			
		});
		
		downloader.start();

	}

	
}
