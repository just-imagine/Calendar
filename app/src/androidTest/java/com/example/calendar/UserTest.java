package com.example.calendar;

import static org.junit.Assert.*;
import android.support.test.internal.util.ReflectionUtil;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Collections;

import javax.annotation.meta.When;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.*;

public class UserTest {

    @Before
    public void setUp() throws Exception {
    }

    @After
    public void tearDown() throws Exception {
    }
    @Test
    public void getName() throws NoSuchFieldException, IllegalAccessException {
        final User temp= new User();
        final Field field= temp.getClass().getDeclaredField("Name");
        field.setAccessible(true);
        field.set(temp,"Tshifhiwa");
        final  String results= temp.getName();
        assertEquals("Tshifhiwa",results);
    }
    @Test
    public void getSurname() throws NoSuchFieldException, IllegalAccessException {
        final User temp= new User();
        final Field field = temp.getClass().getDeclaredField("Surname");
        field.setAccessible(true);
        field.set(temp, "Mavhona");
        final String results = temp.getSurname();
        assertEquals("Mavhona", results);
    }

    @Test
    public void getContact() throws NoSuchFieldException, IllegalAccessException {
        final User temp= new User();
        final Field field= temp.getClass().getDeclaredField("Contact");
        field.setAccessible(true);
        field.set(temp,"0659545378");
        final  String results= temp.getContact();
        assertEquals("0659545378",results);

    }

    @Test
    public void getEmail() throws NoSuchFieldException, IllegalAccessException {
        final User temp= new User();
        final Field field= temp.getClass().getDeclaredField("Email");
        field.setAccessible(true);
        field.set(temp,"tmavhona@gmail.com");
        final  String results= temp.getEmail();
        assertEquals("tmavhona@gmail.com",results);
    }

    @Test
    public void getIdentity() throws NoSuchFieldException, IllegalAccessException {
        final User temp= new User();
        final Field field= temp.getClass().getDeclaredField("Identity");
        field.setAccessible(true);
        field.set(temp,"9812176232089");
        final String results= temp.getIdentity();
        assertEquals("9812176232089",results);

    }


    @Test
    public void getPassword() throws NoSuchFieldException, IllegalAccessException {
        final User temp= new User();
        final Field field= temp.getClass().getDeclaredField("Password");
        field.setAccessible(true);
        field.set(temp,"afterlife");
        final String results= temp.getPassword();
        assertEquals("afterlife",results);

    }


    @Test
    public void getGender() throws NoSuchFieldException, IllegalAccessException {
        final User temp= new User();
        final Field field= temp.getClass().getDeclaredField("Gender");
        field.setAccessible(true);
        field.set(temp,"Male");
        final String results= temp.getGender();
        assertEquals("Male",results);
    }


    @Test
    public void setPassword() throws NoSuchFieldException, IllegalAccessException {
        final User temp= new User();
        temp.setPassword("Mavhona");
        final  Field field = temp.getClass().getDeclaredField("Password");
        field.setAccessible(true);
        assertEquals("Mavhona",field.get(temp));

    }

    @Test
    public void setConfirmPassword() throws NoSuchFieldException, IllegalAccessException {
        final User temp= new User();
        temp.setConfirmPassword("Mavhona");
        final  Field field = temp.getClass().getDeclaredField("confirmPassword");
        field.setAccessible(true);
        assertEquals("Mavhona",field.get(temp));
    }


    @Test
    public void setName() throws NoSuchFieldException, IllegalAccessException {
        final User temp= new User();
        temp.setName("Mavhona");
        final  Field field = temp.getClass().getDeclaredField("Name");
        field.setAccessible(true);
        assertEquals("Mavhona",field.get(temp));

    }

    @Test
    public void setSurname() throws NoSuchFieldException, IllegalAccessException {
        final User temp= new User();
        temp.setSurname("Mavhona");
        final  Field field =temp.getClass().getDeclaredField("Surname");
        field.setAccessible(true);
        assertEquals("Mavhona",field.get(temp));
    }

    @Test
    public void setContact() throws NoSuchFieldException, IllegalAccessException {
        final User temp= new User();
        temp.setContact("0769545378");
        final  Field field =temp.getClass().getDeclaredField("Contact");
        field.setAccessible(true);
        assertEquals("0769545378",field.get(temp));

    }

