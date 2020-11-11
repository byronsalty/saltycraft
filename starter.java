import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.OutputStreamWriter;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.nio.charset.Charset;


import java.util.regex.Pattern;
import java.util.regex.Matcher;



class Starter {
  
  private static boolean gameStarted = false;
  private static Process pr;
  
  public static void main(String[] args) {
    System.out.println("Hello");


    //java -Xmx1024M -Xms1024M -jar server.jar
    try {
      ProcessBuilder pb = new ProcessBuilder("java", "-Xmx1024M", "-Xms1024M", "-jar", "server.jar");
      pb.redirectErrorStream(true);
      pr = pb.start();
      
      BufferedReader in = new BufferedReader(new InputStreamReader(pr.getInputStream()));
      String line;
      while ((line = in.readLine()) != null) {
          checkLine(line);
          //System.out.println(line);
      }
      pr.waitFor();
      
    } catch (Exception e) {
      System.out.println("Error Caught");
      System.out.println(e.toString());

    }
  }

  public static void startGame() {
    System.out.println("starting game");
    gameStarted = true;
    writeToMinecraft("/say OK - Let's get started");
    // reset any variables
    qsleep(4000);
    // begin game
    startNight();
  }

  public static void qsleep(int ms) {
    try {
      Thread.sleep(ms);
    } catch (InterruptedException e) {
      // do nothing
    }
  }

  public static void startNight() {
    // update gametime...
    writeToMinecraft("/time set 16000");
    qsleep(200);
    writeToMinecraft("/time set 16500");
    qsleep(200);
    writeToMinecraft("/time set 17000");
    qsleep(200);
    writeToMinecraft("/time set 17500");
    qsleep(200);
    writeToMinecraft("/time set 18000");
    qsleep(2000);
    writeToMinecraft("/say There's been a murder!");
    qsleep(2000);
    assignRoles();
  }

  public static void assignRoles() {
    writeToMinecraft("/msg vsalty you're the seer");
  }

  public static void playerSaid(String player, String saying) {
    System.out.println("player: " + player);
    System.out.println("said: " + saying);

    if (saying.equals("lets play")) {
      startGame();
    }
  }

  public static void commandPlayed(String command) {
    System.out.println("command: " + command);

    // if (saying.equals("lets play")) {
    //   startGame();
    // }
  }

  public static void checkForChat(String line) {
    Pattern pattern = Pattern.compile("^<(\\w+)> (.*)$");
    Matcher matcher = pattern.matcher(line);

    if (matcher.find()) {
      System.out.println("matched");
      playerSaid(matcher.group(1), matcher.group(2));
    } else {
      System.out.println("no match");
    }
  }

  public static void checkForCommand(String line) {
    Pattern pattern = Pattern.compile("^\\[@\\] (.*)$");
    Matcher matcher = pattern.matcher(line);

    if (matcher.find()) {
      System.out.println("matched");
      commandPlayed(matcher.group(1));
    } else {
      System.out.println("no match");
    }
  }

  public static void checkLine(String rawLine) {
    System.out.println("orig: " + rawLine);
    String[] lineArr = rawLine.split(": ", 2);
    String line = lineArr[1];
    System.out.println("line: " + line);

    checkForChat(line);
    checkForCommand(line);
  }
  public static void writeToMinecraft(String toWrite) {
    try {
      System.out.println("writing");
      OutputStream outStream = pr.getOutputStream();
      OutputStreamWriter writer = new OutputStreamWriter(outStream);

      //writer.write(toWrite.getBytes(Charset.forName("UTF-8")));
      writer.write(toWrite);
      writer.write("\n");
      writer.flush();  

    } catch (Exception e) {
      System.out.println("Error!");
      System.out.println(e);
    }

  }

}
