import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

public class FileIO {

	public static final String fileSeparator = System.getProperty("file.separator");
	public static final String lineSeparator = System.getProperty("line.separator");

	public static ArrayList<String> readFile(String fileName) {
		Scanner scan = null;
		try {
			ArrayList<String> output = new ArrayList<String>();
			FileReader reader = new FileReader(fileName);
			scan = new Scanner(reader);

			while (scan.hasNextLine()) {
				output.add(scan.nextLine());
			}

			return output;
		} catch (FileNotFoundException e) {
			e.printStackTrace();
			return null;
		} finally {
			if (scan != null)
				scan.close();
		}
	}

	public static void writeFile(String fileName, ArrayList<String> fileData) {

		FileWriter writer = null;

		try {

			writer = new FileWriter(fileName);
			for (String s : fileData) {
				writer.write(s);
				writer.write(lineSeparator);
			}

		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (writer != null) {
				try {
					writer.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}

		}
	}
}
