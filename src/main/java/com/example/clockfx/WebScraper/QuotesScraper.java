package com.example.clockfx.WebScraper;

import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.net.URL;
import java.net.URLConnection;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;
@SuppressWarnings("all")
public class QuotesScraper {

    List<String> quotes;
    HashMap<String, String> topics;

    List<String> pages;

    private final String baseUrl = "https://quotefancy.com/";

    private String selectedTopic = null;

    private final String userAgent = "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/100.0.4896.60 Safari/537.36";

    private boolean offlineMode;

    private final String fileDel = "\\|";



    private QuotesScraper(){
        pages = new ArrayList<>();
        quotes = new ArrayList<>();
        topics = new HashMap<>();
    }

    public static QuotesScraper createNew(){
        return new QuotesScraper();
    }

    public void run() throws InterruptedException {
        checkInternet();
        if (!offlineMode){
            fetchTopics();
            fetchQuotes();
            saveQuotesToFile();
        }
        else{
            fetchQuotesOffline();
        }
    }

    public void checkInternet(){
        try{
            URL url = new URL("https://google.com");
            URLConnection conn = url.openConnection();
            conn.connect();
            offlineMode = true;
        }
        catch(IOException e){
            offlineMode = false;
        }
    }

    public List<String> getQuotes(){
        return quotes;
    }



    public void saveQuotesToFile(){

        FileUtils.createFile("quotes.txt");

        Path path = Paths.get(FileUtils.getFinalPath("quotes.txt"));

        StringBuilder contentBuilder = new StringBuilder();

        for(String quote : quotes){
            contentBuilder.append(quote).append(fileDel);
        }

        String content = contentBuilder.toString();

        try{
            Files.write(path, content.getBytes(StandardCharsets.UTF_8));
        }
        catch (IOException ignore){
            System.out.println("Error writing file!");
        }





    }

    public void fetchQuotesOffline() {
        if (!FileUtils.fileExists("quotes.txt")) {
            return;
        }

        try (BufferedReader br = new BufferedReader(new FileReader(FileUtils.getFinalPath("quotes.txt")))) {
            String line;
            while ((line = br.readLine()) != null){
                String[] quotesArray = line.split(fileDel);
                quotes.addAll(Arrays.asList(quotesArray));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }


    }

    public void fetchTopics() throws InterruptedException{

        if (FileUtils.fileExists("quotes.txt")){
            FileUtils.delFile("quotes.txt");
        }


        boolean success = false;
        while(!success){
            try{
                Document doc = Jsoup.connect(baseUrl).userAgent(userAgent).get();
                Elements topicObjects = doc.select(".gridblock-title");

                for(Element topicObject : topicObjects){
                    Element a = topicObject.select("a").first();
                    String topic = a.text().strip().toLowerCase();
                    String url = a.attr("href");
                    topics.put(topic, url);
                }
                success = true;
            }
            catch (IOException e) {
                System.out.println("Error occurred while fetching topics. Retrying...");
                e.printStackTrace();
                Thread.sleep(3000);
            }
        }


    }

    public void printDelimiter(int size){
        for(int i = 0; i < size; i++){
            System.out.print("-");
        }
        System.out.println();
    }

    public void printFetchedQuotes(){
        for(String quote : quotes){

            printDelimiter(quote.length());
            System.out.println(quote);
            printDelimiter(quote.length());
        }


    }

    public void fetchQuotes() throws InterruptedException {

        Random random = new Random();
        int topicIndex = random.nextInt(topics.size() + 1);
        selectedTopic = (String) topics.keySet().toArray()[topicIndex];


        boolean success = false;

        while(!success){
            try{
                String topicsLink = topics.get(selectedTopic);

                Connection.Response response = Jsoup.connect(topicsLink).userAgent(userAgent).execute();

                int statusCode = response.statusCode();
                String body = response.body();
                if (statusCode != 200){
                    continue;
                }


                Document doc = Jsoup.parse(body);

                Elements quoteObjects = doc.select(".quote-a");
                for(Element quoteObj : quoteObjects) {
                    quotes.add(quoteObj.text());

                }

                success = true;
            }
            catch(IOException e){
                System.out.println("Error occurred while fetching quotes. Retrying...");
                e.printStackTrace();
                Thread.sleep(3000);

            }

        }




    }



}
