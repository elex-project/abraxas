/*
 * Project Abraxas (a.k.a core-utils)
 * http://www.elex-project.com/
 * Copyright (c) 2019. Elex. All Rights Reserved.
 */

package com.elex_project.abraxas;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.io.File;
import java.io.IOException;
import java.lang.annotation.Annotation;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.URL;
import java.util.*;

/**
 * 리플렉션
 *
 * @author Elex
 */
public final class Reflectz {
	private Reflectz() {
	}

	/**
	 * 클래스 어노테이션 리스트
	 *
	 * @param clazz 대상 클래스
	 * @return set
	 */
	@NotNull
	public static Set<Annotation> getAnnotations(@NotNull final Class<?> clazz) {
		return Arrayz.concatIgnoreDuplicate(clazz.getDeclaredAnnotations(), clazz.getAnnotations());
	}

	/**
	 * 클래스 어노테이션 리스트
	 *
	 * @param object 대상 객체
	 * @return set
	 */
	@NotNull
	public static Set<Annotation> getAnnotations(@NotNull final Object object) {
		return getAnnotations(object.getClass());
	}

	/**
	 * 클래스 어노테이션 리스트
	 *
	 * @param clazz      대상 클래스
	 * @param annotation 찾고자하는 어노테이션
	 * @return 어노테이션
	 */
	@Nullable
	public static <T extends Annotation> T getAnnotation(@NotNull final Class<?> clazz, @NotNull final Class<T> annotation) {
		T a = clazz.getDeclaredAnnotation(annotation);
		if (null == a) {
			return clazz.getAnnotation(annotation);
		} else {
			return a;
		}
	}

	/**
	 * 필드 어노테이션 리스트
	 *
	 * @param field 필드
	 * @return set
	 */
	@NotNull
	public static Set<Annotation> getAnnotations(@NotNull final Field field) {
		return Arrayz.concatIgnoreDuplicate(field.getDeclaredAnnotations(), field.getAnnotations());
	}

	/**
	 * 필드 어노테이션
	 *
	 * @param field      필드
	 * @param annotation 찾고자하는 어노테이션
	 * @param <T>        어노테이션
	 * @return 어노테이션
	 */
	@Nullable
	public static <T extends Annotation> T getAnnotation(@NotNull Field field, @NotNull Class<T> annotation) {
		T a = field.getDeclaredAnnotation(annotation);
		if (null == a) {
			return field.getAnnotation(annotation);
		} else {
			return a;
		}
	}

	/**
	 * 메서드 어노테이션 리스트
	 *
	 * @param method 메서드
	 * @return set
	 */
	@NotNull
	public static Set<Annotation> getAnnotations(@NotNull final Method method) {
		return Arrayz.concatIgnoreDuplicate(method.getDeclaredAnnotations(), method.getAnnotations());
	}

	/**
	 * 메서드 어노테이션
	 *
	 * @param method     메서드
	 * @param annotation 어노테이션
	 * @param <T>        어노테이션
	 * @return 어노테이션
	 */
	@Nullable
	public static <T extends Annotation> T getAnnotation(@NotNull final Method method, @NotNull final Class<T> annotation) {
		T a = method.getDeclaredAnnotation(annotation);
		if (null == a) {
			return method.getAnnotation(annotation);
		} else {
			return a;
		}
	}

	/**
	 * 생성자 리스트
	 *
	 * @param clazz 대상 클래스
	 * @return 생성자 배열
	 */
	@NotNull
	public static Set<Constructor<?>> getConstructors(@NotNull final Class<?> clazz) {
		return Arrayz.concatIgnoreDuplicate(clazz.getDeclaredConstructors(), clazz.getConstructors());
	}

	/**
	 * 생성자 리스트
	 *
	 * @param clazz 대상 클래스
	 * @param args  찾고자 하는 생성자의 파라미터 목록
	 * @return 생성자
	 * @throws NoSuchMethodException 그런거 없음
	 */
	public static Constructor<?> getConstructor(@NotNull final Class<?> clazz, final Class<?>... args) throws NoSuchMethodException {
		try {
			return clazz.getDeclaredConstructor(args);
		} catch (NoSuchMethodException e) {
			return clazz.getConstructor(args);
		}
	}

	/**
	 * 필드 리스트
	 *
	 * @param clazz 대상 클래스
	 * @return 배열
	 */
	@NotNull
	public static Set<Field> getFields(@NotNull final Class<?> clazz) {
		return Arrayz.concatIgnoreDuplicate(clazz.getDeclaredFields(), clazz.getFields());
	}

	/**
	 * 필드 리스트
	 * @param clazz 대상 클래스
	 * @param annotation 어노태이션
	 * @return 필드
	 */
	@NotNull
	public static Set<Field> getFields(@NotNull final Class<?> clazz, @NotNull final Class<? extends Annotation> annotation) {
		Set<Field> result = new HashSet<>();
		Set<Field> fields = getFields(clazz);
		for (Field field : fields) {
			Annotation a = getAnnotation(field, annotation);
			if (null != a) {
				result.add(field);
			}
		}
		//methods.clear();
		return result;
	}

