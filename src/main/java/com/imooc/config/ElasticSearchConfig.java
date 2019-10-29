package com.imooc.config;

import java.net.InetAddress;
import java.net.UnknownHostException;

import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.common.settings.Settings;
//import org.elasticsearch.common.transport.InetSocketTransportAddress;
import org.elasticsearch.common.transport.TransportAddress;
import org.elasticsearch.transport.client.PreBuiltTransportClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.elasticsearch.client.ClientConfiguration;
import org.springframework.data.elasticsearch.client.RestClients;

/**
 * Created by 瓦力.
 */
@Configuration
public class ElasticSearchConfig {
    @Value("${elasticsearch.host}")
    private String esHost;

    @Value("${elasticsearch.port}")
    private int esPort;

    @Value("${elasticsearch.cluster.name}")
    private String esName;

    static {
        System.setProperty("es.set.netty.runtime.available.processors", "false");
    }

//    @Bean
//    RestHighLevelClient client() {
//
//        ClientConfiguration clientConfiguration = ClientConfiguration.builder()
//            .connectedTo("localhost:9300")
//            .build();
//
//        return RestClients.create(clientConfiguration).rest();
//    }

    @Bean
    public TransportClient esClient() throws UnknownHostException {
        Settings settings = Settings.builder()
                .put("cluster.name", this.esName)
//                .put("cluster.name", "elasticsearch")
                .put("client.transport.sniff", false)
                .build();
        System.out.println("--------------");
        System.out.println("cluster_name: " + this.esName);
        TransportAddress master = new TransportAddress(
            InetAddress.getByName(esHost), esPort
//          InetAddress.getByName("10.99.207.76"), 8999
        );

        TransportClient client = new PreBuiltTransportClient(settings)
                .addTransportAddress(master);

        return client;
    }
}
