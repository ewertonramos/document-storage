package com.ewerton.controller;

import java.util.Optional;

class PathHandler {

    static Optional<String> getResourceId(String pathInfo) {
        Optional<String> result = Optional.empty();
        if (pathInfo != null) {
            String[] splitResult = pathInfo.split("/");
            if (splitResult.length > 1) {
                result = Optional.of(splitResult[1]);
            }
        }
        return result;
    }
}
