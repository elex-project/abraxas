/*
 * Project Abraxas (a.k.a core-utils)
 * http://www.elex-project.com/
 * Copyright (c) 2020. Elex. All Rights Reserved.
 */

package com.elex_project.abraxas;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.*;

/**
 * Resource Bundle
 *
 * @author Elex
 */
public class ResourceBundle {
	private static final String EXT_PROPERTIES = "properties";
	private static final String EXT_XML = "xml";

	private static Map<String, ResourceBundle> CACHE = new HashMap<>();

	/**
	 * 리소스 번들.
	 * 한 번 읽은 파일은 캐시에 저장해둔다.
	 *
	 * @param path      incl. file extension
	 * @param baseClass 리소스를 불러올 기준 클래스
	 * @return
	 */
	public static ResourceBundle getBundle(final @NotNull String path, final @NotNull Class<?> baseClass) {
		if (null == CACHE) {
			CACHE = new HashMap<>();
		}
		if (CACHE.containsKey(path)) {
			return CACHE.get(path);
		} else {
			ResourceBundle bundle = new ResourceBundle(path, baseClass);
			CACHE.put(path, bundle);
			return bundle;
		}
	}

	private String baseName;
	private final Map<Locale, Properties> data;

	/**
	 * 외부 파일로부터 읽는다.
	 * 캐시에 저장되지 않는다. 즉, 인스턴스를 생성할 때마다 매번 파일을 읽는다.
	 *
	 * @param file
	 * @throws MalformedURLException
	 */
	public ResourceBundle(final @NotNull File file) throws MalformedURLException {
		this(file.toURI().toURL());
	}

	private ResourceBundle(final @NotNull URL url) {
		data = new HashMap<>();
		final String path = url.getPath();

		// 파일 경로를 쪼개서 파일 이름만 뽑는다.
		String[] slug = path.split("/");
		this.baseName = slug[slug.length - 1];
		// 다시 파일 이름에서 확장자 부분을 없앤다.
		slug = baseName.split("\\.");
		this.baseName = baseName.replace("." + slug[slug.length - 1], Stringz.EMPTY_STRING);

		// 파일이 포함된 디렉토리에서 유사한 이름의 파일들을 추려낸다.
		File folder = new File(path).getParentFile();
		File[] files = folder.listFiles((file, s) -> s.startsWith(baseName));
		if (null == files) {
			throw new RuntimeException();
		}

		java.util.Arrays.sort(files, Comparator.comparing(File::getName));

		for (File f : files) {
			Locale locale;

			// 파일 이름에서 확장자 부분을 제거하고
			slug = path.split("\\.");
			String extension = slug[slug.length - 1];
			String name = f.getName().replace("." + extension, Stringz.EMPTY_STRING);
			// 파일 이름을 쪼개서 로케일 정보를 얻는다.
			slug = name.replaceFirst(baseName, Stringz.EMPTY_STRING)
					.split("_");
			if (slug.length > 3) {
				locale = new Locale(slug[1], slug[2]);
			} else if (slug.length == 2) {
				locale = new Locale(slug[1]);
			} else {
				locale = Locale.ROOT;
			}

			try {
				Properties properties = null;
				if (locale == Locale.ROOT) {
					properties = new Properties();
				} else {
					properties = new Properties(data.get(Locale.ROOT));
				}
				if (f.getName().endsWith(EXT_XML)) {
					properties.loadXml(new FileInputStream(f));
				} else {
					properties.load(new FileInputStream(f));
				}

				data.put(locale, properties);
			} catch (IOException ignore) {

			}

		}

		//CACHE.put(url.getPath(), this);
	}

	/**
	 * @param path      리소스 경로, 파일확장자 포함
	 * @param baseClass 리소스를 가져올 기준 클래스
	 */
	private ResourceBundle(final @NotNull String path, final @NotNull Class<?> baseClass) {
		this(baseClass.getResource(path));
	}

	/**
	 * Get value from that locale, with that key.
	 * if not found, returns a value within a root locale.
	 *
	 * @param key    key
	 * @param locale locate
	 * @return optional value
	 */
	@NotNull
	public Optional<String> get(final @NotNull String key, final @NotNull Locale locale) {
		if (data.containsKey(locale)) {
			// matches locale
			return data.get(locale).getProperty(key);
		} else {
			for (Locale loc : data.keySet()) {
				// matches language only
				if (loc.getLanguage().equals(locale.getLanguage())) {
					return data.get(loc).getProperty(key);
				}
			}
			// retrieve a value from root locale
			return data.get(Locale.ROOT).getProperty(key);
		}
	}

	@Nullable
	public String getOrNull(final @NotNull String key, final @NotNull Locale locale) {
		return get(key, locale).orElse(null);
	}

	@NotNull
	public String getOrEmpty(final @NotNull String key, final @NotNull Locale locale) {
		return get(key, locale).orElse(Stringz.EMPTY_STRING);
	}

	/**
	 * Get value with a default locale, with that key.
	 *
	 * @param key key
	 * @return optional value
	 */
	@NotNull
	public Optional<String> get(final String key) {
		return get(key, Locale.getDefault());
	}

	@Nullable
	public String getOrNull(final String key) {
		return get(key).orElse(null);
	}

	@NotNull
	public String getOrEmpty(final String key) {
		return get(key).orElse(Stringz.EMPTY_STRING);
	}

	/**
	 * Get locales with the bundle
	 *
	 * @return set
	 */
	@NotNull
	public Set<Locale> getAvailableLocales() {
		return data.keySet();
	}
}
