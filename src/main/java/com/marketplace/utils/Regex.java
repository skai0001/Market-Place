package com.marketplace.utils;

import java.util.regex.Pattern;


public interface Regex {
	
	public static final Pattern NAME = Pattern.compile(
		"\\b[a-zA-Z][a-zA-Z0-9\\-._]{3,}\\b"
	);
	
	public static final Pattern EMAIL = Pattern.compile(
		"[a-z0-9!#$%&'*+/=?^_`{|}~-]+(?:\\.[a-z0-9!#$%&'*+/=?^_`{|}~-]+)*@(?:[a-z0-9](?:[a-z0-9-]*[a-z0-9])?\\.)+[a-z0-9](?:[a-z0-9-]*[a-z0-9])?"
	);
	
	public static final Pattern PASSWORD = Pattern.compile(
		"^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z]).{8,}$"
	);
}