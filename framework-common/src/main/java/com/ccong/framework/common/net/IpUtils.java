package com.ccong.framework.common.net;

import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.util.Enumeration;
import javax.annotation.Nonnull;

import com.ccong.framework.common.lang.StringUtils;
import com.ccong.framework.common.math.NumberUtils;
import com.ccong.framework.common.ops.SystemUtils;
import com.ccong.framework.common.regex.RegexUtils;
import com.google.common.base.Strings;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by yusong on 2017/9/15.
 * ip工具类
 */
public class IpUtils {

    private static final Logger logger = LoggerFactory.getLogger(IpUtils.class);

    //未知IP地址
    public static final String UNKNOWN_IP_ADDRESS = "0.0.0.0";

    //默认本机IP地址
    private static String DEFAULT_LOCAL_HOST = "127.0.0.1";

    private IpUtils() {

    }

    /**
     * 将ip地址转为整数形式
     *
     * @param ipStr ip地址字符串，不能为null
     * @return 返回ip地址的整数形式；如果ip地址格式不正确或者为null，返回0
     */
    public static long ipToLong(@Nonnull String ipStr) {
        if (Strings.isNullOrEmpty(ipStr) || !RegexUtils.isIp(ipStr)) {
            logger.warn("invalid ip: {}", ipStr);
        }
        long ipLong = 0L;
        String[] numbers = StringUtils.split(ipStr, '.');
        for (int i = 0; i < 4; ++i) {
            ipLong = ipLong << 8 | NumberUtils.toInt(numbers[i]);
        }
        return ipLong;
    }

    public static String longToIp(long ipLong) {
        return "" + (ipLong >> 24) + "." + ((ipLong & 0xFFFFFF) >> 16) + "." + ((ipLong & 0xFFFF) >> 8) + "." + (ipLong
                & 0xFF);
    }

    /*
    *获取本机IP地址
    */
    public static String getLocalHost() {
        String host = null;
        try {
            if (SystemUtils.IS_OS_WINDOWS) {
                host = InetAddress.getLocalHost().getHostAddress();
            } else {
                host = getLinuxLocalIp();
            }
        } catch (Exception e) {
            logger.warn("getLocalHost|获取本地ip地址出错|", e);
            host = DEFAULT_LOCAL_HOST;
        }
        return host;
    }


    /**
     * 获取Linux下的IP地址
     *
     * @return IP地址
     * @throws SocketException
     */
    private static String getLinuxLocalIp() throws SocketException {
        String ip = "";
        try {
            for (Enumeration<NetworkInterface> en = NetworkInterface.getNetworkInterfaces(); en.hasMoreElements(); ) {
                NetworkInterface intf = en.nextElement();
                String name = intf.getName();
                if (!name.contains("docker") && !name.contains("lo")) {
                    for (Enumeration<InetAddress> enumIpAddr = intf.getInetAddresses();
                            enumIpAddr.hasMoreElements(); ) {
                        InetAddress inetAddress = enumIpAddr.nextElement();
                        if (!inetAddress.isLoopbackAddress()) {
                            String ipaddress = inetAddress.getHostAddress();
                            if (!ipaddress.contains("::") && !ipaddress.contains("0:0:") && !ipaddress
                                    .contains("fe80")) {
                                ip = ipaddress;
                                logger.info(ipaddress);
                            }
                        }
                    }
                }
            }
        } catch (SocketException ex) {
            ip = DEFAULT_LOCAL_HOST;
            logger.info("getLinuxLocalIp|获取Linux Ip异常", ex);
        }
        logger.info("IP:{}", ip);
        return ip;
    }

}
