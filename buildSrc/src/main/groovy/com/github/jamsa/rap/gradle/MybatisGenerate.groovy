package com.github.jamsa.rap.gradle

import org.gradle.api.Action
import org.gradle.api.DefaultTask
import org.gradle.api.Task
import org.gradle.api.logging.Logger
import org.gradle.api.logging.Logging
import org.gradle.api.tasks.TaskAction
import org.mybatis.generator.api.MyBatisGenerator
import org.mybatis.generator.config.Configuration
import org.mybatis.generator.config.xml.ConfigurationParser
import org.mybatis.generator.internal.DefaultShellCallback
import org.yaml.snakeyaml.Yaml

/**
 * Created by zhujie on 16/10/21.
 */
class MybatisGenerate extends DefaultTask{
    private static Logger logger = Logging.getLogger(MybatisGenerate.class);

    //String dbConfigFile = "src/main/resources/config.properties"
    String dbConfigFile = "src/main/resources/application.yml"
    String generateConfigFile='src/main/resources/generator-config.xml'
    String modelPackage = null
    String mapperPackage = null
    String sqlMapperPackage = null
    boolean overwrite = true

    /*
    def getDbProperties() {
        Properties props = new Properties()
        project.file(dbConfigFile).withInputStream { inputStream ->
            props.load(inputStream)
        }
        props
    }*/

    def getDbProperties() {
        Properties props = new Properties()
        project.file(dbConfigFile).withInputStream { inputStream ->
            def result = new Yaml().load(inputStream)
            props.put("jdbc.driver",result.spring.datasource."driver-class-name")
            props.put("jdbc.url",result.spring.datasource.url)
            props.put("jdbc.username",result.spring.datasource.username)
            props.put("jdbc.password",result.spring.datasource.password)
        }
        props
    }

    def getGenerateProperties() {
        Properties properties = this.getDbProperties()
        Properties result = new Properties()
        result['targetProject'] = project.projectDir.path
        result['driverClass'] = properties.getProperty("jdbc.driver")
        result['connectionURL'] = properties.getProperty("jdbc.url")
        result['username'] = properties.getProperty("jdbc.username")
        result['password'] = properties.getProperty("jdbc.password")
        result['src_main_java'] = project.sourceSets.main.java.srcDirs[0].path
        result['src_main_resources'] = project.sourceSets.main.resources.srcDirs[0].path
        result['modelPackage'] = this.modelPackage
        result['mapperPackage'] = this.mapperPackage
        result['sqlMapperPackage'] = this.sqlMapperPackage
        return result
    }

    //加载驱动类
    def loadJdbcDriver(){
        def loader = this.class.getClassLoader()
        //getConfigurations().getByName('mybatisGenerator').each
        project.configurations.mybatisGenerator.each{File f->
            loader.addURL(f.toURL())
        }
    }

    @TaskAction
    def generate () {
        loadJdbcDriver()

        def properties = this.getGenerateProperties()
        def warnings = new ArrayList<String>()

        Configuration config = new ConfigurationParser(properties,warnings).parseConfiguration(project.file(generateConfigFile))

        MyBatisGenerator generator = new MyBatisGenerator(config,new DefaultShellCallback(overwrite),warnings)
        generator.generate(null)
    }
}
