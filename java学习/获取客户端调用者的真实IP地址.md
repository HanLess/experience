```
public static String getRemoteAddr(final HttpServletRequest request) {
        try {

            String remoteAddr = request.getHeader("X-Forwarded-For"); // 如果通过多级反向代理，X-Forwarded-For的值不止一个，而是一串用逗号分隔的IP值，此时取X-Forwarded-For中第一个非unknown的有效IP字符串
            if (isEffective(remoteAddr) && (remoteAddr.indexOf(",") > -1)) {
                String[] array = remoteAddr.split(",");
                for (String element : array) {
                    if (isEffective(element)) {
                        remoteAddr = element;
                        break;
                    }
                }
            }
            if (!isEffective(remoteAddr)) {
                remoteAddr = request.getHeader("Proxy-ClientEnum-IP");
            }
            if (!isEffective(remoteAddr)) {
                remoteAddr = request.getHeader("WL-Proxy-ClientEnum-IP");
            }
            if (!isEffective(remoteAddr)) {
                remoteAddr = request.getHeader("HTTP_CLIENT_IP");
            }
            if (!isEffective(remoteAddr)) {
                remoteAddr = request.getHeader("X-Real-IP");
            }
            if (!isEffective(remoteAddr)) {
                remoteAddr = request.getRemoteAddr();
            }
            return "0:0:0:0:0:0:0:1".equals(remoteAddr) ? "127.0.0.1" : remoteAddr;
        } catch (Exception e) {
            logger.error("getRemoteAddr ERROR:{}", e);
            return "";
        }
    }
```
