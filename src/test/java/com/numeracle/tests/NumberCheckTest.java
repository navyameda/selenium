package com.numeracle.tests;

import org.testng.annotations.Test;

import com.cynnent.framework.TestBase;
import com.cynnent.framework.TestContextManager;
import com.cynnent.utils.TestNGUtils;
import com.numeracle.pages.HomePage;
import com.numeracle.pages.LoginPage;

public class NumberCheckTest extends TestBase{
    private LoginPage loginPage;
    private HomePage homePage;

    @Test(description = "Numeracle Login Valid credentials" ,priority = 1)
    public void testLogin() {
        getDriver().get("https://www.staging.numeracle.com/app/signin/login");
        loginPage = new LoginPage(getDriver());

        loginPage.enterUsername("portal_admin@numeracle.com");
        loginPage.enterPassword("Admin@333");
        loginPage.clickLoginButton();
        
        homePage = new HomePage(getDriver());
        String welcomeMessage = homePage.getLoginConfirmationMessage();
        System.out.println(welcomeMessage);
        TestContextManager.setRuntimeValue("welcomeMessage", welcomeMessage.trim());

        // Assert login success
//        System.out.println(welcomeMessage);
//        Assert.assertTrue(welcomeMessage.equals("User Login Successful"));
    }
    @Test(description = "Numeracle Landing page",priority = 2)
    public void testhome() {

//    	extentTest = ExtentFactory.getInstance().getExtent();
//		 System.out.println("Testbase : "+extentTest); 
//		TestNGUtils.setExtentTest(extentTest);
//    	TestContextManager.setRuntimeValue("welcomeMessage", "SUCCESS!\n"
//    			+ "User Login Successful".trim());
    	TestNGUtils.assertEquals(TestContextManager.getRuntimeValue("welcomeMessage").toString(),"SUCCESS!\n"
    			+ "User Login Successful".trim(),"String Match");
//    	TestNGUtils.removeExtentTest();
    }
    
}

