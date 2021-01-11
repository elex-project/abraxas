/*
 * Project Abraxas (a.k.a core-utils)
 * http://www.elex-project.com/
 * Copyright (c) 2017. Elex. All Rights Reserved.
 */

package com.elex_project.abraxas;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.io.*;
import java.net.URISyntaxException;
import java.nio.file.*;
import java.nio.file.attribute.BasicFileAttributes;
import java.nio.file.attribute.FileTime;
import java.util.regex.Pattern;

/**
 * Files
 *
 * @author Elex
 */
public final class Filez {
	public static final Pattern SAFE_FILENAME_PATTERN = Pattern.compile("[\\w%+,./=_-]+");

	private Filez() {

	}


	/**
	 * 패키지 JAR 파일
	 *
	 * @param clazz
	 * @return
	 * @throws URISyntaxException
	 */
	@NotNull
	public static File getPackageFile(@NotNull Class<?> clazz) throws URISyntaxException {
		return new File(clazz.getProtectionDomain().getCodeSource().getLocation()
				.toURI());
	}

	// copy a file from srcFile to destFile, return true if succeed, return
	// false if fail
	public static void copyToFileOrThrow(final File srcFile, final File destFile) throws IOException {
		try (InputStream in = new FileInputStream(srcFile)) {
			copyToFileOrThrow(in, destFile);
		}
	}

	/**
	 * Copy data from a source stream to destFile.
	 * Return true if succeed, return false if failed.
	 *
	 * @param destFile    ..
	 * @param inputStream ..
	 * @throws IOException ..
	 */
	public static void copyToFileOrThrow(final InputStream inputStream, @NotNull final File destFile)
			throws IOException {
		if (destFile.exists()) {
			destFile.delete();
		}
		FileOutputStream out = new FileOutputStream(destFile);
		try {
			byte[] buffer = new byte[4096];
			int bytesRead;
			while ((bytesRead = inputStream.read(buffer)) >= 0) {
				out.write(buffer, 0, bytesRead);
			}
		} finally {
			out.flush();
			try {
				out.getFD().sync();
			} catch (IOException ignore) {
			}
			out.close();
		}
	}

	/**
	 * Check if a filename is "safe" (no metacharacters or spaces).
	 *
	 * @param file The file to check
	 * @return ..
	 */
	public static boolean isFilenameSafe(@NotNull final File file) {
		// Note, we check whether it matches what's known to be safe,
		// rather than what's known to be unsafe.  Non-ASCII, control
		// characters, etc. are all unsafe by default.
		return SAFE_FILENAME_PATTERN.matcher(file.getPath()).matches();
	}

	/**
	 * Check if given filename is valid for a FAT filesystem.
	 *
	 * @param name ..
	 * @return ..
	 */
	public static boolean isValidFatFilename(final String name) {
		return (name != null) && name.equals(buildValidFatFilename(name));
	}

	/**
	 * Mutate the given filename to make it valid for a FAT filesystem,
	 * replacing any invalid characters with "_".
	 *
	 * @param name ..
	 * @return ..
	 */
	@NotNull
	public static String buildValidFatFilename(final String name) {
		if (Stringz.isEmpty(name) || ".".equals(name) || "..".equals(name)) {
			return "(invalid)";
		}
		final StringBuilder res = new StringBuilder(name.length());
		for (int i = 0; i < name.length(); i++) {
			final char c = name.charAt(i);
			if (isValidFatFilenameChar(c)) {
				res.append(c);
			} else {
				res.append('_');
			}
		}
		// Even though vfat allows 255 UCS-2 chars, we might eventually write to
		// ext4 through a FUSE layer, so use that limit.
		trimFilename(res, 255);
		return res.toString();
	}

	private static void trimFilename(@NotNull final StringBuilder res, int maxBytes) {
		byte[] raw = Stringz.toBytes(res.toString());//res.toString().getBytes(StringUtils.UTF_8);
		if (raw.length > maxBytes) {
			maxBytes -= 3;
			while (raw.length > maxBytes) {
				res.deleteCharAt(res.length() / 2);
				raw = Stringz.toBytes(res.toString());
			}
			res.insert(res.length() / 2, "...");
		}
	}

