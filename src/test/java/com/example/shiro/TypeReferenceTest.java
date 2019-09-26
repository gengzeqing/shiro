package com.example.shiro;

import com.alibaba.fastjson.JSONObject;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.List;

/**
 * @author rudolf.
 * @date 2019/8/12.
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class TypeReferenceTest {



    @Test
    public void typeReferenceqw() {
        List<Integer> list = new ArrayList<>();
        list.add(1);
        list.add(9);
        list.add(4);
        list.add(8);
        JSONObject jsonObj = new JSONObject();
        jsonObj.put("a", list);
        System.out.println(jsonObj);

    }
}
