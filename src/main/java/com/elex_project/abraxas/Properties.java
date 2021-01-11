/*
 * Project Abraxas (a.k.a core-utils)
 * http://www.elex-project.com/
 * Copyright (c) 2020. Elex. All Rights Reserved.
 */

package com.elex_project.abraxas;

import lombok.extern.slf4j.Slf4j;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.io.*;
import java.util.Enumeration;
import java.util.Hashtable;
import java.util.Optional;
import java.util.Set;
import java.util.function.Function;

/**
 * Properties
 *
 * @author Elex
 */
@Slf4j
public class Properties {

	private final Hashtable<String, String> properties;

	public Properties() {
		properties = new Hashtable<>();
	}

	public Properties(Properties defaults) {
		this();
		patch(defaults);
	}

	public Properties(InputStream inputStream) {
		this();
		try {
			load(inputStream);
		} catch (IOException e) {
			log.error("While loading from an input stream...", e);
		}
	}

	/**
	 * 값을 불러와서 엎어 쓴다.
	 * @param props props
	 */
	private void patch(@NotNull Properties props) {
		for (String key : props.properties.keySet()) {
			setProperty(key, props.getProperty(key));
		}
	}

	/**
	 * 값을 불러와서 엎어 쓴다.
	 * @param props props
	 */
	private void patch(@NotNull java.util.Properties props) {
		for (Object key : props.keySet()) {
			setProperty((String) key, props.getProperty((String) key));
		}
	}

	@NotNull
	public java.util.Properties toProperties() {
		final java.util.Properties props = new java.util.Properties();
		for (String key : properties.keySet()) {
			try {
				props.setProperty(key, getProperty(key)
						.orElseThrow(IllegalStateException::new));
			} catch (IllegalStateException ignore) {
			}
		}
		return props;
	}

	public Set<String> keySet() {
		return properties.keySet();
	}

	public Enumeration<String> keys() {
		return properties.keys();
	}

	public boolean hasProperty(@NotNull String key) {
		return properties.containsKey(key);
	}

	@NotNull
	public Optional<String> getProperty(@NotNull String key) {
		return Optional.ofNullable(properties.get(key));
	}

	@NotNull
	public Optional<Integer> getIntProperty(@NotNull String key) {
		return getProperty(key).map(s -> {
			try {
				return Integer.parseInt(s);
			} catch (NumberFormatException e) {
				return null;
			}
		});
	}
	@NotNull
	public Optional<Integer> getIntProperty(@NotNull String key, final int defaultValue) {
		return getProperty(key).map(s -> {
			try {
				return Integer.parseInt(s);
			} catch (NumberFormatException e) {
				return defaultValue;
			}
		});
	}

	@NotNull
	public Optional<Long> getLongProperty(@NotNull String key) {
		return getProperty(key).map(s -> {
			try {
				return Long.parseLong(s);
			} catch (NumberFormatException e) {
				return null;
			}
		});
	}
	@NotNull
	public Optional<Long> getLongProperty(@NotNull String key, final long defaultValue) {
		return getProperty(key).map(s -> {
			try {
				return Long.parseLong(s);
			} catch (NumberFormatException e) {
				return defaultValue;
			}
		});
	}
	@NotNull
	public Optional<Float> getFloatProperty(@NotNull String key) {
		return getProperty(key).map(s -> {
			try {
				return Float.parseFloat(s);
			} catch (NumberFormatException e) {
				return null;
			}
		});
	}
	@NotNull
	public Optional<Float> getFloatProperty(@NotNull String key, final float defaultValue) {
		return getProperty(key).map(s -> {
			try {
				return Float.parseFloat(s);
			} catch (NumberFormatException e) {
				return defaultValue;
			}
		});
	}
	@NotNull
	public Optional<Double> getDoubleProperty(@NotNull String key) {
		return getProperty(key).map(s -> {
			try {
				return Double.parseDouble(s);
			} catch (NumberFormatException e) {
				return null;
			}
		});
	}
	@NotNull
	public Optional<Double> getDoubleProperty(@NotNull String key, final double defaultValue) {
		return getProperty(key).map(s -> {
			try {
				return Double.parseDouble(s);
			} catch (NumberFormatException e) {
				return defaultValue;
			}
		});
	}

	public Optional<Boolean> getBooleanProperty(@NotNull String key) {
		return getProperty(key).map(Boolean::parseBoolean);
	}

	public <T> void setProperty(@NotNull String key, @Nullable T value) {
		if (null == value) {
			properties.remove(key);
		} else {
			if (value instanceof String) {
				properties.put(key, (String) value);
			} else {
				properties.put(key, value.toString());
			}

		}
	}

	public synchronized void load(InputStream inputStream) throws IOException {
		load(IOz.getReader(inputStream));
	}

	public synchronized void load(Reader reader) throws IOException {
		java.util.Properties props = new java.util.Properties();
		props.load(reader);
		patch(props);
	}

	public synchronized void loadXml(InputStream inputStream) throws IOException {
		java.util.Properties props = new java.util.Properties();
		props.loadFromXML(inputStream);
		patch(props);
	}

	public synchronized void save(Writer write, String comment) throws IOException {
		toProperties().store(write, comment);
	}

	public synchronized void save(OutputStream outputStream, String comment) throws IOException {
		save(IOz.getWriter(outputStream), comment);
	}

	public synchronized void saveToXml(OutputStream outputStream, String comment) throws IOException {
		toProperties().storeToXML(outputStream, comment);
	}

}
