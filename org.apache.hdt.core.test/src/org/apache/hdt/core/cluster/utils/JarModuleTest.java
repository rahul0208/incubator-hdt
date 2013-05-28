package org.apache.hdt.core.cluster.utils;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import java.io.File;

import org.apache.hdt.test.utils.TempProjectRule;
import org.junit.Rule;
import org.junit.Test;

public class JarModuleTest {
	@Rule
	public TempProjectRule projectRule = new TempProjectRule();

	@Test
	public void shouldNotCreateJarForAProject() throws Exception {
		File file = JarModule.createJarPackage(projectRule.getProject());
		assertNull(file);
	}

	@Test
	public void shouldCreateJarForAMainClass() throws Exception {
		File file = JarModule.createJarPackage(projectRule.getMainClass()
				.getResource());
		assertNotNull(file);
		assertTrue(file.getAbsolutePath().endsWith(".jar"));
	}
}
