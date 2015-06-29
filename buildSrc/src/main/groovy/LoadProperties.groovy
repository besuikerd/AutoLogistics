import org.apache.tools.ant.filters.ReplaceTokens
import org.gradle.api.*;
import org.gradle.language.jvm.tasks.ProcessResources

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