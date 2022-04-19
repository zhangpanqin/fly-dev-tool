package com.mflyyou.common.id;

import cn.hutool.core.lang.Snowflake;

public class SnowflakeGenerate {
    private static final Snowflake SNOWFLAKE = new Snowflake(1L);

    public static long getId() {
        return SNOWFLAKE.nextId();
    }
}