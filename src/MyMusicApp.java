import java.util.*;
import org.jfugue.*;

public class MyMusicApp {

	private static Random rng = new Random();
	private static String[] octaveChordsH = { "Ch+Eh+Gh", "Dh+Fh+Ah",
			"Eh+Gh+Bh", "Fh+Ah+Ch", "Gh+Bh+Dh", "Ah+Ch+Eh", "Bh+Dh+Fh" };

	private static String[] octaveChordsW = { "Cw+Ew+Gw", "Dw+Fw+Aw",
			"Ew+Gw+Bw", "Fw+Aw+Cw", "Gw+Bw+Dw", "Aw+Cw+Ew", "Bw+Dw+Fw" };

	private static String[] octave = { "C6wa40d10", "D6wa40d10", "E6wa40d10",
			"F5wa40d10", "G5wa40d10", "A5wa40d10", "B5wa40d10" };

	// Chords in Roman
	private static String[] I = { "C", "E", "G" };
	private static String[] II = { "D", "F", "A" };
	private static String[] III = { "E", "G", "B" };
	private static String[] IV = { "F", "A", "C" };
	private static String[] V = { "G", "B", "D" };
	private static String[] VI = { "A", "C", "E" };
	private static String[] VII = { "B", "D", "F" };

	private static String[][] romanChords = { I, II, III, IV, V, VI, VII };
	private static String[] rChords = { "I", "II", "III", "IV", "V", "VI",
			"VII" };

	// Harmonizing notes for each note
	// private static String[] note_C = {"C", "E", "G", "F", "A"};
	// private static String[] note_D = {"D", "F", "A", "B", "G"};
	// private static String[] note_E = {"E", "G", "B", "A", "C"};
	// private static String[] note_F = {"F", "A", "C", "B", "D"};
	// private static String[] note_G = {"G", "B", "D", "C", "E"};
	// private static String[] note_A = {"A", "C", "E", "D", "F"};
	// private static String[] note_B = {"B", "D", "F", "E", "G"};

	// The Matrix
	private static String[][] note_C = { I, IV, VI };
	private static String[][] note_D = { II, V, VII };
	private static String[][] note_E = { III, VI, I };
	private static String[][] note_F = { IV, VII, II };
	private static String[][] note_G = { V, I, III };
	private static String[][] note_A = { VI, II, IV };
	private static String[][] note_B = { VII, III, V };

	private static int SNAP_HOME = 5; // steps to snap back home
	private static int GOING_AWAY = 12; // steps to get to "away"

	public static void main(String[] args) {
		Player player = new Player();
		// Pattern pattern = new
		// Pattern("E5majw A5s C6s B5s E5s B5s D6s C6i E6i G#5i E6i | A5s E5s A5s C6s B5s E5s B5s D6s C6i A5i Ri");
		// player.play(pattern);
		// player.play(pattern);

		new GUI();
		int[] homeAndAway = homeAndAway();
		ArrayList<String> journey1 = goingAway(GOING_AWAY, 0);
		ArrayList<String> journey2 = comingHome(SNAP_HOME, 0, journey1);

		// String n = compose(journey1, journey2);
		// ArrayList<String> t1 = composeArr("C6h E6h D6h B5h C6h C6h D6h G5h");
		// ArrayList<String> v1 = composeArr(n);
		// ArrayList<String> v2 = voice2(v1);
		// ArrayList<String> v3 = voice3(v1, v2, 4);
		// ArrayList<String> v4 = voice4(v1, v2, v3);
		// System.out.println(v1);
		// System.out.println(v2);
		// System.out.println(v3);
		// System.out.println(v4);
		// Pattern pat2 = new Pattern(combine(v1, v2, v3, v4));
		// player.play(pat2);

	}

