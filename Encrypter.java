import java.nio.file.Path;

public class Encrypter implements Runnable{

    Path path;
    Thread thread;

    public Encrypter(Path path) {
        this.path = path;
        this.thread = new Thread(this);
    }

    @Override
    public void run() {

    }
}
