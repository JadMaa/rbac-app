package com.gti619;

import com.gti619.configurations.AuthParam;
import com.gti619.services.PasswordManager;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class PasswordManagerTest {

    @Autowired
    private PasswordManager passwordManager;

    @Test
    public void testChange(){
        AuthParam.setComplexPassword(false);

        String msg = this.passwordManager.change("admin","secret45");

        System.out.print(msg);
    }

    @Test
    public void testChangeUser(){
        AuthParam.setComplexPassword(true);
        String msg = this.passwordManager.change("admin","Secret45123","secret45123");
        System.out.print(msg);
    }

}
