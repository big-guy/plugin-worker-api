package org.gradle.sample

import groovy.transform.CompileStatic
import javax.inject.Inject
import edu.umd.cs.findbugs.Version

@CompileStatic
class UnitOfWork implements Runnable {
    private final File outputFile

    @Inject
    UnitOfWork(File outputFile) {
        this.outputFile = outputFile
    }

    void run() {
        outputFile.text = edu.umd.cs.findbugs.Version.getReleaseWithDateIfDev()
    }
}
