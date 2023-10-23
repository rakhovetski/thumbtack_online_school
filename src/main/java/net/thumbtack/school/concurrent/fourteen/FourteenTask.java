package net.thumbtack.school.concurrent.fourteen;

import java.io.*;
import java.util.ArrayList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

class Transport{
    public void send(Message msg) {
        try (FileWriter fw = new FileWriter("emails.txt")) {
            fw.write(msg.toString() + "\n");
            fw.flush();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }
}

public class FourteenTask {
    private static void createEmails() {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter("emails.txt"))) {
            for (int i = 0; i < 100; i++) {
                bw.write("emailNumber" + i + "@gmail.com");
                bw.newLine();
            }
            bw.flush();
            System.out.println("Emails are ready");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static ArrayList<String> readEmails() {
        ArrayList<String> list = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader("emails.txt"))) {
            for (int i = 0; i < 100; i++) {
                String s = br.readLine();
                list.add(s);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return list;
    }

    public static void main(String[] args) {
        createEmails();
        ArrayList<String> emails = readEmails();
        Transport transport = new Transport();
        ExecutorService es = Executors.newCachedThreadPool();
        for (String email : emails) {
            es.execute(() -> transport.send(new Message(email, "Inyutin Vladislav", "ttschool", "Task14")));
        }
        es.shutdown();
    }
}
