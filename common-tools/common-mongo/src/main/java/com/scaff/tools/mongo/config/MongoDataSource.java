package com.scaff.tools.mongo.config;

import com.mongodb.MongoClient;
import com.mongodb.MongoClientOptions;
import com.mongodb.MongoCredential;
import com.mongodb.ServerAddress;
import com.netflix.discovery.converters.Auto;
import com.scaff.utils.ReflectionUtil;

import org.junit.Test;
import org.mongodb.morphia.Datastore;
import org.mongodb.morphia.Morphia;
import org.springframework.beans.TypeConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Set;

/**
 * Created by chenwen on 17/3/30.
 */
@Configuration
public class MongoDataSource {

    private final String basePackages = "com.scaff";


    @Bean(name = {"mongoConfig"})
    @ConditionalOnMissingBean
    public MongoConfig mongoConfig() {
        return new MongoConfig();
    }

    /**
     * 可能会有这样一种情况，当你创建多个具有相同类型的 bean 时，并且想要用一个属性只为它们其中的一个进行装配，在这种情况下，你可以使用 @Qualifier 注释和 @Autowired 注释通过指定哪一个真正的 bean 将会被装配来消除混乱。
     */
    @Autowired
    @Qualifier("mongoConfig")
    private MongoConfig config;

    @Bean
    @ConditionalOnMissingBean
    public MongoClient mongoClient(){

        MongoClientOptions.Builder builder = MongoClientOptions.builder();

        builder.maxWaitTime(config.getMaxWaitTime());
        builder.connectTimeout(config.getConnectionTimeout());
        builder.connectionsPerHost(config.getConnectionsPerHost());
        builder.threadsAllowedToBlockForConnectionMultiplier(config.getThreadsAllowedToBlockForConnectionMultiplier());
        builder.codecRegistry(MongoClient.getDefaultCodecRegistry());
        if(config.getHost() == null){
            return null;
        }
        String[] hosts = config.getHost().split(",");

        List<ServerAddress> servers = new ArrayList<>();

        for(String host : hosts){
            String[] hostAndPort = host.split(":");
            if (hostAndPort.length > 1){
                servers.add(new ServerAddress(hostAndPort[0], Integer.parseInt(hostAndPort[1])));
            }else if (config.getPort() != null){
                servers.add(new ServerAddress(host, config.getPort()));
            }else {
                servers.add(new ServerAddress(host, 27017));
            }
        }
        if (config.isAuth()){
            MongoCredential credential = MongoCredential.createCredential(config.getUsername(), config.getDatabase(), config.getPassword().toCharArray());
            return new MongoClient(servers, Collections.singletonList(credential), builder.build());
        }
        return new MongoClient(servers,builder.build());

    }

    @Bean
    @ConditionalOnMissingBean
    public Datastore datastore(){
        Morphia morphia = new Morphia();

        Set<Class<? extends TypeConverter>> subClass = ReflectionUtil.getSubClass(basePackages, TypeConverter.class);

        for(Class<? extends TypeConverter> converter : subClass) {
            morphia.getMapper().getConverters().addConverter((Class<? extends org.mongodb.morphia.converters.TypeConverter>) converter);
        }

        morphia.mapPackage(basePackages);
        if (mongoClient() == null){
            return null;
        }
        Datastore datastore = morphia.createDatastore(mongoClient(), config.getDatabase());
        datastore.ensureIndexes();

        return datastore;
    }

}
