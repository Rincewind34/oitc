package de.janhektor.oitc.command.dyn;

import java.lang.reflect.Field;
import java.util.Map;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.SimpleCommandMap;

public final class ReflectionUtil {
	
	private final static ReflectionUtil INSTANCE = new ReflectionUtil();
	
	public static ReflectionUtil instance() {
		return ReflectionUtil.INSTANCE;
	}
	
	public final String VERSION;
	
	public final Class<?> CLASS_CRAFTSERVER;
	public final Class<?> CLASS_SIMPLECOMMANDMAP;
	
	public final Field FIELD_COMMANDMAP;
	public final Field FIELD_COMMANDS;
	
	public final SimpleCommandMap COMMAND_MAP;
	public final Map<String, Command> COMMANDS;
	
	@SuppressWarnings("unchecked")
	private ReflectionUtil() {
		this.VERSION = Bukkit.getServer().getClass().getPackage().getName().split("\\.")[3];
		
		this.CLASS_CRAFTSERVER = this.getClass("org.bukkit.craftbukkit." + this.VERSION + ".CraftServer");
		this.CLASS_SIMPLECOMMANDMAP = this.getClass("org.bukkit.command.SimpleCommandMap");
		
		this.FIELD_COMMANDMAP = this.getField(this.CLASS_CRAFTSERVER, "commandMap");
		this.FIELD_COMMANDS = this.getField(this.CLASS_SIMPLECOMMANDMAP, "knownCommands");
		
		this.accessFields();
		
		this.COMMAND_MAP = (SimpleCommandMap) this.createObject(this.FIELD_COMMANDMAP, Bukkit.getServer());
		this.COMMANDS = (Map<String, Command>) this.createObject(this.FIELD_COMMANDS, this.COMMAND_MAP);
	}
	
	private void accessFields() {
		this.FIELD_COMMANDMAP.setAccessible(true);
		this.FIELD_COMMANDS.setAccessible(true);
	}
	
	public Class<?> getClass(String className) {
		try {
			return Class.forName(className);
		} catch (ClassNotFoundException e) {
			throw new ReflectException("Could not get the class '" + className + "'!");
		} 
	}
	
	public Field getField(Class<?> clazz, String name) {
		try {
			return clazz.getDeclaredField(name);
		} catch (NoSuchFieldException e) {
			throw new ReflectException("Could not find the field '" + name + "' in class '" + clazz.getName() + "'!");
		} catch (SecurityException e) {
			throw new ReflectException("A security error occured!");
		} 
	}
	
	public Object createObject(Field field, Object from) {
		try {
			return field.get(from);
		} catch (IllegalArgumentException e) {
			throw new ReflectException("Could not get the field '" + field.getName() + "' from '" + from.getClass().getName() + "'!");
		} catch (IllegalAccessException e) {
			throw new ReflectException("Could not access the field '" + field.getName() + "'!");
		}
	}
	
	public boolean check() {
		return this.COMMAND_MAP != null && this.COMMANDS != null;
	}
	
	@SuppressWarnings("serial")
	public static class ReflectException extends NullPointerException {
		
		public ReflectException(String s) {
			super(s);
		}
		
	}
	
}
