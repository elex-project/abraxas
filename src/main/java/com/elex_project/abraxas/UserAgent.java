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

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Http User agent string parser
 *
 * @author  Elex
 */
public final class UserAgent {
	private static final Map<String, String> PLATFORMS = new HashMap<>();
	private static final Map<String, String> BROWSERS = new HashMap<>();
	private static final Map<String, String> ROBOTS = new HashMap<>();
	private static final List<String> MOBILES = new ArrayList<>();

	static {
		PLATFORMS.put("windows nt 10.0", "Windows 10");
		PLATFORMS.put("windows nt 6.3", "Windows 8.1");
		PLATFORMS.put("windows nt 6.2", "Windows 8");
		PLATFORMS.put("windows nt 6.1", "Windows 7");
		PLATFORMS.put("windows nt 6.0", "Windows Vista");
		PLATFORMS.put("windows nt 5.2", "Windows 2003");
		PLATFORMS.put("windows nt 5.1", "Windows XP");
		PLATFORMS.put("windows nt 5.0", "Windows 2000");
		PLATFORMS.put("windows nt 4.0", "Windows NT 4.0");
		PLATFORMS.put("winnt4.0", "Windows NT 4.0");
		PLATFORMS.put("winnt 4.0", "Windows NT");
		PLATFORMS.put("winnt", "Windows NT");
		PLATFORMS.put("windows 98", "Windows 98");
		PLATFORMS.put("win98", "Windows 98");
		PLATFORMS.put("windows 95", "Windows 95");
		PLATFORMS.put("win95", "Windows 95");
		PLATFORMS.put("windows phone", "Windows Phone");
		PLATFORMS.put("windows", "Unknown Windows OS");
		PLATFORMS.put("android", "Android");
		PLATFORMS.put("blackberry", "BlackBerry");
		PLATFORMS.put("iphone", "iOS");
		PLATFORMS.put("ipad", "iOS");
		PLATFORMS.put("ipod", "iOS");
		PLATFORMS.put("os x", "Mac OS X");
		PLATFORMS.put("ppc mac", "Power PC Mac");
		PLATFORMS.put("freebsd", "FreeBSD");
		PLATFORMS.put("ppc", "Macintosh");
		PLATFORMS.put("linux", "Linux");
		PLATFORMS.put("debian", "Debian");
		PLATFORMS.put("sunos", "Sun Solaris");
		PLATFORMS.put("beos", "BeOS");
		PLATFORMS.put("apachebench", "ApacheBench");
		PLATFORMS.put("aix", "AIX");
		PLATFORMS.put("irix", "Irix");
		PLATFORMS.put("osf", "DEC OSF");
		PLATFORMS.put("hp-ux", "HP-UX");
		PLATFORMS.put("netbsd", "NetBSD");
		PLATFORMS.put("bsdi", "BSDi");
		PLATFORMS.put("openbsd", "OpenBSD");
		PLATFORMS.put("gnu", "GNU/Linux");
		PLATFORMS.put("unix", "Unknown Unix OS");
		PLATFORMS.put("symbian", "Symbian OS");

		BROWSERS.put("OPR", "Opera");
		BROWSERS.put("Flock", "Flock");
		BROWSERS.put("Edge", "Edge");
		BROWSERS.put("Chrome", "Chrome");
		// Opera 10+ always reports Opera/9.80 and appends Version/<real version> to the user agent string
		//BROWSERS.put("Opera.*?Version", "Opera");
		BROWSERS.put("Opera", "Opera");
		BROWSERS.put("MSIE", "Internet Explorer");
		BROWSERS.put("Internet Explorer", "Internet Explorer");
		BROWSERS.put("Trident", "Internet Explorer");
		BROWSERS.put("Shiira", "Shiira");
		BROWSERS.put("Firefox", "Firefox");
		BROWSERS.put("Chimera", "Chimera");
		BROWSERS.put("Phoenix", "Phoenix");
		BROWSERS.put("Firebird", "Firebird");
		BROWSERS.put("Camino", "Camino");
		BROWSERS.put("Netscape", "Netscape");
		BROWSERS.put("OmniWeb", "OmniWeb");
		BROWSERS.put("Safari", "Safari");
		BROWSERS.put("Mozilla", "Mozilla");
		BROWSERS.put("Konqueror", "Konqueror");
		BROWSERS.put("icab", "iCab");
		BROWSERS.put("Lynx", "Lynx");
		BROWSERS.put("Links", "Links");
		BROWSERS.put("hotjava", "HotJava");
		BROWSERS.put("amaya", "Amaya");
		BROWSERS.put("IBrowse", "IBrowse");
		BROWSERS.put("Maxthon", "Maxthon");
		BROWSERS.put("Ubuntu", "Ubuntu Web Browser");

		ROBOTS.put("googlebot", "Googlebot");
		ROBOTS.put("adsbot-google", "AdsBot Google");
		ROBOTS.put("mediapartners-google", "MediaPartners Google");
		ROBOTS.put("msnbot", "MSNBot");
		ROBOTS.put("baiduspider", "Baiduspider");
		ROBOTS.put("baidu", "Baiduspider");
		ROBOTS.put("yandex", "YandexBot");
		ROBOTS.put("YandexBot", "YandexBot");
		ROBOTS.put("bingbot", "Bing");
		ROBOTS.put("bingpreview", "Bing");
		ROBOTS.put("duckduckgo", "DuckDuckBot");
		ROBOTS.put("slurp", "Yahoo");
		ROBOTS.put("yahoo", "Yahoo");
		ROBOTS.put("ask jeeves", "Teoma");
		ROBOTS.put("teoma", "Teoma");
		ROBOTS.put("fastcrawler", "FastCrawler");
		ROBOTS.put("infoseek", "InfoSeek Robot 1.0");
		ROBOTS.put("lycos", "Lycos");
		ROBOTS.put("CRAZYWEBCRAWLER", "Crazy Webcrawler");
		ROBOTS.put("feedfetcher-google", "Feedfetcher Google");
		ROBOTS.put("curious george", "Curious George");
		ROBOTS.put("ia_archiver", "Alexa Crawler");
		ROBOTS.put("MJ12bot", "Majestic-12");
		ROBOTS.put("Uptimebot", "Uptimebot");
		ROBOTS.put("Yeti", "Naver Yeti");
		ROBOTS.put("NetcraftSurveyAgent", "NetcraftSurveyAgent");
		ROBOTS.put("Researchscan", "Researchscan");
		ROBOTS.put("ABCdatos BotLink", "ABCdatos BotLink");
		ROBOTS.put("SemrushBot", "SemrushBot");
		ROBOTS.put("DotBot", "DotBot");
		ROBOTS.put("Nimbostratus-Bot", "Cloud System Networks");
		ROBOTS.put("Seekport Crawler", "Seekport Crawler");

		MOBILES.add("mobileexplorer");
		MOBILES.add("palmsource");
		MOBILES.add("palmscape");
		MOBILES.add("motorola");
		MOBILES.add("nokia");
		MOBILES.add("nexus");
		MOBILES.add("palm");
		MOBILES.add("iphone");
		MOBILES.add("ipad");
		MOBILES.add("ipod");
		MOBILES.add("sony");
		MOBILES.add("ericsson");
		MOBILES.add("blackberry");
		MOBILES.add("cocoon");
		MOBILES.add("blazer");
		MOBILES.add("lg");
		MOBILES.add("amoi");
		MOBILES.add("xda");
		MOBILES.add("mda");
		MOBILES.add("vario");
		MOBILES.add("htc");
		MOBILES.add("samsung");
		MOBILES.add("sharp");
		MOBILES.add("sie-");
		MOBILES.add("alcatel");
		MOBILES.add("benq");
		MOBILES.add("ipaq");
		MOBILES.add("mot-");
		MOBILES.add("playstation portable");
		MOBILES.add("playstation 3");
		MOBILES.add("playstation vita");
		MOBILES.add("hiptop");
		MOBILES.add("nec-");
		MOBILES.add("panasonic");
		MOBILES.add("philips");
		MOBILES.add("sagem");
		MOBILES.add("sanyo");
		MOBILES.add("spv");
		MOBILES.add("zte");
		MOBILES.add("sendo");
		MOBILES.add("nintendo dsi");
		MOBILES.add("nintendo ds");
		MOBILES.add("nintendo 3ds");
		MOBILES.add("wii");
		MOBILES.add("open web");
		MOBILES.add("openweb");
		MOBILES.add("android");
		MOBILES.add("symbian");
		MOBILES.add("SymbianOS");
		MOBILES.add("elaine");
		MOBILES.add("series60");
		MOBILES.add("windows ce");
		MOBILES.add("obigo");
		MOBILES.add("netfront");
		MOBILES.add("openwave");
		MOBILES.add("mobilexplorer");
		MOBILES.add("operamini");
		MOBILES.add("opera mini");
		MOBILES.add("opera mobi");
		MOBILES.add("fennec");
		MOBILES.add("digital paths");
		MOBILES.add("avantgo");
		MOBILES.add("xiino");
		MOBILES.add("novarra");
		MOBILES.add("vodafone");
		MOBILES.add("docomo");
		MOBILES.add("o2");
		MOBILES.add("mobile");
		MOBILES.add("wireless");
		MOBILES.add("j2me");
		MOBILES.add("midp");
		MOBILES.add("cldc");
		MOBILES.add("up.link");
		MOBILES.add("up.browser");
		MOBILES.add("smartphone");
		MOBILES.add("cellphone");
	}

