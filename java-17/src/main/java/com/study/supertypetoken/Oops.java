package com.study.supertypetoken;

import java.util.ArrayList;
import java.util.List;

/**
 * [슈퍼 타입 토큰의 한계 예제]
 * 출처: http://gafter.blogspot.com/2007/05/limitation-of-super-type-tokens.html
 */
public class Oops {
    static Favorites2 f = new Favorites2();

    static <T> List<T> favoriteList() {
        TypeRef<List<T>> ref = new TypeRef<>() {};
        List<T> result = f.getFavorite(ref);

        if (result == null) {
            result = new ArrayList<T>();
            f.setFavorite(ref, result);
        }

        return result;
    }

    public static void main(String[] args) {
        List<String> ls = favoriteList();
        List<Integer> li = favoriteList();

        // ls와 li는 같은 객체를 참조하는 와중에 데이터 "1" 추가
        li.add(1);

        /*
        실행 시 아래 오류 발생
        Exception in thread "main" java.lang.ClassCastException: class java.lang.Integer cannot be cast to class java.lang.String (java.lang.Integer and java.lang.String are in module java.base of loader 'bootstrap')
         */
        for (String s : ls) System.out.println(s);
    }
}