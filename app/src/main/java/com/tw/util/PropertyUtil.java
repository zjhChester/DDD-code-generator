package com.tw.util;

import org.springframework.beans.factory.config.YamlPropertiesFactoryBean;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.FileUrlResource;

import java.net.MalformedURLException;
import java.util.Properties;

public class PropertyUtil {
    /**
     *   配置接收对象
     */
    private static  Properties properties = null;


    static private void init (){
        YamlPropertiesFactoryBean yamlPropertiesFactoryBean = new YamlPropertiesFactoryBean();
        try {
            findConfigInRunPath(yamlPropertiesFactoryBean);
        } catch (Exception e) {
            e.printStackTrace();
        }
        properties = yamlPropertiesFactoryBean.getObject();
    }

    private static void findConfigInRunPath(YamlPropertiesFactoryBean yamlPropertiesFactoryBean) throws MalformedURLException {
        String path = PropertyUtil.class.getProtectionDomain()
                .getCodeSource().getLocation().getPath();
        if(path.endsWith(".jar")){
            path = path.substring(0, path.lastIndexOf("/")+1);
        }
        System.out.println("\n\n===============================\n\n");
        System.out.printf("loading configFilePath address:「 %s%n", path +"generator-config.yml」");
        System.out.println("\n\n===============================\n\n");
        FileUrlResource fileUrlResource = new FileUrlResource(path + "generator-config.yml");
        yamlPropertiesFactoryBean.setResources(fileUrlResource);
        if(!fileUrlResource.exists()){
            yamlPropertiesFactoryBean.setResources(new ClassPathResource("generator-config.yml"));
        }
    }

    public static String  getConfig(String config){
        init();
        return properties.getProperty(config);
    }
}
