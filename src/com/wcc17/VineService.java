package com.wcc17;

import java.io.*;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.channels.Channels;
import java.nio.channels.ReadableByteChannel;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by wcc17 on 1/15/17.
 */
public class VineService {

    public static List<Vine> parseVineList() {
        List<Vine> vines = new ArrayList<Vine>();
        File file = new File("full-vines-list-VICTORIA.txt");
        BufferedReader reader = null;

        try {
            reader = new BufferedReader(new FileReader(file));
            String text = null;

            while((text = reader.readLine()) != null) {
                Vine vine = new Vine();
                vine.index = Integer.parseInt(text);

                while(!(text = reader.readLine()).contains("created")) {
                    vine.avatarUrl += text;
                }

                vine.created += text;
                while(!(text = reader.readLine()).contains("description")) {
                    vine.created += text;
                }

                vine.description += text;
                while(!(text = reader.readLine()).contains("likes")) {
                    vine.description += text;
                }

                vine.likes += text;
                while(!(text = reader.readLine()).contains("loops")) {
                    vine.likes += text;
                }

                vine.loops += text;
                while(!(text = reader.readLine()).contains("username")) {
                    vine.loops += text;
                }

                vine.username += text;
                while(!(text = reader.readLine()).contains("venueAddress")) {
                    vine.username += text;
                }

                vine.venueAddress += text;
                while(!(text = reader.readLine()).contains("venueCity")) {
                    vine.venueAddress += text;
                }

                vine.venueCity += text;
                while(!(text = reader.readLine()).contains("venueCountryCode")) {
                    vine.venueCity += text;
                }

                vine.venueCountryCode += text;
                while(!(text = reader.readLine()).contains("venueName")) {
                    vine.venueCountryCode += text;
                }

                vine.venueName = text;
                while(!(text = reader.readLine()).contains("venueState")) {
                    vine.venueName = text;
                }

                vine.venueState = text;
                while(!(text = reader.readLine()).contains("videoUrl")) {
                    vine.venueState = text;
                }

                vine.videoUrl = text;
                while(!(text = reader.readLine()).contains("")) {
                    vine.videoUrl = text;
                }

                vines.add(vine);
            }
        } catch (FileNotFoundException e) {
            System.out.println("Vine list not found");
        } catch (IOException e) {
            System.out.println("Error opening up vine list text file");
        }

        return vines;
    }

    public static void downloadVines(List<Vine> vines) {
        for(Vine vine : vines) {
            try {
                String url = vine.videoUrl.replace("videoUrl: ", "");

                URL website = new URL(url);
                ReadableByteChannel rbc = Channels.newChannel(website.openStream());

                String fileName = vine.toString();
                fileName = fileName.replace("/", "-");
                fileName = fileName.replace("\\", "-");
                fileName = fileName.replace(":", "-");
                fileName += ".mp4";

                FileOutputStream fos = new FileOutputStream("vines/" + fileName);

                System.out.println("Downloading " + vine.toString());
                fos.getChannel().transferFrom(rbc, 0, Long.MAX_VALUE);
            } catch (MalformedURLException e) {
                System.out.println("ERROR - MalformedURL " + vine.toString());
            } catch (FileNotFoundException e) {
                System.out.println("ERROR - FileNotFound " + vine.toString());
            } catch (IOException e) {
                System.out.println("ERROR - IOException " + vine.toString());
            }

        }
    }

    public static Map<Vine, String> buildVineFileMap(List<Vine> vines) {
        Map<Vine, String> vineFileMap = new HashMap<Vine, String>();

        //just to ensure that vine indexes are always matched up correctly without looping through all
        Map<Integer, Vine> vineMap = new HashMap<Integer, Vine>();
        for(Vine vine : vines) {
            vineMap.put(vine.index, vine);
        }

        File folder = new File("vines/");
        File[] listOfFiles = folder.listFiles();
        for(File file : listOfFiles) {
            String fileName = file.getName();
            String fileIndexString = new String();
            for(int i = 0; i < fileName.length(); i++) {
                if(fileName.charAt(i) != ' '
                        && fileName.charAt(i) != '-'
                        && Character.isDigit(fileName.charAt(i))) {
                    fileIndexString += String.valueOf(fileName.charAt(i));
                } else {
                    break;
                }
            }

            try {
                Integer fileIndex = Integer.parseInt(fileIndexString);
                if(file.getName().equals("2117 - Jonathan Coffman - 2013-05-17T03-23-47.000000.mp4")) {
                    System.out.println("broken");
                }
                vineFileMap.put(vineMap.get(fileIndex), file.getName());
            } catch (NumberFormatException e) {
                //skip the file, we don't need it (its not a vine file if it doesnt have the index at the front)
                System.out.println(fileName + " not added to map of vines to files");
            }
        }

        return vineFileMap;
    }
}
