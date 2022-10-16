package com.cgm.test;

import com.MyApplication;
import com.cgm.annocation.DataSource;
import com.cgm.enums.DataType;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = MyApplication.class)
public class DataSourceTest {



    @Test
    @DataSource(DataType.MASTER)
    public void test1(){

    }
}
