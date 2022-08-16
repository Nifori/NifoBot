package nifori.me.nifobot.ports;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class NetStatUtil {

  private String prepareWinRegex(int port) {
    return ".*(([0-9.]+:)|(\\[[0-f:]*\\]:))" + port + ".*(([0-9.]+:[0-9]{1,7})|(\\[[0-f:]*\\]:[0-9]{1,7})).*";
  }

  public int readConnections(int port) {
    if (System.getProperty("os.name")
        .toLowerCase()
        .contains("windows")) {
      return readWindows(port);
    } else {
      return readUnix(port);
    }
  }

  public int readWindows(int port) {
    int counter = 0;
    try {
      ProcessBuilder pb = new ProcessBuilder("netstat", "-an");
      // | findstr -i HERGESTELLT | findstr " +
      // Integer.toString(port));
      Process process = pb.start();
      try (BufferedReader br = new BufferedReader(new InputStreamReader(process.getInputStream()))) {
        String line;
        while ((line = br.readLine()) != null) {
          if ((line.contains("HERGESTELLT") || line.contains("ESTABLISHED")) && line.contains("TCP")
              && line.matches(prepareWinRegex(port))) {
            System.out.println(line);
            counter++;
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

  public int readUnix(int port) {
    return -1;
  }

}
