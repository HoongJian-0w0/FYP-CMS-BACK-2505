package io.github.hoongjian_0w0.cmsback.common.utils;

import com.baomidou.mybatisplus.generator.FastAutoGenerator;
import com.baomidou.mybatisplus.generator.config.OutputFile;

import java.util.Collections;
import java.util.Map;

public class CodeGenerator {

    private final static String MAIN_DIRECTORY = "C:\\Users\\Hoong Jian\\IdeaProjects\\club-management-system\\cms-back\\src\\main\\java\\";

    private final static String MAPPER_XML_DIRECTORY = "C:\\Users\\Hoong Jian\\IdeaProjects\\club-management-system\\cms-back\\src\\main\\resources\\mapper";


    public static void main(String[] args) {
        generate();
    }

    private static void generate() {
        FastAutoGenerator.create("jdbc:mysql://localhost:3306/cms?serverTimezone=Asia/Kuala_Lumpur","root","1234")
                .globalConfig(builder -> {
                    builder.author("") // Author
                            //            .enableSwagger() // Enable Swagger
                            .outputDir(MAIN_DIRECTORY); // Export Directory
                })
                .packageConfig(builder -> {
                    builder.parent("io.github.hoongjian_0w0.cmsback") // Parent Package
                            .moduleName("") // Parent Module
                            .pathInfo(Map.of(
                                    OutputFile.xml, MAPPER_XML_DIRECTORY,                     // XML file location
                                    OutputFile.mapper, MAIN_DIRECTORY + "io/github/hoongjian_0w0/cmsback/mapper/" // Mapper.java
                            ));
                })
                .strategyConfig(builder -> {
                    builder.entityBuilder().enableLombok();
                    builder.mapperBuilder().enableMapperAnnotation().build();
                    builder.controllerBuilder().enableHyphenStyle() // enable CamelCase
                            .enableRestStyle(); // Enable @RestController Interceptor
                    builder.addInclude("cms_menu") // <---- Configure Needed Table Name
                            .addTablePrefix("t_","cms_"); // <---- Configure Filter Table Prefix
                })
                // .templateEngine(new FreemarkerTemplateEngine()) // Default Engine Template: Velocity
                .execute();
    }

}