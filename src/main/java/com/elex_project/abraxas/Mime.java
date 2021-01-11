/*
 * BSD 3-Clause License
 *
 * Copyright (c) 2021, Elex
 * All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions are met:
 *
 * 1. Redistributions of source code must retain the above copyright notice, this
 *    list of conditions and the following disclaimer.
 *
 * 2. Redistributions in binary form must reproduce the above copyright notice,
 *    this list of conditions and the following disclaimer in the documentation
 *    and/or other materials provided with the distribution.
 *
 * 3. Neither the name of the copyright holder nor the names of its
 *    contributors may be used to endorse or promote products derived from
 *    this software without specific prior written permission.
 *
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS"
 * AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE
 * IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE
 * DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT HOLDER OR CONTRIBUTORS BE LIABLE
 * FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL
 * DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR
 * SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER
 * CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY,
 * OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE
 * OF THIS SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 */

package com.elex_project.abraxas;

import org.jetbrains.annotations.NotNull;

import java.io.File;

/**
 * References
 *
 * @see "https://github.com/nginx/nginx/blob/master/conf/mime.types"
 * @see "https://developer.mozilla.org/ko/docs/Web/HTTP/Basics_of_HTTP/MIME_types/Complete_list_of_MIME_types"
 */
public enum Mime {
	URL_ENCODED("application/x-www-form-urlencoded", Stringz.EMPTY_STRING),

	TEXT("text/plain", "txt"),

	HTML("text/html", "html", "htm", "shtml"),
	CSS("text/css", "css"),
	XML("application/xml", "xml"),

	JAVASCRIPT("application/js", "js"),
	JSON("application/json", "json"),
	XHTML("application/xhtml+xml", "xhtml"),
	MATH_ML("text/mathml", "mml"),

	JNLP("application/x-java-jnlp-file", "jnlp"),
	JAR_DIFF("application/x-java-archive-diff", "jardiff"),

	MARKDOWN("text/markdown", "md", "markdown"),
	ATOM("application/atom+xml", "atom"),
	RSS("application/rss+xml", "rss"),

	BINARY("application/octet-stream", "bin", "exe", "dll", "deb", "dmg", "iso", "img", "msi", "msp", "msm"),

	IMAGE_PNG("image/png", "png"),
	IMAGE_JPEG("image/jpeg", "jpeg", "jpg"),
	IMAGE_SVG("image/svg+xml", "svg", "svgz"),
	IMAGE_GIF("image/gif", "gif"),
	IMAGE_TIFF("image/tiff", "tiff", "tif"),
	IMAGE_WEBP("image/webp", "webp"),
	IMAGE_ICO("image/x-icon", "ico"),
	IMAGE_JNG("image/x-jng", "jng"),
	IMAGE_BMP("image/x-ms-bmp", "bmp"),
	IMAGE_WBMP("image/vnd.wap.wbmp", "wbmp"),

	AUDIO_AAC("audio/aac", "aac"),
	AUDIO_MP3("audio/mpeg", "mp3"),
	AUDIO_MIDI("audio/midi", "midi", "mid", "kar"),

	AUDIO_WAV("audio/x-wav", "wav"),
	AUDIO_RA("audio/x-realaudio", "ra"),

	VIDEO_MPEG("video/mpeg", "mpeg", "mpg"),
	VIDEO_AVI("video/x-msvideo", "avi"),

	VIDEO_MP4("video/mp4", "mp4"),
	AUDIO_M4A("audio/x-m4a", "m4a"),
	VIDEO_MPV("video/x-m4v", "m4v"),

	AUDIO_WEBM("audio/webm", "weba"),
	VIDEO_WEBM("video/webm", "webm"),

	AUDIO_OGG("audio/ogg", "oga", "ogg"),
	VIDEO_OGG("video/ogg", "ogv"),
	APP_OGG("application/ogg", "ogg"),

	VIDEO_MOV("video/quicktime", "mov"),
	VIDEO_MNG("video/x-mng", "mng"),
	VIDEO_3GPP("video/3gpp", "3gpp", "3gp"),
	VIDEO_TS("video/mp2t", "ts"),
	VIDEO_FLV("video/x-flv", "flv"),
	VIDEO_ASF("video/x-ms-asf", "asf"),
	VIDEO_WMV("video/x-ms-wmv", "wmv"),

