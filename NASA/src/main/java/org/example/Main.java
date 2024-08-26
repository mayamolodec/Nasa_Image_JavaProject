package org.example;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.hc.client5.http.classic.methods.HttpGet;
import org.apache.hc.client5.http.impl.classic.CloseableHttpClient;
import org.apache.hc.client5.http.impl.classic.CloseableHttpResponse;
import org.apache.hc.client5.http.impl.classic.HttpClients;

import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Scanner;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {
    public static void main(String[] args) throws IOException {
        String url = "https://api.nasa.gov/planetary/apod?api_key=JqetCABg2tZ2x9aoE6Gh22cEvs2vlTlRDan69oqc&date=2024-08-19";

        CloseableHttpClient httpclient = HttpClients.createDefault();
        HttpGet httpGet = new HttpGet(url);
        CloseableHttpResponse response = httpclient.execute(httpGet);

//        Scanner scan = new Scanner(response.getEntity().getContent());
//        String imageInfo = scan.nextLine();
//        System.out.println(imageInfo);

        ObjectMapper mapper = new ObjectMapper();
        NasaAnswer answer = mapper.readValue(response.getEntity().getContent(), NasaAnswer.class);

        System.out.println("gh"+answer.url);
        String imageUrl = answer.url;
        String[] separatedUrl = imageUrl.split("/");
        String fileName = separatedUrl[separatedUrl.length-1];
        HttpGet imageRequest = new HttpGet(imageUrl);
        CloseableHttpResponse image = httpclient.execute(imageRequest);

        FileOutputStream fos = new FileOutputStream("Image/"+fileName);
        image.getEntity().writeTo(fos);
    }
}