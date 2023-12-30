package com.hbm.main;

import java.awt.AWTException;
import java.awt.Color;
import java.awt.Robot;
import java.awt.event.ActionEvent;
import java.awt.event.InputEvent;
import java.awt.event.KeyEvent;
import java.util.logging.Level;
import java.util.logging.LogManager;
import java.util.logging.Logger;

import javax.swing.Timer;

import org.jnativehook.GlobalScreen;
import org.jnativehook.NativeHookException;
import org.jnativehook.keyboard.NativeKeyEvent;
import org.jnativehook.keyboard.NativeKeyListener;


public class ReactionTime implements NativeKeyListener{
	private static Timer t;
	private static Robot bot;
	
	public ReactionTime() throws AWTException {
		bot = new Robot();
		t = new Timer(0,(ActionEvent e)->{
			try {
				if(grabColor(304,380)) {
					click();
				}
			} catch (AWTException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}

		});
	}
	
	public static boolean grabColor(int x, int y) throws AWTException {
		return 75==bot.getPixelColor(x,y).getRed();
	}
	
	public void click() {
//		System.out.println("CLICK");
	    bot.mousePress(InputEvent.BUTTON1_DOWN_MASK);
	    bot.mouseRelease(InputEvent.BUTTON1_DOWN_MASK);
	}

	@Override
	public void nativeKeyPressed(NativeKeyEvent arg0) {
		// TODO Auto-generated method stub
		if(arg0.getKeyCode()==NativeKeyEvent.VC_Z) {
			t.start();
		}
	}

	@Override
	public void nativeKeyReleased(NativeKeyEvent arg0) {
		// TODO Auto-generated method stub
		if(arg0.getKeyCode()==NativeKeyEvent.VC_Z) {
			t.stop();
		}
	}

	@Override
	public void nativeKeyTyped(NativeKeyEvent arg0) {
		// TODO Auto-generated method stub
		
	}
	
	public static void run() throws AWTException {
		GlobalScreen.addNativeKeyListener(new ReactionTime());
		LogManager.getLogManager().reset();

		// Get the logger for "org.jnativehook" and set the level to off.
		Logger logger = Logger.getLogger(GlobalScreen.class.getPackage().getName());
		logger.setLevel(Level.OFF);
		try {
			GlobalScreen.registerNativeHook();
		}
		catch (NativeHookException ex) {}
	}

	
	public static void main(String[] args) throws AWTException {
		run();
		
	}
}
