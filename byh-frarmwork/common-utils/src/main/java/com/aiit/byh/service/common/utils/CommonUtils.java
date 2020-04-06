package com.aiit.byh.service.common.utils;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.google.common.collect.Sets;
import com.aiit.byh.service.common.utils.config.ConfigUtil;
import org.apache.commons.beanutils.BeanUtilsBean;
import org.apache.commons.beanutils.ConvertUtils;
import org.apache.commons.beanutils.PropertyUtils;
import org.apache.commons.beanutils.converters.DateConverter;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.time.DurationFormatUtils;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.RandomUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.math.NumberUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpServletRequest;
import java.beans.PropertyDescriptor;
import java.io.*;
import java.lang.management.ManagementFactory;
import java.lang.reflect.InvocationTargetException;
import java.net.Inet4Address;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.text.DecimalFormat;
import java.util.*;

/**
 * 公共工具类
 *
 * @author dsqin
 */
public class CommonUtils {
    private final static Logger logger = LoggerFactory
            .getLogger(CommonUtils.class);

    /**
     * map -> bean
     *
     * @param bean         类对象
     * @param properties   map对象
     * @param isFirstUpper map中首字母是否大写
     * @throws IllegalAccessException
     * @throws InvocationTargetException
     */
    @SuppressWarnings("rawtypes")
    @Deprecated
    public static void populate(Object bean, Map properties,
                                boolean isFirstUpper) throws IllegalAccessException,
            InvocationTargetException {
        if ((bean == null) || (properties == null)) {
            return;
        }

        Iterator entries = properties.entrySet().iterator();
        while (entries.hasNext()) {
            Map.Entry entry = (Map.Entry) entries.next();
            try {
                String name = (String) entry.getKey();
                if (name == null) {
                    continue;
                }
                if (isFirstUpper) {
                    char pref = name.charAt(0);
                    name = name.replaceFirst(String.valueOf(pref),
                            String.valueOf(Character.toLowerCase(pref)));
                }
                ConvertUtils.register(new DateConverter(), Date.class);
                BeanUtilsBean beanUtil = BeanUtilsBean.getInstance();
                beanUtil.setProperty(bean, name, entry.getValue());
            } catch (Exception ex) {
                logger.error("{}", entry, ex);
            }
        }
    }

    /**
     * object -> map
     *
     * @param obj
     * @return
     */
    public static Map<String, String> describe(Object obj) {
        return describe(obj, false);
    }

    /**
     * object -> map
     *
     * @param obj
     * @return
     */
    public static Map<String, String> describe(Object obj, boolean isFirstUpper) {
        Map<String, String> map = Maps.newHashMap();
        PropertyDescriptor[] descriptors = PropertyUtils
                .getPropertyDescriptors(obj);

        for (int i = 0; i < descriptors.length; ++i) {
            String name = descriptors[i].getName();
            if (descriptors[i].getReadMethod() != null)
                try {
                    if (!"class".equals(name)) {
                        String value = BeanUtilsBean.getInstance().getProperty(
                                obj, name);
                        if (StringUtils.isNotBlank(value)) {
                            if (isFirstUpper) {
                                char pref = name.charAt(0);
                                name = name.replaceFirst(String.valueOf(pref),
                                        String.valueOf(Character.toUpperCase(pref)));
                            }
                            map.put(name, value);
                        }
                    }
                } catch (Exception ex) {

                }
        }
        return map;
    }

    /**
     * 判断是否为空
     *
     * @param obj
     * @return
     */
    @SuppressWarnings("rawtypes")
    public static boolean isEmpty(Object obj) {
        if (null == obj) {
            return true;
        }
        try {
            if (obj instanceof List) {
                List list = (List) obj;
                return list.isEmpty();
            } else if (obj instanceof String[]) {
                String[] array = (String[]) obj;
                if (array.length <= 0) {
                    return true;
                }
            } else if (obj instanceof Map) {
                Map map = (Map) obj;
                return map.isEmpty();
            } else if (obj instanceof Set) {
                Set set = (Set) obj;
                return set.isEmpty();
            }
        } catch (Exception ex) {
            return false;
        }
        return false;
    }

    /**
     * 判断非空
     *
     * @param obj
     * @return
     */
    public static boolean isNotEmpty(Object obj) {
        return !isEmpty(obj);
    }

