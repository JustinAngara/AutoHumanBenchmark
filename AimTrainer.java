package com.hbm.main;

import java.awt.AWTException;
import java.awt.Rectangle;
import java.awt.Robot;
import java.awt.event.ActionEvent;
import java.awt.event.InputEvent;
import java.awt.image.BufferedImage;
import java.util.logging.Level;
import java.util.logging.LogManager;
import java.util.logging.Logger;

import javax.swing.Timer;

import org.jnativehook.GlobalScreen;
import org.jnativehook.NativeHookException;
import org.jnativehook.keyboard.NativeKeyEvent;
import org.jnativehook.keyboard.NativeKeyListener;
/*
p1: 828, 336
p4: 1723, 614

dY = 614-336
dX = 1723-828
 
 - when taking screen shot
 true xCoord = 823 + foundBluePX
 true yCoord = 336  + foundBlueY
 * */
public class AimTrainer implements NativeKeyListener{
	private static Timer t;
	private static Robot bot;
	public AimTrainer() throws AWTException {
		bot = new Robot();
		t = new Timer(0,(ActionEvent e)->{
			try {
				findingPixels();
			} catch (AWTException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}

		});
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
	
	
	public static void findingPixels() throws AWTException {
        int targetRedValue = 149; // Target red value
        int tolerance = 10;
        int x1 = 828;
        int y1 = 336;
        int x2 = 1723;
        int y2 = 614;

        Robot robot = new Robot();
        Rectangle screenRect = new Rectangle(x1, y1, x2 - x1, y2 - y1);
        BufferedImage screenCapture = robot.createScreenCapture(screenRect);

        int pixelFoundX = -1;
        int pixelFoundY = -1;

        for (int y = 0; y < screenCapture.getHeight(); y++) {
            for (int x = 0; x < screenCapture.getWidth(); x++) {
                int pixelRGB = screenCapture.getRGB(x, y);
                int red = (pixelRGB >> 16) & 0xFF;

                if (Math.abs(red - targetRedValue) <= tolerance) {
                    pixelFoundX = x + x1; // Adjust for the captured region's offset
                    pixelFoundY = y + y1;
                    break;
                }
            }
            if (pixelFoundX != -1) {
                break;
            }
        }

        if (pixelFoundX != -1) {
//		            System.out.println("Red pixel found at coordinates: (" + pixelFoundX + ", " + pixelFoundY + ")");
        	click(pixelFoundX,pixelFoundY);
        } else {
//		            System.out.println("Red pixel not found within the specified area and tolerance.");
        }
	}
	public static void click(int x, int y) throws AWTException{
	    Robot bot = new Robot();
	    bot.mouseMove(x, y);    
	    bot.mousePress(InputEvent.BUTTON1_DOWN_MASK);
	    bot.mouseRelease(InputEvent.BUTTON1_DOWN_MASK);
	}
	public static void run() throws AWTException {
		GlobalScreen.addNativeKeyListener(new AimTrainer());
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
//		AimTrainer at = new AimTrainer();
		run();
		
	}
}