	private String userAgentString;
	private String platform, browser, robotName;
	private boolean isRobot = false;
	private boolean isMobile = false;

	private UserAgent() {
	}

	/**
	 * Parse User-Agent string
	 * @param userAgentString string
	 * @return userAgent
	 */
	@NotNull
	public static UserAgent of(final String userAgentString) {
		UserAgent ua = new UserAgent();
		ua.userAgentString = userAgentString;
		for (String platform : PLATFORMS.keySet()) {
			if (userAgentString.matches("(?i).*" + platform + ".*")) {
				ua.platform = PLATFORMS.get(platform);
				break;
			}
			ua.platform = Stringz.UNKNOWN;
		}
		for (String browser : BROWSERS.keySet()) {
			if (userAgentString.matches("(?i).*" + browser + ".*")) {
				ua.browser = BROWSERS.get(browser);
				break;
			}
			ua.browser = Stringz.UNKNOWN;
		}
		for (String robot : ROBOTS.keySet()) {
			if (userAgentString.matches("(?i).*" + robot + ".*")) {
				ua.isRobot = true;
				ua.robotName = ROBOTS.get(robot);
				break;
			}
			ua.robotName = Stringz.UNKNOWN;
		}
		for (String mobile : MOBILES) {
			if (userAgentString.matches("(?i).*" + mobile + ".*")) {
				ua.isMobile = true;
				break;
			}
		}
		return ua;
	}

	public String getPlatform() {
		return platform;
	}

	public String getBrowser() {
		return browser;
	}

	public String getRobotName() {
		return robotName;
	}

	public boolean isRobot() {
		return isRobot;
	}

	public boolean isMobile() {
		return isMobile;
	}

	public String getUserAgentString() {
		return userAgentString;
	}
}