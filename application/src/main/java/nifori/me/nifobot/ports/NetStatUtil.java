package nifori.me.nifobot.ports;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class NetStatUtil {

  public int readConnections(int port) {
    int counter = 0;
    try {
      ProcessBuilder pb = new ProcessBuilder("netstat", "-an");
      // | findstr -i HERGESTELLT | findstr " +
      // Integer.toString(port));
      Process process = pb.start();
      try (BufferedReader br = new BufferedReader(new InputStreamReader(process.getInputStream()))) {
        String line;
        while ((line = br.readLine()) != null) {
          if ((line.contains("HERGESTELLT") || line.contains("ESTABLISHED"))
              && line.matches(".*TCP(\t| )*[0-9]{1,3}.[0-9]{1,3}.[0-9]{1,3}.[0-9]{1,3}:" + port + ".*")) {
            System.out.println(line);
            counter++;
          } else {
            // System.out.println("Doesnt Match");
          }
        }
      } catch (IOException e) {
        throw new RuntimeException(e);
      }
    } catch (IOException e) {
      throw new RuntimeException(e);
    }
    return counter;
  }

}