    @Test
    public void setEmail() throws NoSuchFieldException, IllegalAccessException {
        final User temp= new User();
        temp.setEmail("tmavhona@gmail.com");
        final  Field field =temp.getClass().getDeclaredField("Email");
        field.setAccessible(true);
        assertEquals("tmavhona@gmail.com",field.get(temp));

    }

    @Test
    public void setIdentity() throws NoSuchFieldException, IllegalAccessException {
        final User temp= new User();
        temp.setIdentity("9812176232089");
        final  Field field =temp.getClass().getDeclaredField("Identity");
        field.setAccessible(true);
        assertEquals("9812176232089",field.get(temp));

    }


    @Test
    public void setGender() throws NoSuchFieldException, IllegalAccessException {
        final User temp= new User();
        temp.setGender("Female");
        final  Field field =temp.getClass().getDeclaredField("Gender");
        field.setAccessible(true);
        assertEquals("Female",field.get(temp));
    }

    @Test
    public void validEmail() throws NoSuchFieldException, IllegalAccessException {
        final User temp= new User();
        final Field field= temp.getClass().getDeclaredField("Email");
        field.setAccessible(true);
        field.set(temp,"tmavhona@gmail.com");
        assertTrue(temp.validEmail());
        field.set(temp,"tmavhona@gmailcom");
        assertFalse(temp.validEmail());
        field.set(temp,"tmavhonagmail.com");
        assertFalse(temp.validEmail());
        field.set(temp,"@gmail.com");
        assertFalse(temp.validEmail());
        field.set(temp,"@g");
        assertFalse(temp.validEmail());
        field.set(temp,"");
        assertFalse(temp.validEmail());
        field.set(temp,null);
        assertFalse(temp.validEmail());
    }

    @Test
    public void isAlphaNum() {
        final User temp= new User();
        assertFalse(temp.isAlphaNum('@'));
        assertTrue(temp.isAlphaNum('a'));
        assertTrue(temp.isAlphaNum('1'));
    }

    @Test
    public void validName() throws IllegalAccessException, NoSuchFieldException {
        final User temp= new User();
        final User SpyUser= spy(temp);
        final Field field= temp.getClass().getDeclaredField("Name");
        field.setAccessible(true);
        field.set(temp,"Tshifhiwa");
        assertTrue(temp.validName());
        field.set(temp,"Tshif hiwa");
        assertTrue(temp.validName());
        field.set(temp,"");
        assertFalse(temp.validName());
        field.set(temp,null);
        assertFalse(temp.validName());
        field.set(temp,"Tshifhiwa88");
        assertFalse(temp.validName());

    }

    @Test
    public void validIdentity() throws NoSuchFieldException, IllegalAccessException {
        final User temp= new User();
        final Field field= temp.getClass().getDeclaredField("Identity");
        field.setAccessible(true);
        field.set(temp,"9812176232089");
        assertTrue(temp.validIdentity());
        field.set(temp,"981217623208");
        assertFalse(temp.validIdentity());
        field.set(temp,"98121762320809");
        assertFalse(temp.validIdentity());
        field.set(temp,"");
        assertFalse(temp.validIdentity());
        field.set(temp,null);
        assertFalse(temp.validIdentity());
        field.set(temp,"981217623208T");
        assertFalse(temp.validIdentity());





    }

    @Test
    public void validSurname() throws NoSuchFieldException, IllegalAccessException {
        final User temp= new User();
        final Field field= temp.getClass().getDeclaredField("Surname");
        field.setAccessible(true);
        field.set(temp,"Tshifhiwa");
        assertTrue(temp.validSurname());
        field.set(temp,"Tshif hiwa");
        assertTrue(temp.validSurname());
        field.set(temp,"");
        assertFalse(temp.validSurname());
        field.set(temp,null);
        assertFalse(temp.validSurname());
        field.set(temp,"Tshifhiwa88");
        assertFalse(temp.validSurname());
    }

    @Test
    public void validPassword() throws NoSuchFieldException, IllegalAccessException {
        final User temp= new User();
        final Field field= temp.getClass().getDeclaredField("Password");
        field.setAccessible(true);
        field.set(temp,"Tshifhiwa");
        assertTrue(temp.validPassword());
        field.set(temp,"");
        assertFalse(temp.validPassword());
        field.set(temp,"sad");
        assertFalse(temp.validPassword());
        field.set(temp,null);
        assertFalse(temp.validPassword());
    }