	private static boolean isValidFatFilenameChar(final char c) {
		if ((0x00 <= c && c <= 0x1f)) {
			return false;
		}
		switch (c) {
			case '"':
			case '*':
			case '/':
			case ':':
			case '<':
			case '>':
			case '?':
			case '\\':
			case '|':
			case 0x7F:
				return false;
			default:
				return true;
		}
	}

	public static boolean deleteContentsAndDir(final File dir) {
		if (deleteContents(dir)) {
			return dir.delete();
		} else {
			return false;
		}
	}

	public static boolean deleteContents(@NotNull final File dir) {
		File[] files = dir.listFiles();
		boolean success = true;
		if (files != null) {
			for (File file : files) {
				if (file.isDirectory()) {
					success &= deleteContents(file);
				}
				if (!file.delete()) {
					//L.w("Failed to delete " + file);
					success = false;
				}
			}
		}
		return success;
	}

	private static File buildUniqueFileWithExtension(final File parent, final String name, final String ext) {
		File file = buildFile(parent, name, ext);

		// If conflicting file, try adding counter suffix
		int n = 0;
		while (file.exists()) {
			if (n++ >= 128) {
				file = buildFile(parent, name + " (" + Timez.currentTime() + ")", ext);
			} else {
				file = buildFile(parent, name + " (" + n + ")", ext);
			}
		}

		return file;
	}


	/**
	 * Generates a unique file name under the given parent directory, keeping
	 * any extension intact.
	 *
	 * @param displayName ..
	 * @param parent      ..
	 * @return ..
	 */
	public static File buildUniqueFile(final File parent, @NotNull final String displayName) {
		final String name;
		final String ext;

		// Extract requested extension from display name
		final int lastDot = displayName.lastIndexOf('.');
		if (lastDot >= 0) {
			name = displayName.substring(0, lastDot);
			ext = displayName.substring(lastDot + 1);
		} else {
			name = displayName;
			ext = null;
		}

		return buildUniqueFileWithExtension(parent, name, ext);
	}

	private static File buildFile(final File parent, final String name, final @Nullable String ext) {
		if (Stringz.isEmpty(ext)) {
			return new File(parent, name);
		} else {
			return new File(parent, name + "." + ext);
		}
	}

	/**
	 * Round the given size of a storage device to a nice round power-of-two
	 * value, such as 256MB or 32GB. This avoids showing weird values like
	 * "29.5GB" in UI.
	 *
	 * @param size ..
	 * @return ..
	 */
	public static long roundStorageSize(final long size) {
		long val = 1;
		long pow = 1;
		while ((val * pow) < size) {
			val <<= 1;
			if (val > 512) {
				val = 1;
				pow *= 1000;
			}
		}
		return val * pow;
	}

	/**
	 * Creates a directory with name {@code name} under an existing directory {@code baseDir}.
	 * Returns a {@code File} object representing the directory on success, {@code null} on
	 * failure.
	 *
	 * @param baseDir ..
	 * @param name    ..
	 * @return ..
	 */
	public static @Nullable File createDir(final File baseDir, final String name) {
		final File dir = new File(baseDir, name);

		if (dir.exists()) {
			return dir.isDirectory() ? dir : null;
		}

		return dir.mkdir() ? dir : null;
	}

	/**
	 * 새 파일을 생성한다. 부모 폴더가 없으면 생성한다.
	 *
	 * @param file 새 파일
	 * @return 이미 존재하는 파일이 있으면 false
	 * @throws IOException ..
	 */
	public static boolean createFile(@NotNull final File file) throws IOException {
		if (file.exists()) return false;
		File dir = file.getParentFile();
		if (!dir.exists()) dir.mkdirs();
		return file.createNewFile();
	}

