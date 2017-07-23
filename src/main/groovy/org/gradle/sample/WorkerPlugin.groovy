package org.gradle.sample

import org.gradle.api.*
import groovy.transform.CompileStatic
import org.gradle.api.artifacts.DependencySet

@CompileStatic
class WorkerPlugin implements Plugin<Project> {
    void apply(Project project) {
        project.repositories {
            mavenCentral()
        }

        def configuration = project.configurations.create("worker")
        configuration.defaultDependencies { DependencySet dependencies ->
            dependencies.add(project.dependencies.create("com.google.code.findbugs:findbugs:2.0.1"))
        }

        project.tasks.create("extractVersion", WorkerTask) { WorkerTask task ->
            task.classpath = configuration
            task.outputFile = new File(project.buildDir, "version.txt")
        }
    }
}