	/**
	 * 필드
	 *
	 * @param clazz     대상 클래스
	 * @param fieldName 필드 이름
	 * @return 필드
	 * @throws NoSuchFieldException 그런거 없음
	 */
	public static Field getField(@NotNull final Class<?> clazz, @NotNull final String fieldName) throws NoSuchFieldException {
		try {
			return clazz.getDeclaredField(fieldName);
		} catch (NoSuchFieldException e) {
			return clazz.getField(fieldName);
		}
	}

	/**
	 * 필드
	 *
	 * @param object    대상 인스턴스
	 * @param fieldName 필드 이름
	 * @return 필드
	 * @throws NoSuchFieldException 그런거 없음
	 */
	public static Field getField(@NotNull final Object object, @NotNull final String fieldName) throws NoSuchFieldException {
		return getField(object.getClass(), fieldName);
	}

	/**
	 * 메서드 리스트
	 *
	 * @param clazz 대상 클래스
	 * @return 배열
	 */
	@NotNull
	public static Set<Method> getMethods(@NotNull final Class<?> clazz) {
		return Arrayz.concatIgnoreDuplicate(clazz.getDeclaredMethods(), clazz.getMethods());
	}

	/**
	 * 메서드 리스트
	 * @param clazz 대상 클래스
	 * @param annotation 어노테이션
	 * @return 메서드
	 */
	@NotNull
	public static Set<Method> getMethods(@NotNull final Class<?> clazz, @NotNull final Class<? extends Annotation> annotation) {
		Set<Method> result = new HashSet<>();
		Set<Method> methods = getMethods(clazz);
		for (Method method : methods) {
			Annotation a = getAnnotation(method, annotation);
			if (null != a) {
				result.add(method);
			}
		}
		//methods.clear();
		return result;
	}

	/**
	 * 메서드
	 *
	 * @param clazz      대상 클래스
	 * @param methodName 메서드 이름
	 * @return 메서드
	 * @throws NoSuchMethodException 그런거 없음
	 */
	public static Method getMethod(@NotNull final Class<?> clazz, @NotNull final String methodName) throws NoSuchMethodException {
		try {
			return clazz.getDeclaredMethod(methodName);
		} catch (NoSuchMethodException e) {
			return clazz.getMethod(methodName);
		}
	}

	/**
	 * 메서드
	 *
	 * @param object     대상 인스턴스
	 * @param methodName 메서드 이름
	 * @return 메서드
	 * @throws NoSuchMethodException 그런거 없음
	 */
	public static Method getMethod(@NotNull final Object object, @NotNull final String methodName) throws NoSuchMethodException {
		return getMethod(object.getClass(), methodName);
	}

	/**
	 * 인스턴스 멤버의 값
	 *
	 * @param object    인스턴스
	 * @param fieldName 필드 이름
	 * @param type      필드의 자료형
	 * @param <T>       반환 자료형
	 * @return 값
	 * @throws InvocationTargetException e
	 * @throws IllegalAccessException e
	 */
	public static <T> T getValue(@NotNull final Object object, @NotNull final String fieldName, @NotNull final Class<T> type)
			throws  InvocationTargetException, IllegalAccessException {
		if (type.equals(getType(object, fieldName))) {
			//noinspection unchecked
			return (T) getValue(object, fieldName);
		} else {
			throw new IllegalArgumentException();
		}
	}

	/**
	 * 필드 혹은 메서드의 값
	 * 먼저 필드명으로 찾고, 없으면 같은 이름의 메서드를 찾아보고, 그래도 없으면 getter 메서드를 찾아본다.
	 *
	 * @param object    인스턴스
	 * @param fieldName 필드 이름
	 * @return 값
	 * @throws InvocationTargetException e
	 * @throws IllegalAccessException e
	 */
	//@ReflectPermission("suppressAccessChecks")
	@Nullable
	public static Object getValue(@NotNull final Object object, @NotNull final String fieldName)
			throws InvocationTargetException, IllegalAccessException {
		for (Field field : getFields(object.getClass())) { // 필드 탐색
			if (fieldName.equals(field.getName())) {
				if (!field.isAccessible()) {
					field.setAccessible(true);
					Object result = field.get(object);
					field.setAccessible(false);
					return result;
				} else {
					return field.get(object);
				}
			}
		}
		for (Method method : getMethods(object.getClass())) { // 메서드 탐색
			if (fieldName.equals(method.getName()) ||
					("get" + Stringz.capitalizeFirstLetter(fieldName)).equals(method.getName())) {
				if (!method.isAccessible()) {
					method.setAccessible(true);
					Object result = method.invoke(object);
					method.setAccessible(false);
					return result;
				} else {
					return method.invoke(object);
				}
			}
		}

		return null;
	}

	/**
	 * for Multi-level Object
	 *
	 * @param object     인스턴스
	 * @param fieldNames 순서대로, 자식 항목으로 내려가며 값을 가져온다.
	 * @return 값
	 * @throws InvocationTargetException e
	 * @throws IllegalAccessException e
	 */
	public static Object getValue(@NotNull final Object object, @NotNull final String... fieldNames)
			throws InvocationTargetException, IllegalAccessException {
		Object value = null;
		for (String fieldName : fieldNames) {
			if (null == value) {
				value = getValue(object, fieldName);
			} else {
				value = getValue(value, fieldName);
			}
		}
		return value;
	}

