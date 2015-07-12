package de.janhektor.oitc;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.bukkit.command.CommandSender;

public class InfoLayout {
	
	private List<String> lines;
	
	public final String clPri;
	public final String clSec;
	public final String clPos;
	public final String clNeg;
	public final String clHiLi;
	
	public final String prefix;
	
	public InfoLayout(String name) {
		this.lines = new ArrayList<String>();
		this.clPri = "§6";
		this.clSec = "§7";
		this.clPos = "§2";
		this.clNeg = "§4";
		this.clHiLi = "§a";
		this.prefix = this.clPri + "[" + this.clSec + name + this.clPri + "] §r";
	}
	
	public List<String> getLines() {
		return Collections.unmodifiableList(this.lines);
	}
	
	public String getPrefix() {
		return this.prefix;
	}
	
	public void newCategory(String name) {
		this.newBarrier();
		this.lines.add(this.prefix + this.clHiLi + name);
	}
	
	public void newBarrier() {
		this.lines.add(this.prefix + this.clPri + "===============================================");
	}
	
	public void addInfo(String name, String info, boolean positiv) {
		this.lines.add(this.prefix + this.clSec + name + ": " + ((positiv) ? this.clPos : this.clNeg) + info);
	}
	
	public void addInfo(String name, boolean element) {
		this.lines.add(this.prefix + this.clSec + name + ": " + ((element) ? this.clPos : this.clNeg) + element);
	}
	
	public void addElement(String element, boolean positiv) {
		this.lines.add(this.prefix + ((positiv) ? this.clPos : this.clNeg) + element);
	}
	
	public void addComent(String element, boolean highlight) {
		this.lines.add(this.prefix + ((highlight) ? this.clHiLi : this.clSec) + element);
	}
	
	public void send(CommandSender sender) {
		for (String line : this.lines) {
			sender.sendMessage(line);
		}
	}
}
