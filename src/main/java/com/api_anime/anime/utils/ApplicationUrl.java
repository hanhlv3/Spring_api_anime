package com.api_anime.anime.utils;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpRequest;

public class ApplicationUrl {

    public static String getApplicationUrl(HttpServletRequest request) {
        return "http://" +
                request.getServerName() +
                ":" + request.getServerPort() +
                request.getContextPath();
    }

    public static String getUrlImage(String imageName, HttpServletRequest request) {

        return ApplicationUrl.getApplicationUrl(request)
                + "/api/v1/public/image/"
                + imageName;
    }
}
