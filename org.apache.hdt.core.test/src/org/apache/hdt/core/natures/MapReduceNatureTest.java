package org.apache.hdt.core.natures;

import static org.junit.Assert.assertTrue;

import org.apache.hdt.test.utils.TestUtils;
import org.eclipse.core.resources.IProject;
import org.eclipse.jdt.core.IClasspathEntry;
import org.eclipse.jdt.core.IJavaProject;
import org.junit.Test;

public class MapReduceNatureTest {

	@Test
	public void testConfigure() throws Exception {
		IProject project = TestUtils.createProject();
		IJavaProject javaProject = TestUtils.setJavaNature(project);
		MapReduceNature nature = new MapReduceNature();
		nature.setProject(project);
		nature.configure();
		boolean classpathStatus = false;
		for (IClasspathEntry classpathEntry : javaProject.getRawClasspath()) {
			String path = classpathEntry.getPath().toOSString();
			classpathStatus = classpathStatus
					|| path.contains("org.apache.hadoop.eclipse");
		}
		assertTrue("Classpath did not contain HDT libs ", classpathStatus);

	}
}
