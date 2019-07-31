package com.cmd.exchange.common.utils;


import org.apache.commons.lang3.StringUtils;

import java.io.UnsupportedEncodingException;
import java.lang.reflect.InvocationTargetException;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.Date;
import java.util.Map;
import java.util.Random;
import java.util.UUID;
import java.util.regex.Pattern;

/**
 * 工具类
 */
public final class ToolsUtil {

    public static final Charset UTF8 = StandardCharsets.UTF_8;
    // 这个类在多线程环境下很容易重复，看UtilTest中的代码
    // public static final ThreadLocalRandom RANDOM = ThreadLocalRandom.current();
    public static final Random RANDOM = new Random();
    public static final String EMPTY = "";
    public static final String SPACE = " ";
    public static final String SPLIT = ",|，";

    private static final String LIKE = "%";
    private static final String PHONE = "^1[0-9]{10}$";
    /**
     * _abc-def@123-hij.uvw_xyz.com 是正确的, -123@xyz.com 不是
     */
    private static final String EMAIL = "^\\w[\\w\\-]*@([\\w\\-]+\\.\\w+)+$";
    /**
     * ico, jpeg, jpg, bmp, png 后缀
     */
    private static final String IMAGE = "(?i)^(.*)\\.(ico|jpeg|jpg|bmp|png)$";
    /**
     * 帐号输入(字母或数字开头, 长度 5-30, 可以有下划线)
     */
    private static final String USER_NAME = "^[a-zA-Z0-9]\\w{4,29}$";
    /**
     * IPv4 地址
     */
    private static final String IPV4 = "^([01]?[0-9]{1,2}|2[0-4][0-9]|25[0-5])(\\.([01]?[0-9]{1,2}|2[0-4][0-9]|25[0-5])){3}$";
    /**
     * 身份证号码
     */
    private static final String ID_CARD = "^([0-9]{15}|[0-9]{17}[0-9Xx])$";

    /**
     * 中文
     */
    private static final String CHINESE = "[\\u4e00-\\u9fa5]";
    /**
     * 是否是移动端: https://gist.github.com/dalethedeveloper/1503252
     */
    private static final String MOBILE = "(?i)Mobile|iP(hone|od|ad)|Android|BlackBerry|Blazer|PSP|UCWEB|IEMobile|Kindle|NetFront|Silk-Accelerated|(hpw|web)OS|Fennec|Minimo|Opera M(obi|ini)|Dol(f|ph)in|Skyfire|Zune";
    /**
     * 是否是 iOS 端
     */
    private static final String IOS = "(?i)iP(hone|od|ad)";
    /**
     * 是否是 android 端. Android 端使用的是 okHttp 这个组件
     */
    private static final String ANDROID = "(?i)Mobile|Android";
    /**
     * 是否是 pc 端
     */
    private static final String PC = "(?i)AppleWebKit|Mozilla|Chrome|Safari|MSIE|Windows NT";
    /**
     * 是否是本地 ip
     */
    private static final String LOCAL = "(?i)127.0.0.1|localhost|::1|0:0:0:0:0:0:0:1";

    /**
     * 生成指定位数的随机数
     */
    public static String random(int length) {
        if (length <= 0) return EMPTY;

        StringBuilder sbd = new StringBuilder();
        for (int i = 0; i < length; i++) {
            sbd.append(RANDOM.nextInt(10));
        }
        return sbd.toString();
    }

    private static final String TMP = "0123456789abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ";

    public static String randomLetterAndNumber(int length) {
        if (length <= 0) return EMPTY;

        StringBuilder sbd = new StringBuilder();
        for (int i = 0; i < length; i++) {
            sbd.append(TMP.charAt(RANDOM.nextInt(TMP.length())));
        }
        return sbd.toString();
    }

    /**
     * 获取枚举中的值, 先匹配 name, 再匹配 getCode(数字), 再匹配 getValue(中文), 都匹配不上则返回 null
     *
     * @param clazz 枚举的类信息
     * @param obj   要匹配的值
     */
    public static <E extends Enum> E toEnum(Class<E> clazz, Object obj) {
        if (isNotBlank(obj)) {
            E[] constants = clazz.getEnumConstants();
            if (constants != null) {
                String source = obj.toString().trim();
                for (E em : constants) {
                    // 如果传递过来的是枚举名, 且能匹配上则返回
                    if (source.equalsIgnoreCase(em.name())) return em;

                    // 如果传递过来的值跟枚举的 getCode(数字) 相同则返回
                    Object code = getMethod(em, "getCode");
                    if (code != null && source.equalsIgnoreCase(code.toString().trim())) return em;

                    // 如果传递过来的值跟枚举的 getValue(中文) 相同则返回
                    code = getMethod(em, "getValue");
                    if (code != null && source.equalsIgnoreCase(code.toString().trim())) return em;

                    // 如果传递过来的值跟枚举的 ordinal(数字. 表示枚举所在的索引) 相同则返回
                    // if (source.equalsIgnoreCase(String.valueOf(em.ordinal()))) return em;
                }
            }
        }
        return null;
    }


