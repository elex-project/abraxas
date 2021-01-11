/*
 * Project Abraxas (a.k.a core-utils)
 * http://www.elex-project.com/
 * Copyright (c) 2017. Elex. All Rights Reserved.
 */

package com.elex_project.abraxas;

import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.w3c.dom.Document;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.OpenOption;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

/**
 * I/O
 *
 * @author Elex
 */
public final class IOz {
	/**
	 * {http://xml.apache.org/xslt}indent-amount
	 */
	private static final String INDENT_AMOUNT = "{http://xml.apache.org/xslt}indent-amount";
	/**
	 * yes
	 */
	private static final String YES = "yes";
	/**
	 * no
	 */
	private static final String NO = "no";

	private IOz() {

	}

	@NotNull
	@Contract("_ -> new")
	public static BufferedReader getReader(final InputStream inputStream) {
		try {
			return new BufferedReader(new InputStreamReader(inputStream, StandardCharsets.UTF_8));
		} catch (NoClassDefFoundError e) {
			try {
				return new BufferedReader(new InputStreamReader(inputStream, Stringz.UTF_8));
			} catch (UnsupportedEncodingException ex) {
				throw new RuntimeException(ex);
			}
		}
	}

	@NotNull
	public static BufferedReader getReader(final File file) throws FileNotFoundException {
		return getReader(new FileInputStream(file));
	}

	@NotNull
	public static BufferedReader getReader(final Path path) throws IOException {
		return Files.newBufferedReader(path);
	}

	@NotNull
	@Contract("_ -> new")
	public static BufferedWriter getWriter(final OutputStream outputStream) {
		try {
			return new BufferedWriter(new OutputStreamWriter(outputStream, StandardCharsets.UTF_8));
		} catch (NoClassDefFoundError e) {
			try {
				return new BufferedWriter(new OutputStreamWriter(outputStream, Stringz.UTF_8));
			} catch (UnsupportedEncodingException ex) {
				throw new RuntimeException(ex);
			}
		}
	}

	@NotNull
	public static BufferedWriter getWriter(final File file) throws FileNotFoundException {
		return getWriter(new FileOutputStream(file));
	}

	@NotNull
	public static BufferedWriter getWriter(final Path path) throws IOException {
		return Files.newBufferedWriter(path);
	}

	@NotNull
	public static Document readXMLFrom(final File file) throws ParserConfigurationException, IOException, SAXException {
		return readXMLFrom(new FileInputStream(file));
	}

	@NotNull
	public static Document readXMLFrom(final Path path) throws ParserConfigurationException, IOException, SAXException {
		return readXMLFrom(getReader(path));
	}

	@NotNull
	public static Document readXMLFrom(final String str) throws ParserConfigurationException, IOException, SAXException {
		return readXMLFrom(toInputStream(str));
	}

	@NotNull
	public static Document readXMLFrom(final Reader reader) throws ParserConfigurationException, IOException, SAXException {
		return readXMLFrom(readStringFrom(reader));
	}

	@NotNull
	public static Document readXMLFrom(final InputStream is) throws ParserConfigurationException, IOException, SAXException {
		Document xml = DocumentBuilderFactory.newInstance()
				.newDocumentBuilder().parse(is);
		xml.normalize();
		return xml;
	}

	public static void writeXMLTo(final Document document, final File file) throws TransformerException {
		DOMSource xmlDOM = new DOMSource(document);
		StreamResult xmlFile = new StreamResult(file);
		TransformerFactory.newInstance().newTransformer().transform(xmlDOM, xmlFile);
	}

	public static void writeStreamTo(@NotNull final InputStream inputStream, final File outFile) throws IOException {
		BufferedOutputStream outputStream = new BufferedOutputStream(
				new FileOutputStream(outFile));

		byte[] buffer = new byte[1024];
		int len;
		while ((len = inputStream.read(buffer)) > 0) {
			outputStream.write(buffer, 0, len);
		}
		inputStream.close();
		outputStream.close();
	}

