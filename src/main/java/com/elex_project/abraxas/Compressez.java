/*
 * Project Abraxas (a.k.a core-utils)
 * http://www.elex-project.com/
 * Copyright (c) 2018. Elex. All Rights Reserved.
 */

package com.elex_project.abraxas;

import org.jetbrains.annotations.NotNull;

import java.io.*;
import java.util.Enumeration;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;
import java.util.zip.*;

/**
 * ZIP 압축 유틸리티
 *
 * @author Elex
 */
public final class Compressez {

	private static final int BUFFER_SIZE = 1024;

	private Compressez() {
	}

	private static void addEntry(final ZipOutputStream zipOutputStream, String path, final File file)
			throws IOException {
		if (null == path) {
			path = "";
		} else {
			path += "/";
		}

		if (file.isDirectory()) {
			File[] children = file.listFiles();
			if (null != children) {
				for (File f : children) {
					addEntry(zipOutputStream, path + file.getName(), f);
				}
			}

		} else {
			FileInputStream fis = new FileInputStream(file);

			ZipEntry zipEntry = new ZipEntry(path + file.getName());
			zipOutputStream.putNextEntry(zipEntry);

			final byte[] bytes = new byte[BUFFER_SIZE];
			int length;
			while ((length = fis.read(bytes)) >= 0) {
				zipOutputStream.write(bytes, 0, length);
			}

			fis.close();
		}
	}


	/**
	 * 파일 또는 디렉토리를 압축한다.
	 *
	 * @param zipFile    압축 파일
	 * @param filesToZip 압축할 파일 또는 디렉토리
	 * @throws IOException ...
	 */
	public static void zip(final File zipFile, @NotNull final File... filesToZip)
			throws IOException {
		ZipOutputStream zos = new ZipOutputStream(new FileOutputStream(zipFile));

		for (File fileToZip : filesToZip) {
			addEntry(zos, null, fileToZip);
		}

		zos.close();

	}

	/**
	 * 압축 해제
	 *
	 * @param zipFile 압축 파일
	 * @param toDir   압축을 해제할 폴더
	 * @throws IOException ..
	 */
	public static void unzip(final File zipFile, final File toDir)
			throws IOException {
		//toDir.mkdirs();
		byte[] buffer = new byte[BUFFER_SIZE];

		ZipInputStream zis = new ZipInputStream(new FileInputStream(zipFile));
		ZipEntry zipEntry = zis.getNextEntry();
		while (zipEntry != null) {
			String fileName = zipEntry.getName();
			File newFile = new File(toDir, fileName);
			Filez.createFile(newFile);

			FileOutputStream fos = new FileOutputStream(newFile);
			int len;
			while ((len = zis.read(buffer)) > 0) {
				fos.write(buffer, 0, len);
			}
			fos.close();
			//zis.closeEntry();
			zipEntry = zis.getNextEntry();
		}
		zis.close();
	}


	/**
	 * gzip
	 *
	 * @param gzipFile    생성할 압축 파일
	 * @param inputStream 데이터
	 * @throws IOException
	 */
	public static void gzip(@NotNull final File gzipFile, final InputStream inputStream) throws IOException {
		byte[] buffer = new byte[BUFFER_SIZE];
		if (!gzipFile.exists()) {
			gzipFile.createNewFile();
		}

		GZIPOutputStream outputStream = new GZIPOutputStream(
				new BufferedOutputStream(new FileOutputStream(gzipFile)));

		int len;
		while ((len = inputStream.read(buffer)) > 0) {
			outputStream.write(buffer, 0, len);
		}

		inputStream.close();
		outputStream.close();
	}

	public static void gzip(final File gzipFile, final File inputFile) throws IOException {
		gzip(gzipFile, new FileInputStream(inputFile));
	}

