import java.time.Duration;
import java.time.Instant;

public class Timer {

    private static Instant start;
    private static Instant stop;

    public static void Start()
    {
        start = Instant.now();
    }

    public static void Stop()
    {
        stop = Instant.now();
        System.out.println(Duration.between(start, stop).toMillis());
    }
    
}
