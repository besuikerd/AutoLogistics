import org.gradle.api.*;

class LoadPropertiesPlugin implements Plugin<Project>{
    @Override
    void apply(Project project) {
        project.extensions.add('loadProperties', {File file ->
            def properties = new Properties()
            if(file.exists()){

                return file.withReader{ reader ->
                    properties.load(reader)
                    return properties
                }
            } else{
                println("could not load properties, file not found: ${file.getAbsolutePath()}")
            }
            return properties
        })
    }
}