	/** randomly pick the "home" and "away" notes and store in an int array */
	public static int[] homeAndAway() {
		int[] hA = new int[2];

		int home = rng.nextInt(7);
		int away;
		int k = rng.nextInt(7);
		away = k;
		while (k == home) {
			k = rng.nextInt(7);
			away = k;
		}

		hA[0] = home;
		hA[1] = away;

		return hA;
	}

	/** pick a random note only distance 1 or 2 away from the given note n */
	public static String nextNote(int n, String[] keys) {
		String note;
		int k = 0;
		switch (n) {
		case 0:
			k = 2 + rng.nextInt(4);
			break;
		case 1:
			k = 3 + rng.nextInt(4);
			break;
		case 2:
			k = 4 + rng.nextInt(4);
			break;
		case 3:
			k = 5 + rng.nextInt(4);
			break;
		case 4:
			k = 6 + rng.nextInt(4);
			break;
		case 5:
			k = 7 + rng.nextInt(4);
			break;
		case 6:
			k = 1 + rng.nextInt(4);
			break;
		default:
			break;
		}

		note = fetchNote(k, keys);

		return note;
	}

	/**
	 * return the note (as a letter) represented by given int in the octave
	 * array
	 */
	private static String fetchNote(int k, String[] keys) {
		if (k < 7)
			return keys[k];
		else
			return keys[k - 7];
	}

	/** return the index of the given letter note in the octave array */
	private static int findNote(String n) {

		for (int i = 0; i < octave.length; i++) {
			if (n.equals(octave[i]))
				return i;
		}

		return -1;
	}

	/** creates the sequence of notes from home -> away */
	public static ArrayList<String> goingAway(int len, int home) {
		ArrayList<String> j1 = new ArrayList<String>(len);
		// pick a random starting note and make it the first

		j1.add(octave[home]);

		for (int i = 1; i < len; i++) {
			// if (i == len - 1) {
			// int k = findNote(j1.get(i - 1));
			// j1.add(nextNote(k, octave) + "h");
			// } else {
			int k = findNote(j1.get(i - 1));
			j1.add(nextNote(k, octave));
			// }
		}

		return j1;
	}

	/** creates the sequence of notes from away -> home */
	public static ArrayList<String> comingHome(int len, int home,
			ArrayList<String> j1) {
		ArrayList<String> j2 = new ArrayList<String>();
		int k = findNote("" + (j1.get(j1.size() - 1)).charAt(0));
		j2.add(nextNote(k, octave));
		for (int i = 1; i < len - 1; i++) {
			int kk = findNote(j2.get(i - 1));
			j2.add(i, nextNote(kk, octave));
		}
		j2.add(len - 1, octave[home]);
		return j2;
	}

	/** joins the "going away" to the "coming home" to create a full tune */
	public static String compose(ArrayList<String> j1, ArrayList<String> j2) {
		String piece = "";

		for (int i = 0; i < j1.size(); i++) {
			piece = piece + j1.get(i) + " ";
		}
		for (int j = 0; j < j2.size(); j++) {
			piece = piece + j2.get(j) + " ";
		}

		return piece;
	}

	/**
	 * does some weird thing where it turns a string into an ArrayList<String>,
	 * for some reason
	 */
	public static ArrayList<String> composeArr(String n) {
		ArrayList<String> piece = new ArrayList<String>();
		Scanner sc = new Scanner(n);
		while (sc.hasNext()) {
			piece.add(sc.next());
		}

		return piece;
	}