    // ========== number ==========

    /**
     * 传入的数不为 null 且 大于 0 就返回 true
     */
    public static boolean greater0(Number obj) {
        return obj != null && obj.doubleValue() > 0;
    }

    /**
     * 传入的数为 null 或 小于等于 0 就返回 true
     */
    public static boolean less0(Number obj) {
        return obj == null || obj.doubleValue() <= 0;
    }

    /**
     * 数值在指定的数区间时(包含边界)返回 true
     */
    public static boolean betweenBorder(Number num, Number min, Number max) {
        return num.doubleValue() >= min.doubleValue() && num.doubleValue() <= max.doubleValue();
    }

    /**
     * 数值不在指定的数区间时(包含边界)返回 true
     */
    public static boolean notBetweenBorder(Number num, Number min, Number max) {
        return !betweenBorder(num, min, max);
    }

    /**
     * 数值在指定的数区间时(不包含边界)返回 true
     */
    public static boolean between(Number num, Number min, Number max) {
        return num.doubleValue() > min.doubleValue() && num.doubleValue() < max.doubleValue();
    }

    /**
     * 数值不在指定的数区间时(不包含边界)返回 true
     */
    public static boolean notBetween(Number num, Number min, Number max) {
        return !between(num, min, max);
    }
    // ========== number ==========


    // ========== object & string ==========

    /**
     * 对象为 null, 或者其字符串形态为 空白符, "null" 时返回 true
     */
    public static boolean isBlank(Object obj) {
        return obj == null || StringUtils.isBlank(obj.toString()) || "null".equalsIgnoreCase(obj.toString().trim());
    }

    /**
     * 对象非空时返回 true
     */
    public static boolean isNotBlank(Object obj) {
        return !isBlank(obj);
    }

    /**
     * 对象长度在指定的数值经内(包含边距)就返回 true
     */
    public static boolean lengthBorder(Object obj, int max) {
        return obj != null && obj.toString().trim().length() <= max;
    }

    /**
     * 对象长度在指定的数值以内(不包含边距)就返回 true
     */
    public static boolean length(Object obj, int max) {
        return obj != null && obj.toString().trim().length() < max;
    }

    /**
     * 将字符串中指定位数的值模糊成 * 并返回. 索引位从 0 开始
     */
    public static String foggy(String param, int start, int end) {
        if (isBlank(param)) return EMPTY;
        if (start < 0 || end < start || end > param.length()) return param;

        return param.substring(0, start) + param.substring(start, end).replaceAll("[0-9]", "*") + param.substring(end);
    }

    /**
     * 去掉所有的空白符(空格, 制表符, 换行符)
     */
    public static String trim(String str) {
        return isBlank(str) ? EMPTY : str.replaceAll("\\s", "");
    }

    /**
     * 获取图片后缀(包含点 .)
     */
    public static String getSuffix(String image) {
        return (isNotBlank(image) && image.contains("."))
                ? image.substring(image.lastIndexOf(".")) : EMPTY;
    }

    // ========== object & string ==========
    // ========== regex ==========

    /**
     * 验证 指定正则 是否 <span style="color:red;">全字匹配</span> 指定字符串, 匹配则返回 true <br/><br/>
     * <p>
     * 左右空白符 : (?m)(^\s*|\s*$)<br>
     * 空白符 : (^\\s*)|(\\s*$)<br/>
     * 匹配多行注释 : /\*\*(\s|.)*?\* /<br/>
     */
    public static boolean checkRegexWithStrict(String param, String regex) {
        return isNotBlank(param) && Pattern.compile(regex).matcher(param).matches();
    }

    /**
     * 后缀是图片则返回 true
     */
    public static boolean checkImage(String image) {
        return checkRegexWithStrict(image, IMAGE);
    }

