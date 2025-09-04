## Teabreak Gradle Plugin Series
The TeaBreak Gradle Plugin is a series of Gradle plugins that includes several subsets.

### teabreak-codegen(id : io.github.ultimark.sd.teabreak.codegen)
teabreak-codegen is a code generation plugin based on Doma CodeGen. It automatically generates Entities and DAOs based on the database schema. The generated Java class names are automatically determined based on the table names. teabreak-codegen treats the first two characters of the table name as a prefix when generating Java classes. This means, for example, that a table named M_USER will generate an Entity class named User.java and a DAO class named UserDao.java.

- Apply teabreak-codegen to the plugins block in your build.gradle.

	```
	plugins {
		id 'io.github.ultimark.sd.teabreak.codegen' version '1.0.0'
	}
	```

- Specify the JDBC driver in the dependencies block.

  	```
	dependencies {
		domaCodeGen 'org.postgresql:postgresql:42.7.7'
	}
	```
	
- Configure the plugin settings using the domaCodeGen block.

	```
	domaCodeGen {
		register("postgresql") {
			url.set("jdbc:postgresql://localhost:5432/database-name")
			user.set("username")
			password.set("password")
			templateDir.set(file("src/main/resources/codegen-template"))
			
			entity {
				packageName.set("package.to.your.destination.directory")
				useAccessor.set(true)
				useListener.set(false)
				showDbComment.set(true)
			}
			
			dao {
				packageName.set("package.to.your.destination.directory")
			}
		}
	}
 	```

By configuring the above settings, tasks such as domaCodeGenPostgresqlAll, domaCodeGenPostgresqlEntity, and domaCodeGenPostgresqlDao become available.For details on various configuration methods, refer to the [Doma CodeGen reference](https://docs.domaframework.org/en/stable/codegen/#configuration-reference).


### teabreak-ezweb(id : io.github.ultimark.sd.teabreak.ezweb)
teabreak-ezweb is a Gradle plugin for deploying mockup HTML and static resources (CSS, JavaScript, image files, etc.) created as mockups to resources managed by Spring Boot. By default, it deploys mockups located under src/main/resources/mockup to either src/main/resources/templates or src/main/resources/static. Since editing web resources like HTML, CSS, and JavaScript is the primary focus under src/main/resources/mockup, we recommend the following development workflow: Edit resources in this directory using an editor like VSCode, deploy them to the Spring Boot directory using this plugin, and then integrate them with Java source code developed in Eclipse or similar tools to assemble the application.

- Apply teabreak-ezweb to the plugins block in your build.gradle.

	```
	plugins {
		id 'io.github.ultimark.sd.teabreak.ezweb' version '1.0.0'
	}
	```

By making the above settings, the two tasks deployHtml and deployStaticResources will become executable.
To change the storage location or deployment destination for mockups, please configure the following in your build.gradle.

	```
	deployHtml {
		sourceDir 'path/to/your/mockup/files'
		htmlDestDir 'path/to/your/destination/directory'
	}
 
	deployStaticResources {
		sourceDir 'path/to/your/mockup/files'
		resourceDestDir 'path/to/your/destination/directory'
	}
 	```
