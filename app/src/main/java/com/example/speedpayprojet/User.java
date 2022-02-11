package com.example.speedpayprojet;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;

public class User {

    public  String fullname, age, email;
    public Long money;
    public List<Object> Historico;

    public String getFullname() {
        return fullname;
    }

    public void setFullname(String fullname) {
        this.fullname = fullname;
    }

    public void setAge(String age) {
        this.age = age;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setMoney(Long money) {
        this.money = money;
    }

    public void setHistorico(List<Object> historico) {
        Historico = historico;
    }

    public String getAge() {
        return age;
    }

    public String getEmail() {
        return email;
    }

    public Long getMoney() {
        return money;
    }

    public List<Object> getHistorico() {
        return Historico;
    }

    public User(){


    }

    public User(String fullname, String age, String email) {
        this.fullname = fullname;
        this.age = age;
        this.email = email;
        this.money =Long.valueOf(5000);
        this.Historico= new List<Object>() {
            @Override
            public int size() {
                return 0;
            }

            @Override
            public boolean isEmpty() {
                return false;
            }

            @Override
            public boolean contains(@Nullable Object o) {
                return false;
            }

            @NonNull
            @Override
            public Iterator<Object> iterator() {
                return null;
            }

            @NonNull
            @Override
            public Object[] toArray() {
                return new Object[0];
            }

            @NonNull
            @Override
            public <T> T[] toArray(@NonNull T[] a) {
                return null;
            }

            @Override
            public boolean add(Object o) {
                return false;
            }

            @Override
            public boolean remove(@Nullable Object o) {
                return false;
            }

            @Override
            public boolean containsAll(@NonNull Collection<?> c) {
                return false;
            }

            @Override
            public boolean addAll(@NonNull Collection<?> c) {
                return false;
            }

            @Override
            public boolean addAll(int index, @NonNull Collection<?> c) {
                return false;
            }

            @Override
            public boolean removeAll(@NonNull Collection<?> c) {
                return false;
            }

            @Override
            public boolean retainAll(@NonNull Collection<?> c) {
                return false;
            }

            @Override
            public void clear() {

            }

            @Override
            public Object get(int index) {
                return null;
            }

            @Override
            public Object set(int index, Object element) {
                return null;
            }

            @Override
            public void add(int index, Object element) {

            }

            @Override
            public Object remove(int index) {
                return null;
            }

            @Override
            public int indexOf(@Nullable Object o) {
                return 0;
            }

            @Override
            public int lastIndexOf(@Nullable Object o) {
                return 0;
            }

            @NonNull
            @Override
            public ListIterator<Object> listIterator() {
                return null;
            }

            @NonNull
            @Override
            public ListIterator<Object> listIterator(int index) {
                return null;
            }

            @NonNull
            @Override
            public List<Object> subList(int fromIndex, int toIndex) {
                return null;
            }
        };
    }
}