	public static void writeStreamTo(@NotNull final InputStream inputStream, final Path outPath, OpenOption... openOptions) throws IOException {
		BufferedOutputStream outputStream = new BufferedOutputStream(Files.newOutputStream(outPath, openOptions));

		byte[] buffer = new byte[1024];
		int len;
		while ((len = inputStream.read(buffer)) > 0) {
			outputStream.write(buffer, 0, len);
		}
		inputStream.close();
		outputStream.close();
	}

	/**
	 * XML Document를 문자열로.
	 *
	 * @param document           XML Document
	 * @param omitXmlDeclaration XML 선언문 생략.
	 * @param indent             들여쓰기.
	 * @return 문자열.
	 * @throws TransformerException
	 */
	@NotNull
	public static String convertXMLToString(final Document document, final boolean omitXmlDeclaration, final int indent) throws TransformerException {
		Transformer transformer = TransformerFactory.newInstance().newTransformer();

		transformer.setOutputProperty(OutputKeys.OMIT_XML_DECLARATION,
				(omitXmlDeclaration ? YES : NO));

		if (indent > 0) {
			transformer.setOutputProperty(OutputKeys.INDENT, YES);
			transformer.setOutputProperty(INDENT_AMOUNT, String.valueOf(indent));
		}

		StringWriter writer = new StringWriter();

		transformer.transform(new DOMSource(document), new StreamResult(writer));


		return writer.getBuffer().toString();
	}

	@NotNull
	public static String convertXMLToString(final Document document) throws TransformerException {
		return convertXMLToString(document, false, 0);
	}

	@NotNull
	public static String convertXMLToString(final Document document, final int indent) throws TransformerException {
		return convertXMLToString(document, false, indent);
	}

	/**
	 * 입력 스트림에서 바이트 배열을 읽어온다.
	 *
	 * @param inputStream 입력스트림
	 * @return 바이트 배열
	 * @throws IOException ..
	 */
	@NotNull
	public static byte[] readByteArrayFrom(@NotNull final InputStream inputStream) throws IOException {
		ByteArrayOutputStream buffer = new ByteArrayOutputStream();
		int nRead;
		byte[] buf = new byte[1024];
		while ((nRead = inputStream.read(buf, 0, buf.length)) != -1) {
			buffer.write(buf, 0, nRead);
		}

		buffer.flush();
		buffer.close();
		inputStream.close();
		return buffer.toByteArray();
	}


	/**
	 * 파일에서 바이트 배열을 읽어 온다.
	 *
	 * @param file 파일
	 * @return 바이트 배열
	 * @throws IOException ..
	 */
	@NotNull
	public static byte[] readByteArrayFrom(@NotNull final File file) throws IOException {
		return readByteArrayFrom(new FileInputStream(file));
	}

	@NotNull
	public static byte[] readByteArrayFrom(@NotNull final Path path) throws IOException {
		return Files.readAllBytes(path);
		//return readByteArrayFrom(new FileInputStream(file));
	}

	@NotNull
	public static byte[] readAllBytes(@NotNull final InputStream inputStream) throws IOException {
		return readByteArrayFrom(inputStream);
	}

	@NotNull
	public static byte[] readAllBytes(@NotNull final File file) throws IOException {
		return readByteArrayFrom(file);
	}

	@NotNull
	public static byte[] readAllBytes(@NotNull final Path path) throws IOException {
		return readByteArrayFrom(path);
	}

	/**
	 * 바이트 배열을 출력 스트림에 기록한다.
	 *
	 * @param bytes        바이트 배열
	 * @param outputStream 출력 스트림
	 * @throws IOException ..
	 */
	public static void writeByteArrayTo(final byte[] bytes, final OutputStream outputStream) throws IOException {
		BufferedOutputStream os = new BufferedOutputStream(outputStream);
		os.write(bytes, 0, bytes.length);
		os.close();
	}

	/**
	 * 바이트 배열을 파일에 기록한다.
	 *
	 * @param bytes 바이트 배열
	 * @param file  파일
	 * @throws IOException ..
	 */
	public static void writeByteArrayTo(final byte[] bytes, final File file) throws IOException {
		writeByteArrayTo(bytes, new FileOutputStream(file));
	}

