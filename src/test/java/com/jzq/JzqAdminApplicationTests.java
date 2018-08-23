package com.jzq;


import org.apache.shiro.codec.Base64;
import org.apache.shiro.crypto.AesCipherService;
import org.apache.shiro.crypto.SecureRandomNumberGenerator;
import org.apache.shiro.crypto.hash.DefaultHashService;
import org.apache.shiro.crypto.hash.HashRequest;
import org.apache.shiro.crypto.hash.SimpleHash;
import org.apache.shiro.util.ByteSource;
import org.apache.shiro.util.SimpleByteSource;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.security.Key;

@RunWith(SpringRunner.class)
@SpringBootTest
public class JzqAdminApplicationTests {

    @Test
    public void contextLoads() {
        String str = "4AvVhmFLUs0KTA3Kprsdag==";
        String base64Encoded = Base64.encodeToString(str.getBytes());
        System.out.println(base64Encoded);
        System.out.println(Base64.decode(str));
        System.out.println(Base64.decodeToString(base64Encoded));
        String str1 = "hello";
        String salt = "123";
        //内部使用MessageDigest
        String simpleHash = new SimpleHash("SHA-1", str1, salt).toString();
        System.out.println("=====================");
        System.out.println(simpleHash);
        System.out.println("**************************");
        DefaultHashService hashService = new DefaultHashService(); //默认算法SHA-512
        hashService.setHashAlgorithmName("SHA-512");
        hashService.setPrivateSalt(new SimpleByteSource("123")); //私盐，默认无
        hashService.setGeneratePublicSalt(true);//是否生成公盐，默认false
        hashService.setRandomNumberGenerator(new SecureRandomNumberGenerator());//用于生成公盐。默认就这个
        hashService.setHashIterations(1); //生成Hash值的迭代次数

        HashRequest request = new HashRequest.Builder()
                .setAlgorithmName("MD5")
                .setSource(ByteSource.Util.bytes("hello"))
                .setSalt(ByteSource.Util.bytes("123"))
                .setIterations(2).build();
        String hex = hashService.computeHash(request).toHex();
        System.out.println(hex);
        System.out.println("**************************");
        AesCipherService aesCipherService = new AesCipherService();
        aesCipherService.setKeySize(128); //设置key长度
        //生成key
        Key key = aesCipherService.generateNewKey();
        String text = "hello";
        //加密
        String encrptText = aesCipherService.encrypt(text.getBytes(), key.getEncoded()).toHex();
        System.out.println(encrptText);
        System.out.println("**************************");
        String algorithmName = "md5";
        String username = "liu";
        String password = "123";
        String salt1 = username;
        String salt2 = new SecureRandomNumberGenerator().nextBytes().toHex();
        int hashIterations = 2;

        SimpleHash hash = new SimpleHash(algorithmName, password, salt1 + salt2, hashIterations);
        String encodedPassword = hash.toHex();
        System.out.println(encodedPassword);
    }

}
