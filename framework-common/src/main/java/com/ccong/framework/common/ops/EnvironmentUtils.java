package com.ccong.framework.common.ops;


import com.ccong.framework.common.lang.ArrayUtils;

public class EnvironmentUtils {

    private EnvironmentUtils() {

    }

    private static EnvironmentEnum determineCurrentEnv(String[] activeProfiles) {
        if (ArrayUtils.contains(activeProfiles, "pro") || ArrayUtils.contains(activeProfiles, "prod") || ArrayUtils
                .contains(activeProfiles, "product")) {
            return EnvironmentEnum.PRO;
        } else if (ArrayUtils.contains(activeProfiles, "trunk") || ArrayUtils.contains(activeProfiles, "pre")) {
            return EnvironmentEnum.TRUNK;
        } else if (ArrayUtils.contains(activeProfiles, "high") || ArrayUtils.contains(activeProfiles, "stress")) {
            return EnvironmentEnum.HIGH;
        } else if (ArrayUtils.contains(activeProfiles, "live") || ArrayUtils.contains(activeProfiles, "test")) {
            return EnvironmentEnum.LIVE;
        } else {
            return EnvironmentEnum.DEV;
        }
    }

}