	/**
	 * 입력 스트림에서 문자열을 읽는다.
	 *
	 * @param inputStream 입력 스트림
	 * @return 문자열
	 * @throws IOException ..
	 */
	@NotNull
	public static String readStringFrom(@NotNull final InputStream inputStream) throws IOException {
		return readStringFrom(inputStream, Stringz.NEW_LINE);
	}

	@NotNull
	public static String readStringFrom(@NotNull final InputStream inputStream, final CharSequence lineSeparator) throws IOException {
		BufferedReader reader;
		try {
			reader = new BufferedReader(new InputStreamReader(inputStream, StandardCharsets.UTF_8));
		} catch (NoClassDefFoundError e) {
			reader = new BufferedReader(new InputStreamReader(inputStream, Stringz.UTF_8));
		}
		String str = readStringFrom(reader, lineSeparator);
		inputStream.close();
		reader.close();
		return str;
	}

	@NotNull
	public static String readStringFrom(@NotNull final Reader reader) throws IOException {
		return readStringFrom(reader, Stringz.NEW_LINE);
	}

	@NotNull
	public static String readStringFrom(@NotNull final Reader reader, @Nullable final CharSequence lineSeparator) throws IOException {
		BufferedReader bReader = new BufferedReader(reader);
		StringBuilder sb = new StringBuilder();
		String line;
		while ((line = bReader.readLine()) != null) {
			sb.append(line);
			if (null != lineSeparator) {
				sb.append(lineSeparator);
			}
		}
		bReader.close();
		return sb.toString();
	}

	@NotNull
	public static List<String> readLinesFrom(@NotNull final Path path) throws IOException {
		return Files.readAllLines(path);
	}

	@NotNull
	public static List<String> readLinesFrom(@NotNull final File file) throws IOException {
		return readLinesFrom(new FileInputStream(file));
	}

	@NotNull
	public static List<String> readLinesFrom(@NotNull final Reader reader) throws IOException {
		ArrayList<String> strings = new ArrayList<>();

		BufferedReader bReader = new BufferedReader(reader);
		String line;
		while ((line = bReader.readLine()) != null) {
			strings.add(line);
		}
		bReader.close();

		return strings;
	}

	@NotNull
	public static List<String> readLinesFrom(@NotNull final InputStream inputStream) throws IOException {
		BufferedReader reader;
		try {
			reader = new BufferedReader(new InputStreamReader(inputStream, StandardCharsets.UTF_8));
		} catch (NoClassDefFoundError e) {
			reader = new BufferedReader(new InputStreamReader(inputStream, Stringz.UTF_8));
		}
		List<String> strings = readLinesFrom(reader);
		reader.close();

		return strings;
	}

	/**
	 * 파일에서 문자열을 읽는다.
	 *
	 * @param file 파일
	 * @return 문자열
	 * @throws IOException ..
	 */
	@NotNull
	public static String readStringFrom(@NotNull final File file) throws IOException {
		return readStringFrom(file, Stringz.NEW_LINE);
	}

	@NotNull
	public static String readStringFrom(@NotNull final File file, CharSequence lineSeparator) throws IOException {
		return readStringFrom(new FileInputStream(file), lineSeparator);
	}

	/**
	 * 출력 스트림에 문자열을 기록한다.
	 *
	 * @param string       문자열
	 * @param outputStream 출력스트림
	 * @throws IOException ..
	 */
	public static void writeStringTo(@NotNull final String string, @NotNull final OutputStream outputStream) throws IOException {
		BufferedWriter writer;
		try {
			writer = new BufferedWriter(new OutputStreamWriter(outputStream, StandardCharsets.UTF_8));
		} catch (NoClassDefFoundError e) {
			writer = new BufferedWriter(new OutputStreamWriter(outputStream, Stringz.UTF_8));
		}
		writer.write(string);
		writer.close();
	}

	/**
	 * 파일에 문자열을 기록한다.
	 *
	 * @param string 문자열
	 * @param file   파일
	 * @throws IOException ..
	 */
	public static void writeStringTo(@NotNull final String string, @NotNull final File file) throws IOException {
		writeStringTo(string, new FileOutputStream(file));
	}