    /**
     * String set to String array
     *
     * @param set
     * @return
     */
    public static String[] setToArray(Set<String> set) {
        if (null == set || set.isEmpty()) {
            return null;
        }
        try {
            String[] strArray = new String[set.size()];
            String[] toArray = (String[]) set.toArray(strArray);
            return toArray;
        } catch (Exception ex) {
            return null;
        }
    }

    /**
     * list to string array
     *
     * @param list
     * @return
     */
    public static String[] listToArray(List<String> list) {
        if (null == list || list.isEmpty()) {
            return null;
        }
        try {
            String[] strArray = new String[list.size()];
            String[] toArray = (String[]) list.toArray(strArray);
            return toArray;
        } catch (Exception e) {
            return null;
        }
    }

    /**
     * list to set
     *
     * @param list
     * @return
     */
    public static HashSet<String> listToSet(List<String> list) {
        if (CommonUtils.isEmpty(list)) {
            return null;
        }
        return Sets.newHashSet(list);
    }

    /**
     * list contains of str
     *
     * @param list
     * @param str
     * @return
     */
    public static boolean listContainsOf(List<String> list, String str) {
        HashSet<String> set = listToSet(list);
        return set.contains(str);
    }

    /**
     * set to list
     *
     * @param list
     * @return
     */
    public static List<String> setToList(Set<String> list) {
        if (null == list || list.isEmpty()) {
            return null;
        }
        try {
            List<String> toList = Lists.newArrayListWithExpectedSize(list
                    .size());
            toList.addAll(list);
            return toList;
        } catch (Exception e) {
            return null;
        }
    }

    /**
     * set2list
     *
     * @param list
     * @return
     */
    public static <T extends Object> List<T> set2List(Set<T> list) {
        if (null == list || list.isEmpty()) {
            return null;
        }
        try {
            List<T> toList = Lists.newArrayListWithExpectedSize(list.size());
            toList.addAll(list);
            return toList;
        } catch (Exception e) {
            return null;
        }
    }

    /**
     * array to set
     *
     * @param array
     * @return
     */
    public static <T extends Object> Set<T> Array2Set(T[] array) {
        if (null == array || array.length <= 0) {
            return null;
        }
        Set<T> tSet = new HashSet<T>(Arrays.asList(array));
        return tSet;
    }

    /**
     * Object to string
     *
     * @param obj
     * @param defaultValue
     * @return
     */
    public static String toString(Object obj, String defaultValue) {
        if (null == obj) {
            return defaultValue;
        }
        return obj.toString();
    }

    /**
     * integer默认值
     *
     * @param value
     * @return
     */
    public static int defaultInteger(Integer value) {
        if (null == value) {
            return 0;
        }
        return value.intValue();
    }

    /**
     * Integer转String
     *
     * @param value
     * @return
     */
    public static String defaultIntegerString(Integer value) {
        return String.valueOf(CommonUtils.defaultInteger(value));
    }

    /**
     * Long默认值
     *
     * @param value
     * @return
     */
    public static long defaultLong(Long value) {
        if (null == value) {
            return 0;
        }
        return value.longValue();
    }

    /**
     * 生成号码 格式(手机号码中间4位为 * )
     *
     * @param phoneNo
     * @return
     */
    public static String genPhoneNoProtect(String phoneNo) {
        if (phoneNo.length() < 11) {
            return phoneNo;
        }
        String replace = "****";
        return StringUtils.join(phoneNo.substring(0, 3), replace,
                phoneNo.substring(7));
    }

    /**
     * mm:ss 时间字符-> 秒数
     *
     * @param time
     * @return
     */
    public static long formatDuration(String time) {
        if (StringUtils.isBlank(time)) {
            return Long.MAX_VALUE;
        }
        String[] times = StringUtils.split(time, ":");
        if (null == times || times.length <= 1) {
            return Long.MAX_VALUE;
        }
        int length = times.length;
        long seconds = NumberUtils.toLong(times[length - 1], 0);
        long minutes = NumberUtils.toLong(times[length - 2], 0);

        return minutes * 60 + seconds;
    }

    /**
     * Set是否包含string
     *
     * @param set
     * @param str
     * @return
     */
    public static boolean setContainsString(Set<String> set, String str) {
        if (null == set || set.isEmpty()) {
            return false;
        }
        return set.contains(str);
    }

