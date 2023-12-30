package com.hbm.main;

import java.awt.AWTException;
import java.awt.Robot;
import java.awt.event.KeyEvent;
import java.io.IOException;
import java.util.Scanner;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

public class Typing {
	private String fullSentence;
	private Robot bot;
	private static Document doc;
	private static String english;
	
	public Typing() throws AWTException {		
		fullSentence = getWordSeq();
		bot = new Robot();
	}
	
	public static String getWordSeq() {
		Scanner sc = new Scanner(System.in);
		System.out.println("Enter in sentence: ");
		return sc.nextLine();
	}
	
	public void typeOutSeq(String[] seq) {
		bot.delay(5000);
        for (String letter : seq) {
            char keyChar = letter.charAt(0);
            int keyCode = KeyEvent.getExtendedKeyCodeForChar(keyChar);
            bot.keyPress(keyCode);
            bot.keyRelease(keyCode); // Release the key after pressing
        }
		
	}
	
	public void getContents() throws IOException {

//		doc = Jsoup.connect("https://humanbenchmark.com/tests/typing").get();
//
//		english = doc.getElementsByClass("letters notranslate").get(0).text();
//		System.out.println(english);
	}
	
	
	public static void main(String[] args) throws AWTException, IOException {
		Typing te = new Typing();
		String[] arr = te.fullSentence.split("");
		te.typeOutSeq(arr);
//		te.getContents();
	}
}
