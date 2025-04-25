//package pingpong.server;
//
//import org.springframework.boot.SpringApplication;
//import org.springframework.boot.autoconfigure.SpringBootApplication;
//
//@SpringBootApplication
//public class ServerApplication {
//
//	public static void main(String[] args) {
//		SpringApplication.run(ServerApplication.class, args);
//	}
//
//}

package pingpong.server;

import java.awt.Desktop;
import java.net.URI;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class ServerApplication {

    public static void main(String[] args) {
        SpringApplication.run(ServerApplication.class, args);

        try {
            String url = "http://localhost:8080/";
            if (Desktop.isDesktopSupported()) {
                Desktop.getDesktop().browse(new URI(url));
            } 
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}