    /**
     * 计算页数
     *
     * @param total    数据总数
     * @param pageSize 单页数量
     * @return
     */
    public static long calcPageCount(long total, long pageSize) {
        if (total == 0 || pageSize == 0) {
            return 0;
        }
        long pageCount = total / pageSize + (total % pageSize == 0 ? 0 : 1);
        return pageCount;
    }

    /**
     * @param pageCount
     * @param pageIndex 从0开始
     * @return
     */
    public static boolean hasMore(long pageCount, long pageIndex) {
        if (pageCount <= (pageIndex + 1)) {
            return false;
        }

        return true;
    }

    /**
     * 计算是否有下一页
     *
     * @param total
     * @param pageSize
     * @param pageIndex 从0开始
     * @return
     */
    public static boolean hasMore(long total, long pageSize, long pageIndex) {
        long pageCount = calcPageCount(total, pageSize);
        return hasMore(pageCount, pageIndex);
    }

    /**
     * hit是否命中概率
     *
     * @param probability 概率 0 ~ 1
     * @return 是否命中
     */
    public static boolean hit(double probability) {
        int value = RandomUtils.nextInt(1, 101);
        if (value <= probability * 100) {
            return true;
        }
        return false;
    }

    /**
     * 获取list长度 null返回0
     *
     * @param list
     * @return
     */
    public static int getListSize(List<?> list) {
        if (null == list) {
            return 0;
        }
        return list.size();
    }

