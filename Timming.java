public class Timming {
    public static void main() {
        long startTime = System.nanoTime();
        long endTime = System.nanoTime();
        long duration = (endTime - startTime);
        System.out.println(duration);
    }
}