	public static void ungzip(final File gzipFile, @NotNull final File toFile) throws IOException {
		byte[] buffer = new byte[BUFFER_SIZE];
		if (!toFile.exists()) {
			toFile.createNewFile();
		}

		GZIPInputStream inputStream = new GZIPInputStream(
				new BufferedInputStream(new FileInputStream(gzipFile)));
		BufferedOutputStream outputStream = new BufferedOutputStream(new FileOutputStream(toFile));

		int len;
		while ((len = inputStream.read(buffer)) > 0) {
			outputStream.write(buffer, 0, len);
		}

		inputStream.close();
		outputStream.close();
	}


	/**
	 * PHP에서 압축을 해제할 때에는
	 * gzcompress()를 사용하시오.
	 * <br>
	 *
	 * @param data 바이트 배열
	 * @return 압축된 바이트 배열
	 * @see #deflate(byte[])
	 */
	@NotNull
	public static byte[] deflate(@NotNull final byte[] data) throws IOException {
		Deflater compressor = new Deflater();
		compressor.setInput(data);
		ByteArrayOutputStream outputStream = new ByteArrayOutputStream(data.length);

		compressor.finish();
		try {
			byte[] buffer = new byte[1024];
			while (!compressor.finished()) {
				int count = compressor.deflate(buffer); // returns the generated code... index
				outputStream.write(buffer, 0, count);
			}

			outputStream.close();
		} catch (IOException e) {
			throw e;
		}
		byte[] output = outputStream.toByteArray();

		compressor.end();

		return output;
	}

	@NotNull
	public static byte[] deflate(@NotNull final String data) throws IOException {
		return deflate(Stringz.toBytes(data));
	}

	/**
	 * 압축 해제
	 * PHP에서는 gzuncompress()를 시용하시오.
	 *
	 * @param data 압축된 데이터
	 * @return 압축 해제된 데이터
	 */
	@NotNull
	public static byte[] inflate(@NotNull final byte[] data) throws DataFormatException, IOException {
		Inflater inflater = new Inflater();
		inflater.setInput(data);

		ByteArrayOutputStream outputStream = new ByteArrayOutputStream(data.length);
		try {
			byte[] buffer = new byte[1024];
			while (!inflater.finished()) {
				int count = inflater.inflate(buffer);
				outputStream.write(buffer, 0, count);
			}
			outputStream.close();
		} catch (IOException | DataFormatException e) {
			//L.e(TAG, e);
			throw e;
		}
		byte[] output = outputStream.toByteArray();

		inflater.end();

		return output;
	}

	/**
	 * 사용 후, 파일과 스트림을 꼭 닫아 주시오.
	 * <pre><code>
	 *    JarFile jarFile = new JarFile("html-1.0.14-javadoc.jar");
	 * 	  InputStream is = ZipUtils.getJarEntry(jarFile, "com/elex_project/hydra/E.html");
	 * 	  L.i(IOUtils.readStringFrom(is));
	 * 	  is.close();
	 * 	  jarFile.close();
	 * </code></pre>
	 *
	 * @param jarFile
	 * @param entryName '/'로 시작하지 않는다.
	 * @return 스트림
	 * @throws IOException
	 */
	public static InputStream readJarEntry(@NotNull final JarFile jarFile, final String entryName) throws IOException {
		JarEntry entry = jarFile.getJarEntry(entryName);
		return jarFile.getInputStream(entry);
	}

	public static Enumeration<JarEntry> jarEntries(@NotNull final JarFile jarFile) {
		return jarFile.entries();
	}

	/**
	 * 사용 후, 파일과 스트림을 꼭 닫아 주시오.
	 *
	 * @param zipFile
	 * @param entryName '/'로 시작하지 않는다.
	 * @return
	 * @throws IOException
	 */
	public static InputStream readZipEntry(@NotNull final ZipFile zipFile, final String entryName) throws IOException {
		ZipEntry entry = zipFile.getEntry(entryName);
		return zipFile.getInputStream(entry);
	}

	public static Enumeration<? extends ZipEntry> zipEntries(@NotNull final ZipFile zipFile) {
		return zipFile.entries();
	}
}
