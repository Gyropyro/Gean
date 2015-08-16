package uk.co.samholder.gean.maven.gean.maven.plugin;

/*
 * Copyright 2001-2005 The Apache Software Foundation.
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
import java.io.File;
import java.io.IOException;
import java.util.List;
import org.apache.maven.plugin.AbstractMojo;
import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.plugins.annotations.LifecyclePhase;
import org.apache.maven.plugins.annotations.Mojo;
import org.apache.maven.plugins.annotations.Parameter;
import uk.co.samholder.games.gean.Gean;

/**
 *
 *
 */
@Mojo(name = "gean", defaultPhase = LifecyclePhase.GENERATE_SOURCES)
public class GeanMojo
        extends AbstractMojo {

    @Parameter(defaultValue = "target/generated-sources")
    private File outputDir;

    @Parameter(defaultValue = "src/main/gean")
    private File inputDir;

    @Override
    public void execute()
            throws MojoExecutionException {
        if (!inputDir.exists()) {
            throw new RuntimeException("input directory not found!");
        }
        if (!inputDir.isDirectory()) {
            throw new RuntimeException("inputDir is not a directory!");
        }
        System.out.println("Attempting to generate gean sources.");
        System.out.println("Using input directory: " + inputDir.getPath());
        System.out.println("Using output directory: " + outputDir.getPath());
        List<File> inFiles = FileUtil.getDirectoryFilesRecursive(inputDir);

        Gean gean = new Gean();
        try {
            gean.processFiles(inFiles, outputDir);
        } catch (IOException ex) {
            throw new RuntimeException("Input/output error occured!", ex);
        }
        System.out.println("Finished generating gean sources.");
    }

}