	public static void readLineFrom(@NotNull final File file, @NotNull final OnReadingLineListener listener) throws IOException {
		readLineFrom(new FileInputStream(file), listener);
	}

	public static void readLineFrom(@NotNull final InputStream inputStream, @NotNull final OnReadingLineListener listener) throws IOException {
		BufferedReader reader;
		try {
			reader = new BufferedReader(new InputStreamReader(inputStream, StandardCharsets.UTF_8));
		} catch (NoClassDefFoundError e) {
			reader = new BufferedReader(new InputStreamReader(inputStream, Stringz.UTF_8));
		}

		if (null != listener) listener.onStarted();
		String line = null;
		while (reader.ready() && null != (line = reader.readLine())) {
			if (null != listener) listener.line(line);
		}
		if (null != listener) listener.onFinished();

		inputStream.close();
		reader.close();
	}

	/**
	 * UTF-8 문자열을 바이트 배열 입력 스트림으로.
	 *
	 * @param str utf-8
	 * @return ByteArrayInputStream
	 * @throws UnsupportedEncodingException
	 */
	@NotNull
	@Contract("_ -> new")
	public static InputStream toInputStream(@NotNull final String str) throws UnsupportedEncodingException {
		try {
			return new ByteArrayInputStream(str.getBytes(StandardCharsets.UTF_8));
		} catch (NoClassDefFoundError e) {
			return new ByteArrayInputStream(str.getBytes(Stringz.UTF_8));
		}
	}

	public static void appendTo(final File file, final String line) throws IOException {
		PrintWriter out = new PrintWriter(new BufferedWriter(new FileWriter(file, true)));
		out.println(line);
		out.close();
	}

	/**
	 * @param clazz    some class
	 * @param resource path for a resource file eg. /kr/pe/elex/some_file.txt
	 * @return input stream
	 * @see Class#getResourceAsStream(String)
	 */
	public static InputStream getResourceAsStream(@NotNull Class<?> clazz, @NotNull String resource) {
		return clazz.getResourceAsStream(resource);
	}

	/**
	 * @param clazz    some class
	 * @param resource path for a resource file eg. /kr/pe/elex/some_file.txt
	 * @return BufferedReader
	 * @see #getResourceAsStream(Class, String)
	 */
	@NotNull
	public static BufferedReader getResourceAsReader(@NotNull Class<?> clazz, @NotNull String resource) {
		final InputStream is = getResourceAsStream(clazz, resource);
		try {
			return new BufferedReader(new InputStreamReader(is, StandardCharsets.UTF_8));
		} catch (NoClassDefFoundError e) {
			try {
				return new BufferedReader(new InputStreamReader(is, Stringz.UTF_8));
			} catch (UnsupportedEncodingException ex) {
				return new BufferedReader(new InputStreamReader(is));
			}
		}
	}

	@NotNull
	public static RandomAccessFile openRandomAccessFile(File file) throws FileNotFoundException {
		return openRandomAccessFile(file, "rw");
	}

	/**
	 * "r"	Open for reading only. Invoking any of the write methods of the resulting object will cause an IOException to be thrown.
	 * <br>
	 * "rw"	Open for reading and writing. If the file does not already exist then an attempt will be made to create it.
	 * <br>
	 * "rws"	Open for reading and writing, as with "rw", and also require that every update to the file's content or metadata be written synchronously to the underlying storage device.
	 * <br>
	 * "rwd"  	Open for reading and writing, as with "rw", and also require that every update to the file's content be written synchronously to the underlying storage device.
	 * <br>
	 *
	 * @param file
	 * @param mode
	 * @return
	 * @throws FileNotFoundException
	 */
	@NotNull
	@Contract("_, _ -> new")
	public static RandomAccessFile openRandomAccessFile(File file, String mode) throws FileNotFoundException {
		return new RandomAccessFile(file, mode);
	}

	public interface OnReadingLineListener {
		public void onStarted();

		public void line(final String line);

		public void onFinished();
	}

}
