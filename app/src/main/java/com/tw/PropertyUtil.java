package com.tw;

import org.springframework.beans.factory.config.YamlPropertiesFactoryBean;
import org.springframework.core.io.FileUrlResource;

import java.net.MalformedURLException;
import java.util.Properties;

public class PropertyUtil {
    /**
     *   配置接收对象
     */
    private static final Properties properties;


    static{
        YamlPropertiesFactoryBean yamlPropertiesFactoryBean = new YamlPropertiesFactoryBean();
        try {
            String path = PropertyUtil.class.getProtectionDomain()
                    .getCodeSource().getLocation().getPath();
            if(path.endsWith(".jar")){
                path = path.substring(0, path.lastIndexOf("/")+1);
            }
            System.out.println("\n\n===============================\n\n");
            System.out.printf("loading configFilePath address:「 %s%n", path +"generator-config.yml」");
            System.out.println("\n\n===============================\n\n");
            yamlPropertiesFactoryBean.setResources(new FileUrlResource(path+"generator-config.yml"));
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        properties = yamlPropertiesFactoryBean.getObject();
    }
    public static String  getConfig(String config){
        return properties.getProperty(config);
    }
}
