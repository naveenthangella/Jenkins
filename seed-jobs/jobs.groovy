job('example1') {
    label('JAVA')
    scm {
        git {
            remote {
                url('https://github.com/javabycode/spring-boot-maven-example-helloworld.git')
            }
        }
    }
    steps {
        shell('mvn clean package')
    }
}