    /**
     * 是正确的邮箱地址则返回 true
     */
    public static boolean checkEmail(String email) {
        return checkRegexWithStrict(email, EMAIL);
    }

    /**
     * 是一个手机则返回 true. 可以使用 - 和空格. 131-1234-5678, 131 1234 5678 是正确的手机号, 131-1234 5678 不是
     */
    public static boolean checkPhone(String phone) {
        return checkRegexWithStrict(phone, PHONE);
    }

    public static boolean checkUserName(String userName) {
        return checkRegexWithStrict(userName, USER_NAME);
    }

    /**
     * 是一个有效的 ip 地址则返回 true
     */
    public static boolean isLicitIp(String ip) {
        return checkRegexWithStrict(ip, IPV4);
    }

    /**
     * 是一个有效的身份证号就返回 true
     */
    public static boolean isIdCard(String num) {
        return checkRegexWithStrict(num, ID_CARD);
    }

    /**
     * 是本地请求则返回 true
     */
    public static boolean isLocalRequest(String ip) {
        return checkRegexWithStrict(ip, LOCAL);
    }

    /**
     * 只要找到匹配即返回 true
     */
    public static boolean checkRegexWithRelax(String param, String regex) {
        return isNotBlank(param) && Pattern.compile(regex).matcher(param).find();
    }

    /**
     * 传入的参数只要包含中文就返回 true
     */
    public static boolean checkChinese(String param) {
        return checkRegexWithRelax(param, CHINESE);
    }

    /**
     * 传入的参数只要来自移动端就返回 true
     */
    public static boolean checkMobile(String param) {
        return checkRegexWithRelax(param, MOBILE);
    }

    /**
     * 传入的参数只要是 iOS 端就返回 true
     */
    public static boolean checkIos(String param) {
        return checkRegexWithRelax(param, IOS);
    }

    /**
     * 传入的参数只要是 android 端就返回 true
     */
    public static boolean checkAndroid(String param) {
        return checkRegexWithRelax(param, ANDROID);
    }

    /**
     * 传入的参数只要是 pc 端就返回 true
     */
    public static boolean checkPc(String param) {
        return checkRegexWithRelax(param, PC);
    }


    /**
     * 将两个 int 合并成 long
     */
    public static long merge(int property, int value) {
        return ((long) property << 32) + (long) value;
    }

    /**
     * 将 long 拆分两个 int
     */
    public static int[] parse(long pv) {
        return new int[]{(int) (pv >> 32), (int) pv};
    }


    /**
     * 字符转义. 主要针对 url 传递给后台前的操作. 如 ? 转换为 %3F, = 转换为 %3D, & 转换为 %26 等
     */
    public static String urlEncode(String url) {
        if (isBlank(url)) return EMPTY;
        try {
            // java 中的 encode 是把空格变成 +, 转义后需要将 + 替换成 %20
            return URLEncoder.encode(url, UTF8.displayName());//.replaceAll("\\+", "%20");
        } catch (UnsupportedEncodingException e) {
            return EMPTY;
        }
    }

    /**
     * 字符反转义, 主要针对 url 传递到后台后的操作
     */
    public static String urlDecode(String src) {
        if (isBlank(src)) return EMPTY;
        try {
            // java 中的 encode 是把空格变成 +, 反转义前需要将 %20 替换成 +
            return URLDecoder.decode(src/*.replaceAll("%20", "\\+")*/, UTF8.displayName());
        } catch (UnsupportedEncodingException e) {
            return EMPTY;
        }
    }

    /**
     * 生成不带 - 的 uuid
     */
    public static String uuid() {
        return UUID.randomUUID().toString().replace("-", "");
    }

    /**
     * 将传入的文件重命名成不带 - 的 uuid 名称并返回
     */
    public static String renameFile(String fileName) {
        return uuid() + getSuffix(fileName);
    }

    /**
     * 为空则返回空字符串, 如果传入的 url 中有 ? 则在尾部拼接 &, 否则拼接 ? 返回
     */
    public static String appendUrl(String src) {
        if (isBlank(src)) return EMPTY;
        return src + (src.contains("?") ? "&" : "?");
    }

    /**
     * 为空则返回 /, 如果开头有 / 则直接返回, 否则在开头拼接 / 并返回
     */
    public static String addPrefix(String src) {
        if (isBlank(src)) return "/";
        if (src.startsWith("/")) return src;
        return "/" + src;
    }

