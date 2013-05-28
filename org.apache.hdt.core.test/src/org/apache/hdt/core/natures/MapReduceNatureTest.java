package org.apache.hdt.core.natures;

import static org.junit.Assert.assertTrue;

import org.apache.hdt.test.utils.TempProjectRule;
import org.eclipse.jdt.core.IClasspathEntry;
import org.junit.Rule;
import org.junit.Test;

public class MapReduceNatureTest {
	@Rule
	public TempProjectRule projectRule = new TempProjectRule();

	@Test
	public void configureShouldAddLibsToClasspath() throws Exception {
		MapReduceNature nature = new MapReduceNature();
		nature.setProject(projectRule.getProject());
		nature.configure();
		boolean classpathStatus = false;
		for (IClasspathEntry classpathEntry : projectRule.getJavaProject()
				.getRawClasspath()) {
			String path = classpathEntry.getPath().toOSString();
			classpathStatus = classpathStatus
					|| path.contains("org.apache.hadoop.eclipse");
		}
		assertTrue("Classpath did not contain HDT libs ", classpathStatus);

	}
}
