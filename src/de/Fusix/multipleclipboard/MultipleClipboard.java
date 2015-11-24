package de.Fusix.multipleclipboard;

import java.awt.AWTException;
import java.awt.Robot;
import java.awt.Toolkit;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.StringSelection;
import java.awt.datatransfer.UnsupportedFlavorException;
import java.awt.event.KeyEvent;
import java.io.IOException;
import java.util.ArrayList;
import java.util.logging.Handler;
import java.util.logging.Logger;

import org.jnativehook.GlobalScreen;
import org.jnativehook.NativeHookException;
import org.jnativehook.keyboard.NativeKeyEvent;
import org.jnativehook.keyboard.NativeKeyListener;

public class MultipleClipboard implements NativeKeyListener {
	
	private Clipboard cb = Toolkit.getDefaultToolkit().getSystemClipboard();
	private ClipboardManager cbManager = new ClipboardManager();
	private ArrayList<Integer> keysPressed = new ArrayList<>();
	
	public static void main(String[] args) {
		Logger globalLogger = Logger.getLogger(GlobalScreen.class.getPackage().getName());
		globalLogger.setUseParentHandlers(false);
		Handler[] handlers = globalLogger.getHandlers();
		for(Handler handler : handlers) globalLogger.removeHandler(handler);
		try {
            GlobalScreen.registerNativeHook();
            Toolkit.getDefaultToolkit().beep();
        } catch (NativeHookException ex) {
            System.err.println("There was a problem registering the native hook.");
            System.err.println(ex.getMessage());
            System.exit(1);
        }
        GlobalScreen.addNativeKeyListener(new MultipleClipboard());
    }

    public void nativeKeyPressed(NativeKeyEvent e) {
    	if(!keysPressed.contains(e.getKeyCode())) keysPressed.add(e.getKeyCode());
        if (keysPressed.contains(NativeKeyEvent.VC_CONTROL_L) && keysPressed.contains(NativeKeyEvent.VC_ESCAPE)) {
            try {
				GlobalScreen.unregisterNativeHook();
			} catch (NativeHookException ex) {
				ex.printStackTrace();
			}
        } else if(keysPressed.contains(NativeKeyEvent.VC_CONTROL_L)) {
        	switch (e.getKeyCode()) {
	        	case NativeKeyEvent.VC_1:
					copyText(0);
	        		System.out.println("Copied! (1)");
	        		break;
	        	case NativeKeyEvent.VC_2:
	        		copyText(1);
	        		System.out.println("Copied! (2)");
	        		break;
	        	case NativeKeyEvent.VC_3:
	        		copyText(2);
	        		System.out.println("Copied! (3)");
	        		break;
	        	case NativeKeyEvent.VC_4:
	        		copyText(3);
	        		System.out.println("Copied! (4)");
	        		break;
	        	case NativeKeyEvent.VC_5:
	        		copyText(4);
	        		System.out.println("Copied! (5)");
	        		break;
	        	case NativeKeyEvent.VC_6:
	        		copyText(5);
	        		System.out.println("Copied! (6)");
	        		break;
	        	case NativeKeyEvent.VC_7:
	        		copyText(6);
	        		System.out.println("Copied! (7)");
	        		break;
	        	case NativeKeyEvent.VC_8:
	        		copyText(7);
	        		System.out.println("Copied! (8)");
	        		break;
	        	case NativeKeyEvent.VC_9:
	        		copyText(8);
	        		System.out.println("Copied! (9)");
	        		break;
	        	case NativeKeyEvent.VC_0:
	        		copyText(9);
					System.out.println("Copied! (0)");
					break;
			}
        } else if(keysPressed.contains(NativeKeyEvent.VC_INSERT)) {
        	switch (e.getKeyCode()) {
	        	case NativeKeyEvent.VC_1:
	        		pasteText(0);
	        		System.out.println("Pasted! (1)");
	        		break;
	        	case NativeKeyEvent.VC_2:
	        		pasteText(1);
	        		System.out.println("Pasted! (2)");
	        		break;
	        	case NativeKeyEvent.VC_3:
	        		pasteText(2);
	        		System.out.println("Pasted! (3)");
	        		break;
	        	case NativeKeyEvent.VC_4:
	        		pasteText(3);
	        		System.out.println("Pasted! (4)");
	        		break;
	        	case NativeKeyEvent.VC_5:
	        		pasteText(4);
	        		System.out.println("Pasted! (5)");
	        		break;
	        	case NativeKeyEvent.VC_6:
	        		pasteText(5);
	        		System.out.println("Pasted! (6)");
	        		break;
	        	case NativeKeyEvent.VC_7:
	        		pasteText(6);
	        		System.out.println("Pasted! (7)");
	        		break;
	        	case NativeKeyEvent.VC_8:
	        		pasteText(7);
	        		System.out.println("Pasted! (8)");
	        		break;
	        	case NativeKeyEvent.VC_9:
	        		pasteText(8);
	        		System.out.println("Pasted! (9)");
	        		break;
	        	case NativeKeyEvent.VC_0:
	        		pasteText(9);
					System.out.println("Pasted! (0)");
					break;
			}
        }
    }

    public void nativeKeyReleased(NativeKeyEvent e) {
    	if(keysPressed.contains(e.getKeyCode())) {
    		for(int i = 0; i < keysPressed.size(); i++) {
    			if(keysPressed.get(i) == e.getKeyCode()) {
    				keysPressed.remove(i);
    				return;
    			}
    		}
    	}
    }

    public void nativeKeyTyped(NativeKeyEvent e) {}
    
    public void copyText(int id) {
    	try {
			Robot r = new Robot();
			String lastCb = (String) cb.getContents(null).getTransferData(DataFlavor.stringFlavor);
			r.keyPress(KeyEvent.VK_CONTROL);
			r.keyPress(KeyEvent.VK_C);
			r.keyRelease(KeyEvent.VK_C);
			r.keyRelease(KeyEvent.VK_CONTROL);
			Thread.sleep(50);
			String hightlightedText = (String) cb.getContents(null).getTransferData(DataFlavor.stringFlavor);
			StringSelection sel = new StringSelection(lastCb);
			cb.setContents(sel, sel);
			cbManager.setClipboard(id, hightlightedText);
			System.out.println(hightlightedText);
		} catch (AWTException | UnsupportedFlavorException | IOException | InterruptedException e1) {
			e1.printStackTrace();
		}
    }
    
    public void pasteText(int id) {
    	try {
			Robot r = new Robot();
			String lastCb = (String) cb.getContents(null).getTransferData(DataFlavor.stringFlavor);
			String copiedText = cbManager.getClipboard(id);
			StringSelection sel = new StringSelection(copiedText);
			cb.setContents(sel, sel);
			System.out.println(copiedText);
			r.keyPress(KeyEvent.VK_BACK_SPACE);
			r.keyRelease(KeyEvent.VK_BACK_SPACE);
			r.keyPress(KeyEvent.VK_CONTROL);
			r.keyPress(KeyEvent.VK_V);
			r.keyRelease(KeyEvent.VK_V);
			r.keyRelease(KeyEvent.VK_CONTROL);
			Thread.sleep(50);
			sel = new StringSelection(lastCb);
			cb.setContents(sel, sel);
		} catch (AWTException | UnsupportedFlavorException | IOException | InterruptedException e1) {
			e1.printStackTrace();
		}
    }
	
}
