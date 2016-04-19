import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

public class Requirement2 {

	// @param args @throws FileNotFoundException when the file cannot be loaded

	public static void main(String[] args) throws IOException {

		Scanner outputType = new Scanner(System.in);

		System.out.println("Select (H)TML or (P)lain text output:");
		String input = outputType.nextLine();

		// this flag is used to identify whether we want HTML or plain text

		File resultsFile = new File("fbScores.txt");

		Scanner scan1 = new Scanner(resultsFile); // create a scanner which
													// scans from a file, in
													// this case fbScores

		String line; // stores the each line of text read from the file

		int invalidCount = 0; // will store all invalid counts

		int validCount = 0;// will store all valid counts

		int goals = 0;// will hold goals scored

		String homeTeam, awayTeam;
		String htmlText = "";

		boolean validLine;

		while (scan1.hasNext()) {

			int homescore = 0;

			int awayscore = 0;

			line = scan1.nextLine(); // scans and reads each line from the
										// text file
			line = line.trim();// trims any unnecessary white spaces from
								// the line

			String[] splitLine = line.split(":"); // array to split lines
													// (to start testing
													// process)

			validLine = true; // sets everything to true at first, if when
			// tested and they aren't, they become false.

			if (splitLine.length == 4) { // TEST 1- do all lines have 4
											// fields?
											// -if yes, passes test.

				homeTeam = splitLine[0].trim(); // trims the first two
												// fields to eliminate white
												// space
				awayTeam = splitLine[1].trim();

				if (homeTeam != null && homeTeam.isEmpty()) { // if the home
																// team
																// (first
																// field) is
																// empty, it
																// then
																// fails
																// test

					validLine = false;

				}

				if (awayTeam != null && awayTeam.isEmpty()) { // if the away
																// team(second
																// field) is
																// empty,
																// then
																// fails
																// test

					validLine = false;

				}

				try { // this tries the third and forth field to see if they
						// are integers

					homescore = Integer.parseInt(splitLine[2].trim());

					awayscore = Integer.parseInt(splitLine[3].trim());

				}

				catch (NumberFormatException e) { // 'catch' prevents the
													// loop from crashing if
													// they aren't integers,
													// and is taken as an
													// exception

					validLine = false; // if not an integer, it fails the
										// test

				}

			} else {

				validLine = false; // if any of these don't meet the
									// requirements, they are invalid

			}

			if (validLine == true) {
				if (input.equalsIgnoreCase("P")) {
					System.out.println(splitLine[0] + "[" + splitLine[2] + "]"
							+ splitLine[1] + " [" + splitLine[3] + " ]");

					validCount++;// if a line passes all the validation, it is
									// displayed and is a valid count

					goals += homescore + awayscore; // adds the two right
													// numbers to left number
													// and assigns it to the
													// left ie goals.
					// the goals are placed here, as we only want valid lines to
					// be counted

				} else {
					if (input.equalsIgnoreCase("H")) {

						htmlText = htmlText + "<tr><td>" + splitLine[0]
								+ "</td><td>" + splitLine[2] + "</td><td>"
								+ splitLine[1] + "</td><td>" + splitLine[3]
								+ "</td></tr>";

					}
				}
			} else {

				invalidCount++; // all invalid lines are counted and then
								// output below

			}

		}
		if (input.equalsIgnoreCase("P")) {
			System.out.println("\nInvalid Match Count is " + invalidCount + ", Valid Match Count is " + validCount);

			System.out.println("Total number of goals scored " + goals);

			System.out.println("\nEOF"); // Output and End Of File message.

		} else if (input.equalsIgnoreCase("H")) {
			htmlText = "<html><body><table border=3>" + htmlText + ("----------------------Results Table------------------------");
			htmlText = htmlText + "</table></body></html>";

			String htmlFile = "htmlfile2.html"; // name of the file to be opened
			FileWriter newwriter = new FileWriter(htmlFile); //creates the file
			BufferedWriter bwriter = new BufferedWriter(newwriter);
			bwriter.write(htmlText); //writes text directly to file
			bwriter.close();

			
			try {
					//opens file in web browser
				Runtime.getRuntime().exec("cmd.exe /C start " + htmlFile);

			} catch (IOException e) {

				// if the file cannot be opened, this message is displayed
				System.err.println("Error: Unable to open " + htmlFile);
			}
		}

	}
}