	public static <T> T getValue(@NotNull final Object object, @NotNull final Class<T> type, @NotNull final String... fieldName)
			throws InvocationTargetException, IllegalAccessException {
		if (type.equals(getType(object, fieldName))) {
			//noinspection unchecked
			return (T) getValue(object, fieldName);
		} else {
			throw new IllegalArgumentException();
		}
	}

	/**
	 * 필드 또는 메서드의 자료형
	 *
	 * @param clazz     클래스
	 * @param fieldName 필드 이름
	 * @return 자료형
	 */
	@Nullable
	public static Class<?> getType(@NotNull final Class<?> clazz, @NotNull final String fieldName) {
		for (Field field : getFields(clazz)) { // 필드 탐색
			if (fieldName.equals(field.getName())) {
				//return field.getType();
				if (!field.isAccessible()) {
					field.setAccessible(true);
					Class<?> result = field.getType();
					field.setAccessible(false);
					return result;
				} else {
					return field.getType();
				}
			}
		}
		for (Method method : getMethods(clazz)) { // 메서드 탐색
			if (fieldName.equals(method.getName()) ||
					("get" + Stringz.capitalizeFirstLetter(fieldName)).equals(method.getName())) {
				if (!method.isAccessible()) {
					method.setAccessible(true);
					Class<?> result = method.getReturnType();
					method.setAccessible(false);
					return result;
				} else {
					return method.getReturnType();
				}
			}
		}

		return null;
	}

	/**
	 * 필드 또는 메서드의 자료형
	 * @param object 객체
	 * @param fieldName 필드 이름
	 * @return 클래스
	 */
	public static Class<?> getType(@NotNull final Object object, @NotNull final String fieldName) {
		return getType(object.getClass(), fieldName);
	}

	/**
	 * 필드 또는 메서드의 자료형.
	 * @param object 객체
	 * @param fieldNames 필드 이름. 필드의 필드의 필드의 필드.
	 * @return 클래스
	 */
	public static Class<?> getType(@NotNull final Object object, @NotNull final String... fieldNames) {
		Class<?> value = null;
		for (String fieldName : fieldNames) {
			if (null == value) {
				value = getType(object, fieldName);
			} else {
				value = getType(value, fieldName);
			}
		}
		return value;
	}


	/**
	 * Recursive method used to find all classes in a given directory and subdirs.
	 *
	 * @param directory   The base directory
	 * @param packageName The package name for classes found inside the base directory
	 * @return The classes
	 * @throws ClassNotFoundException e
	 */
	@NotNull
	public static List<Class<?>> getClasses(@NotNull final File directory, @NotNull final String packageName) throws ClassNotFoundException {
		List<Class<?>> classes = new ArrayList<>();
		if (!directory.exists()) {
			return classes;
		}
		File[] files = directory.listFiles();
		if (null == files) throw new ClassNotFoundException("No such class file.");

		for (File file : files) {
			if (file.isDirectory()) {
				assert !file.getName().contains(".");
				classes.addAll(getClasses(file, packageName + "." + file.getName()));
			} else if (file.getName().endsWith(".class")) {
				classes.add(Class.forName(packageName + '.' + file.getName().substring(0, file.getName().length() - 6)));
			}
		}
		return classes;
	}

	/**
	 * Scans all classes accessible from the context class loader which belong to the given package and subpackages.
	 *
	 * @param packageName The base package
	 * @return The classes
	 * @throws ClassNotFoundException e
	 * @throws IOException e
	 */
	@NotNull
	public static List<Class<?>> getClasses(@NotNull final String packageName)
			throws ClassNotFoundException, IOException {
		ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
		//assert classLoader != null;
		String path = packageName.replace('.', '/');
		Enumeration<URL> resources = classLoader.getResources(path);
		List<File> dirs = new ArrayList<>();
		while (resources.hasMoreElements()) {
			URL resource = resources.nextElement();
			dirs.add(new File(resource.getFile()));
		}
		ArrayList<Class<?>> classes = new ArrayList<>();
		for (File directory : dirs) {
			classes.addAll(getClasses(directory, packageName));
		}
		return classes;
	}

	/**
	 * 지정된 패키지 및 하위 패키지에서 해당 어노테이션으로 마킹된 클래스를 찾는다.
	 *
	 * @param packageName package anem
	 * @param annotation annotation
	 * @return classes
	 * @throws ClassNotFoundException e
	 * @throws IOException e
	 * @see #getClasses(String)
	 */
	@NotNull
	public static List<Class<?>> getClasses(final @NotNull String packageName, @NotNull final Class<? extends Annotation> annotation)
			throws ClassNotFoundException, IOException {
		//List<Class> classes = getClasses(packageName);
		ArrayList<Class<?>> classes = new ArrayList<>();
		for (Class<?> c : getClasses(packageName)) {
			if (c.isAnnotationPresent(annotation)) {
				classes.add(c);
			}
		}
		return classes;
	}
}