    /**
     * list随机几个
     *
     * @param list
     * @param n
     * @return
     */
    public static List<String> randomList(List<String> list, int n) {
        if (list == null || list.size() <= 0 || n == 0) {
            return null;
        }

        List<String> respList = Lists.newArrayListWithCapacity(n);
        int size = list.size();
        if (size < n) {
            Collections.shuffle(list);
            respList.addAll(list);
            respList.addAll(randomList(list, n - size));
        } else {
            int ranId = -1;
            Set<String> sets = new HashSet<String>();

            while (sets.size() < n) {
                Random random = new Random(System.currentTimeMillis());
                ranId = random.nextInt(size);
                sets.add(list.get(ranId));
                try {
                    Thread.sleep(1);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }

            respList = Lists.newArrayList(sets);
        }

        return respList;
    }

    /**
     * 获取资源Url后缀
     *
     * @param url
     * @return
     */
    public static String getUrlResFormat(String url) {
        if (StringUtils.isBlank(url)) {
            return StringUtils.EMPTY;
        }
        int index = StringUtils.lastIndexOf(url, ".");
        if (index < 0) {
            return StringUtils.EMPTY;
        }
        return url.substring(index + 1);
    }

    /**
     * 获取数组的第一个元素
     *
     * @param array
     * @return
     */
    public static <T> T getArrayFirstElement(T[] array) {
        if (ArrayUtils.isEmpty(array)) {
            return null;
        }

        return array[0];
    }

    /**
     * if t1 = t2 return t3 else return t1
     *
     * @param t1
     * @param t2
     * @param t3
     * @return
     */
    public static <T> T replaceObject(T t1, T t2, T t3) {
        if (null == t1 && null == t2) {
            return t3;
        }

        if (null == t1) {
            return t1;
        }

        if (t1.equals(t2)) {
            return t3;
        }
        return t1;
    }

    /**
     * black string to null
     *
     * @param str
     * @return
     */
    public static String blackToNull(String str) {
        return StringUtils.isBlank(str) ? null : str;
    }

    /**
     * 字符串首字母大写
     *
     * @param str
     * @return
     */
    public static String firstCharUpper(String str) {
        if (StringUtils.isBlank(str) || Character.isUpperCase(str.charAt(0)))
            return str;
        else
            return (new StringBuilder())
                    .append(Character.toUpperCase(str.charAt(0)))
                    .append(str.substring(1)).toString();
    }

    /**
     * 获取当前进程的PID
     *
     * @return
     */
    public static long getPid() {
        String name = ManagementFactory.getRuntimeMXBean().getName();
        String pid = name.split("@")[0];
        return NumberUtils.toLong(pid);
    }

    /**
     * list去除重复元素 保证list原始的顺序
     *
     * @param list
     */
    public static void removeDuplicateNoOrder(List list) {
        if (isEmpty(list)) {
            return;
        }

        Set set = new HashSet();
        List newList = Lists.newArrayListWithExpectedSize(list.size());
        for (Iterator iter = list.iterator(); iter.hasNext(); ) {
            Object element = iter.next();
            if (set.add(element)) {
                newList.add(element);
            }
        }
        list.clear();
        list.addAll(newList);
    }

    /**
     * list1和list2的并集，去除重复元素
     *
     * @param list1
     * @param list2
     * @return
     */
    public static List unionRemoveDuplicate(List list1, List list2) {
        if (isEmpty(list1) && isEmpty(list2)) {
            return null;
        }

        Set set = new HashSet();
        if (isNotEmpty(list1)) {
            set.addAll(list1);
        }
        if (isNotEmpty(list2)) {
            set.addAll(list2);
        }
        return set2List(set);
    }

    /**
     * 比较两个list是否相等
     *
     * @param list1
     * @param list2
     * @return
     */
    public static boolean equal(List list1, List list2) {
        if (isEmpty(list1) && isEmpty(list2)) {
            return true;
        }
        if (isEmpty(list1) || isEmpty(list2)) {
            return false;
        }
        return CollectionUtils.isEqualCollection(list1, list2);
    }

    /**
     * set1和set2的差集，得到多余元素:set1中有而set2中没有的
     *
     * @param set1
     * @param set2
     * @return
     */
    public static Set subtract(Set set1, Set set2) {
        if (isEmpty(set1) && isEmpty(set2)) {
            return null;
        }
        Set set = Sets.difference(set1, set2);
        return set;
    }

    /**
     * 转换为integer的List
     *
     * @param list
     * @return
     */
    public static List<Integer> convert(List<String> list) {
        List<Integer> integerList = Lists.newArrayList();

        if (isEmpty(list)) {
            return null;
        }
        for (String term : list) {
            if (StringUtils.isNotBlank(term)) {
                integerList.add(Integer.parseInt(term));
            }
        }
        return integerList;
    }

    /**
     * 对象深拷贝
     *
     * @param obj
     * @return
     */
    public static Object deepClone(Object obj) {
        Object newObj = null;
        try {
            // Write the object out to a byte array
            ByteArrayOutputStream bos = new ByteArrayOutputStream();
            ObjectOutputStream out = new ObjectOutputStream(bos);
            out.writeObject(obj);
            out.flush();
            out.close();

            // Make an input stream from the byte array and read
            // a copy of the object back in.
            ObjectInputStream in = new ObjectInputStream(
                    new ByteArrayInputStream(bos.toByteArray()));
            newObj = in.readObject();
        } catch (IOException e) {
            logger.error("异常", e);
        } catch (ClassNotFoundException cnfe) {
            logger.error("异常", cnfe);
        }
        return newObj;
    }

    /**
     * 获取本地IPV4地址
     *
     * @return
     */
    public static String getIp() {
        try {
            Enumeration<NetworkInterface> interfaces = null;
            interfaces = NetworkInterface.getNetworkInterfaces();
            while (interfaces.hasMoreElements()) {
                NetworkInterface ni = interfaces.nextElement();
                Enumeration<InetAddress> addresss = ni.getInetAddresses();
                while (addresss.hasMoreElements()) {
                    InetAddress ip = addresss.nextElement();
                    if (ip != null && ip instanceof Inet4Address) {
                        return ip.getHostAddress();
                    }
                }
            }
        } catch (Exception e) {
            logger.error("****获取本地IP异常****", e);
        }
        return null;
    }

    /**
     * f分页获取list
     *
     * @param list
     * @param quantity
     * @return
     */
    public static List groupListByQuantity(List list, int quantity) {
        if (list == null || list.size() == 0) {
            return list;
        }
        if (quantity <= 0) {
            new IllegalArgumentException("Wrong quantity.");
        }
        List wrapList = new ArrayList();
        int count = 0;
        while (count < list.size()) {
            wrapList.add(list.subList(count, (count + quantity) > list.size() ? list.size() : count + quantity));
            count += quantity;
        }

        return wrapList;
    }

    /**
     * 获取调用标示
     *
     * @return
     */
    public static String getTraceId() {
        return UUID.randomUUID().toString().replaceAll("-", "");
    }

    /**
     * 替换ceph的文件域名为酷音文件的域名
     *
     * @param url
     * @return
     */
    public static String replace2KuyinFileDomain(String url) {
        // 云存储服务接口地址，通常是http://oss.xfinfr.com
        String configKeyOfCephDomain = "ceph.domain";
        // 替换成酷音的域名，通常是http://oss.kuyinyun.com
        String configKeyOfKuyinFileDomain = "kuyin.file.domain";
        try {
            String cephDomain = ConfigUtil.getString(configKeyOfCephDomain);
            String kuyinDomain = ConfigUtil
                    .getString(configKeyOfKuyinFileDomain);
            if (StringUtils.isNotBlank(cephDomain)
                    && StringUtils.isNotBlank(kuyinDomain)
                    && url.contains(cephDomain)) {
                url = url.replace(cephDomain, kuyinDomain);
            }
        } catch (Exception ex) {
            logger.error("****替换为酷音文件的域名异常，入参：{}****", url, ex);
        }
        return url;
    }

    /**
     * 分 -- > 元  00.00
     *
     * @param p
     * @return
     */
    public static String getShowPrice(String p) {
        double price = NumberUtils.toDouble(p);
        if (price <= 0) {
            return "0";
        }
        DecimalFormat df2 = new DecimalFormat("##0.00");
        double result = price / 100;

        String temp = df2.format(result);
        if (temp.startsWith("\\.")) {
            temp = "0" + temp;
        }
        String[] tempList = temp.split("\\.");

        if (tempList.length <= 1) {
            return tempList[0];
        }
        if ("".equals(tempList[1])) {
            return tempList[0];
        } else {
            temp = tempList[1];
            while (temp.endsWith("0")) {
                int index = temp.lastIndexOf("0");
                if (index > 0) {
                    temp = temp.substring(0, index);
                } else {
                    temp = "";
                }
            }
            if ("".equals(temp)) {
                return tempList[0];
            } else {
                return String.format(Locale.getDefault(), "%1$s.%2$s", tempList[0], temp);
            }
        }
    }

    /**
     * 获取远程IP
     *
     * @param request
     * @return
     */
    public static String getRemoteHost(HttpServletRequest request) {
        String ip = request.getHeader("x-forwarded-for");
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("Proxy-Client-IP");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("WL-Proxy-Client-IP");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getRemoteAddr();
        }
        return ip.equals("0:0:0:0:0:0:0:1") ? "127.0.0.1" : ip;
    }

