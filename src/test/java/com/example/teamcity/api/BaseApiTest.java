package com.example.teamcity.api;

import com.example.teamcity.api.generators.TestDataStorage;
import com.example.teamcity.api.requests.CheckedRequests;
import com.example.teamcity.api.requests.UncheckedRequests;
import com.example.teamcity.api.spec.Specifications;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
public class BaseApiTest extends BaseTest {

    public TestDataStorage testDataStorage;
   // public TestData testData;
    public CheckedRequests checkedWithSuperUser = new CheckedRequests(Specifications.getSpec().superUserSpec());
    public UncheckedRequests uncheckedWithSuperUser = new UncheckedRequests(Specifications.getSpec().superUserSpec());

    @BeforeMethod
//    public void setupTest() {
//        testData = new TestDataGenerator().generate();
//    }
    public void setupTest() {
        testDataStorage = TestDataStorage.getStorage();
    }

    @AfterMethod
//    public void cleanTest() {
//        testData.delete();
//    }
    public void cleanTest() {
        testDataStorage.delete();
    }
}
