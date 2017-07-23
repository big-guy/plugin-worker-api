package org.gradle.sample

import org.gradle.api.*
import org.gradle.api.file.*
import org.gradle.api.tasks.*
import org.gradle.workers.*

import groovy.transform.CompileStatic

import javax.inject.Inject

@CompileStatic
class WorkerTask extends DefaultTask {
    private final WorkerExecutor workerExecutor

    @Classpath
    FileCollection classpath

    @OutputFile
    File outputFile

    // The WorkerExecutor will be injected by Gradle at runtime
    @Inject
    public WorkerTask(WorkerExecutor workerExecutor) {
        this.workerExecutor = workerExecutor
    }

    @TaskAction
    void generate() {
        workerExecutor.submit(UnitOfWork) { WorkerConfiguration config ->
            config.isolationMode = IsolationMode.PROCESS
            config.classpath = classpath
            config.params outputFile
        }
    }
}