	/**
	 * finds the next note needed to harmonize, given the original note
	 * 
	 */
	private static String v2Helper(String n) {

		String nn = "" + n.charAt(0);
		String n3 = "";
		if (n.length() > 1) {
			n3 = n.substring(2);
		}

		String next = "";

		if (nn.equals("C")) {
			String[] un = note_C[0];
			int j = rng.nextInt(3);
			while (un[j].equals(nn)) { // cannot allow same note twice for now
				j = rng.nextInt(3);
			}
			next = un[j] + n3;
		}

		else if (nn.equals("D")) {
			String[] un = note_D[0];
			int j = rng.nextInt(3);
			while (un[j].equals(nn)) {
				j = rng.nextInt(3);
			}
			next = un[j] + n3;
		}

		else if (nn.equals("E")) {
			String[] un = note_E[0];
			int j = rng.nextInt(3);
			while (un[j].equals(nn)) {
				j = rng.nextInt(3);
			}
			next = un[j] + n3;
		}

		else if (nn.equals("F")) {
			String[] un = note_F[0];
			int j = rng.nextInt(3);
			while (un[j].equals(nn)) {
				j = rng.nextInt(3);
			}
			next = un[j] + n3;
		}

		else if (nn.equals("G")) {
			String[] un = note_G[0];
			int j = rng.nextInt(3);
			while (un[j].equals(nn)) {
				j = rng.nextInt(3);
			}
			next = un[j] + n3;
		}

		else if (nn.equals("A")) {
			String[] un = note_A[0];
			int j = rng.nextInt(3);
			while (un[j].equals(nn)) {
				j = rng.nextInt(3);
			}
			next = un[j] + n3;
		}

		else if (nn.equals("B")) {
			String[] un = note_B[0];
			int j = rng.nextInt(3);
			while (un[j].equals(nn)) {
				j = rng.nextInt(3);
			}
			next = un[j] + n3;
		}

		else {
			return "";
		}

		return next.substring(0, 1) + "5" + next.substring(1);
	}

	/** Give it two notes, finds a possible third in the harmony */
	private static String v3Helper(String nn1, String nn2, int oc) {

		String n1 = "" + nn1.charAt(0);
		String n2 = "" + nn2.charAt(0);
		String n3 = "";
		String next = "";

		if (nn1.length() > 1) {
			n3 = nn1.substring(2);
		}

		if (n1.equals("C")) {
			String[] un = note_C[0];
			int j = rng.nextInt(3);
			while (un[j].equals(n1) || un[j].equals(n2)) { // cannot allow same
															// note twice for
															// now
				j = rng.nextInt(3);
			}
			next = un[j] + n3;
		}

		else if (n1.equals("D")) {
			String[] un = note_D[0];
			int j = rng.nextInt(3);
			while (un[j].equals(n1) || un[j].equals(n2)) {
				j = rng.nextInt(3);
			}
			next = un[j] + n3;
		}

		else if (n1.equals("E")) {
			String[] un = note_E[0];
			int j = rng.nextInt(3);
			while (un[j].equals(n1) || un[j].equals(n2)) {
				j = rng.nextInt(3);
			}
			next = un[j] + n3;
		}

		else if (n1.equals("F")) {
			String[] un = note_F[0];
			int j = rng.nextInt(3);
			while (un[j].equals(n1) || un[j].equals(n2)) {
				j = rng.nextInt(3);
			}
			next = un[j] + n3;
		}

		else if (n1.equals("G")) {
			String[] un = note_G[0];
			int j = rng.nextInt(3);
			while (un[j].equals(n1) || un[j].equals(n2)) {
				j = rng.nextInt(3);
			}
			next = un[j] + n3;
		}

		else if (n1.equals("A")) {
			String[] un = note_A[0];
			int j = rng.nextInt(3);
			while (un[j].equals(n1) || un[j].equals(n2)) {
				j = rng.nextInt(3);
			}
			next = un[j] + n3;
		}

		else if (n1.equals("B")) {
			String[] un = note_B[0];
			int j = rng.nextInt(3);
			while (un[j].equals(n1) || un[j].equals(n2)) {
				j = rng.nextInt(3);
			}
			next = un[j] + n3;
		}

		else {
			return "";
		}

		return next.substring(0, 1) + oc + next.substring(1);
	}