	/**
	 * @param file 파일
	 * @return 파일이 있으면 참, 없으면 거짓. 이도저도 아니면 예외.
	 * @throws Exception 이도저도 아님.
	 */
	public static boolean isFileExists(final Path file) throws IllegalStateException {
		if (Files.exists(file, LinkOption.NOFOLLOW_LINKS)) {
			return true;
		} else if (Files.notExists(file, LinkOption.NOFOLLOW_LINKS)) {
			return false;
		} else {
			throw new IllegalStateException();
		}
	}

	public static boolean isFileExists(final File file) throws IllegalStateException {
		return isFileExists(file.toPath());
	}

	public static boolean isFileExists(final Path file, final LinkOption option) throws IllegalStateException {
		if (Files.exists(file, option)) {
			return true;
		} else if (Files.notExists(file, option)) {
			return false;
		} else {
			throw new IllegalStateException();
		}
	}

	public static boolean isFileExists(final File file, final LinkOption option) throws IllegalStateException {
		return isFileExists(file.toPath(), option);
	}

	public static boolean isReadable(final Path path) {
		return Files.isReadable(path);
	}

	public static boolean isReadable(final File file) {
		return isReadable(file.toPath());
	}

	public static boolean isWritable(final Path path) {
		return Files.isWritable(path);
	}

	public static boolean isWritable(final File file) {
		return isWritable(file.toPath());
	}

	public static boolean isExecutable(final Path path) {
		return Files.isExecutable(path);
	}

	public static boolean isExecutable(final File file) {
		return isExecutable(file.toPath());
	}

	public static boolean isRegularFile(final Path path) {
		return Files.isRegularFile(path, LinkOption.NOFOLLOW_LINKS);
	}

	public static boolean isRegularFile(final File file) {
		return isRegularFile(file.toPath());
	}

	public static boolean isDirectory(final Path path) {
		return Files.isDirectory(path, LinkOption.NOFOLLOW_LINKS);
	}

	public static boolean isDirectory(final File file) {
		return isDirectory(file.toPath());
	}

	public static boolean isSymbolicLink(final Path path) {
		return Files.isSymbolicLink(path);
	}

	public static boolean isSymbolicLink(final File file) {
		return isSymbolicLink(file.toPath());
	}

	public static boolean isHidden(final Path path) throws IOException {
		return Files.isHidden(path);
	}

	public static boolean isHidden(final File file) throws IOException {
		return isHidden(file.toPath());
	}

	public static boolean isSameFile(final Path p1, final Path p2) throws IOException {
		return Files.isSameFile(p1, p2);
	}

	public static boolean isSameFile(final File f1, final File f2) throws IOException {
		return isSameFile(f1.toPath(), f2.toPath());
	}

	public static void delete(@NotNull final File path) throws IOException {
		delete(path.toPath());
	}

	/**
	 * delete a file, or delete a directory and all its contents
	 *
	 * @param path file or dir
	 * @throws IOException ..
	 */
	public static void delete(final Path path) throws IOException {
		if (isDirectory(path)) {
			Files.walkFileTree(path, new FileVisitor<Path>() {
				@Override
				public FileVisitResult preVisitDirectory(Path path, BasicFileAttributes basicFileAttributes) {
					return FileVisitResult.CONTINUE;
				}

				@Override
				public FileVisitResult visitFile(Path path, BasicFileAttributes basicFileAttributes) throws IOException {
					deleteIfExists(path);
					return FileVisitResult.CONTINUE;
				}

				@Override
				public FileVisitResult visitFileFailed(Path path, IOException e) {
					return FileVisitResult.CONTINUE;
				}

				@Override
				public FileVisitResult postVisitDirectory(Path path, IOException e) throws IOException {
					deleteIfExists(path);
					return FileVisitResult.CONTINUE;
				}
			});
		} else {
			Files.delete(path);
		}

	}

	public static void deleteIfExists(final Path path) throws IOException {
		Files.deleteIfExists(path);
	}

