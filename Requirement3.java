/*
 * Read each line of match results from file, processing each result per team.
 * Store each teams data in a separate object and hold all the objects in a list
 * 
 */
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

public class Requirement3 {

	public enum RESULTS {
		WIN, LOSE, DRAW, UNDEFINED // used to determine result of game,
									// variables given predefined constants
	};

	static ArrayList<Team> teams = new ArrayList<Team>(); // array list called
															// teams which takes
															// values from the
															// 'Team' class.

	public static void main(String[] args) {
		String htmlText = ""; // used for string builder when writing html text.

		Scanner input = new Scanner(System.in);
		System.out.print("Select (H)TML or (P)lain text output: ");
		String outputFormat = input.nextLine().trim();
		System.out
				.print("Select a specific team (<enter> to show all teams): ");
		String teamInput = input.nextLine().trim();
		if (teamInput.equals(""))
			teamInput = "ALL";// ALL

		// Get team name input
		// if team name is ENTER then set team name = "ALL"
		// Create a list to hold the team objects

		if (!teamInput.equals("ALL")) {
			processResults();
			try {
				printResults(teamInput, outputFormat);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		} else {// print out all
			try {
				requirementTwo(outputFormat);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

	private static void requirementTwo(String outputFormat) throws IOException {

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
				if (outputFormat.equalsIgnoreCase("P")) {
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
					if (outputFormat.equalsIgnoreCase("H")) {

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
		if (outputFormat.equalsIgnoreCase("P")) {
			System.out.println("\nInvalid Match Count is " + invalidCount
					+ ", Valid Match Count is " + validCount);

			System.out.println("Total number of goals scored " + goals);

			System.out.println("\nEOF"); // Output and End Of File message.

		} else if (outputFormat.equalsIgnoreCase("H")) {
			htmlText = "<html><body><table border=3>"
					+ htmlText
					+ ("----------------------Results Table------------------------");
			htmlText = htmlText + "</table></body></html>";

			String htmlFile = "htmlfile2.html"; // name of the file to be opened
			FileWriter newwriter = new FileWriter(htmlFile);// creates the file
			BufferedWriter bwriter = new BufferedWriter(newwriter);
			bwriter.write(htmlText);// writes text directly to the file
			bwriter.close();

			// Execute a command to the OS.
			try {
				// opens file in web browser
				Runtime.getRuntime().exec("cmd.exe /C start " + htmlFile);

			} catch (IOException e) {

				// if the file cannot be opened, this message is displayed
				System.err.println("Error: Unable to open " + htmlFile);
			}
		}

	}

	private static void printResults(String teamInput, String outputFormat)
			throws IOException {
		String htmlText = "";
		boolean teamExists = false; // boolean set to false
		for (Team t : teams) { // Team is the class, small 't' is a variable
			// (object pulled out each time loop until
			// matching is found), teams is arraylist (list
			// of teams)
			if (outputFormat.equalsIgnoreCase("P")) {
				if (t.getName().equalsIgnoreCase(teamInput)
						|| teamInput.equals("ALL")) {
					System.out.println("");
					System.out.println(t.getName());
					System.out.println("--------------------");
					System.out.println("Games Played: " + t.getGamesPlayed());
					System.out.println("Games Won: " + t.getGamesWon());
					System.out.println("Games Drawn: " + t.getGamesDrawn());
					System.out.println("Games Lost: " + t.getGamesLost());
					System.out.println("Goals For: " + t.getGoalsFor());
					System.out.println("Goals Against: " + t.getGoalsAgainst());
					teamExists = true; // found the team

					if (!teamInput.equals("ALL")) {
						break; // if team if found, break out of the loop
					}
				}
			} else if (outputFormat.equalsIgnoreCase("H")) {// else if HTML
				if (t.getName().equalsIgnoreCase(teamInput)
						|| teamInput.equals("ALL")) {
					System.out.println(t.getName());
					// repetitive code, could use another method called
					// 'FormatText'

					htmlText = htmlText + "<tr><td colspan=\"2\"><b>      "
							+ t.getName() + "</b></td></tr>";
					htmlText = htmlText + "";
					htmlText = htmlText + "<tr><td>Games Played: </td><td>"
							+ t.getGamesPlayed() + "</td></tr>";
					htmlText = htmlText + "<tr><td>Games Won:</td><td> "
							+ t.getGamesWon() + "</td></tr>";
					htmlText = htmlText + "<tr><td>Games Drawn:</td><td> "
							+ t.getGamesDrawn() + "</td></tr>";
					htmlText = htmlText + "<tr><td>Games Lost:</td><td> "
							+ t.getGamesLost() + "</td></tr>";
					htmlText = htmlText + "<tr><td>Goals For:</td><td> "
							+ t.getGoalsFor() + "</td></tr>";
					htmlText = htmlText + "<tr><td>Goals Against:</td><td> "
							+ t.getGoalsAgainst() + "</td></tr>";

					teamExists = true; // found the team

					// }

					if (!teamInput.equals("ALL")) {
						break; // if team if found, break out of the loop
					}
				}

			}

		} // end for loop

		if (outputFormat.equalsIgnoreCase("H")) {
			htmlText = "<html><body>" + "<table border=3 style=\"width:15%\">"
					+ htmlText + ("----------Results Table-----------");
			htmlText = htmlText + "</table></body></html><br />";

			String htmlFile = "htmlfile.html"; // name of the file to be opened
			FileWriter newwriter = new FileWriter(htmlFile);// creates the file
			BufferedWriter bwriter = new BufferedWriter(newwriter);
			bwriter.write(htmlText);// writes text directly to the file
			bwriter.close();

			// Execute a command to the OS.
			try {
				// opens in web browser
				Runtime.getRuntime().exec("cmd.exe /C start " + htmlFile);

			} catch (IOException e) {

				// if the file cannot be opened, this message is
				// displayed
				System.err.println("Error: Unable to open " + htmlFile);
			}
		}

		if (teamExists == false && !teamInput.equals("ALL")) {
			System.out.println("No such team");
		}
	}

	private static void processResults() {

		File resultsFile = new File("fbScores.txt");

		// create a scanner which scans from a file, in this case fbScores
		Scanner scan1 = null;
		try {
			scan1 = new Scanner(resultsFile);
		} catch (FileNotFoundException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}

		String line; // stores the each line of text read from the file

		int invalidCount = 0; // will store all invalid counts

		int validCount = 0;// will store all valid counts

		int goals = 0;// will hold goals scored

		String homeTeam = "";
		String awayTeam = "";

		boolean validLine;

		while (scan1.hasNext()) {

			int homeScore = 0;

			int awayScore = 0;

			line = scan1.nextLine(); // scans and reads each line from the text
			// file
			line = line.trim();// trims any unnecessary white spaces from
			// the line

			String[] splitLine = line.split(":"); // array to split lines (to
			// start testing process)

			validLine = true; // sets everything to true at first, if when
			// tested and they aren't, they become false.

			if (splitLine.length == 4) { // TEST 1- do all lines have 4
				// fields?
				// -if yes, passes test.

				homeTeam = splitLine[0].trim(); // trims the first two
				// fields to eliminate white space
				awayTeam = splitLine[1].trim();

				if (homeTeam != null && homeTeam.isEmpty()) {
					// if the home team (first field) is empty, it then fails
					// test
					validLine = false;
				}

				if (awayTeam != null && awayTeam.isEmpty()) {
					// if the away team(second field) is empty, then fails test
					validLine = false;
				}

				try { // this tries the third and forth field to see if they
						// are integers

					homeScore = Integer.parseInt(splitLine[2].trim());

					awayScore = Integer.parseInt(splitLine[3].trim());

				}

				catch (NumberFormatException e) { // 'catch' prevents the
					// loop from crashing if they aren't integers, and is taken
					// as an exception
					validLine = false; // if not an integer, it fails the test
				}

			} else {

				validLine = false; // if any of these don't meet the
				// requirements, they are invalid

			}

			if (validLine == true) {
				// We have a valid match result so process it
				// Use enum here to define the results and process them
				RESULTS homeResult = RESULTS.UNDEFINED; //start off as undefined
				RESULTS awayResult = RESULTS.UNDEFINED;
				;

				validCount++;// if a line passes all the validation, it is
				// displayed and is a valid count

				if (homeScore > awayScore) { //now we're defining the results
					homeResult = RESULTS.WIN;
					awayResult = RESULTS.LOSE;
				} else if (homeScore < awayScore) {
					homeResult = RESULTS.LOSE;
					awayResult = RESULTS.WIN;
				} else if (homeScore == awayScore) {
					homeResult = RESULTS.DRAW;
					awayResult = RESULTS.DRAW;
				}
				processTeam(homeTeam, homeScore, awayScore, homeResult);
				processTeam(awayTeam, awayScore, homeScore, awayResult);
			} else {
				invalidCount++; // all invalid lines are counted and then output
				// below
			}

		}

	}

	private static void processTeam(String teamName, int goalsFor,
			int goalsAgainst, RESULTS result) {// private- no other class can
												// access this (don't need to
												// use it)
		// void- no value returned by method
		boolean teamExists = false;
		// find object for this team in arraylist

		for (Team t : teams) { // Team is the class, small 't' is a variable
			// (object pulled out each time loop until matching is found)
			// teams is arraylist
			if (t.getName().equalsIgnoreCase(teamName)) {// update results if
															// found
				updateResults(goalsFor, goalsAgainst, result, t);
				teamExists = true; // found the team

				break; // if team if found, break out of the loop
			}
		}
		if (teamExists == false) {// if team isn't found, add a new team object
									// to the array list
			Team newTeam = new Team(teamName);
			// create new Team object and assign to variable
			// 'newTeam' - calls constructor with teamName

			teams.add(newTeam);
			updateResults(goalsFor, goalsAgainst, result, newTeam);// updating
			// updates the teams data to the object on arraylist
		}

	}

	private static void updateResults(int goalsFor, int goalsAgainst,
			RESULTS result, Team t) {
		t.setGoalsFor(t.getGoalsFor() + goalsFor);
		// goalsFor taken from result and is totalled using getGoalsFor
		t.setGoalsAgainst(t.getGoalsAgainst() + goalsAgainst);
		
		t.setGamesPlayed(t.getGamesPlayed() + 1);// we don't need the argument
		// 'gamesPlayed' as know
		// already a game has been
		// played, +1 to add to
		// total.

		switch (result) {
		case WIN:
			t.setGamesWon(t.getGamesWon() + 1);// cannot get number (i.e. like
			break; // goals) so +1 to add to total.

		case LOSE:
			t.setGamesLost(t.getGamesLost() + 1);
			break;

		case DRAW:
			t.setGamesDrawn(t.getGamesDrawn() + 1);
			break;
		}
	}
}
