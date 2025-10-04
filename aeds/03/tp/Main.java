import controller.ControleBiblioteca;
import java.io.File;

public class Main {
    public static void main(String[] args) {
        File dataDir = new File("dados");
        if (!dataDir.exists()) {
            dataDir.mkdirs();
        }

        ControleBiblioteca cb = new ControleBiblioteca();
        cb.iniciar();
    }
}