	public static void copy(@NotNull final File from, @NotNull final File to, final CopyOption... copyOptions) throws IOException {
		copy(from.toPath(), to.toPath(), copyOptions);
	}

	public static void copy(final Path from, final Path to, final CopyOption... copyOptions) throws IOException {
		if (isDirectory(from)) {
			Files.walkFileTree(from, new FileVisitor<Path>() {
				@Override
				public FileVisitResult preVisitDirectory(Path path, BasicFileAttributes basicFileAttributes) throws IOException {
					Path newdir = to.resolve(from.relativize(path));
					try {
						Files.copy(path, newdir, copyOptions);
					} catch (FileAlreadyExistsException x) {
						// ignore
					} catch (IOException x) {
						System.err.format("Unable to create: %s: %s%n", newdir, x);
						return FileVisitResult.SKIP_SUBTREE;
					}
					return FileVisitResult.CONTINUE;
				}

				@Override
				public FileVisitResult visitFile(Path path, BasicFileAttributes basicFileAttributes) throws IOException {
					try {
						Files.copy(path, to.resolve(from.relativize(path)), copyOptions);
					} catch (IOException x) {
						System.err.format("Unable to copy: %s: %s%n", path, x);
					}
					return FileVisitResult.CONTINUE;
				}

				@Override
				public FileVisitResult visitFileFailed(Path path, IOException e) throws IOException {
					return FileVisitResult.CONTINUE;
				}

				@Override
				public FileVisitResult postVisitDirectory(Path path, IOException e) throws IOException {
					if (e == null) {
						Path newdir = to.resolve(from.relativize(path));
						try {
							FileTime time = Files.getLastModifiedTime(path);
							Files.setLastModifiedTime(newdir, time);
						} catch (IOException x) {
							System.err.format("Unable to copy all attributes to: %s: %s%n", newdir, x);
						}
					}
					return FileVisitResult.CONTINUE;
				}
			});
		} else {
			Files.copy(from, to, copyOptions);
		}
	}

	public static void move(@NotNull final File from, @NotNull final File to, final CopyOption... copyOptions) throws IOException {
		move(from.toPath(), to.toPath(), copyOptions);
	}

	public static void move(final Path from, final Path to, final CopyOption... copyOptions) throws IOException {
		if (isDirectory(from)) {
			Files.walkFileTree(from, new FileVisitor<Path>() {
				@Override
				public FileVisitResult preVisitDirectory(Path path, BasicFileAttributes basicFileAttributes) throws IOException {
					Path newdir = to.resolve(from.relativize(path));
					try {
						Files.move(path, newdir, copyOptions);
					} catch (FileAlreadyExistsException x) {
						// ignore
					} catch (IOException x) {
						System.err.format("Unable to create: %s: %s%n", newdir, x);
						return FileVisitResult.SKIP_SUBTREE;
					}
					return FileVisitResult.CONTINUE;
				}

				@Override
				public FileVisitResult visitFile(Path path, BasicFileAttributes basicFileAttributes) throws IOException {
					try {
						Files.move(path, to.resolve(from.relativize(path)), copyOptions);
					} catch (IOException x) {
						System.err.format("Unable to copy: %s: %s%n", path, x);
					}
					return FileVisitResult.CONTINUE;
				}

				@Override
				public FileVisitResult visitFileFailed(Path path, IOException e) throws IOException {
					return FileVisitResult.CONTINUE;
				}

				@Override
				public FileVisitResult postVisitDirectory(Path path, IOException e) throws IOException {
					if (e == null) {
						Path newdir = to.resolve(from.relativize(path));
						try {
							FileTime time = Files.getLastModifiedTime(path);
							Files.setLastModifiedTime(newdir, time);
						} catch (IOException x) {
							System.err.format("Unable to copy all attributes to: %s: %s%n", newdir, x);
						}
					}
					return FileVisitResult.CONTINUE;
				}
			});
		} else {
			Files.move(from, to, copyOptions);
		}
	}
}
