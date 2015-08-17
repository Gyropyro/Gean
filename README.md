#Gean

A code generation tool in java that focused around automatic generation of code for bean like classes and other objects which use them.

======

# Syntax

Gean uses YAML to specify the objects being generated, since there is easy to read and write. Gean specification files generally use the `.gspec` suffix.

```
className: Player
description: A player.
sourcePackage: uk.co.samholder.test
fields:
 - fieldName : username
   fieldType : String
 - fieldName : score
   fieldType : int
 - fieldName : last login
   fieldType : long
```

The above is an example of a class specification. Classes automatically generate fully documented setters and getters for their fields.
Processing this will lead to the generation of a java class:

======

# Future Features


## Basic
- [x] Equals and Hashcode generation (Still needs integration into the specifications)
- [ ] Add support for lists.
- [ ] Allow a Gean class field to be typed with another Gean class.

## Advanced
- [ ] Generate an SQL database schema installation script that deploys a database capable of storing the Geans.
- [ ] Generate an SQL data manager, that takes sql connections and uses them to store and retrieve Gean objects.
- [ ] Generate similar things for MongoDB, DynamoDB?
- [ ] Generate a REST service that allows CRUD operations on gean objects at a given endpoint.

## Other Ideas
- [ ] Generate code for reading and writing gean objects to inputStreams and outputStreams.

======

# Maven Plugin

Gean comes with a maven plugin to allow automatic generation and compiling of your code, making it useful in projects where a large number of data transfer objects are required, without requiring the writing and documentation of these trivial bits of code. With Gean, you can automatically generate the boring bits like getters, setters, equals and hashcode, leaving you free to worry about the more important bits.

The easiest way to useGgean is to create one maven project to store your 'bean' objects, and using it as a dependency within another project.

A sample `POM.xml` is included below that should get you going with generating a library from scratch, from Gean to Jar.

```
<?xml version="1.0" encoding="UTF-8"?>
<project xmlns="http://maven.apache.org/POM/4.0.0" xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance" xsi:schemaLocation="http://maven.apache.org/POM/4.0.0 http://maven.apache.org/xsd/maven-4.0.0.xsd">
    <modelVersion>4.0.0</modelVersion>

    <groupId>test</groupId>
    <artifactId>testgen</artifactId>
    <version>1.0-SNAPSHOT</version>

    <packaging>jar</packaging>

    <properties>
        <project.build.sourceEncoding>UTF-8</project.build.sourceEncoding>
        <maven.compiler.source>1.8</maven.compiler.source>
        <maven.compiler.target>1.8</maven.compiler.target>
        <gean.version>1.0-SNAPSHOT</gean.version>
        <gean.target.dir>${basedir}/target/generated-java</gean.target.dir>
        <gean.source.dir>${basedir}/src/main/gean</gean.source.dir>
    </properties>
    <build>
        <plugins>
            <!-- Changes the java source directory to the Gean target directory. -->
            <plugin>
                <groupId>org.codehaus.mojo</groupId>
                <artifactId>build-helper-maven-plugin</artifactId>
                <executions>
                    <execution>
                        <phase>generate-sources</phase>
                        <goals>
                            <goal>add-source</goal>
                        </goals>
                        <configuration>
                            <sources>
                                <source>${gean.target.dir}</source>
                            </sources>
                        </configuration>
                    </execution>
                </executions>
            </plugin>
            <!-- Generates the Gean sources. -->
            <plugin>
                <groupId>uk.co.samholder.gean</groupId>
                <artifactId>gean-maven-plugin</artifactId>
                <version>${gean.version}</version>
                <configuration>
                    <inputDir>${gean.source.dir}</inputDir>
                    <outputDir>${gean.target.dir}</outputDir>
                </configuration>
                <executions>
                    <execution>
                        <phase>generate-sources</phase>
                        <goals>
                            <goal>gean</goal>
                        </goals>
                    </execution>
                </executions>
            </plugin>
        </plugins>
    </build>
</project>
```

Just place your `*.gspec` files in the `src/main/gean` directory and build the project.
