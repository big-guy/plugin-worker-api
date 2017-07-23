/*
 * Copyright 2015 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.gradle.sample

import org.gradle.testkit.runner.GradleRunner
import static org.gradle.testkit.runner.TaskOutcome.*
import org.junit.Rule
import org.junit.rules.TemporaryFolder
import spock.lang.Specification

class BuildLogicFunctionalTest extends Specification {
    @Rule final TemporaryFolder testProjectDir = new TemporaryFolder()
    File buildFile

    def setup() {
        buildFile = testProjectDir.newFile('build.gradle')
    }

    def "extracts findbugs version"() {
        given:
        buildFile << """
            plugins {
                id 'sample'
            }
        """

        when:
        def result = GradleRunner.create()
            .withProjectDir(testProjectDir.root)
            .withArguments('extractVersion')
            .withPluginClasspath()
            .build()

        then:
        result.task(":extractVersion").outcome == SUCCESS
        new File(testProjectDir.root, "build/version.txt").text == "2.0.1"
    }

    def "extracts findbugs version when version is specified"() {
        given:
        buildFile << """
            plugins {
                id 'sample'
            }

            dependencies {
                worker 'com.google.code.findbugs:findbugs:3.0.1'
            }
        """

        when:
        def result = GradleRunner.create()
            .withProjectDir(testProjectDir.root)
            .withArguments('extractVersion')
            .withPluginClasspath()
            .build()

        then:
        result.task(":extractVersion").outcome == SUCCESS
        new File(testProjectDir.root, "build/version.txt").text == "3.0.1"
    }
}