	ARC_ZIP("application/zip", "zip"),
	ARC_JAR("application/java-archive", "jar", "war", "ear"),
	ARC_7ZIP("application/x-7z-compressed", "7z"),
	ARC_BZ("application/x-bzip", "bz"),
	ARC_BZ2("application/x-bzip2", "bz2"),
	ARC_RAR("application/x-rar-compressed", "rar"),
	ARC_TAR("application/x-tar", "tar"),
	ARC_RPM("application/x-redhat-package-manager", "rpm"),

	CERT("application/x-x509-ca-cert", "der", "pem", "crt"),

	FNT_WEB_OPEN_FONT_FORMAT("font/woff", "woff"),
	FNT_WEB_OPEN_FONT_FORMAT_2("font/woff2", "woff2"),
	FNT_TRUE_TYPE_FONT("application/x-font-ttf", "ttf"),

	DOC_PDF("application/pdf", "pdf"),
	DOC_CSV("text/csv", "csv"),
	DOC_RICH_TEXT_FORMAT("application/rtf", "rtf"),
	DOC_MS_DOC("application/msword", "doc"),
	DOC_MS_XLS("application/vnd.ms-excel", "xls"),
	DOC_MS_PPT("application/vnd.ms-powerpoint", "ppt"),
	DOC_MS_FONT_OBJ("application/vnd.ms-fontobject", "eot"),
	DOC_MS_PPTX("application/vnd.openxmlformats-officedocument.presentationml.presentation", "pptx"),
	DOC_MS_XLSX("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet", "xlsx"),
	DOC_MS_DOCX("application/vnd.openxmlformats-officedocument.wordprocessingml.document", "docx"),
	DOC_EPUB("application/epub+zip", "epub"),
	DOC_POSTSCRIPT("application/postscript", "ps", "eps", "ai"),
	DOC_OOO_GRAPHICS("application/vnd.oasis.opendocument.graphics", "odg"),
	DOC_OOO_PRESENTATION("application/vnd.oasis.opendocument.presentation", "odp"),
	DOC_OOO_SPREADSHEET("application/vnd.oasis.opendocument.spreadsheet", "ods"),
	DOC_OOO_TEXT("application/vnd.oasis.opendocument.text", "odt"),
	DOC_CALENDAR("text/calendar", "ics"),

	KML("application/vnd.google-earth.kml+xml", "kml"),
	KMZ("application/vnd.google-earth.kmz", "kmz"),

	SHELL_SCRIPT("application/x-sh", "sh"),
	PERL("application/x-perl", "pl", "pm"),

	J2ME_APP_DESCRIPTOR("text/vnd.sun.j2me.app-descriptor", "jad"),
	WML("text/vnd.wap.wml", "wml"),
	HTC("text/x-component", "htc"),
	HQX("application/mac-binhex40", "hqx"),
	M3U8("application/vnd.apple.mpegurl", "m3u8"),
	WMLC("application/vnd.wap.wmlc", "wmlc"),
	COCOA("application/x-cocoa", "cco"),
	RUN("application/x-makeself", "run"),
	PILOT("application/x-pilot", "prc", "pdb"),
	SEA("application/x-sea", "sea"),
	FLASH("application/x-shockwave-flash", "swf"),
	SIT("application/x-stuffit", "sit"),
	TCL("application/x-tcl", "tcl", "tk"),
	XPI("application/x-xpinstall", "xpi"),
	XSPF("application/xspf+xml", "xspf");

	private final String mimeType;
	private final String[] extensions;

	Mime(String mimeType, String... extensions) {
		this.mimeType = mimeType;
		this.extensions = extensions;
	}

	@NotNull
	public static Mime getMimeWith(@NotNull String filename) {
		return getMimeWith(filename, BINARY);
	}

	@NotNull
	public static Mime getMimeWith(@NotNull String filename, Mime defaultMime) {
		String[] tmp = filename.split("\\.");
		String ext = tmp[tmp.length - 1];
		for (Mime mime : values()) {
			for (String ex : mime.extensions) {
				if (ex.equalsIgnoreCase(ext)) {
					return mime;
				}
			}
		}
		return defaultMime;
	}

	@NotNull
	public static Mime getMimeWith(@NotNull File file, Mime defaultMime) {
		return getMimeWith(file.getName().toLowerCase(), defaultMime);
	}

	public String getMimeType() {
		return mimeType;
	}

	public String[] getExtensions() {
		return extensions;
	}

	@Override
	public String toString() {
		return mimeType;
	}
}
