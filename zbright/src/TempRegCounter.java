public class TempRegCounter {
	private static int regCounter = 0;

	public static String getNext() {
		return String.valueOf(regCounter++);
	}

	public static void resetCounter() {
		regCounter = 0;
	}
}