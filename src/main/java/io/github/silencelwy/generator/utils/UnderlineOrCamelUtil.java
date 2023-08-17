package io.github.silencelwy.generator.utils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class UnderlineOrCamelUtil {
    private static final Pattern PATTERN_UNDER_CAMEL = Pattern.compile("([A-Za-z\\d]+)(_)?");
    private static final Pattern PATTERN_CAMEL_UNDER = Pattern.compile("[A-Z]([a-z\\d]+)?");

    private UnderlineOrCamelUtil() {
    }

    /**
     * 包名转路径
     * @author mb.wang
     * @date 2018/5/21 09:05
     * @param packageName
     * @return java.lang.String
     */
     public static String packageConvertPath(String packageName) {
        return String.format("/%s/", packageName.contains(".") ? packageName.replaceAll("\\.", "/") : packageName);
    }

    /**
     * 下划线转驼峰法
     *
     * @param line       源字符串
     * @param smallCamel 大小驼峰,是否首字母为小驼峰
     * @return 转换后的字符串
     */
    public static String underline2Camel(String line, boolean smallCamel) {
        if (line == null || "".equals(line)) {
            return "";
        }
        StringBuffer sb = new StringBuffer();
        Matcher matcher = PATTERN_UNDER_CAMEL.matcher(line);
        while (matcher.find()) {
            String word = matcher.group();
            sb.append(smallCamel && matcher.start() == 0 ? Character.toLowerCase(word.charAt(0)) : Character.toUpperCase(word.charAt(0)));
            int index = word.lastIndexOf('_');
            if (index > 0) {
                sb.append(word.substring(1, index).toLowerCase());
            } else {
                sb.append(word.substring(1).toLowerCase());
            }
        }
        return sb.toString();
    }

    /**
     * 驼峰法转下划线
     *
     * @param line 源字符串
     * @return 转换后的字符串
     */
    public static String camel2Underline(String line) {
        if (line == null || "".equals(line)) {
            return "";
        }
        line = String.valueOf(line.charAt(0)).toUpperCase().concat(line.substring(1));
        StringBuffer sb = new StringBuffer();
        Matcher matcher = PATTERN_CAMEL_UNDER.matcher(line);
        while (matcher.find()) {
            String word = matcher.group();
            sb.append(word.toUpperCase());
            sb.append(matcher.end() == line.length() ? "" : "_");
        }
        return sb.toString();
    }
}