	private static String v4Helper(String n, String v1, String v2) {

		String nn = "" + n.charAt(0);
		String n3 = "";
		int dur = 5;

		// get note duration stuff if present
		if (n.length() > 1) {
			n3 = n.substring(2);
		}

		String next = "";

		if (nn.equals("C")) {
			String[] un = note_C[0];
			int j = rng.nextInt(3);
			next = un[j] + dur + n3;
			if (next.equals(n) || next.equals(v1) || next.equals(v2)) {
				dur -= 1;
				next = un[j] + dur + n3;
			}
		}

		else if (nn.equals("D")) {
			String[] un = note_D[0];
			int j = rng.nextInt(3);
			next = un[j] + dur + n3;
			if (next.equals(n) || next.equals(v1) || next.equals(v2)) {
				dur -= 1;
				next = un[j] + dur + n3;
			}
		}

		else if (nn.equals("E")) {
			String[] un = note_E[0];
			int j = rng.nextInt(3);
			next = un[j] + dur + n3;
			if (next.equals(n) || next.equals(v1) || next.equals(v2)) {
				dur -= 1;
				next = un[j] + dur + n3;
			}
		}

		else if (nn.equals("F")) {
			String[] un = note_F[0];
			int j = rng.nextInt(3);
			next = un[j] + dur + n3;
			if (next.equals(n) || next.equals(v1) || next.equals(v2)) {
				dur -= 1;
				next = un[j] + dur + n3;
			}
		}

		else if (nn.equals("G")) {
			String[] un = note_G[0];
			int j = rng.nextInt(3);
			next = un[j] + dur + n3;
			if (next.equals(n) || next.equals(v1) || next.equals(v2)) {
				dur -= 1;
				next = un[j] + dur + n3;
			}
		}

		else if (nn.equals("A")) {
			String[] un = note_A[0];
			int j = rng.nextInt(3);
			next = un[j] + dur + n3;
			if (next.equals(n) || next.equals(v1) || next.equals(v2)) {
				dur -= 1;
				next = un[j] + dur + n3;
			}
		}

		else if (nn.equals("B")) {
			String[] un = note_B[0];
			int j = rng.nextInt(3);
			next = un[j] + dur + n3;
			if (next.equals(n) || next.equals(v1) || next.equals(v2)) {
				dur -= 1;
				next = un[j] + dur + n3;
			}
		}

		else {
			return "";
		}

		return next;
	}

	/** returns an ArrayList of the harmonies for this ArrayList */
	public static ArrayList<String> voice2(ArrayList<String> piece) {
		ArrayList<String> second = new ArrayList<String>(piece.size());
		ArrayList<Integer> k = new ArrayList<Integer>(piece.size());

		for (int i = 0; i < piece.size(); i++) {
			second.add(v2Helper(piece.get(i)));
		}

		return second;
	}

	/** returns the third ArrayList of the harmonies given two note sequences */
	public static ArrayList<String> voice3(ArrayList<String> p1,
			ArrayList<String> p2, int oct) {
		ArrayList<String> second = new ArrayList<String>(p1.size());
		ArrayList<Integer> k = new ArrayList<Integer>(p1.size());

		for (int i = 0; i < p1.size(); i++) {
			second.add(v3Helper(p1.get(i), p2.get(i), oct));
		}

		return second;
	}

	public static ArrayList<String> voice4(ArrayList<String> p1,
			ArrayList<String> p2, ArrayList<String> p3) {
		ArrayList<String> second = new ArrayList<String>(p1.size());

		for (int i = 0; i < p1.size(); i++) {
			second.add(v4Helper(p1.get(i), p2.get(i), p3.get(i)));
		}

		return second;
	}

	/**
	 * give it four ArrayLists it will return one with all similar-index
	 * elements joined with +'s
	 */
	public static String combine(ArrayList<String> v1, ArrayList<String> v2,
			ArrayList<String> v3, ArrayList<String> v4) {
		String piece = "";

		for (int i = 0; i < v1.size(); i++) {
			piece = piece + v1.get(i) + "+" + v2.get(i) + "+" + v3.get(i) + " ";
			// + "+"
			// + v4.get(i) + " ";
		}

		return piece;
	}

}
