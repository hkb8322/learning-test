package com.study.supertypetoken;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Favorites2 {

    private Map<TypeRef<?>, Object> favorites = new HashMap<>();

    public <T> void setFavorite(TypeRef<T> type, T thing) {
        favorites.put(type, thing);
    }

    @SuppressWarnings("unchecked")
    public <T> T getFavorite(TypeRef<T> type) {
        return (T) favorites.get(type);
    }

    public static void main(String[] args) {
        Favorites2 f = new Favorites2();

        List<String> stooges = Arrays.asList("Larry", "Moe", "Curly");

        f.setFavorite(new TypeRef<>() {}, stooges);

        List<String> ls = f.getFavorite(new TypeRef<>() {});
    }
}