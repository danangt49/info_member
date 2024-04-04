package com.project.member.util;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class UtilFn {
    public static String createUsernameFromName(String name) {
        String[] words = name.split("\\s+");

        StringBuilder usernameBuilder = new StringBuilder();
        for (String word : words) {
            usernameBuilder.append(word.charAt(0));
        }
        usernameBuilder.append((int) (Math.random() * 100));

        return usernameBuilder.toString().toLowerCase();
    }

}
