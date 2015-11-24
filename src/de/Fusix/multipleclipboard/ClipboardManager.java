package de.Fusix.multipleclipboard;

public class ClipboardManager {
	
	private String[] clipboards;
	
	public ClipboardManager() {
		this.clipboards = new String[10];
	}
	
	public void setClipboard(int id, String msg) {
		clipboards[id] = msg;
	}
	
	public String getClipboard(int id) {
		return clipboards[id];
	}

}
