# baselib
baselib
使用方式

    allprojects {
        repositories {
            google()
            jcenter()
            mavenCentral()
            maven { url 'https://jitpack.io' }
            maven { url 'http://maven.aliyun.com/nexus/content/groups/public/' }
        }
    }


    dependencies {
	        implementation 'com.github.hujiayi1002:baselib:Tag'
    }
