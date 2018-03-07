//package com.scaff.tools.mongo.using;
//
//import com.scaff.tools.mongo.MainApp;
//import com.scaff.tools.mongo.model.Basic;
//import com.scaff.tools.mongo.model.MobileTestModel;
//
//import org.junit.Test;
//import org.junit.runner.RunWith;
//import org.mongodb.morphia.Datastore;
//import org.mongodb.morphia.Key;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.test.context.ContextConfiguration;
//import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
//
//import lombok.extern.slf4j.Slf4j;
//
///**
// * Created by chenwen on 17/3/30.
// */
//@SuppressWarnings("SpringJavaAutowiringInspection")
//@RunWith(SpringJUnit4ClassRunner.class)
//@ContextConfiguration
//@SpringBootTest(classes = {MainApp.class})
//@Slf4j
//public class MongoStoreTests {
//
//    @Autowired
//    Datastore datastore;
//
//    String mobile = "13241847378";
//
//    MobileTestModel model = MobileTestModel
//            .builder()
//            .id(mobile)
//            .version("v1.0.0.0")
//            .basic(
//                    Basic.builder()
//                    .areaCode("10000")
//                    .operator("CHINA_MOBILE")
//                    .build()
//            )
//            .build();
//
//    /**
//     * 测试插入
//     */
//    @Test
//    public void testMongo() {
//        /**
//         * step 1: 增加
//         */
//        Key<MobileTestModel> key = datastore.save(model);
//
//        log.info("key = {}", key);
//
//
////        /**
////         * step 2: 查询
////         */
////        Query<MobileTestModel> query = datastore.createQuery(MobileTestModel.class).field("_id").equal(mobile);
////
////        MobileTestModel dbModel = query.get();
////
////        log.info("model = {}", dbModel);
////
////        assertEquals(model.toString(), dbModel.toString());
////
////
////
////        /**
////         * step 3: 修改
////         */
////
////        /**
////         * step 1: 更新
////         */
////
////        String updateOperator = "CHINA_UNICOM";
////
////        UpdateOperations<MobileTestModel> updateOperations = datastore.createUpdateOperations(MobileTestModel.class).set("basic.operator", updateOperator);
////
////        query = datastore.createQuery(MobileTestModel.class).field("_id").equal(mobile);
////        UpdateResults updateResults = datastore.update(query, updateOperations);
////
////        log.info("updateResults = {}", updateResults);
////
////        /**
////         * step 2: 查询
////         */
////        query = datastore.createQuery(MobileTestModel.class).field("_id").equal(mobile);
////        dbModel = query.get();
////
////        model.getBasic().setOperator(updateOperator);
////
////        /**
////         * step 3: 比较
////         */
////        assertEquals(dbModel.toString(), model.toString());
////
////
////
////        /**
////         * step 4: 删除
////         */
////        query = datastore.createQuery(MobileTestModel.class).field("_id").equal(mobile);
////        datastore.delete(query);
////
////
////        query = datastore.createQuery(MobileTestModel.class).field("_id").equal(mobile);
////        dbModel = query.get();
////        assertNull(dbModel);
//    }
//}