    /**
     * coll1 是否包含 coll2中的任一元素
     *
     * @param coll1
     * @param coll2
     * @return
     */
    public static boolean collectionContainsAny(Collection coll1, Collection coll2) {
        if (isEmpty(coll1) || isEmpty(coll2)) {
            return false;
        }

        Iterator it = coll2.iterator();

        while (it.hasNext()) {
            if (coll1.contains(it.next())) {
                return true;
            }
        }

        return false;
    }

    /**
     * 将时间段转为字符串
     *
     * @param durationMillis
     * @return
     */
    public static String formatDurationMS(final long durationMillis) {
        return DurationFormatUtils.formatDuration(durationMillis, "mm:ss");
    }

    /**
     * 生成随机字符串，包含数字、英文大写字母、英文小写字母
     *
     * @param length 字符串长度
     * @return
     */
    public static String randomString(int length) {
        //ASCII码
//        48-67：0-9
//        65-90：A-Z
//        97~122：a-z

//        10+26+26=62，
//        随机数落到10以内，生成数字
//        随机数落到10~36以内，生成大写字母
//        随机数落到36~62以内，生成小写字母

        StringBuilder value = new StringBuilder();
        for (int i = 0; i < length; i++) {
            int num = RandomUtils.nextInt(0, 62);
            if (0 <= num && num < 10) {
                value.append((char) (48 + num));
            } else if (10 <= num && num < 36) {
                value.append((char) (65 + num - 10));
            } else if (36 <= num && num < 62) {
                value.append((char) (97 + num - 36));
            }
        }
        return value.toString();
    }

    /**
     * 判断url是否以suffix结尾
     * @param url
     * @param suffix
     * @return
     */
    public static boolean urlEndsWith(String url, String suffix) {
        if (StringUtils.isBlank(url)) {
            return false;
        }

        if (!url.contains("?")) {
            return url.endsWith(suffix);
        }

        String[] temp = StringUtils.splitByWholeSeparatorPreserveAllTokens(url,"?");
        return temp[0].endsWith(suffix);
    }

    /**
     * 去除空格
     * @param str
     * @return
     */
    public static String replaceEmpty(String str) {
        return StringUtils.isBlank(str) ? "" : str.replaceAll("(\\s|\\uFEFF|\\xA0)+", " ").trim();
    }
}
