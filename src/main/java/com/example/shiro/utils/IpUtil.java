package com.example.shiro.utils;


import javax.servlet.http.HttpServletRequest;

/**
 * @author carlosxiao
 */
public class IpUtil {
    private static final String[] PROXY_REMOTE_IP_ADDRESS = {"X-Forwarded-For", "X-Real-IP"};

    public static String getRemoteIp(HttpServletRequest request) {
        for (int i = 0; i < PROXY_REMOTE_IP_ADDRESS.length; i++) {
            String ip = request.getHeader(PROXY_REMOTE_IP_ADDRESS[i]);
            if (ip != null && ip.trim().length() > 0) {
                String remoteIp = getRemoteIpFromForward(ip.trim());
                if (remoteIp != null) {
                    return remoteIp;
                }
            }
        }
        return request.getRemoteAddr();
    }

    private static String getRemoteIpFromForward(String xforwardIp) {
        String[] commaOffset = xforwardIp.split(",");
        if (commaOffset.length < 0) {
            return null;
        }
        String regIp = "^(25[0-5]|2[0-4]\\d|[0-1]?\\d?\\d)(\\.(25[0-5]|2[0-4]\\d|[0-1]?\\d?\\d)){3}$";
        for (int i = 0; i < commaOffset.length; i++) {
            if (commaOffset[i].matches(regIp)) {
                return commaOffset[i];
            }
        }
        return null;
    }

}
