package com.savannah.config;

import org.apache.coyote.http11.Http11NioProtocol;
import org.springframework.boot.web.embedded.tomcat.TomcatServletWebServerFactory;
import org.springframework.boot.web.server.ConfigurableWebServerFactory;
import org.springframework.boot.web.server.WebServerFactoryCustomizer;
import org.springframework.stereotype.Component;

/**
 * 当spring容器中没有TomcatEmbeddedServletContainerFactory这个bean时，会把此bean加载进去
 * @author stalern
 * @date 2020/01/02~01:00
 */
@Component
public class WebServerConfiguration implements WebServerFactoryCustomizer<ConfigurableWebServerFactory> {
    @Override
    public void customize(ConfigurableWebServerFactory configurableWebServerFactory) {
        //使用对应工厂类提供给我们的接口定制化我们的tomcat connector
        ((TomcatServletWebServerFactory)configurableWebServerFactory).addConnectorCustomizers(connector -> {
            Http11NioProtocol protocol = (Http11NioProtocol) connector.getProtocolHandler();
            //定制化keepAliveTimeOut,设置30秒内没有请求则服务端自动断开keepalive链接
            protocol.setKeepAliveTimeout(30000);
            //当客户端发送超过10000个请求则自动断开keepalive链接
            protocol.setMaxKeepAliveRequests(10000);
        });
    }
}