    /**
     * 为空则返回 /, 如果结尾有 / 则直接返回, 否则在结尾拼接 / 并返回
     */
    public static String addSuffix(String src) {
        if (isBlank(src)) return "/";
        if (src.endsWith("/")) return src;
        return src + "/";
    }

    /**
     * 从 url 中获取图片名, 最后一个 斜杆(/) 后的内容
     */
    public static String getFileNameInUrl(String url) {
        if (isBlank(url) || !url.contains("/")) return EMPTY;
        // 只截取到 ? 处, 如果有的话
        int last = url.contains("?") ? url.lastIndexOf("?") : url.length();
        return url.substring(url.lastIndexOf("/") + 1, last);
    }

    /**
     * 当值为 null, 空白符, "null" 时则返回指定的字符
     */
    public static String getNil(Object obj, String defaultStr) {
        return isBlank(obj) ? defaultStr : obj.toString().trim();
    }

    /**
     * 当值为 null, 空白符, "null" 时, 返回空字符串
     */
    public static String getNil(Object obj) {
        return getNil(obj, EMPTY);
    }

    /**
     * 属性转换成方法, 加上 get 并首字母大写
     */
    public static String fieldToMethod(String field) {
        if (isBlank(field)) return EMPTY;

        field = field.trim();
        return "get" + field.substring(0, 1).toUpperCase() + field.substring(1);
    }

    /**
     * 调用对象的属性对应的 get 方法
     *
     * @param data  对象
     * @param field 属性名
     * @return 如果属性是枚举则调用枚举的 getValue 方法, 如果是日期则格式化, 否则返回此属性值的 toString 方法
     */
    public static String getField(Object data, String field) {
        if (data == null || isBlank(field)) return EMPTY;

        Object value = getMethod(data, fieldToMethod(field));
        if (value == null) {
            return EMPTY;
        } else if (value.getClass().isEnum()) {
            // 如果是枚举, 则调用其 getValue 方法, getValue 没有值则使用枚举的 name
            Object enumValue = getMethod(value, "getValue");
            return getNil(enumValue != null ? enumValue : value.toString());
        } else if (value instanceof Date) {
            // 如果是日期, 则格式化
            return getNil(DateUtil.getDateTimeString((Date) value));
        } else {
            return getNil(value);
        }
    }

    /**
     * 调用对象的公有方法. 将会忽略异常只返回 null, 如果要对异常专门记录勿调用此方法
     */
    public static Object getMethod(Object obj, String method, Object... param) {
        if (isNotBlank(method)) {
            try {
                return obj.getClass().getDeclaredMethod(method).invoke(obj, param);
            } catch (IllegalAccessException | InvocationTargetException | NoSuchMethodException e) {
                // ignore
            }
            // getMethod 会将从父类继承过来的 public 方法也查询出来
            try {
                return obj.getClass().getMethod(method).invoke(obj, param);
            } catch (IllegalAccessException | InvocationTargetException | NoSuchMethodException e1) {
                // ignore
            }
        }
        return null;
    }

    /**
     * 转换成 id=123&name=xyz&name=opq
     */
    public static String formatParam(Map<String, ?> params) {
        if (params.size() <= 0) return EMPTY;

        StringBuilder sbd = new StringBuilder();
        int i = 0;
        for (Map.Entry<String, ?> entry : params.entrySet()) {
            Object value = entry.getValue();
            if (value != null && isNotBlank(value.toString())) {
                if (value.getClass().isArray()) {
                    for (Object obj : (Object[]) value) {
                        if (obj != null && isNotBlank(obj.toString())) {
                            if (i > 0) sbd.append("&");
                            sbd.append(entry.getKey()).append("=").append(obj.toString());
                            i++;
                        }
                    }
                } else {
                    if (i > 0) sbd.append("&");
                    sbd.append(entry.getKey()).append("=").append(value);
                    i++;
                }
            }
        }
        return sbd.toString();
    }


    /**
     * 对象为 null、空白符、"null" 字符串时, 则抛出异常
     */
    public static void assertNil(Object obj, String msg) throws Exception {
        assertException(isBlank(obj), msg);
    }

    /**
     * 数值为空或小于 1 则抛出异常
     */
    public static void assert0(Number obj, String msg) throws Exception {
        assertException(less0(obj), msg);
    }

    /**
     * 条件为 true 则抛出业务异常
     */
    public static void assertException(Boolean flag, String msg) throws Exception {
        if (flag != null && flag) throw new Exception(msg);
    }

}
