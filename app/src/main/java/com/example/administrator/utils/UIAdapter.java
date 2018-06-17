package com.example.administrator.utils;

import java.util.HashMap;
import java.util.List;

public interface UIAdapter<T>{

     List<HashMap<String,String>> UIPageAdapter(List<T> info);

}
