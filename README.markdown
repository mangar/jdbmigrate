JDBMigrate
==========

Short explanation: The motivation of the JDBMigrate is the rake db:migrate for Java projects.

Complete Explanation: After some time developing with Java, I've learned RoR and to be very honest it was absolutely amazed with all the benefits of a dynamic language mixed up with the simplicity of the development and the way how to organize the database migration scripts. 

Backing to the Java world, I've looking for a simple way how to control the database migrations as it is in RoR and I've found nothing as simple as that.

So, this is the why I started this project: "Simplicity to control the database migration scripts"

Feel free to contribute with your opinion, bug fixes, tests implementations or just watch it!


dbmigrate.properties sample
---------------------------

    driver_class=com.mysql.jdbc.Driver
    url=jdbc:mysql://localhost:3306/springrest
    username=springrest
    password=springrest



MAVEN (not implemented yet)
---------------------------

- maven integration
  - goals
    - db:new (base_name)
    - db:migrate (version)

ANT
---

Be sure to have the libs included in your classpath:

- jdbmigrate-ant.jar
- jdbmigrate-core.jar
- commons-lang-2.5.jar

Also your database driver, in this case is mysql:
 
- mysql-connector-java-5.1.10.jar 

Usage:

    <?xml version="1.0"?>
    <project default="main">
    
    	<taskdef name="jdbmigrate" classname="mg.jdbmigrate.ant.JDBMigrateTask">
    		<classpath>
    			<pathelement location="./lib/jdbmigrate-ant-0.0.1.jar" />
    			<pathelement location="./lib/jdbmigrate-core-0.0.1.jar" />
    			<pathelement location="./lib/commons-lang-2.5.jar" />
    			<pathelement location="./lib/mysql-connector-java-5.1.10.jar" />
    		</classpath>
    	</taskdef>
    
    	<!-- last available update -->
    	<target name="last">
    		<jdbmigrate config="db/dbmigrate.properties" />
    	</target>
    
    	<!-- back to the version one -->
    	<target name="first">
    		<jdbmigrate to="1" config="db/dbmigrate.properties" />
    	</target>
    
    	<!-- clean databse, version zero -->
    	<target name="zero">
    		<jdbmigrate to="0" config="db/dbmigrate.properties" />
    	</target>
    
    	<target name="main" depends="last, first, zero">
    	</target>
    
    </project>



TODO
====

- [DONE] db configuration
- [DONE] create database version's table
- [DONE] insert the first record
- [DONE] check the database version
- [DONE] get script to run from the current version (db) and the wanted or last, depending on the command line parameter
- [DONE] run db:migrate (upgrade and downgrade)
- [DONE] run db:new
- [DONE] run .sqls files 
  - [DONE] ";" is the break point for the end of the statement
- [DONE] back to a specific version
- [DONE] go through a specific version
- [DONE] get to know the version of the db
- [DONE] start migration (up) from the version.version +1 til the last one

- identify duplicate sequence of script files.
- test rollback
- scripts inside meta-inf (or package with all dependencies including scripts)



