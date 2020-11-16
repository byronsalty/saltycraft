package com.saltylabs.minecraft;

import java.io.BufferedReader;
import java.io.OutputStreamWriter;
import java.io.InputStreamReader;
import java.io.OutputStream;


import java.util.regex.Pattern;
import java.util.regex.Matcher;

public class MineHandler {

  private Process pr;
  Game game;

  public MineHandler (Game game) {

    this.game = game;

  }

  public void runMinecraft() {
    //java -Xmx1024M -Xms1024M -jar server.jar

    try {
      ProcessBuilder pb = new ProcessBuilder("java", "-Xmx1024M", "-Xms1024M", "-jar", "server.jar");
      pb.redirectErrorStream(true);
      this.pr = pb.start();
      
      BufferedReader in = new BufferedReader(new InputStreamReader(pr.getInputStream()));
      String line;
      while ((line = in.readLine()) != null) {
          checkLine(line);
          //System.out.println(line);
      }
      this.pr.waitFor();
        
    } catch (Exception e) {
      System.out.println("Error Caught");
      System.out.println(e.toString());

    }
  }

  public void checkLine(String rawLine) {
    System.out.println("orig: " + rawLine);
    String[] lineArr = rawLine.split(": ", 2);
    String line = lineArr[1];
    System.out.println("line: " + line);

    this.checkForChat(line);
    this.checkForCommand(line);
  }

  public void writeToMinecraft(String toWrite) {
    try {
      System.out.println("writing");
      OutputStream outStream = this.pr.getOutputStream();
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


  public void checkForChat(String line) {
    Pattern pattern = Pattern.compile("^<(\\w+)> (.*)$");
    Matcher matcher = pattern.matcher(line);

    if (matcher.find()) {
      System.out.println("matched");
      this.game.playerSaid(matcher.group(1), matcher.group(2));
    } else {
      System.out.println("no match");
    }
  }

  public void checkForCommand(String line) {
    Pattern pattern = Pattern.compile("^\\[@\\] (.*)$");
    Matcher matcher = pattern.matcher(line);

    if (matcher.find()) {
      System.out.println("matched");
      this.game.commandPlayed(matcher.group(1));
    } else {
      System.out.println("no match");
    }
  }
}
