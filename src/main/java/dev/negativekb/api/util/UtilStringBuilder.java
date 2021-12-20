package dev.negativekb.api.util;

import lombok.experimental.UtilityClass;

@UtilityClass
public class UtilStringBuilder {

    public String build(String[] args) {
        StringBuilder builder = new StringBuilder();
        for (String arg : args) {
            builder.append(arg).append(" ");
        }
        return builder.toString();
    }

    public String build(String[] args, int starting) {
        StringBuilder builder = new StringBuilder();
        for (int i = starting; i < args.length; ++i) {
            builder.append(args[i]).append(" ");
        }
        return builder.toString();
    }
}