    @Test
    public void validContact() throws NoSuchFieldException, IllegalAccessException {

        final User temp= new User();
        final Field field= temp.getClass().getDeclaredField("Contact");
        field.setAccessible(true);
        field.set(temp,"0659545378");
        assertTrue(temp.validContact());
        field.set(temp,"06595453");
        assertFalse(temp.validContact());
        field.set(temp,null);
        assertFalse(temp.validContact());
        field.set(temp,"7659545378");
        assertFalse(temp.validEmail());
        field.set(temp,"765T545378");
        assertFalse(temp.validEmail());
    }

    @Test
    public void confirmPassword() throws NoSuchFieldException, IllegalAccessException {
        final User temp= new User();
        final Field field= temp.getClass().getDeclaredField("Password");
        field.setAccessible(true);
        field.set(temp,"afterlife");
        final Field field1= temp.getClass().getDeclaredField("confirmPassword");
        field1.setAccessible(true);
        field1.set(temp,"afterlife");
        assertTrue(temp.confirmPassword());
        field1.set(temp,"afterl");
        assertFalse(temp.confirmPassword());
    }

    @Test
    public void validGender() throws NoSuchFieldException, IllegalAccessException {
        final User temp= new User();
        final Field field= temp.getClass().getDeclaredField("Gender");
        field.setAccessible(true);
        field.set(temp,"Male");
        assertTrue(temp.validGender());
        field.set(temp,"Female");
        assertTrue(temp.validGender());
        field.set(temp,"");
        assertFalse(temp.validGender());
        field.set(temp,null);
        assertFalse(temp.validGender());
        field.set(temp,"other");
        assertFalse(temp.validGender());
    }

    @Test
    public void validUser() {

        User SpyUser= spy(User.class);
        doReturn(true).when(SpyUser).validEmail();
        doReturn(true).when(SpyUser).validName();
        doReturn(true).when(SpyUser).validSurname();
        doReturn(true).when(SpyUser).validIdentity();
        doReturn(true).when(SpyUser).validGender();
        doReturn(true).when(SpyUser).validPassword();
        doReturn(true).when(SpyUser).confirmPassword();
        assertTrue(SpyUser.validUser());
        doReturn(false).when(SpyUser).validPassword();
        assertFalse(SpyUser.validUser());

    }

    @Test
    public void equals() throws NoSuchFieldException, IllegalAccessException {

        final User temp= new User();
        final User temp2= new User();
        assertTrue(temp.equals(temp));
        Field field= temp.getClass().getDeclaredField("Password");
        field.setAccessible(true);
        field.set(temp,"afterlife");
        field= temp.getClass().getDeclaredField("Email");
        field.setAccessible(true);
        field.set(temp,"tmavhona@gmail.com");
        field= temp2.getClass().getDeclaredField("Password");
        field.setAccessible(true);
        field.set(temp2,"afterlife");
        field= temp2.getClass().getDeclaredField("Email");
        field.setAccessible(true);
        field.set(temp2,"tmavhona@gmail.com");
        assertTrue(temp.equals(temp2));
        field.set(temp2,"tmavhona@gmail.co.za");
        assertFalse(temp.equals(temp2));
    }

    @Test
    public void getData() throws NoSuchFieldException, IllegalAccessException {
        final User temp= new User();
        Field field = temp.getClass().getDeclaredField("Surname");
        field.setAccessible(true);
        field.set(temp, "Mavhona");
        field= temp.getClass().getDeclaredField("Email");
        field.setAccessible(true);
        field.set(temp,"tmavhona@gmail.com");
        field= temp.getClass().getDeclaredField("Identity");
        field.setAccessible(true);
        field.set(temp,"9812176232089");
        field= temp.getClass().getDeclaredField("Name");
        field.setAccessible(true);
        field.set(temp,"Tshifhiwa");
        ArrayList<String>results=temp.getData();
        ArrayList<String>expected= new ArrayList<>();
        Collections.addAll(expected,"9812176232089","tmavhona@gmail.com","Tshifhiwa","Mavhona");
        assertEquals(expected,results);

    }
}

