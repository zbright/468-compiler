public class TempRegCounter {
	public static int regCount = 16; 
	private static int regCounter = 1;

	public static String getNext() {
		return String.valueOf(regCounter++);
	}

	public static void resetCounter() {
		regCounter = 0;
	}
}